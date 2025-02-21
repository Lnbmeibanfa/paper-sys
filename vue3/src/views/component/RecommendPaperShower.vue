<script setup>
import router from '@/router'

defineProps({
  paper: Object,
})
const toDetail = (id) => {
  router.push(`/student/detail?id=${id}`)
}
</script>

<template>
  <div class="recommend-paper-shower" @click="toDetail(paper.id)">
    <div class="name">{{ paper.name }}</div>
    <div class="other-info">
      <div class="teacher">
        <div class="img-box"><img :src="paper.teacherAvatar" alt="" /></div>
        <div class="teacher-name">{{ paper.teacherName }}</div>
      </div>
      <div class="address">{{ paper.teacherAddress }}</div>
    </div>
  </div>
</template>

<style scoped>
.recommend-paper-shower {
  background-color: #fff;
  border-radius: 5px;
  padding: 10px;
  width: 100%;
  margin: 10px 0px;
  cursor: pointer;
}
.recommend-paper-shower:hover {
  background-color: #f7f7f7;
}
.other-info {
  display: flex;
  color: #666;
  justify-content: space-between;
  align-items: center;
}
.other-info:hover {
  color: #409eff;
}
.teacher {
  display: flex;
  align-items: center;
}

.teacher .img-box img {
  border-radius: 5px;
  width: 28px;
  height: 28px;
  margin-right: 10px;
}
.name {
  text-align: left;
  font-size: 16px;
  margin: 10px 0px;
}
.name:hover {
  color: #409eff;
}
</style>
