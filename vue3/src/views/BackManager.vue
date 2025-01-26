<script setup>
import { ElBreadcrumb } from 'element-plus'
</script>

<template>
  <div class="back-end-management backgroud">
    <header>
      <div class="logo-box">
        <div class="logo">
          <img src="@/assets/imgs/logo.svg" alt="" />
        </div>
        <div class="title h1">优策论文</div>
      </div>
      <div class="bread-crumb">
        <el-breadcrumb separator="/">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>页面1</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <div class="account">
        <div class="account-box">
          <el-icon><Bell /></el-icon>
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
                  <el-dropdown-item>Action 1</el-dropdown-item>
                  <el-dropdown-item>Action 2</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </div>
      </div>
    </header>
    <main>
      <nav></nav>
      <RouterView />
    </main>
  </div>
</template>

<style scoped>
@import url('../assets/css/manager.css');
</style>
