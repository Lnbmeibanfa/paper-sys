import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: '/manager/home',
    },
    {
      path: '/manager',
      name: 'manager',
      component: () => import('@/views/BackManager.vue'),
      children: [
        {
          path: 'home',
          component: () => import('@/views/manager/ManagerHome.vue'),
          meta: { name: '系统首页' },
        },
        // 用户管理页面
        {
          path: 'student',
          component: () => import('@/views/manager/account/ManagerStudent.vue'),
          meta: { name: '学生信息' },
        },
        {
          path: 'admin',
          component: () => import('@/views/manager/account/ManagerAdmin.vue'),
          meta: { name: '管理员信息' },
        },
        {
          path: 'teacher',
          component: () => import('@/views/manager/account/ManagerTeacher.vue'),
          meta: { name: '教师信息' },
        },
        // 信息管理页面
        {
          path: 'topicType',
          component: () => import('@/views/manager/information/ManagerTopicType.vue'),
          meta: { name: '论文题目类型' },
        },
      ],
    },
    {
      path: '/teacher',
      name: 'teacher',
      component: () => import('@/views/FrontTeacher.vue'),
      children: [
        { path: 'home', component: () => import('@/views/teacher/TeacherHome.vue'), name: 'home' },
        { path: 'self', component: () => import('@/views/teacher/TeacherSelf.vue'), name: 'self' },
      ],
    },
    { path: '/login', name: 'login', component: () => import('@/views/login/AccountLogin.vue') },
    { path: '/404', component: () => import('@/views/global/invalidPage404.vue') },
    { path: '/:pathMatch(.*)', redirect: '/404' },
  ],
})

export default router
