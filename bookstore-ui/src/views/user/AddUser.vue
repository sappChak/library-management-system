<template>
  <div class="add-user">
    <h2 class="form-title">Add User</h2>
    <form @submit.prevent="handleSubmit" class="form">
      <div class="form-group">
        <label for="username" class="form-label">Username:</label>
        <input
          id="username"
          type="text"
          v-model="username"
          class="form-input"
          placeholder="Enter username"
          required
        />
      </div>
      <div class="form-group">
        <label for="email" class="form-label">Email:</label>
        <input
          id="email"
          type="email"
          v-model="email"
          class="form-input"
          placeholder="Enter email"
          required
        />
      </div>
      <div class="form-group">
        <label for="password" class="form-label">Password:</label>
        <input
          id="password"
          type="password"
          v-model="password"
          class="form-input"
          placeholder="Enter password"
          required
        />
      </div>
      <button type="submit" class="submit-btn">Submit</button>
    </form>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { addUser } from '@/services/user.service'

const username = ref('')
const email = ref('')
const password = ref('')

const handleSubmit = async () => {
  try {
    const user: any = {
      username: username.value,
      email: email.value,
      password: password.value,
    }
    await addUser(user)
    alert('User added successfully!')
    username.value = ''
    email.value = ''
    password.value = ''
  } catch (error) {
    console.error('Error adding user:', error)
    alert('Failed to add user. Please try again.')
  }
}
</script>

<style scoped>
.form-title {
  font-size: 28px;
  color: #333333;
  text-align: left;
  margin-bottom: 24px;
  font-family: 'Roboto', sans-serif;
  font-weight: 700;
}

.form {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.form-group {
  display: flex;
  flex-direction: column;
}

.form-label {
  font-size: 16px;
  color: #666666;
  margin-bottom: 8px;
  font-family: 'Roboto', sans-serif;
}

.form-input {
  padding: 12px 14px;
  font-size: 16px;
  color: #333333;
  background-color: #f9f9f9;
  border: 1px solid #dddddd;
  border-radius: 8px;
  transition:
    border-color 0.3s,
    box-shadow 0.3s;
  font-family: 'Roboto', sans-serif;
}

.form-input:focus {
  border-color: #333333;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
  outline: none;
}

.submit-btn {
  padding: 12px 20px;
  font-size: 16px;
  font-weight: 700;
  color: #ffffff;
  background-color: #333333;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition:
    background-color 0.3s,
    transform 0.2s;
  font-family: 'Roboto', sans-serif;
}

.submit-btn:hover {
  background-color: #000000;
  transform: translateY(-2px);
}

.submit-btn:active {
  transform: translateY(0);
}

@media screen and (max-width: 480px) {
  .form-title {
    font-size: 24px;
  }

  .form-input {
    font-size: 14px;
    padding: 10px 12px;
  }

  .submit-btn {
    font-size: 14px;
    padding: 10px 16px;
  }
}
</style>
