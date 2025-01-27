<script setup>
import { reactive, onMounted } from 'vue'
import { addAdminAPI, selectAdminDataAPI } from '@/api/admin'
import { ElMessage } from 'element-plus'

// 图片访问地址
const baseUrl = import.meta.env.VITE_BASE_URL
// 文件上传之前，限制上传文件大小
const beforeUpload = (file) => {
  const maxSize = 1024 * 1024
  if (file.size > maxSize) {
    ElMessage.error('文件过大，请上传小于1MB的文件')
    return false
  } else {
    return true
  }
}

const data = reactive({
  adminData: [],
  dialogVisible: false,
  formData: {},
  keyword: '',
})
const add = () => {
  data.dialogVisible = true
}
const loadAdminData = (name) => {
  selectAdminDataAPI(name).then((res) => {
    if (res.code === '200') {
      data.adminData = res.data.list
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const reset = () => {}
const search = () => {}
const save = () => {
  addAdminAPI(data.formData).then((res) => {
    console.log(res)
    if (res.code === '200') {
      ElMessage.success('添加成功')
    } else {
      ElMessage.error(res.msg)
    }
  })
  data.dialogVisible = false
  loadAdminData()
}
const del = () => {}
onMounted(() => {
  loadAdminData()
})
</script>

<template>
  <div class="admin-manage">
    <div class="search-box card">
      <el-input v-model="data.keyword" style="width: 240px" placeholder="请输入" />
      <el-button type="primary" size="default" @click="search">查询</el-button>
      <el-button type="warning" size="default" @click="reset">重置</el-button>
    </div>
  </div>
  <div class="operation card">
    <el-button type="primary" size="default" @click="add">新增</el-button>
    <el-button type="danger" size="default" @click="del">删除</el-button>
  </div>
  <div class="display card">
    <el-table :data="data.adminData" stripe style="width: 100%">
      <el-table-column prop="username" label="账号" width="180" />
      <el-table-column prop="name" label="昵称" width="180" />
      <el-table-column prop="avatar" label="头像" />
    </el-table>
  </div>
  <div class="pagination card">
    <el-pagination background layout="prev, pager, next" :total="2" />
  </div>
  <!--dialog-->
  <el-dialog v-model="data.dialogVisible" title="增加管理员" width="700">
    <el-form label-width="70px" :model="data.formData">
      <el-form-item label="用户名">
        <el-input v-model="data.formData.username" placeholder="请输入账号" clearable></el-input>
      </el-form-item>
      <el-form-item label="昵称">
        <el-input v-model="data.formData.name" placeholder="请输入昵称" clearable></el-input>
      </el-form-item>
      <el-form-item label="头像">
        <el-upload
          :before-upload="beforeUpload"
          :action="baseUrl + '/files/upload'"
          :limit="1"
          :on-exceed="handleExceed"
          :auto-upload="false"
        >
          <template #trigger>
            <el-button type="primary">select file</el-button>
          </template>
        </el-upload>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button type="primary" @click="save">保存</el-button>
        <el-button @click="data.dialogVisible = false">取消</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<style scoped>
.el-input {
  margin-right: 12px;
}
</style>
