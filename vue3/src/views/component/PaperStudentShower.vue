<script setup>
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'

const router = useRouter()
const paperData = defineProps({
  paper: Object,
  toChat: {
    type: Boolean,
    default: false,
  },
})
const toDetail = () => {
  if (paperData.paper.studentId != null) {
    ElMessage.warning('论文已被选择请浏览其他论文')
  } else {
    router.push(`/student/detail?id=${paperData.paper.id}`)
  }
}
const chat = () => {
  router.push(`/student/chat?paperId=${paperData.paper.id}&teacherId=${paperData.paper.teacherId}`)
}
</script>

<template>
  <div class="paper_student">
    <div class="left">
      <div class="title" @click="toDetail">{{ paperData.paper.name }}</div>
      <div class="tags">
        <el-tag v-for="course in paper.courses" :key="course.id">{{ course.name }}</el-tag>
      </div>
      <div class="tags">
        <el-tag v-for="language in paper.languages" :key="language.id">{{ language.name }}</el-tag>
      </div>
      <div class="tags">
        <el-tag v-for="technology in paper.technologies" :key="technology.id">{{
          technology.name
        }}</el-tag>
      </div>
      <div class="resource_type">{{ paper.resource }} | {{ paper.type }}</div>
    </div>
    <div class="right">
      <div class="content">
        <div class="teacher_info">
          <div class="img-box"><img :src="paper.teacherAvatar" /></div>
          <div class="info">
            <div class="name">{{ paper.teacherName }}</div>
            <div class="direction">{{ paper.teacherResearchDirection }}</div>
          </div>
        </div>
        <div class="requirment">{{ paper.requirement }}</div>
      </div>
      <div class="button" v-if="toChat">
        <el-button type="primary" @click="chat" :disabled="paperData.paper.studentId != null"
          >发起沟通</el-button
        >
      </div>
    </div>
  </div>
</template>

<style scoped>
.paper_student {
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  padding: 20px;
  width: 100%;
  display: flex;
  justify-content: space-between;
  margin: 10px 0px;
}
.left {
  flex: 1;
}
.left .title {
  cursor: pointer;
  font-size: 18px;
}
.right {
  display: flex;
  margin-top: 10px;
  width: 400px;
}
.right .info {
  padding: 0px 10px;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
}
.img-box img {
  border-radius: 10px;
  width: 75px;
  height: 75px;
}
.tags {
  margin: 10px 0px;
}
.el-tag {
  margin-right: 5px;
}
.teacher_info {
  display: flex;
}
.requirment {
  margin: 15px 0px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.button {
  padding: 20px 20px;
}
</style>
