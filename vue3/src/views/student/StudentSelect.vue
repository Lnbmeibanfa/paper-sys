<script setup>
import PaperStudentShower from '../component/PaperStudentShower.vue'
import { selectSelectedPaper } from '@/api/paper'
import { useAccountStore } from '@/stores/account'
import { ElMessage } from 'element-plus'
import { ref } from 'vue'
const accont = useAccountStore()
const selectedPapers = ref(null)
const isLoading = ref(true)
const loadSelectedPaper = () => {
  selectSelectedPaper(accont.accountInfo.id).then((res) => {
    if (res.code === '200') {
      selectedPapers.value = res.data
      console.log(selectedPapers.value)
    } else {
      ElMessage.error(res.msg)
    }
    isLoading.value = false
  })
}
loadSelectedPaper()
</script>

<template>
  <div class="student-select">
    <div v-if="isLoading">加载中...</div>
    <div class="paper-box" v-else-if="selectedPapers.length !== 0">
      <paper-student-shower
        v-for="paper in selectedPapers"
        :key="paper.id"
        :paper="paper"
        :toChat="true"
      />
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
