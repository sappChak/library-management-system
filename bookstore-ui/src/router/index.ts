import { createRouter, createWebHistory } from "vue-router";
import { authGuard } from "./auth.guard";
import Login from "@/views/Login.vue";
import Register from "@/views/Register.vue";
import UserDashboard from "@/views/UserDashboard.vue";
import AdminDashboard from "@/views/AdminDashboard.vue";

const routes = [
  { path: "/login", component: Login },
  { path: "/register", component: Register },
  {
    path: "/dashboard",
    component: UserDashboard,
    meta: { requiresAuth: true },
  },
  {
    path: "/admin",
    component: AdminDashboard,
    meta: { requiresAuth: true, requiresAdmin: true },
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach(authGuard);

export default router;
