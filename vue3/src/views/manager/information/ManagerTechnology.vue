<script setup>
import { reactive, onMounted, ref } from 'vue'
import {
  addTechnologyAPI,
  selectTechnologyDataAPI,
  deleteByIdAPI,
  deleteBatchAPI,
  updateTechnologyAPI,
} from '@/api/technology'
import { ElMessage, ElMessageBox } from 'element-plus'
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)
const name = ref()
const data = reactive({
  technologyData: [],
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
        loadTechnologyData()
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
        loadTechnologyData()
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
const loadTechnologyData = () => {
  selectTechnologyDataAPI(pageNum.value, pageSize.value, name.value).then((res) => {
    if (res.code === '200') {
      data.technologyData = res.data?.list || []
      total.value = res.data?.total
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const reset = () => {
  name.value = null
  loadTechnologyData()
}
const save = () => {
  if (data.fileList.length !== 0 && !data.isupload) {
    ElMessage.warning('您选择了头像却没有上传')
    return
  }
  data.formData.id ? update() : insert()
  data.dialogVisible = false
}
const insert = () => {
  addTechnologyAPI(data.formData).then((res) => {
    if (res.code === '200') {
      ElMessage.success('添加成功')
      loadTechnologyData()
    } else {
      console.log(res)
      ElMessage.error(res.msg)
    }
  })
}
const update = () => {
  console.log(data.formData)
  updateTechnologyAPI(data.formData).then((res) => {
    if (res.code === '200') {
      ElMessage.success('修改成功')
      loadTechnologyData()
    } else {
      ElMessage.error(res.msg)
    }
  })
}
onMounted(() => {
  loadTechnologyData()
})
</script>

<template>
  <div class="technology-manage">
    <div class="search-box card">
      <el-input v-model="name" style="width: 240px" placeholder="请输入名称搜索" />
      <el-button type="primary" size="default" @click="loadTechnologyData">查询</el-button>
      <el-button type="warning" size="default" @click="reset">重置</el-button>
    </div>
  </div>
  <div class="operation card">
    <el-button type="primary" size="default" @click="add">新增</el-button>
    <el-button type="danger" size="default" @click="delBatch">删除</el-button>
  </div>
  <div class="display card">
    <el-table
      :data="data.technologyData"
      stripe
      style="width: 100%"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column prop="name" label="论文编程技术" />
      <el-table-column label="操作" width="200">
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
      @current-change="loadTechnologyData"
    />
  </div>
  <!--dialog-->
  <el-dialog v-model="data.dialogVisible" title="增加编程技术" width="700">
    <el-form label-width="70px" :model="data.formData">
      <el-form-item label="编程技术">
        <el-input v-model="data.formData.name" placeholder="请输入编程技术" clearable></el-input>
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
