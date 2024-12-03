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
          <th></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="user in users" :key="user.id">
          <td>{{ user.id }}</td>
          <td>{{ user.username }}</td>
          <td>{{ user.email }}</td>
          <td>
            <span v-for="(role, index) in user.roles" :key="index">
              {{ role }}
              <span v-if="index < user.roles.length - 1">, </span>
            </span>
          </td>
          <td>
            <button
              class="delete-btn"
              @click="deleteUser(user.id)"
              aria-label="Delete user"
            >
              Delete
            </button>
          </td>
        </tr>
      </tbody>
    </table>
  </section>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { fetchUsers, deleteUserById } from '@/services/user.service'
import { UserResponse } from '@/types/user'

const users = ref<UserResponse[]>([])

const fetchUsersData = async () => {
  try {
    users.value = await fetchUsers()
  } catch (error) {
    console.error('Failed to fetch users:', error)
  }
}

const deleteUser = async (id: number) => {
  try {
    const confirmDelete = confirm('Are you sure you want to delete this user?')
    if (confirmDelete) {
      await deleteUserById(id)
      users.value = users.value.filter((user) => user.id !== id) // Remove user locally
      alert(`User with ID ${id} has been deleted successfully.`)
    }
  } catch (error) {
    console.error('Failed to delete user:', error)
    alert('Error deleting the user. Please try again.')
  }
}

onMounted(() => {
  fetchUsersData()
})
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
  transition:
    background-color 0.3s ease,
    transform 0.2s ease;
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

.delete-btn {
  background-color: #ff6f61;
  color: white;
  border: none;
  padding: 8px 12px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 14px;
  transition:
    background-color 0.3s ease,
    transform 0.2s ease;
}

.delete-btn:hover {
  background-color: #ff4a36;
  transform: translateY(-2px);
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
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
