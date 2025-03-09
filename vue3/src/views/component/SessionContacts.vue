<script setup>
import { ref } from 'vue'
const data = defineProps({
  session: Object,
})
const emit = defineEmits(['enterSession'])
const isStudent = ref(data.session.userRole === 'STUDENT')
const enterSession = () => {
  if (isStudent.value) {
    emit('enterSession', data.session)
  } else {
    emit('enterSession', data.session)
  }
}
</script>

<template>
  <div @click="enterSession">
    <div class="session-contacts" v-if="isStudent">
      <div class="img-box">
        <img :src="session.teacherAvatar" alt="" />
      </div>
      <div class="info-box">
        <div>{{ session.teacherName }}</div>
        <div>{{ session.teacherResearchDirection }}</div>
      </div>
    </div>
    <div class="session-contacts" v-else>
      <div class="img-box">
        <img :src="session.studentAvatar" alt="" />
      </div>
      <div class="info-box">
        <div>{{ session.studentName }}</div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.session-contacts {
  border-radius: 10px;
  padding: 15px;
  display: flex;
  margin-bottom: 20px;
  border: 1px solid #cfcece;
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
  border-radius: 50%;
}
.info-box {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
</style>
