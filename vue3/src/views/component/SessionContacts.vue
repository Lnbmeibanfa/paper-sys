<script setup>
defineProps({
  teacher: Object,
})
</script>

<template>
  <div class="session-contacts">
    <div class="img-box">
      <img :src="teacher.avatar" alt="" />
    </div>
    <div class="info-box">
      <div>{{ teacher.name }}</div>
      <div>{{ teacher.researchDirection }}</div>
    </div>
  </div>
</template>

<style scoped>
.session-contacts {
  padding: 15px;
  display: flex;
  margin-bottom: 20px;
}
.session-contacts:hover {
  background-color: #f7f7f7;
}
.img-box {
  height: 60px;
}
.img-box img {
  width: 60px;
  height: 60px;
  margin-right: 10px;
}
.info-box {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
</style>
