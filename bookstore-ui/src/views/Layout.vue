<template>
  <div class="app-layout">
    <!-- Sidebar -->
    <Sidebar @logout="logout" />
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
}

.dashboard-widgets {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(230px, 1fr));
  gap: 25px;
  margin-bottom: 40px;
}

.main-content {
  flex-grow: 1;
  padding: 30px;
  overflow-y: auto;
}
</style>
