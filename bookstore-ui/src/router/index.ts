import { createRouter, createWebHistory } from "vue-router";
import { authGuard } from "./auth.guard";
import Login from "@/views/Login.vue";
import Profile from "@/views/Profile.vue";

const routes = [
  { path: "/login", component: Login },
  { path: "/profile", component: Profile, meta: { requiresAuth: true } },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach(authGuard);

export default router;
