import { createRouter, createWebHistory } from "vue-router";
import { authGuard } from "./auth.guard";

const routes = [
  { path: "/login", component: import("@/views/auth/Login.vue") },
  { path: "/register", component: import("@/views/auth/Register.vue") },
  {
    path: "/admin",
    component: import("@/views/admin/Dashboard.vue"),
    meta: { requiresAuth: true, requiresAdmin: true },
  },
  {
    path: "/admin/users/add",
    component: import("@/component/AddUser.vue"),
    meta: { requiresAuth: true, requiresAdmin: true },
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach(authGuard);

export default router;
