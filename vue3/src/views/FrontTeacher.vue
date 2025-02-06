<script setup>
import router from '@/router'
import { useAccountStore } from '@/stores/account'
const accountStore = useAccountStore()

import defaultAvatarImg from '@/assets/imgs/defaultAvatar.png'
</script>

<template>
  <div class="background teacher-view">
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
          default-active="/teacher/home"
          mode="horizontal"
          :popper-offset="16"
          style="max-width: 600px"
          :ellipsis="false"
        >
          <el-menu-item index="/teacher/home">首页</el-menu-item>
          <el-menu-item index="4">论文管理</el-menu-item>
        </el-menu>
      </nav>
      <div class="account">
        <div class="account-box">
          <el-icon class="message"><Bell /></el-icon>
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
                  <el-dropdown-item @click="router.push({ name: 'self' })"
                    >个人资料</el-dropdown-item
                  >
                  <el-dropdown-item @click="router.push({ name: 'login' })"
                    >退出登录</el-dropdown-item
                  >
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
@import url('../assets/css/teacher.css');
</style>
