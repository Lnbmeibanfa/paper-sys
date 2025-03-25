<script setup>
import PaperStudentShower from '../component/PaperStudentShower.vue'
import { selectSelectedPaper } from '@/api/paper'
import { useAccountStore } from '@/stores/account'
import { ElMessage } from 'element-plus'
import { ref } from 'vue'
const accont = useAccountStore()
const selectedPaper = ref(null)
const loadSelectedPaper = () => {
  selectSelectedPaper(accont.accountInfo.id).then((res) => {
    if (res.code === '200') {
      selectedPaper.value = res.data
      console.log(selectedPaper.value)
    } else {
      ElMessage.error(res.msg)
    }
  })
}
loadSelectedPaper()
</script>

<template>
  <div class="student-select">
    <div class="paper-box" v-if="selectedPaper.id !== null">
      <paper-student-shower :paper="selectedPaper" />
    </div>
    <div v-else>
      <el-empty description="您还没有选择论文" />
    </div>
  </div>
</template>

<style scoped>
.student-select {
  padding: 50px;
  width: 1300px;
  margin: 0 auto;
  background-color: white;
}
</style>
