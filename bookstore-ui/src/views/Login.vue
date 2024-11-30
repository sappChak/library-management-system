<template>
  <div>
    <h1>Login</h1>
    <form @submit.prevent="handleLogin">
      <input v-model="username" placeholder="Username" />
      <input v-model="password" type="password" placeholder="Password" />
      <button type="submit">Login</button>
    </form>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { useAuthStore } from "@/store/auth.store";

const username = ref("");
const password = ref("");
const authStore = useAuthStore();

const handleLogin = async () => {
  try {
    await authStore.login(username.value, password.value);
    alert("Login successful!");
  } catch (error) {
    alert("Login failed!");
  }
};
</script>
