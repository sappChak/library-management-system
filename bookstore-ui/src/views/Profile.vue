<template>
  <div>
    <h1>User Profile</h1>
    <div v-if="user">
      <p><strong>ID:</strong> {{ user.id }}</p>
      <p><strong>Username:</strong> {{ user.username }}</p>
      <p><strong>Email:</strong> {{ user.email }}</p>
    </div>
    <div v-else>
      <p>Loading user profile...</p>
    </div>
    <button @click="logout">Logout</button>
  </div>
</template>

<script setup lang="ts">
import { onMounted } from "vue";
import { useAuthStore } from "@/store/auth.store";

const authStore = useAuthStore();
const user = authStore.user;

onMounted(async () => {
  if (!user) {
    await authStore.fetchUserProfile();
  }
});

const logout = () => {
  authStore.logout();
};
</script>
