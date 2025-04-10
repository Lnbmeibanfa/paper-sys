<script setup>
import { onMounted } from 'vue'
import { selectPaperDataAPI } from '@/api/paper'
import PaperStudentShower from '../component/PaperStudentShower.vue'
import { ref } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
const route = useRoute()
const loadPapersData = () => {
  selectPaperDataAPI(null, null, route.query.teacherId).then((res) => {
    if (res.code === '200') {
      paperData.value = res.data.list
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const paperData = ref()
onMounted(() => {
  loadPapersData()
})
</script>

<template>
  <div>
    <div class="content-box">
      <div class="display-box">
        <paper-student-shower v-for="paper in paperData" :key="paper.id" :paper="paper" />
      </div>
    </div>
  </div>
</template>

<style scoped>
.content-box {
  padding: 20px;
  border-radius: 10px;
  background-color: #fff;
  width: 1200px;
  margin: 15px auto;
}
</style>
