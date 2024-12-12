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
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="user in users" :key="user.id">
          <td>{{ user.id }}</td>
          <td>
            <div v-if="editingUserId !== user.id">{{ user.username }}</div>
            <input
              v-else
              v-model="editableUser.username"
              type="text"
              class="edit-input"
            />
          </td>
          <td>
            <div v-if="editingUserId !== user.id">{{ user.email }}</div>
            <input
              v-else
              v-model="editableUser.email"
              type="text"
              class="edit-input"
            />
          </td>
          <td>
            <div v-if="editingUserId !== user.id">
              <span v-for="(role, index) in user.roles" :key="index">
                {{ role }}
                <span v-if="index < user.roles.length - 1">, </span>
              </span>
            </div>
          </td>
          <td>
            <button
              class="delete-btn"
              @click="deleteUser(user.id)"
              aria-label="Delete user"
            >
              Delete
            </button>
            <button
              class="edit-btn"
              v-if="editingUserId !== user.id"
              @click="enableEdit(user)"
              aria-label="Edit user"
            >
              Edit
            </button>
            <button
              class="save-btn"
              v-else
              @click="saveEdit(user.id)"
              aria-label="Save user"
            >
              Save
            </button>
            <button
              class="cancel-btn"
              v-if="editingUserId === user.id"
              @click="cancelEdit"
              aria-label="Cancel editing"
            >
              Cancel
            </button>
          </td>
        </tr>
      </tbody>
    </table>
  </section>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import {
  fetchUsers,
  deleteUserById,
  updateUser,
} from '@/services/user.service';

const users = ref<UserResponse[]>([]);
const editingUserId = ref<number | null>(null);
const editableUser = ref<UpdateUserRequest>({});

const fetchUsersData = async () => {
  try {
    const fetchedUsers = await fetchUsers();
    users.value = fetchedUsers;
  } catch (error) {
    console.error('Failed to fetch users:', error);
  }
};

const deleteUser = async (id: number) => {
  try {
    const confirmDelete = confirm('Are you sure you want to delete this user?');
    if (confirmDelete) {
      await deleteUserById(id);
      users.value = users.value.filter((user) => user.id !== id);
    }
  } catch (error) {
    console.error('Failed to delete user:', error);
    alert('Error deleting the user. Please try again.');
  }
};

const enableEdit = (user: UserResponse) => {
  editingUserId.value = user.id;
  editableUser.value = {
    username: user.username,
    email: user.email,
  };
};

const saveEdit = async (id: number) => {
  try {
    const updatedUser: UpdateUserRequest = {
      username: editableUser.value.username,
      email: editableUser.value.email,
    };

    const response = await updateUser(id, updatedUser);

    const index = users.value.findIndex((user) => user.id === id);
    if (index !== -1) {
      users.value[index] = response;
    }

    cancelEdit();
  } catch (error) {
    console.error('Failed to update user:', error);
    alert('Error updating the user. Please try again.');
  }
};

const cancelEdit = () => {
  editingUserId.value = null;
  editableUser.value = {};
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
  padding-bottom: 40px;
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

.users-table th,
.users-table td {
  text-align: center;
  padding: 14px 20px;
  font-size: 14px;
  color: #f5f5f5;
}

.edit-input {
  width: 80%;
  padding: 6px;
  font-size: 14px;
  border-radius: 5px;
  border: 1px solid #ddd;
  background-color: #333;
  color: #f5f5f5;
}

.users-table tbody tr:hover {
  background-color: #444444;
  transform: translateY(-2px);
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

/* Button Styles */
button {
  font-size: 14px;
  padding: 8px 14px;
  border-radius: 5px;
  cursor: pointer;
  border: none;
  transition:
    background-color 0.3s,
    transform 0.2s,
    box-shadow 0.2s;
}

button:focus {
  outline: none;
}

.delete-btn {
  background-color: #ff6f61;
  /* Red color for delete */
  color: white;
}

.delete-btn:hover {
  background-color: #e75d53;
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.edit-btn {
  background-color: #4caf50;
  /* Green color for edit */
  color: white;
  margin-right: 5px;
}

.edit-btn:hover {
  background-color: #45a049;
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.save-btn {
  background-color: #2196f3;
  /* Blue color for save */
  color: white;
  margin-right: 5px;
}

.save-btn:hover {
  background-color: #1976d2;
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.cancel-btn {
  background-color: #f44336;
  /* Red color for cancel */
  color: white;
}

.cancel-btn:hover {
  background-color: #e53935;
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

/* Responsive Design for Mobile */
@media (max-width: 768px) {
  .users-table th,
  .users-table td {
    padding: 10px 15px;
  }

  .view-users h2 {
    font-size: 22px;
  }

  button {
    font-size: 12px;
    padding: 6px 12px;
  }

  .edit-input {
    width: 100%;
    font-size: 14px;
  }
}
</style>
