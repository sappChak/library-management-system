import { createRouter, createWebHistory } from "vue-router";
import { authGuard } from "./auth.guard";

const routes = [
  { path: "/login", component: import("@/views/auth/Login.vue") },
  { path: "/register", component: import("@/views/auth/Register.vue") },
  {
    path: "/",
    component: import("@/views/Layout.vue"),
    meta: { requiresAuth: true },
    children: [
      {
        path: "admin",
        component: import("@/views/admin/Home.vue"),
        meta: { requiresAuth: true, requiresAdmin: true },
      },
      {
        path: "/admin/users/add",
        component: import("@/components/AddUser.vue"),
        meta: { requiresAuth: true, requiresAdmin: true },
      },
      {
        path: "/admin/users",
        component: import("@/components/ViewUsers.vue"),
        meta: { requiresAuth: true, requiresAdmin: true },
      },
      {
        path: "/admin/books",
        component: import("@/components/ViewBooks.vue"),
        meta: { requiresAuth: true, requiresAdmin: true },
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach(authGuard);

export default router;
