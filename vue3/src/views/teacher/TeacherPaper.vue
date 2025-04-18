<script setup>
import { selectPaperDataAPI, addPaperAPI, updatePaperAPI, deleteByIdAPI } from '@/api/paper'
import { selectCourseDataAPI, addCourseAPI } from '@/api/course'
import { selectLanguageDataAPI, addLanguageAPI } from '@/api/language'
import { selectTechnologyDataAPI, addTechnologyAPI } from '@/api/technology'
import { ref } from 'vue'
import { useAccountStore } from '@/stores/account'
import { ElMessage } from 'element-plus'
import { onMounted } from 'vue'
import { reactive } from 'vue'
import PaperTeacherShower from '../component/PaperTeacherShower.vue'
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
// 所有select添加选项的状态管理
const addStatusManager = reactive({
  courseAdding: false,
  languageAdding: false,
  technologyAdding: false,
})
const addOptionText = reactive({
  courseOption: '',
  languageOption: '',
  technologyOption: '',
})
const loadPaperData = () => {
  selectPaperDataAPI(null, null, accountInfo.accountInfo.id).then((res) => {
    if (res.code === '200') {
      paperData.value = res.data.list
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
// 创建新的论文
const createNewPaper = () => {
  paperForm.value = {}
  data.dialogVisiable = true
}
const submitPaper = () => {
  paperForm.value.id ? update() : add()
}
const update = () => {
  updatePaperAPI(paperForm.value).then((res) => {
    if (res.code === '200') {
      ElMessage.success('修改成功')
      data.dialogVisiable = false
      loadPaperData()
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const add = () => {
  paperForm.value.teacherId = accountInfo.accountInfo.id
  addPaperAPI(paperForm.value).then((res) => {
    if (res.code === '200') {
      ElMessage.success('创建成功')
      data.dialogVisiable = false
      loadPaperData()
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const handleEdit = (paperId) => {
  data.dialogVisiable = true
  paperForm.value = JSON.parse(
    JSON.stringify(paperData.value.find((paper) => paper.id === paperId)),
  )
  paperForm.value.courseIds = paperForm.value.courses.map((course) => course.id)
  paperForm.value.languageIds = paperForm.value.languages.map((language) => language.id)
  paperForm.value.technologyIds = paperForm.value.technologies.map((technology) => technology.id)
}
const handleDel = (paperId) => {
  deleteByIdAPI(paperId).then((res) => {
    if (res.code === '200') {
      ElMessage.success('删除成功')
      loadPaperData()
    } else {
      ElMessage.error(res.msg)
    }
  })
}
/** 对于所有select的文本添加操作 */
const confirmCourse = () => {
  if (addOptionText.courseOption !== '') {
    const course = {
      name: addOptionText.courseOption,
    }
    addCourseAPI(course).then((res) => {
      if (res.code === '200') {
        loadCourseData()
      } else {
        ElMessage.error(res.msg)
      }
      addOptionText.courseOption = ''
      addStatusManager.courseAdding = false
    })
  }
}
const cancelCourse = () => {
  addOptionText.courseOption = ''
  addStatusManager.courseAdding = false
}
const confirmLanguage = () => {
  if (addOptionText.languageOption !== '') {
    const language = {
      name: addOptionText.languageOption,
    }
    addLanguageAPI(language).then((res) => {
      if (res.code === '200') {
        loadLanguageData()
      } else {
        ElMessage.error(res.msg)
      }
      addOptionText.languageOption = ''
      addStatusManager.languageAdding = false
    })
  }
}
const cancelLanguage = () => {
  addOptionText.languageOption = ''
  addStatusManager.languageAdding = false
}
const confirmTechnology = () => {
  if (addOptionText.technologyOption !== '') {
    const technology = {
      name: addOptionText.technologyOption,
    }
    addTechnologyAPI(technology).then((res) => {
      if (res.code === '200') {
        loadTechnologyData()
      } else {
        ElMessage.error(res.msg)
      }
      addOptionText.technologyOption = ''
      addStatusManager.technologyAdding = false
    })
  }
}
const cancelTechnology = () => {
  addOptionText.technologyOption = ''
  addStatusManager.technologyAdding = false
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
    <el-empty v-if="paperData.total === 0" description="还没有创建任何论文"
      ><el-button type="primary" @click="createNewPaper">去创建</el-button></el-empty
    >
    <div class="paper-data-show" v-else>
      <div class="paper-show-box">
        <paper-teacher-shower
          v-for="paper in paperData"
          :key="paper.id"
          :paper="paper"
          @onEdit="handleEdit"
          @onDel="handleDel"
        >
        </paper-teacher-shower>
      </div>
      <div class="create-button">
        <el-button type="primary" @click="createNewPaper">创建新的论文</el-button>
      </div>
    </div>
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
          <el-form-item prop="courseIds" label="前置课程要求">
            <el-select v-model="paperForm.courseIds" placeholder="请选择课程要求" multiple>
              <el-option
                v-for="course in courseData"
                :key="course.id"
                :value="course.id"
                :label="course.name"
              ></el-option>
              <template #footer>
                <el-button
                  v-if="!addStatusManager.courseAdding"
                  @click="addStatusManager.courseAdding = true"
                  text
                  bg
                  size="small"
                  >添加新的课程要求</el-button
                >
                <template v-else>
                  <el-input
                    v-model="addOptionText.courseOption"
                    placeholder="请输入新的课程要求"
                    class="add-input"
                    size="small"
                    clearable
                  ></el-input>
                  <el-button @click="confirmCourse" type="primary" size="small">提交</el-button>
                  <el-button @click="cancelCourse" type="warning" size="small">取消</el-button>
                </template>
              </template>
            </el-select>
          </el-form-item>
          <el-form-item prop="languageIds" label="编程语言要求">
            <el-select v-model="paperForm.languageIds" placeholder="请选择语言要求" multiple>
              <el-option
                v-for="language in languageData"
                :key="language.id"
                :value="language.id"
                :label="language.name"
              ></el-option>
              <template #footer>
                <el-button
                  v-if="!addStatusManager.languageAdding"
                  @click="addStatusManager.languageAdding = true"
                  text
                  bg
                  size="small"
                  >添加新的课程要求</el-button
                >
                <template v-else>
                  <el-input
                    v-model="addOptionText.languageOption"
                    placeholder="请输入新的课程要求"
                    class="add-input"
                    size="small"
                    clearable
                  ></el-input>
                  <el-button @click="confirmLanguage" type="primary" size="small">提交</el-button>
                  <el-button @click="cancelLanguage" type="warning" size="small">取消</el-button>
                </template>
              </template>
            </el-select></el-form-item
          >
          <el-form-item prop="technologyIds" label="所需技术要求">
            <el-select v-model="paperForm.technologyIds" placeholder="请选择技术要求" multiple>
              <el-option
                v-for="technology in technologyData"
                :key="technology.id"
                :value="technology.id"
                :label="technology.name"
              ></el-option>
              <template #footer>
                <el-button
                  v-if="!addStatusManager.technologyAdding"
                  @click="addStatusManager.technologyAdding = true"
                  text
                  bg
                  size="small"
                  >添加新的课程要求</el-button
                >
                <template v-else>
                  <el-input
                    v-model="addOptionText.technologyOption"
                    placeholder="请输入新的课程要求"
                    class="add-input"
                    size="small"
                    clearable
                  ></el-input>
                  <el-button @click="confirmTechnology" type="primary" size="small">提交</el-button>
                  <el-button @click="cancelTechnology" type="warning" size="small">取消</el-button>
                </template>
              </template></el-select
            ></el-form-item
          >
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
.paper-data-show {
  width: 100%;
}
.add-input {
  margin: 5px 0px;
}
</style>
