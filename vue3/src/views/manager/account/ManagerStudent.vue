<script setup>
import { reactive, onMounted, ref } from 'vue'
import { selectStudentDataAPI, deleteByIdAPI, deleteBatchAPI, addStudentAPI } from '@/api/student'
import { ElMessage, ElMessageBox } from 'element-plus'

const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)
const name = ref(null)
const data = reactive({
  studentData: [],
  dialogVisible: false,
  formData: {},
  fileList: [],
  isupload: false,
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
        loadStudentData()
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
        loadStudentData()
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
const loadStudentData = () => {
  selectStudentDataAPI(pageNum.value, pageSize.value, name.value).then((res) => {
    if (res.code === '200') {
      data.studentData = res.data?.list || []
      total.value = res.data?.total
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const reset = () => {
  name.value = null
  loadStudentData()
}
const save = () => {
  addStudentAPI(data.formData).then((res) => {
    if (res.code === '200') {
      ElMessage.success('添加成功')
      loadStudentData()
    } else {
      console.log(res)
      ElMessage.error(res.msg)
    }
  })
  data.dialogVisible = false
}

onMounted(() => {
  loadStudentData()
})
</script>

<template>
  <div class="student-manage">
    <div class="search-box card">
      <el-input v-model="name" style="width: 240px" placeholder="请输入昵称搜索" />
      <el-button type="primary" size="default" @click="loadStudentData">查询</el-button>
      <el-button type="warning" size="default" @click="reset">重置</el-button>
    </div>
  </div>
  <div class="operation card">
    <el-button type="primary" size="default" @click="add">新增</el-button>
    <el-button type="danger" size="default" @click="delBatch">删除</el-button>
  </div>
  <div class="display card">
    <el-table
      :data="data.studentData"
      stripe
      style="width: 100%"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column prop="username" label="账号" />
      <el-table-column prop="name" label="昵称" />
      <el-table-column prop="phone" label="电话号码" />
      <el-table-column prop="wechat" label="微信号" />
      <el-table-column prop="qq" label="qq号" />
      <el-table-column prop="email" label="电子邮箱" />
      <el-table-column prop="status" label="审核状态" />
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
    <el-pagination
      background
      layout="prev, pager, next"
      :total="total"
      :page-size="pageSize"
      v-model:current-page="pageNum"
      @current-change="loadStudentData"
    />
  </div>
  <!--dialog-->
  <el-dialog v-model="data.dialogVisible" title="增加学生" width="700">
    <el-form label-width="70px" :model="data.formData">
      <el-form-item label="用户名">
        <el-input v-model="data.formData.username" placeholder="请输入账号" clearable></el-input>
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="data.formData.password" placeholder="请输入账号" clearable></el-input>
      </el-form-item>
      <el-form-item label="昵称">
        <el-input v-model="data.formData.name" placeholder="请输入昵称" clearable></el-input>
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
