<script setup>
import router from '@/router'
import { useAccountStore } from '@/stores/account'
import { useRoute } from 'vue-router'
const accountInfo = useAccountStore()
const route = useRoute()
const quit = () => {
  accountInfo.cloneAccountInfo({})
  router.push({ name: 'loginManager' })
}
</script>

<template>
  <div class="back-end-management background">
    <header>
      <div class="logo-box">
        <div class="logo">
          <img src="@/assets/imgs/logo.svg" alt="" />
        </div>
        <div class="title">优策论文</div>
      </div>
      <div class="bread-crumb">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>{{ route.meta.name }}</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <div class="account">
        <div class="account-box">
          <el-icon class="message"><Bell /></el-icon>
          <div class="user">
            <img src="../assets/imgs/avatar.png" />
            <el-dropdown>
              <span class="el-dropdown-link">
                admin
                <el-icon class="el-icon--right">
                  <arrow-down />
                </el-icon>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item>个人资料</el-dropdown-item>
                  <el-dropdown-item @click="quit">退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </div>
      </div>
    </header>
    <main>
      <nav>
        <el-menu default-active="/manager/home" class="el-menu-vertical-demo" router>
          <el-menu-item index="/manager/home">
            <el-icon><House /></el-icon>
            <span>系统首页</span>
          </el-menu-item>
          <el-sub-menu index="/manager/course">
            <template #title>
              <el-icon><DocumentCopy /></el-icon>
              <span>信息管理</span>
            </template>
            <el-menu-item index="/manager/course">论文前置课程管理</el-menu-item>
            <el-menu-item index="/manager/language">论文编程语言管理</el-menu-item>
            <el-menu-item index="/manager/technology">论文编程技术管理</el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="/manager/admin">
            <template #title>
              <el-icon><User /></el-icon>
              <span>用户管理</span>
            </template>
            <el-menu-item index="/manager/admin">管理员信息</el-menu-item>
            <el-menu-item index="/manager/student">学生信息</el-menu-item>
            <el-menu-item index="/manager/teacher">教师信息</el-menu-item>
          </el-sub-menu>
        </el-menu>
      </nav>
      <div class="main-content"><RouterView /></div>
    </main>
  </div>
</template>

<style scoped>
@import url('../assets/css/manager.css');
</style>
