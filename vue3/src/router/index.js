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
          path: 'course',
          component: () => import('@/views/manager/information/ManagerCourse.vue'),
          meta: { name: '论文前置课程' },
        },
        {
          path: 'language',
          component: () => import('@/views/manager/information/ManagerLanguage.vue'),
          meta: { name: '论文编程语言' },
        },
        {
          path: 'technology',
          component: () => import('@/views/manager/information/ManagerTechnology.vue'),
          meta: { name: '论文编程技术' },
        },
      ],
    },
    {
      path: '/teacher',
      name: 'teacher',
      redirect: '/teacher/home',
      component: () => import('@/views/FrontTeacher.vue'),
      children: [
        {
          path: 'home',
          component: () => import('@/views/teacher/TeacherHome.vue'),
          name: 'teacherHome',
        },
        {
          path: 'self',
          component: () => import('@/views/teacher/TeacherSelf.vue'),
          name: 'teacherSelf',
        },
        {
          path: 'paper',
          component: () => import('@/views/teacher/TeacherPaper.vue'),
        },
        {
          path: 'chat',
          component: () => import('@/views/teacher/TeatherChat.vue'),
          name: 'teacherChat',
        },
      ],
    },
    {
      path: '/student',
      name: 'student',
      redirect: '/student/home',
      component: () => import('@/views/FrontStudent.vue'),
      children: [
        {
          path: 'home',
          component: () => import('@/views/student/StudentHome.vue'),
          name: 'studentHome',
        },
        {
          path: 'self',
          component: () => import('@/views/student/StudentSelf.vue'),
          name: 'studentSelf',
        },
        {
          path: 'detail',
          component: () => import('@/views/student/StudentPaperDetail.vue'),
          name: 'paperDetail',
        },
        {
          path: 'chat',
          component: () => import('@/views/student/StudentChat.vue'),
          name: 'studentChat',
        },
        {
          path: 'select',
          component: () => import('@/views/student/StudentSelect.vue'),
          name: 'studentSelect',
        },
      ],
    },
    { path: '/login', name: 'login', component: () => import('@/views/login/AccountLogin.vue') },
    {
      path: '/loginManager',
      name: 'loginManager',
      component: () => import('@/views/login/ManagerLogin.vue'),
    },
    { path: '/404', component: () => import('@/views/global/invalidPage404.vue') },
    { path: '/:pathMatch(.*)', redirect: '/404' },
  ],
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const account = JSON.parse(localStorage.getItem('account'))
  const token = account?.accountInfo?.username
  const role = account?.accountInfo?.role

  // 首先检查是否有 token
  if (!token) {
    // 如果没有 token，且要访问的是受限页面，重定向到登录页
    if (
      to.path.includes('/teacher') ||
      to.path.includes('/student') ||
      to.path.includes('/manager')
    ) {
      console.log('toLOgin')
      next('/login')
    } else {
      // 允许访问其他页面
      next()
    }
  } else {
    // 如果有 token，根据角色进行权限判断
    switch (role) {
      case 'STUDENT':
        if (to.path.includes('/teacher') || to.path.includes('/manager')) {
          next('/student')
        } else if (to.path === '/login') {
          next('/student')
        } else {
          next()
        }
        break
      case 'TEACHER':
        if (to.path.includes('/student') || to.path.includes('/manager')) {
          next('/teacher')
        } else if (to.path === '/login') {
          next('/teacher')
        } else {
          next()
        }
        break
      case 'ADMIN':
        if (!to.path.includes('/manager')) {
          to.path === '/loginManager' ? next() : next('/manager')
        } else {
          next()
        }
        break
      default:
        // 未知角色，重定向到登录页
        next('/login')
    }
  }
})

export default router
