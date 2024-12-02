<template>
  <aside class="sidebar" :class="{ collapsed: isCollapsed }" role="navigation" aria-label="Sidebar">
    <div class="header">
      <h2 class="logo">Library System</h2>
      <button class="toggle-btn" @click="toggleSidebar"
        :aria-label="isCollapsed ? 'Expand Sidebar' : 'Collapse Sidebar'">
        <span v-if="isCollapsed">☰</span>
        <span v-else>✖</span>
      </button>
    </div>

    <nav class="nav-links">
      <ul>
        <li @click="navigate('/admin/info')">General information</li>
      </ul>
      <!--Manage users -->
      <Dropdown title="Manage Users">
        <template #default>
          <router-link to="/admin/users/add">Add User</router-link>
          <router-link to="/admin/users/edit">Edit User</router-link>
          <router-link to="/admin/users/delete">Delete User</router-link>
          <router-link to="/admin/users/list">View Users</router-link>
        </template>
      </Dropdown>

      <Dropdown title="Manage Books">
        <template #default>
          <router-link to="/admin/books/add">Add Book</router-link>
          <router-link to="/admin/books/edit">Edit Book</router-link>
          <router-link to="/admin/books/delete">Delete Book</router-link>
          <router-link to="/admin/books/list">View Books</router-link>
        </template>
      </Dropdown>
    </nav>

    <button class="logout" @click="$emit('logout')" aria-label="Logout">
      Logout
    </button>
  </aside>
</template>

<script setup lang="ts">
import { ref } from "vue";
import Dropdown from "@/components/Dropdown.vue";
import { useRouter } from "vue-router";

const router = useRouter();

const navigate = (path: string) => {
  router.push(path);
}

const isCollapsed = ref(false);

const toggleSidebar = () => {
  isCollapsed.value = !isCollapsed.value;
};
</script>

<style scoped>
:root {
  --primary-color: #f06262;
  --secondary-color: #d32f2f;
  --hover-color: rgba(255, 255, 255, 0.15);
  --logout-hover-color: #c62828;
  --font-family: "Poppins", Arial, sans-serif;
}

.sidebar {
  width: 280px;
  height: 100vh;
  background: linear-gradient(135deg, #f28b82, #c62828);
  color: #ffffff;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 20px;
  transition: width 0.3s ease;
}

.sidebar.collapsed {
  width: 80px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.logo {
  font-size: 18px;
  font-weight: bold;
  letter-spacing: 1px;
  color: #fff;
  border-bottom: 2px solid rgba(255, 255, 255, 0.3);
  padding-bottom: 8px;
}

.toggle-btn {
  background: none;
  border: none;
  color: white;
  font-size: 18px;
  cursor: pointer;
  transition: transform 0.3s ease;
}

.nav-links {
  margin: 20px 0;
}

.nav-links a {
  display: block;
  color: white;
  padding: 10px 15px;
  margin: 5px 0;
  border-radius: 5px;
  text-decoration: none;
  transition: background-color 0.3s ease, transform 0.2s ease;
}

.nav-links a:hover {
  background-color: var(--hover-color);
  transform: translateX(5px);
}

.logout {
  margin-top: auto;
  font-size: 16px;
  padding: 12px 20px;
  background-color: var(--secondary-color);
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.2s ease;
}

.logout:hover {
  background-color: var(--logout-hover-color);
  transform: translateY(-3px);
}

@media screen and (max-width: 768px) {
  .sidebar {
    width: 220px;
  }

  .sidebar.collapsed {
    width: 60px;
  }

  .nav-links a {
    font-size: 14px;
    padding: 8px 10px;
  }

  .logout {
    font-size: 14px;
    padding: 10px 15px;
  }
}
</style>
