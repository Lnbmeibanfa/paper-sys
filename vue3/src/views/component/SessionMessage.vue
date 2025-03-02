<!-- 聊天框中的每一条消息 -->
<script setup>
import { useAccountStore } from '@/stores/account'
import { computed } from 'vue'

const accountStore = useAccountStore()
const data = defineProps({
  message: Object,
})
const isFromMe = computed(() => data.message.sendRole === accountStore.accountInfo.role)
console.log(data.message.sendRole)
</script>
<template>
  <div class="session-message">
    <div class="message-box">
      <div :class="{ 'from-me': isFromMe, 'from-other': !isFromMe }" v-html="message.content"></div>
    </div>
  </div>
</template>

<style scoped>
.session-message {
  display: flex;
  justify-content: right;
}
.message-box {
  display: flex;
  flex-direction: column;
}
.from-me {
  display: inline;
  padding: 10px;
  border-top-left-radius: 10px;
  border-bottom-right-radius: 10px;
  border-bottom-left-radius: 10px;
  background-color: #89d961;
}
.from-other {
  background-color: #ffffff;
}
</style>
