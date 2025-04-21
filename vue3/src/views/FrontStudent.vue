<script setup>
import router from '@/router'
import { useAccountStore } from '@/stores/account'
const accountStore = useAccountStore()
import defaultAvatarImg from '@/assets/imgs/defaultAvatar.png'
import { ElMessageBox } from 'element-plus'
const quit = () => {
  ElMessageBox.confirm('确认退出登录？', '退出登录？', {
    confirmButtonText: '确认',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(() => {
    accountStore.cloneAccountInfo({})
    router.push({ name: 'login' })
  })
}
const toChat = () => {
  router.push({ name: 'studentChat' })
}
</script>

<template>
  <div class="background student-view">
    <header>
      <div class="logo-box">
        <div class="logo">
          <img src="@/assets/imgs/logo.svg" alt="" />
        </div>
        <div class="title">优策论文</div>
      </div>
      <nav>
        <el-menu
          router
          class="el-menu-popper-demo"
          default-active="/student/home"
          mode="horizontal"
          :popper-offset="16"
          style="max-width: 600px"
          :ellipsis="false"
        >
          <el-menu-item index="/student/home">首页</el-menu-item>
          <el-menu-item index="/student/select">我选择的论文</el-menu-item>
          <el-menu-item index="/student/chat">与导师的沟通</el-menu-item>
        </el-menu>
      </nav>
      <div class="account">
        <div class="account-box">
          <el-icon class="message" @click="toChat"><Bell /></el-icon>
          <div class="user">
            <img
              :src="
                accountStore.accountInfo.avatar ? accountStore.accountInfo.avatar : defaultAvatarImg
              "
            />
            <el-dropdown>
              <span class="el-dropdown-link">
                {{
                  accountStore.accountInfo.name
                    ? accountStore.accountInfo.name
                    : accountStore.accountInfo.username
                }}
                <el-icon class="el-icon--right">
                  <arrow-down />
                </el-icon>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item @click="router.push({ name: 'studentSelf' })"
                    >个人资料</el-dropdown-item
                  >
                  <el-dropdown-item @click="quit">退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </div>
      </div>
    </header>
    <main>
      <RouterView></RouterView>
    </main>
  </div>
</template>

<style scoped>
@import url('../assets/css/student.css');
</style>
