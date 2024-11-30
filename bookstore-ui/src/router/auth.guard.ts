import { useAuthStore } from "@/store/auth.store";

export const authGuard = (to: any, _from: any, next: any) => {
  const authStore = useAuthStore();

  const isAuthenticated = authStore.isAuthenticated;
  const isAdmin = authStore.isAdmin;

  if (to.meta.requiresAuth && !isAuthenticated) {
    next("/login");
  } else if (to.meta.requiresAdmin && !isAdmin) {
    next("/dashboard");
  } else {
    next();
  }
};
