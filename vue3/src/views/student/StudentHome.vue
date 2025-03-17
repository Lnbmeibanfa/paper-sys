<script setup>
import { ref } from 'vue'
import { selectCourseDataAPI } from '@/api/course'
import { selectLanguageDataAPI } from '@/api/language'
import { selectTechnologyDataAPI } from '@/api/technology'
import { selectPaperByFilter } from '@/api/paper'
import PaperStudentShower from '@/views/component/PaperStudentShower.vue'
import { ElMessage } from 'element-plus'
import { onMounted } from 'vue'
import { useAccountStore } from '@/stores/account'

const accountInfo = useAccountStore()
const courseData = ref(null)
const languageData = ref(null)
const technologyData = ref(null)
const paperData = ref(null)
const onlyShowCollect = ref(false)
// 数据加载
const loadLanguageData = () => {
  selectLanguageDataAPI().then((res) => {
    if (res.code === '200') {
      languageData.value = res.data.list
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const loadTechnologyData = () => {
  selectTechnologyDataAPI(1, 100).then((res) => {
    if (res.code === '200') {
      technologyData.value = res.data.list
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const loadCourseData = () => {
  selectCourseDataAPI().then((res) => {
    if (res.code === '200') {
      courseData.value = res.data.list
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const loadPaperData = () => {
  selectPaperByFilter(filterCondition.value).then((res) => {
    if (res.code === '200') {
      paperData.value = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}
// 处理筛选条件
const filterCondition = ref({
  keyword: '',
  courseIds: [],
  languageIds: [],
  technologyIds: [],
})
const toggleCourse = (courseId) => {
  const index = filterCondition.value.courseIds.indexOf(courseId)
  index === -1
    ? filterCondition.value.courseIds.push(courseId)
    : filterCondition.value.courseIds.splice(index, 1)
  loadPaperData()
}
const toggleLanguage = (languageId) => {
  const index = filterCondition.value.languageIds.indexOf(languageId)
  index === -1
    ? filterCondition.value.languageIds.push(languageId)
    : filterCondition.value.languageIds.splice(index, 1)
  loadPaperData()
}
const toggleTechnology = (technologyId) => {
  const index = filterCondition.value.technologyIds.indexOf(technologyId)
  index === -1
    ? filterCondition.value.technologyIds.push(technologyId)
    : filterCondition.value.technologyIds.splice(index, 1)
  loadPaperData()
}
const handleCollectChange = () => {
  if (onlyShowCollect.value) {
    filterCondition.value.studentId = accountInfo.accountInfo.id
  } else {
    filterCondition.value.studentId = null
  }
  loadPaperData()
}
const clearFilter = () => {
  filterCondition.value = {
    courseIds: [],
    languageIds: [],
    technologyIds: [],
  }
  loadPaperData()
}
onMounted(() => {
  loadCourseData()
  loadLanguageData()
  loadTechnologyData()
  loadPaperData()
})
</script>

<template>
  <div class="student-home">
    <div class="search-box">
      <div class="search-item">
        <el-input
          v-model="filterCondition.keyword"
          placeholder="搜索教师姓名，论文名"
          size="large"
          clearable
          @clear="loadPaperData"
          @keydown.enter="loadPaperData"
        ></el-input>
        <div class="blank"></div>
        <el-button type="primary" size="large" @click="loadPaperData">搜索</el-button>
      </div>
      <div class="filter-box">
        <div class="filter-item">
          <div class="course filter">
            <el-dropdown>
              <span class="el-dropdown-link">
                前置课程
                <div v-if="filterCondition.courseIds.length !== 0">
                  ({{ filterCondition.courseIds.length }})
                </div>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item
                    v-for="course in courseData"
                    :key="course.id"
                    @click="toggleCourse(course.id)"
                    :class="{ active: filterCondition.courseIds.includes(course.id) }"
                    >{{ course.name }}</el-dropdown-item
                  >
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
          <div class="language filter">
            <el-dropdown>
              <span class="el-dropdown-link">
                编程语言
                <div v-if="filterCondition.languageIds.length !== 0">
                  ({{ filterCondition.languageIds.length }})
                </div>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item
                    v-for="language in languageData"
                    :key="language.id"
                    @click="toggleLanguage(language.id)"
                    :class="{ active: filterCondition.languageIds.includes(language.id) }"
                    >{{ language.name }}</el-dropdown-item
                  >
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
          <div class="technology filter">
            <el-dropdown>
              <span class="el-dropdown-link">
                相关技术
                <div v-if="filterCondition.technologyIds.length !== 0">
                  ({{ filterCondition.technologyIds.length }})
                </div></span
              >
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item
                    v-for="technology in technologyData"
                    :key="technology.id"
                    @click="toggleTechnology(technology.id)"
                    :class="{ active: filterCondition.technologyIds.includes(technology.id) }"
                    >{{ technology.name }}</el-dropdown-item
                  >
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </div>
        <div class="clear-filter" @click="clearFilter"><div>清除筛选条件</div></div>
      </div>
      <div class="check-box">
        <el-checkbox
          v-model="onlyShowCollect"
          label="仅显示我的收藏"
          size="large"
          @change="handleCollectChange"
        />
      </div>
    </div>
    <div class="content-box">
      <div class="display-box">
        <paper-student-shower v-for="paper in paperData" :key="paper.id" :paper="paper" />
      </div>
    </div>
  </div>
</template>

<style scoped>
.search-box {
  padding: 20px;
  border-radius: 10px;
  background-color: #fff;
  width: 1200px;
  margin: 15px auto;
}
.content-box {
  padding: 20px;
  border-radius: 10px;
  background-color: #fff;
  width: 1200px;
  margin: 15px auto;
}
.search-item {
  display: flex;
}
.filter-box {
  display: flex;
  margin-top: 10px;
}
.filter-item {
  display: flex;
  flex: 1;
}
.filter {
  padding: 8px;
  background-color: #f8f8f8;
  margin-right: 10px;
  display: flex;
  align-items: center;
}
.clear-filter:hover {
  color: #79bbff;
}
.clear-filter {
  display: flex;
  align-items: center;
  cursor: pointer;
}
.blank {
  width: 20px;
}
.el-dropdown-link {
  display: flex;
}
::v-deep .active {
  color: #409eff;
}
</style>
