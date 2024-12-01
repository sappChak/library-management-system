<template>
  <div class="add-user-form">
    <h2>Add New User</h2>
    <form @submit.prevent="createUser" class="form">
      <div class="form-group">
        <label for="username">Username:</label>
        <input id="username" v-model="newUser.username" type="text" required />
      </div>
      <div class="form-group">
        <label for="email">Email:</label>
        <input id="email" v-model="newUser.email" type="email" required />
      </div>
      <div class="form-group">
        <label for="password">Password:</label>
        <input id="password" v-model="newUser.password" type="password" required />
      </div>
      <div class="form-actions">
        <button type="submit" class="btn-primary">Add User</button>
        <button type="reset" class="btn-secondary" @click="resetForm">Clear</button>
      </div>
    </form>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { addUser } from "@/api/user.api";

const newUser = ref({
  username: "",
  email: "",
  password: "",
});

const createUser = async () => {
  try {
    await addUser(newUser.value);
    alert("User added successfully!");
    resetForm();
  } catch (error) {
    console.error("Error adding user:", error);
    alert("Failed to add user.");
  }
};

const resetForm = () => {
  newUser.value = { username: "", email: "", password: "" };
};
</script>

<style scoped>
.add-user-form {
  background-color: #f8f9fa;
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  max-width: 600px;
  margin: 2rem auto;
}

h2 {
  text-align: center;
  color: #343a40;
  margin-bottom: 1.5rem;
}

.form {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.form-group {
  display: flex;
  flex-direction: column;
}

label {
  font-weight: bold;
  margin-bottom: 0.5rem;
  color: #495057;
}

input {
  padding: 0.75rem;
  border: 1px solid #ced4da;
  border-radius: 4px;
  font-size: 1rem;
  transition: border-color 0.2s ease-in-out;
}

input:focus {
  border-color: #007bff;
  outline: none;
}

.form-actions {
  display: flex;
  justify-content: space-between;
}

.btn-primary {
  background-color: #007bff;
  color: #fff;
  border: none;
  padding: 0.75rem 1.5rem;
  border-radius: 4px;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.btn-primary:hover {
  background-color: #0056b3;
}

.btn-secondary {
  background-color: #6c757d;
  color: #fff;
  border: none;
  padding: 0.75rem 1.5rem;
  border-radius: 4px;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.btn-secondary:hover {
  background-color: #5a6268;
}
</style>
