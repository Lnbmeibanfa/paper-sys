<script setup>
import { reactive, ref } from 'vue'
import { loginAPI } from '@/api/login'
import { ElMessage } from 'element-plus'
import { useAccountStore } from '@/stores/account'
import router from '@/router'

const accountStore = useAccountStore()
const loginForm = ref()
const userInfo = reactive({
  username: '',
  password: '',
  checked: false,
  role: 'ADMIN',
})
const validateChecked = (rules, value, callback) => {
  console.log(value)
  if (value === false) {
    callback('请勾选我同意')
  } else {
    callback()
  }
}
const data = reactive({
  rules: {
    username: [{ required: true, message: '账号不得为空', trigger: 'blur' }],
    password: [{ required: true, message: '密码不得为空', trigger: 'blur' }],
    checked: [{ validator: validateChecked, trigger: 'blur' }],
  },
})

const login = () => {
  loginForm.value.validate((valid) => {
    if (valid) {
      loginAPI(userInfo).then((res) => {
        console.log(res)
        if (res.code === '200') {
          accountStore.cloneAccountInfo(res.data)
          ElMessage.success('登录成功')
          if (accountStore.accountInfo.role === 'TEACHER') {
            router.push({ name: 'teacher' })
          } else if (accountStore.accountInfo.role === 'STUDENT') {
            router.push({ name: 'student' })
          } else if (accountStore.accountInfo.role === 'ADMIN') {
            console.log(1)
            router.push({ name: 'manager' })
          }
        } else {
          ElMessage.error(res.msg)
        }
      })
    }
  })
}
</script>

<template>
  <div class="login">
    <div class="bg">
      <div class="empty-box"></div>
      <div class="login-box">
        <div class="box-bg">
          <div class="box">
            <el-icon @click="router.push({ name: 'login' })"><User /></el-icon>
            <div class="title">管理员登录</div>

            <div class="info-box">
              <el-form ref="loginForm" :model="userInfo" :rules="data.rules">
                <el-form-item prop="username">
                  <el-input
                    v-model="userInfo.username"
                    placeholder="请输入账号"
                    clearable
                  ></el-input>
                </el-form-item>
                <el-form-item prop="password">
                  <el-input
                    v-model="userInfo.password"
                    placeholder="请输入密码"
                    clearable
                  ></el-input>
                </el-form-item>
                <el-form-item prop="checked">
                  <div class="check-box">
                    <el-checkbox v-model="userInfo.checked" label="我已经阅读同意" />
                    <el-link class="link" type="primary">隐私协议</el-link>
                  </div></el-form-item
                >
              </el-form>
            </div>
            <div class="login-button-box">
              <el-button type="primary" @click="login">登录</el-button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.bg {
  background: url('@/assets/imgs/loginBg.jpg');
  background-size: cover;
  opacity: 0.8;
  display: flex;
  height: 100vh;
}
.login-box {
  display: flex;
  justify-content: center;
  align-items: center;
  flex: 4;
}
.empty-box {
  flex: 6;
}
.box-bg {
  background-color: white;
  border-radius: 10px;
  padding: 10px;
}
.box {
  width: 400px;
  height: 430px;
  padding: 25px;
  border-radius: 10px;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
  position: relative; /* 相对定位，用于伪元素定位 */
  overflow: hidden; /* 防止伪元素溢出 */
  cursor: pointer;
}
.box::after {
  content: ''; /* 伪元素内容 */
  position: absolute; /* 绝对定位 */
  top: 0; /* 定位到顶部 */
  right: 0; /* 定位到右侧 */
  width: 0; /* 宽度为0 */
  height: 0; /* 高度为0 */
  border-style: solid; /* 边框样式 */
  border-width: 0 50px 60px 0; /* 边框宽度，形成三角形 */
  border-color: transparent #007bff transparent transparent; /* 边框颜色，右上角为蓝色 */
}
.box .el-icon {
  font-size: 16px;
  color: white;
  position: absolute;
  top: 10px;
  right: 10px;
  z-index: 1;
}
.title {
  text-align: center;
  font-size: 20px;
  font-weight: 600;
  margin-top: 30px;
  margin-bottom: 30px;
}
.radio-box {
  text-align: center;
}
.info-box .el-form {
  height: 190px;
  margin-top: 20px;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
}
.login-button-box {
  margin-top: 10px;
}
.check-box {
  display: flex;
  align-items: center;
}
.check-box .link {
  margin-left: 5px;
}
/** element样式 */
.login-button-box .el-button {
  width: 100%;
}
</style>
