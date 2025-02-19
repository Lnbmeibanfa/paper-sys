<script setup>
defineProps({
  paper: Object,
})
</script>

<template>
  <div class="paper_student">
    <div class="left">
      <div class="title">{{ paper.name }}</div>
      <div class="tags">
        <el-tag v-for="course in paper.courses" :key="course.id">{{ course.name }}</el-tag>
      </div>
      <div class="tags">
        <el-tag v-for="language in paper.languages" :key="language.id">{{ language.name }}</el-tag>
      </div>
      <div class="tags">
        <el-tag v-for="technology in paper.technologys" :key="technology.id">{{
          technology.name
        }}</el-tag>
      </div>
      <div class="resource_type">{{ paper.resource }} | {{ paper.type }}</div>
    </div>
    <div class="right">
      <div class="teacher_info">
        <div class="img-box"><img :src="paper.teacherAvatar" /></div>
        <div class="name">{{ paper.teacherName }}</div>
        <div class="name">{{ paper.teacherResearchDirection }}</div>
      </div>
      <div class="requirment">{{ paper.requirment }}</div>
      1
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
  font-size: 18px;
}
.right {
  width: 400px;
}
.img-box img {
  width: 50px;
  height: 50px;
}
</style>
