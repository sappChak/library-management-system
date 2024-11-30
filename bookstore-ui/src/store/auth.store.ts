import { defineStore } from "pinia";
import { login, getProfile, register } from "@/api/auth.api";
import { AuthState } from "@/types";

export const useAuthStore = defineStore("auth", {
  state: (): AuthState => ({
    token: localStorage.getItem("token"),
    user: null,
  }),

  actions: {
    async login(username: string, password: string) {
      try {
        const token = await login(username, password);
        this.token = token;
        localStorage.setItem("token", token);

        await this.fetchUserProfile();
      } catch (error) {
        console.error("Login failed:", error);
        throw error;
      }
    },

    async register(email: string, username: string, password: string) {
      try {
        await register(email, username, password);
      } catch (error) {
        console.error("Registration failed:", error);
        throw error;
      }
    },

    async fetchUserProfile() {
      try {
        const user = await getProfile();
        this.user = user;
      } catch (error) {
        console.error("Failed to fetch user profile:", error);
        throw error;
      }
    },

    logout() {
      this.token = null;
      this.user = null;
      localStorage.removeItem("token");
    },
  },

  getters: {
    isAuthenticated: (state) => !!state.token,
    hasRole: (state) => (role: string) => {
      return state.user?.roles.includes(role) || false;
    },
    isAdmin: (state) => state.user?.roles.includes("ROLE_ADMIN"),
  },
});
