<script setup>
import { updateStudentAPI, selectByIdAPI } from '@/api/student'
import EditInfo from '@/views/component/EditInfo.vue'
import { useAccountStore } from '@/stores/account'
import { ElMessage } from 'element-plus'
import { onMounted } from 'vue'
const baseUrl = import.meta.env.VITE_BASE_URL
const accountStore = useAccountStore()
const loadStudentData = () => {
  selectByIdAPI(accountStore.accountInfo.id).then((res) => {
    if (res.code === '200') {
      accountStore.addAccountInfo(res.data)
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const handleUploadSuccess = (res) => {
  accountStore.accountInfo.avatar = res.data
  updateStudentAPI(accountStore.accountInfo).then((res) => {
    if (res.code === '200') {
      ElMessage.success('头像上传成功')
      loadStudentData()
    } else {
      ElMessage.error(res.msg)
    }
  })
}
const handleSave = (val, key) => {
  accountStore.accountInfo[key] = val
  updateStudentAPI(accountStore.accountInfo).then((res) => {
    if (res.code === '200') {
      ElMessage.success('修改成功')
      loadStudentData()
    } else {
      ElMessage.error(res.msg)
    }
  })
}
onMounted(() => {
  loadStudentData()
})
</script>

<template>
  <div class="self-box card">
    <div class="img-box">
      <el-upload
        :action="baseUrl + '/files/upload'"
        :on-success="handleUploadSuccess"
        list-type="picture-card"
        :limit="1"
        :show-file-list="false"
      >
        <img
          style="width: 100px; height: 100px; border-radius: 5px"
          v-if="accountStore.accountInfo.avatar"
          :src="accountStore.accountInfo.avatar"
          class="avatar"
        />
        <div class="avatar-upload-box" v-else>
          <el-icon class="avatar-uploader-icon"><Plus /></el-icon>
          <div>上传我的头像</div>
        </div>
      </el-upload>
    </div>
    <div class="info-box">
      <edit-info
        title="昵称"
        :content="accountStore.accountInfo.name"
        @save="(content) => handleSave(content, 'name')"
      />
      <el-divider />
      <edit-info
        title="密码"
        :content="accountStore.accountInfo.password"
        @save="(content) => handleSave(content, 'password')"
      />
      <el-divider />
      <edit-info
        title="电话号码"
        :content="accountStore.accountInfo.phone"
        @save="(content) => handleSave(content, 'phone')"
      />
      <el-divider />
      <edit-info
        title="微信"
        :content="accountStore.accountInfo.wechat"
        @save="(content) => handleSave(content, 'wechat')"
      />
      <el-divider />
      <edit-info
        title="qq号"
        :content="accountStore.accountInfo.qq"
        @save="(content) => handleSave(content, 'qq')"
      />
      <el-divider />
      <edit-info
        title="邮箱"
        :content="accountStore.accountInfo.email"
        @save="(content) => handleSave(content, 'email')"
      />
      <el-divider />
    </div>
  </div>
</template>

<style scoped>
.self-box {
  padding: 50px;
  width: 1300px;
  margin: 0 auto;
  display: flex;
}

.avatar-upload-box {
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 50px;
  justify-content: space-around;
}
.img-box {
  padding-top: 70px;
  flex: 1;
  text-align: center;
}
.info-box {
  width: 1000px;
}

.password-box .password {
  line-height: 32px;
}
</style>
