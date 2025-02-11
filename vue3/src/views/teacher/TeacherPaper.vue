<script setup>
import { selectPaperDataAPI, addPaperAPI } from '@/api/paper'
import { selectCourseDataAPI } from '@/api/course'
import { selectLanguageDataAPI } from '@/api/language'
import { selectTechnologyDataAPI } from '@/api/technology'
import { ref } from 'vue'
import { useAccountStore } from '@/stores/account'
import { ElMessage } from 'element-plus'
import { onMounted } from 'vue'
import { reactive } from 'vue'
const data = reactive({
  dialogVisiable: false,
})
// 初始信息加载
const accountInfo = useAccountStore()
const paperData = ref({})
const paperForm = ref({})
const courseData = ref({})
const languageData = ref({})
const technologyData = ref({})
const loadPaperData = () => {
  selectPaperDataAPI(accountInfo.accountInfo.id).then((res) => {
    if (res.code === '200') {
      paperData.value = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}
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
// 创建新的论文
const createNewPaper = () => {
  paperForm.value = {}
  data.dialogVisiable = true
}

const submitPaper = () => {
  paperForm.value.teacherId = accountInfo.accountInfo.id
  addPaperAPI(paperForm.value).then((res) => {
    if (res.code === '200') {
      ElMessage.success('创建成功')
    } else {
      ElMessage.error(res.msg)
    }
  })
}
onMounted(() => {
  loadPaperData()
  loadCourseData()
  loadLanguageData()
  loadTechnologyData()
})
</script>

<template>
  <div class="paper-box card">
    <!-- v-if="paperData.total === 0" description="还没有创建任何论文" -->
    <el-empty><el-button type="primary" @click="createNewPaper">去创建</el-button></el-empty>
  </div>

  <!-- dialog -->
  <el-dialog v-model="data.dialogVisiable" title="创建新的论文" width="800">
    <div class="basic-info">
      <div class="title">1.论文基本信息</div>
      <div class="form-box">
        <el-form :model="paperForm" label-width="70px">
          <el-form-item prop="name" label="论文名称">
            <el-input v-model="paperForm.name" placeholder="请输入论文名称"> </el-input>
          </el-form-item>
          <el-form-item prop="resource" label="题目来源">
            <el-select v-model="paperForm.resource" placeholder="请选择题目来源">
              <el-option value="教学" label="教学"></el-option>
              <el-option value="教学科研" label="教学科研"></el-option>
              <el-option value="社会生产实践" label="社会生产实践"></el-option>
              <el-option value="其他" label="其他"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item prop="type" label="题目类型">
            <el-select v-model="paperForm.type" placeholder="请选择题目类型">
              <el-option value="程序设计与实现类" label="程序设计与实现类"></el-option>
              <el-option value="算法研究与应用类" label="算法研究与应用类"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item prop="content" label="论文内容">
            <el-input
              v-model="paperForm.content"
              :rows="5"
              type="textarea"
              maxlength="5000"
              show-word-limit
              placeholder="请输入论文内容"
            ></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div class="title">2.论文要求</div>
      <div class="form-box">
        <el-form :model="paperForm" label-width="100px">
          <el-form-item prop="studentGroup" label="面向学生">
            <el-select v-model="paperForm.studentGroup" placeholder="请选择面向学生">
              <el-option value="计算机科学系" label="计算机科学系"></el-option>
              <el-option value="国际学院" label="国际学院"></el-option>
              <el-option value="计算机科学系/国际学院" label="计算机科学系/国际学院"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item prop="gpa" label="绩点大于">
            <el-input-number v-model="paperForm.gpa" :min="1" :max="5" :step="0.5" />
          </el-form-item>
          <el-form-item prop="coureseIds" label="前置课程要求">
            <el-select v-model="paperForm.courseIds" placeholder="请选择课程要求" multiple>
              <el-option
                v-for="course in courseData"
                :key="course.id"
                :value="course.id"
                :label="course.name"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item prop="languageIds" label="编程语言要求">
            <el-select v-model="paperForm.languageIds" placeholder="请选择语言要求" multiple>
              <el-option
                v-for="language in languageData"
                :key="language.id"
                :value="language.id"
                :label="language.name"
              ></el-option> </el-select
          ></el-form-item>
          <el-form-item prop="technologyIds" label="所需技术要求">
            <el-select v-model="paperForm.technologyIds" placeholder="请选择技术要求" multiple>
              <el-option
                v-for="technology in technologyData"
                :key="technology.id"
                :value="technology.id"
                :label="technology.name"
              ></el-option> </el-select
          ></el-form-item>
          <el-form-item prop="requirement" label="其他要求">
            <el-input
              v-model="paperForm.requirement"
              :rows="5"
              type="textarea"
              maxlength="5000"
              show-word-limit
              placeholder="请输入其他要求"
            ></el-input
          ></el-form-item>
        </el-form>
      </div>
    </div>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="data.dialogVisiable = false">取消</el-button>
        <el-button type="primary" @click="submitPaper">确认</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<style scoped>
.paper-box {
  padding: 50px;
  width: 1300px;
  height: 100%;
  margin: 0 auto;
  display: flex;
}
.el-empty {
  width: 100%;
  height: 100%;
}
.title {
  font-size: 20px;
}
.form-box {
  padding: 20px;
}
</style>
