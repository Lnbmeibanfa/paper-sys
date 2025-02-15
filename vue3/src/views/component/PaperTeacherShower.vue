<script setup>
import { ElMessageBox } from 'element-plus'

defineProps({
  paper: Object,
})
const emit = defineEmits(['onEdit', 'onDel'])
const onEdit = (paperId) => {
  emit('onEdit', paperId)
}
const onDel = (paperId) => {
  ElMessageBox.confirm('确认删除？', '删除后不可恢复', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning',
  })
    .then(() => {
      emit('onDel', paperId)
    })
    .catch((err) => console.log(err))
}
</script>

<template>
  <div class="paper-teacher-shower">
    <el-descriptions :title="paper.name" size="large">
      <el-descriptions-item width="200" label="选题来源">{{ paper.resource }}</el-descriptions-item>
      <el-descriptions-item width="400" label="题目类型">{{ paper.type }}</el-descriptions-item>
      <el-descriptions-item width="200" label="面向学生">{{
        paper.studentGroup
      }}</el-descriptions-item>
      <el-descriptions-item :span="3" label="课程要求">
        <el-tag v-for="course in paper.courses" :key="course.id">{{ course.name }}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item :span="3" label="编程语言">
        <el-tag v-for="language in paper.languages" :key="language.id">{{ language.name }}</el-tag>
      </el-descriptions-item>
      <el-descriptions-item :span="3" label="编程技术">
        <el-tag v-for="technology in paper.technologies" :key="technology.id">{{
          technology.name
        }}</el-tag>
      </el-descriptions-item>
    </el-descriptions>
    <div class="operation-box">
      <div class="btn-box">
        <el-button type="primary" @click="onEdit(paper.id)">修改</el-button>
        <el-button type="danger" @click="onDel(paper.id)">删除</el-button>
      </div>
      <div class="choose-box">
        <el-tag v-if="paper.studentId === null">未被选择</el-tag>
        <el-tag v-else>已有学生选择</el-tag>
      </div>
    </div>
  </div>
</template>

<style scoped>
.paper-teacher-shower {
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  padding: 20px;
  width: 100%;
  display: flex;
  justify-content: space-between;
  margin: 10px 0px;
}
.name-box {
  font-size: 18px;
}
.tags-box {
  margin: 10px 0px;
}
/**elementUI样式更改 */
.el-tag {
  margin-right: 5px;
}
.right {
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
}
.operation-box {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
}
.choose-box {
  text-align: center;
}
</style>
