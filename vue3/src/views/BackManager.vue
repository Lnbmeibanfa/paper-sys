<script setup>
import router from '@/router'
import { useRoute } from 'vue-router'
const route = useRoute()
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
      <nav>
        <el-menu default-active="/manager/home" class="el-menu-vertical-demo" router>
          <el-menu-item index="/manager/home">
            <el-icon><House /></el-icon>
            <span>系统首页</span>
          </el-menu-item>
          <el-sub-menu index="/manager/topicType">
            <template #title>
              <el-icon><DocumentCopy /></el-icon>
              <span>信息管理</span>
            </template>
            <el-menu-item index="/manager/topicType">论文选题类型管理</el-menu-item>
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
