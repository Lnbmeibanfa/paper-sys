<script setup>
import { ref } from 'vue'
import { getAllPaper, getAllStudent, getSelectedPaper } from '@/api/admin'
const loadData = () => {
  getAllPaper().then((res) => {
    if (res.code === '200') {
      paperCount.value = res.data
    } else {
      console.log(res.msg)
    }
  })
  getAllStudent().then((res) => {
    if (res.code == '200') {
      studentCount.value = res.data
    } else {
      console.log(res.msg)
    }
  })
  getSelectedPaper().then((res) => {
    if (res.code === '200') {
      selectedPaper.value = res.data
    } else {
      console.log(res.msg)
    }
  })
}
const paperCount = ref()
const selectedPaper = ref()
const studentCount = ref()
loadData()
</script>

<template>
  <div class="manager-home">
    <el-row :gutter="20">
      <el-col :span="6"
        ><div class="grid-content ep-bg-purple">
          <el-card style="max-width: 480px">
            <template #header>
              <div class="card-header">
                <span>发布论文数</span>
              </div>
            </template>
            <div class="content">
              <img class="pictrue" src="/src/assets/imgs/数据分析-01.png" alt="" />
              <div class="value">{{ paperCount }}</div>
            </div>
          </el-card>
        </div></el-col
      >
      <el-col :span="6"
        ><div class="grid-content ep-bg-purple">
          <el-card style="max-width: 480px">
            <template #header>
              <div class="card-header">
                <span>论文选择数</span>
              </div>
            </template>
            <div class="content">
              <img class="pictrue" src="/src/assets/imgs/删除用户-01.png" alt="" />
              <div class="value">{{ paperCount - selectedPaper }}</div>
            </div>
          </el-card>
        </div></el-col
      >
      <el-col :span="6"
        ><div class="grid-content ep-bg-purple">
          <el-card style="max-width: 480px">
            <template #header>
              <div class="card-header">
                <span>学生数量</span>
              </div>
            </template>
            <div class="content">
              <img class="pictrue" src="/src/assets/imgs/书签-01.png" alt="" />
              <div class="value">{{ studentCount }}</div>
            </div>
          </el-card>
        </div></el-col
      >
      <el-col :span="6"
        ><div class="grid-content ep-bg-purple">
          <el-card style="max-width: 480px">
            <template #header>
              <div class="card-header">
                <span>未选择论文的学生数量</span>
              </div>
            </template>
            <div class="content">
              <img class="pictrue" src="/src/assets/imgs/生物识别-01.png" alt="" />
              <div class="value">{{ studentCount - selectedPaper }}</div>
            </div>
          </el-card>
        </div></el-col
      >
    </el-row>
  </div>
</template>

<style scoped>
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
