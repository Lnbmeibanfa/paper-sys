<script setup>
// 这里编写逻辑代码
</script>

<template>
  <div class="student-manage">
    <div class="search-box card">
      <el-input v-model="keyword" style="width: 240px" placeholder="请输入" />
      <el-button type="primary" size="default" @click="search">查询</el-button>
      <el-button type="warning" size="default" @click="reset">重置</el-button>
    </div>
  </div>
  <div class="operation card">
    <el-button type="primary" size="default" @click="add">新增</el-button>
    <el-button type="danger" size="default" @click="del">删除</el-button>
  </div>
  <div class="display card">
    <el-table :data="studentData" stripe style="width: 100%">
      <el-table-column prop="date" label="Date" width="180" />
      <el-table-column prop="name" label="Name" width="180" />
      <el-table-column prop="address" label="Address" />
    </el-table>
  </div>
</template>

<style scoped>
.el-input {
  margin-right: 12px;
}
</style>
