<template>
  <div class="login-container">
    <h1>Login</h1>
    <form @submit.prevent="handleLogin">
      <div class="form-group">
        <label for="username">Username</label>
        <input
          v-model="username"
          type="text"
          id="username"
          placeholder="Enter your username"
          required
        />
      </div>
      <div class="form-group">
        <label for="password">Password</label>
        <input
          v-model="password"
          type="password"
          id="password"
          placeholder="Enter your password"
          required
        />
      </div>
      <button type="submit" :disabled="isSubmitting">Login</button>
    </form>

    <div v-if="errorMessage" class="error-message">
      <p>{{ errorMessage }}</p>
    </div>

    <p class="register-link">
      Don't have an account? <router-link to="/register">Register</router-link>
    </p>
  </div>
</template>

<script setup lang="ts">
import { useAuthStore } from '@/store/auth.store'
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const authStore = useAuthStore()
const router = useRouter()

const username = ref('')
const password = ref('')
const isSubmitting = ref(false)
const errorMessage = ref('')

const handleLogin = async () => {
  isSubmitting.value = true
  errorMessage.value = ''

  try {
    await authStore.login({
      username: username.value,
      password: password.value,
    })

    if (authStore.isAdmin) {
      router.push('/admin/home')
    } else {
      router.push('/user/home')
    }
  } catch (error) {
    errorMessage.value = 'Invalid username or password.'
  } finally {
    isSubmitting.value = false
  }
}
</script>

<style scoped>
.login-container {
  width: 400px;
  margin: 0 auto;
  padding: 30px;
  background-color: #fff;
  color: #000;
  border-radius: 12px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  font-family: 'Arial', sans-serif;
  text-align: center;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

h1 {
  margin-bottom: 20px;
  font-size: 26px;
  color: #000;
}

.form-group {
  margin-bottom: 20px;
  text-align: left;
}

label {
  display: block;
  margin-bottom: 8px;
  font-weight: bold;
  color: #333;
}

input {
  width: 100%;
  padding: 12px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 6px;
  background-color: #fafafa;
  color: #000;
  transition: all 0.3s ease;
}

input:focus {
  border-color: #000;
  background-color: #fff;
  outline: none;
  box-shadow: 0 0 4px rgba(0, 0, 0, 0.4);
}

button {
  width: 100%;
  padding: 12px;
  font-size: 16px;
  font-weight: bold;
  color: #fff;
  background-color: #000;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  transition: all 0.3s ease;
}

button:hover {
  background-color: #333;
}

button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.error-message {
  margin-top: 15px;
  font-size: 14px;
  color: #e74c3c;
}

p {
  margin-top: 30px;
  font-size: 18px;
  color: #333;
  font-weight: semi-bold;
}

p a {
  color: #000;
  text-decoration: none;
  font-weight: bold;
  font-size: 18px;
}

p a:hover {
  text-decoration: underline;
}

@media (max-width: 500px) {
  .login-container {
    width: 90%;
    padding: 20px;
  }

  h1 {
    font-size: 20px;
  }

  button {
    font-size: 14px;
  }

  p {
    font-size: 16px;
  }
}
</style>
