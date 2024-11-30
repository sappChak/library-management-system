<template>
  <div class="login-container">
    <h1>Login</h1>
    <form @submit.prevent="handleLogin">
      <div class="form-group">
        <label for="username">Username</label>
        <input v-model="username" type="text" id="username" placeholder="Enter your username" required />
      </div>
      <div class="form-group">
        <label for="password">Password</label>
        <input v-model="password" type="password" id="password" placeholder="Enter your password" required />
      </div>
      <button type="submit" :disabled="isSubmitting">Login</button>
    </form>

    <div v-if="errorMessage" class="error-message">
      <p>{{ errorMessage }}</p>
    </div>

    <p>Don't have an account? <router-link to="/register">Register</router-link></p>
  </div>
</template>

<script setup lang="ts">
import { useAuthStore } from "@/store/auth.store";
import { ref } from "vue";
import { useRouter } from "vue-router";

const authStore = useAuthStore();
const router = useRouter();

const username = ref("");
const password = ref("");
const isSubmitting = ref(false);
const errorMessage = ref("");

const handleLogin = async () => {
  isSubmitting.value = true;
  errorMessage.value = "";

  try {
    await authStore.login({ username: username.value, password: password.value });

    // Redirect based on user role
    if (authStore.isAdmin) {
      router.push("/admin"); // Admin dashboard
    } else {
      router.push("/dashboard"); // User dashboard
    }
  } catch (error) {
    errorMessage.value = "Invalid username or password.";
  } finally {
    isSubmitting.value = false;
  }
};
</script>

<style scoped>
/* Styles for Login.vue */
</style>


<style scoped>
.login-container {
  width: 300px;
  margin: 0 auto;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
}

.form-group {
  margin-bottom: 15px;
}

input {
  width: 100%;
  padding: 10px;
  margin-top: 5px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

button {
  width: 100%;
  padding: 10px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:disabled {
  background-color: #ccc;
}

.error-message {
  color: red;
  margin-top: 15px;
}
</style>
