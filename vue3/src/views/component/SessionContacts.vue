<script setup>
import { computed } from 'vue'
import { ref } from 'vue'
const data = defineProps({
  session: Object,
})
const emit = defineEmits(['enterSession'])
const isStudent = ref(data.session.userRole === 'STUDENT')
const formatDate = computed(() => {
  const targetDate = new Date(data.session.lastActive)
  const currentDate = new Date()
  // 获取日期部分（忽略时间）
  const targetDateOnly = new Date(
    targetDate.getFullYear(),
    targetDate.getMonth(),
    targetDate.getDate(),
  )
  const currentDateOnly = new Date(
    currentDate.getFullYear(),
    currentDate.getMonth(),
    currentDate.getDate(),
  )
  // 判断时间关系
  const isToday = targetDateOnly.getTime() === currentDateOnly.getTime()
  const isYesterday =
    targetDateOnly.getTime() === new Date(currentDateOnly.getTime() - 24 * 60 * 60 * 1000).getTime()

  // 格式化输出
  if (isToday) {
    return targetDate.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit', hour12: false })
  } else if (isYesterday) {
    return '昨天'
  } else {
    return targetDate.toLocaleString([], {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit',
      hour12: false,
    })
  }
})
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
      <div class="last-active">{{ formatDate }}</div>
    </div>
    <div class="session-contacts" v-else>
      <div class="img-box">
        <img :src="session.studentAvatar" alt="" />
      </div>
      <div class="info-box">
        <div>{{ session.studentName }}</div>
      </div>
      <div class="last-active">{{ formatDate }}</div>
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
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.last-active {
  width: 80px;
  color: #999999;
}
</style>
