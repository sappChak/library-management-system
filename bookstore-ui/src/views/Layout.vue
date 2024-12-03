<template>
  <div class="app-layout">
    <!-- Sidebar -->
    <Sidebar :is-admin="authStore.isAdmin" @logout="logout" />
    <!-- Main Content -->
    <div class="main-content">
      <router-view />
    </div>
  </div>
</template>

<script setup lang="ts">
import { useAuthStore } from "@/store/auth.store";
import Sidebar from "@/components/Sidebar.vue";

const authStore = useAuthStore();

const logout = () => {
  authStore.logout();
  window.location.reload();
};
</script>

<style scoped>
.app-layout {
  display: flex;
  height: 100vh;
  font-family: "Poppins", Arial, sans-serif;
  background-color: #f3f4f6;
  overflow: hidden;
}

.main-content {
  flex-grow: 1;
  padding: 80px;
  transition: margin-left 0.3s ease;
  overflow-y: auto;
}

.app-layout .sidebar.collapsed+.main-content {
  margin-left: 80px;
}
</style>
