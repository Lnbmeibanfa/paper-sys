<!-- 聊天框中的每一条消息 -->
<script setup>
import { useAccountStore } from '@/stores/account'
import { computed } from 'vue'

const accountStore = useAccountStore()
const data = defineProps({
  message: Object,
})
const isFromMe = computed(() => data.message.sendRole === accountStore.accountInfo.role)
</script>
<template>
  <div v-if="isFromMe" class="message-me">
    <div class="message-box">
      <div class="content from-me" v-html="data.message.content"></div>
    </div>
  </div>
  <div class="message-other" v-else>
    <div class="message-box">
      <div class="content from-other" v-html="data.message.content"></div>
    </div>
  </div>
</template>

<style scoped>
.message-me {
  display: flex;
  justify-content: right;
}
.message-other {
  display: flex;
  justify-content: left;
}
.message-box {
  margin: 5px 0px;
  display: flex;
  flex-direction: column;
}
.content {
  display: inline;
  padding: 10px;
  border-bottom-right-radius: 10px;
  border-bottom-left-radius: 10px;
}
.from-me {
  border-top-left-radius: 10px;

  background-color: #89d961;
}
.from-other {
  border-top-right-radius: 10px;
  background-color: #f4f4f7;
}
</style>
