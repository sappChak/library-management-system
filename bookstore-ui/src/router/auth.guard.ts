import { useAuthStore } from "@/store/auth.store";

export const authGuard = (to: any, _from: any, next: any) => {
  const authStore = useAuthStore();

  if (to.meta.requiresAuth && !authStore.isAuthenticated) {
    next("/login");
  } else {
    next();
  }

  if (to.meta.roles) {
    const hasRole = to.meta.roles.some((role: string) =>
      authStore.hasRole(role),
    );
    if (!hasRole) {
      return next("/403");
    }
  }

  next();
};
