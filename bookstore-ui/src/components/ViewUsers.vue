<template>
  <section class="view-users">
    <h2>Users</h2>
    <table class="users-table">
      <thead>
        <tr>
          <th>ID</th>
          <th>Username</th>
          <th>Email</th>
          <th>Roles</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="user in users" :key="user.id">
          <td>{{ user.id }}</td>
          <td>{{ user.username }}</td>
          <td>{{ user.email }}</td>
          <td>
            <span v-for="(role, index) in user.roles" :key="index">{{ role }}<span
                v-if="index < user.roles.length - 1">, </span></span>
          </td>
        </tr>
      </tbody>
    </table>
  </section>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { fetchUsers } from "@/api/user.api";
import { UserResponse } from "@/types/user";

const users = ref<UserResponse[]>([]);

// Fetch users data from the backend API
const fetchUsersData = async () => {
  try {
    users.value = await fetchUsers();
  } catch (error) {
    console.error("Failed to fetch users:", error);
  }
};

onMounted(() => {
  fetchUsersData();
});
</script>

<style scoped>
.view-users {
  background-color: #1c1c1c;
  color: #f5f5f5;
  padding: 30px;
  border-radius: 15px;
  box-shadow: 0 6px 15px rgba(0, 0, 0, 0.2);
}

.view-users h2 {
  margin-bottom: 20px;
  font-size: 26px;
  color: #ffffff;
  font-weight: 600;
  border-bottom: 2px solid #ffffff;
  padding-bottom: 10px;
  text-transform: uppercase;
  letter-spacing: 1px;
  text-align: center;
}

.users-table {
  width: 100%;
  border-collapse: collapse;
  overflow: hidden;
  border-radius: 10px;
}

.users-table thead {
  background-color: #333333;
  color: #f5f5f5;
}

.users-table th {
  text-align: center;
  padding: 14px 20px;
  font-size: 15px;
  text-transform: uppercase;
  font-weight: 600;
}

.users-table tbody {
  background-color: #2a2a2a;
  /* Slightly lighter dark background */
}

.users-table td {
  text-align: center;
  padding: 14px 20px;
  font-size: 14px;
  color: #f5f5f5;
}

.users-table tbody tr {
  transition: background-color 0.3s ease, transform 0.2s ease;
}

.users-table tbody tr:hover {
  background-color: #444444;
  transform: translateY(-2px);
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

.users-table tbody tr:nth-child(even) {
  background-color: #3a3a3a;
}

.users-table tbody tr:nth-child(odd) {
  background-color: #2a2a2a;
}

@media (max-width: 768px) {
  .users-table {
    font-size: 13px;
  }

  .users-table th,
  .users-table td {
    padding: 10px 15px;
  }

  .view-users h2 {
    font-size: 22px;
  }
}
</style>
