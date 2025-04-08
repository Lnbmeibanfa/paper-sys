<script setup>
import { getPaperCountById } from '@/api/teacher'
import { useAccountStore } from '@/stores/account'
import { ElMessage } from 'element-plus'
import { ref } from 'vue'
const accountInfo = useAccountStore()
const loadData = () => {
  getPaperCountById(accountInfo.accountInfo.id).then((res) => {
    if (res.code === '200') {
      teacherPaperCount.value = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const teacherPaperCount = ref()
loadData()
</script>

<template>
  <div class="home-box card">
    <div class="grid-content ep-bg-purple">
      <el-card style="max-width: 480px">
        <template #header>
          <div class="card-header">
            <span>我创建的论文数量</span>
          </div>
        </template>
        <div class="content">
          <img class="pictrue" src="/src/assets/imgs/数据分析-01.png" alt="" />
          <div class="value">{{ teacherPaperCount }}</div>
        </div>
      </el-card>
    </div>
  </div>
</template>

<style scoped>
.home-box {
  padding: 50px;
  width: 1300px;
  height: 100%;
  margin: 0 auto;
  display: flex;
}
.content {
  display: flex;
  justify-content: space-around;
  align-items: center;
}
.card-header {
  font-size: 25px;
  text-align: center;
}
.value {
  font-weight: bold;
  font-size: 30px;
}
.pictrue {
  width: 150px;
  height: 150px;
}
</style>
