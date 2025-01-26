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
      children: [{ path: 'home', component: () => import('@/views/manager/ManagerHome.vue') }],
    },
  ],
})

export default router
