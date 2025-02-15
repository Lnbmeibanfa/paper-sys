<script setup>
import { ref } from 'vue'
import { selectCourseDataAPI } from '@/api/course'
import { selectLanguageDataAPI } from '@/api/language'
import { selectTechnologyDataAPI } from '@/api/technology'
import { ElMessage } from 'element-plus'
import { onMounted } from 'vue'

const keyword = ref('')
const courseData = ref(null)
const languageData = ref(null)
const technologyData = ref(null)
const search = () => {}
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
  selectTechnologyDataAPI().then((res) => {
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
// 处理筛选条件
const filterCondition = ref({})
onMounted(() => {
  loadCourseData()
  loadLanguageData()
  loadTechnologyData()
})
</script>

<template>
  <div class="student-home">
    <div class="search-box">
      <div class="search-item">
        <el-input
          v-model="keyword"
          placeholder="搜索教师姓名，论文名"
          size="large"
          clearable
          @keydown.enter="search"
        ></el-input>
        <div class="blank"></div>
        <el-button type="primary" size="large" @click="search">搜索</el-button>
      </div>
      <div class="filter-box">
        <div class="filter-item">
          <div class="course filter">
            <el-dropdown>
              <span class="el-dropdown-link">
                前置课程
                <div>{{}}</div>
                <el-icon class="el-icon--right">
                  <arrow-down />
                </el-icon>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item
                    v-for="course in courseData"
                    :key="course.id"
                    @click="toggleCourse(course.id)"
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
                <el-icon class="el-icon--right">
                  <arrow-down />
                </el-icon>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item v-for="language in languageData" :key="language.id">{{
                    language.name
                  }}</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
          <div class="technology filter">
            <el-dropdown>
              <span class="el-dropdown-link">
                相关技术
                <el-icon class="el-icon--right">
                  <arrow-down />
                </el-icon>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item v-for="technology in technologyData" :key="technology.id">{{
                    technology.name
                  }}</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </div>
        <div class="clear-filter"><div>清除筛选条件</div></div>
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
  padding: 5px;
  background-color: #f8f8f8;
  margin-right: 10px;
}
.clear-filter {
  display: flex;
  align-items: center;
}
.blank {
  width: 20px;
}
</style>
