<template>
  <aside class="sidebar" :class="{ collapsed: isCollapsed }" role="navigation" aria-label="Sidebar">
    <div class="header">
      <h2 class="logo" :class="{ collapsed: isCollapsed }">
        <img v-if="isCollapsed" src="@/assets/logo-icon.png" alt="Library Logo" />
        <span v-else>Library System</span>
      </h2>
      <button class="toggle-btn" @click="toggleSidebar"
        :aria-label="isCollapsed ? 'Expand Sidebar to view navigation options' : 'Collapse Sidebar'">
        <span v-if="isCollapsed">☰</span>
        <span v-else>✖</span>
      </button>
    </div>

    <nav class="nav-links">
      <router-link to="/admin" class="nav-link" active-class="active">Home</router-link>

      <Dropdown title="Manage Users">
        <ul class="dropdown-list">
          <li><router-link to="/admin/users" class="nav-link">View Users</router-link></li>
          <li><router-link to="/admin/users/add" class="nav-link">Add User</router-link></li>
        </ul>
      </Dropdown>

      <Dropdown title="Manage Books">
        <ul class="dropdown-list">
          <li><router-link to="/admin/books" class="nav-link">View Books</router-link></li>
          <li><router-link to="/admin/books/add" class="nav-link">Add Book</router-link></li>
          <li><router-link to="/admin/books/edit" class="nav-link">Edit Book</router-link></li>
          <li><router-link to="/admin/books/delete" class="nav-link">Delete Book</router-link></li>
        </ul>
      </Dropdown>
    </nav>

    <button class="logout" @click="$emit('logout')" aria-label="Logout">Logout</button>
  </aside>
</template>

<script setup lang="ts">
import { ref } from "vue";
import Dropdown from "@/components/Dropdown.vue";

const isCollapsed = ref(false);

const toggleSidebar = () => {
  isCollapsed.value = !isCollapsed.value;
};
</script>

<style scoped>
/* Sidebar Styles */
.sidebar {
  background-color: #333;
  /* Darker background for modern minimalism */
  color: #f5f5f5;
  /* Light text color for contrast */
  width: 250px;
  min-height: 100vh;
  padding: 20px;
  border-radius: 12px;
  /* Slightly rounded corners for a modern feel */
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.2);
  transition: all 0.3s ease-in-out;
  overflow: hidden;
}

.sidebar.collapsed {
  width: 70px;
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 25px;
  border-bottom: 2px solid #444;
  /* Lighter border */
  padding-bottom: 12px;
}

.logo {
  font-family: 'Roboto', sans-serif;
  /* Clean, minimalistic font */
  font-size: 1.4rem;
  font-weight: 500;
  color: #ffffff;
  text-transform: uppercase;
  letter-spacing: 1px;
  display: flex;
  align-items: center;
}

.logo img {
  height: 35px;
  width: 35px;
  margin-right: 10px;
}

.toggle-btn {
  background: none;
  border: none;
  color: #ffffff;
  font-size: 1.5rem;
  cursor: pointer;
  transition: color 0.3s ease;
}

.toggle-btn:hover {
  color: #ff6f61;
  /* Accent color */
}

/* Navigation Links */
.nav-links {
  margin-top: 15px;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.nav-link {
  padding: 12px 18px;
  border-radius: 8px;
  font-size: 15px;
  color: #f5f5f5;
  text-decoration: none;
  font-family: 'Roboto', sans-serif;
  transition: background-color 0.3s ease, transform 0.2s ease;
  display: block;
}

.nav-link:hover {
  background-color: #444444;
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.nav-link.active {
  background-color: #555555;
  font-weight: 600;
}

/* Dropdown Styles */
.dropdown {
  margin-bottom: 15px;
}

.dropdown-btn {
  background-color: transparent;
  color: white;
  border: none;
  padding: 12px 18px;
  font-size: 16px;
  text-align: left;
  width: 100%;
  cursor: pointer;
  border-radius: 8px;
  transition: background-color 0.3s ease;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.dropdown-btn:hover {
  background-color: rgba(255, 255, 255, 0.1);
}

.dropdown-list {
  list-style: none;
  padding: 0;
  margin-top: 10px;
}

.dropdown-list li {
  margin-bottom: 8px;
}

.dropdown-list a {
  color: white;
  text-decoration: none;
  padding: 10px 15px;
  display: block;
  font-family: 'Roboto', sans-serif;
  border-radius: 8px;
  transition: background-color 0.3s ease;
}

.dropdown-list a:hover {
  background-color: #444444;
}

/* Logout Button */
.logout {
  margin-top: auto;
  padding: 12px 20px;
  background-color: #ff6f61;
  /* Modern accent color */
  color: #ffffff;
  border: none;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  width: calc(100% - 40px);
  transition: background-color 0.3s ease, transform 0.2s ease;
}

.logout:hover {
  background-color: #ff4a36;
  transform: translateY(-2px);
}

/* Responsive Styles */
@media (max-width: 768px) {
  .sidebar {
    width: 100%;
  }

  .nav-links {
    gap: 8px;
  }

  .nav-link {
    font-size: 13px;
  }

  .dropdown-btn {
    font-size: 14px;
  }

  .logout {
    padding: 10px 15px;
  }
}
</style>
