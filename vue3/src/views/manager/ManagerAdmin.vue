<script setup>
import { reactive, onMounted, ref } from 'vue'
import { addAdminAPI, selectAdminDataAPI, deleteByIdAPI, deleteBatchAPI } from '@/api/admin'
import { ElMessage, ElMessageBox } from 'element-plus'
import { genFileId } from 'element-plus'

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
const upload = ref()
const data = reactive({
  adminData: [],
  dialogVisible: false,
  formData: {},
  fileList: [],
  isupload: false,
  keyword: '',
  selectedIds: [],
})
const add = () => {
  data.formData = {}
  data.fileList = []
  data.isupload = false
  data.dialogVisible = true
}
const delBatch = () => {
  ElMessageBox.confirm('删除后数据不可恢复', '确认删除', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning',
  })
    .then(() => {
      deleteBatchAPI(data.selectedIds).then((res) => {
        res.code === '200' ? ElMessage.success('删除成功') : ElMessage.error('删除失败')
        loadAdminData()
      })
    })
    .catch((err) => console.log(err))
}
const hanleDeleteRow = (row) => {
  ElMessageBox.confirm('删除后数据不可恢复', '确认删除', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning',
  })
    .then(() => {
      deleteByIdAPI(row.id).then((res) => {
        res.code === '200' ? ElMessage.success('删除成功') : ElMessage.error('删除失败')
        loadAdminData()
      })
    })
    .catch((err) => console.log(err))
}
const hanleUpdateRow = (row) => {
  data.formData = {}
  data.fileList = []
  data.isupload = false
  data.formData = JSON.parse(JSON.stringify(row))
  data.dialogVisible = true
}
const handleSelectionChange = (selectedRows) => {
  data.selectedIds = selectedRows.map((row) => row.id)
}
const handleExceed = (files) => {
  upload.value.clearFiles()
  const file = files[0]
  file.uid = genFileId()
  upload.value.handleStart(file)
}
const handleSuccess = (res) => {
  data.formData.avatar = res.data
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
  if (data.fileList.length !== 0 && !data.isupload) {
    ElMessage.warning('您选择了头像却没有上传')
    return
  }
  addAdminAPI(data.formData).then((res) => {
    console.log(res)
    if (res.code === '200') {
      ElMessage.success('添加成功')
      loadAdminData()
    } else {
      ElMessage.error(res.msg)
    }
  })
  data.dialogVisible = false
}
const submitUpload = () => {
  upload.value.submit()
  data.isupload = true
}
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
    <el-button type="danger" size="default" @click="delBatch">删除</el-button>
  </div>
  <div class="display card">
    <el-table
      :data="data.adminData"
      stripe
      style="width: 100%"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column prop="username" label="账号" />
      <el-table-column prop="name" label="昵称" />
      <el-table-column prop="avatar" label="头像">
        <template #default="scope">
          <el-image
            style="width: 40px; height: 40px; border-radius: 50%"
            :preview-src-list="[scope.row.avatar]"
            :preview-teleported="true"
            :src="scope.row.avatar"
          ></el-image>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button
            type="primary"
            icon="Edit"
            circle
            @click="hanleUpdateRow(scope.row)"
          ></el-button>
          <el-button
            type="danger"
            icon="Delete"
            circle
            @click="hanleDeleteRow(scope.row)"
          ></el-button>
        </template>
      </el-table-column>
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
          ref="upload"
          :before-upload="beforeUpload"
          :action="baseUrl + '/files/upload'"
          v-model:file-list="data.fileList"
          list-type="picture"
          :limit="1"
          :on-success="handleSuccess"
          :on-exceed="handleExceed"
          :auto-upload="false"
        >
          <template #trigger>
            <el-button type="primary">选择图片</el-button>
          </template>
          <el-button style="margin-left: 5px" type="success" @click="submitUpload">上传</el-button>
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
