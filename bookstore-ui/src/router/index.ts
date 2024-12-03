import { createRouter, createWebHistory } from 'vue-router'
import { authGuard } from './auth.guard'

const routes = [
  { path: '/login', component: import('@/views/auth/Login.vue') },
  { path: '/register', component: import('@/views/auth/Register.vue') },
  {
    path: '/',
    component: import('@/views/Layout.vue'),
    meta: { requiresAuth: true },
    children: [
      {
        path: '/admin/home',
        component: import('@/views/admin/Home.vue'),
        meta: { requiresAuth: true, requiresAdmin: true },
      },
      {
        path: '/user/home',
        component: import('@/views/user/Home.vue'),
        meta: { requiresAuth: true },
      },
      {
        path: '/user/books',
        component: import('@/views/book/Books.vue'),
        meta: { requiresAuth: true },
      },
      {
        path: '/user/books/borrowed',
        component: import('@/views/book/BorrowedBooks.vue'),
        meta: { requiresAuth: true },
      },

      {
        path: '/admin/users/add',
        component: import('@/views/user/AddUser.vue'),
        meta: { requiresAuth: true, requiresAdmin: true },
      },
      {
        path: '/admin/users',
        component: import('@/views/user/ViewUsers.vue'),
        meta: { requiresAuth: true, requiresAdmin: true },
      },
      {
        path: '/admin/books',
        component: import('@/views/book/ViewBooks.vue'),
        meta: { requiresAuth: true, requiresAdmin: true },
      },
      {
        path: '/admin/books/add',
        component: import('@/views/book/AddBook.vue'),
        meta: { requiresAuth: true, requiresAdmin: true },
      },
    ],
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

router.beforeEach(authGuard)

export default router
