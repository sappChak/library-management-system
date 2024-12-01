<template>
  <div class="admin-dashboard">
    <!-- Sidebar -->
    <aside class="sidebar">
      <h2 class="logo">Library System</h2>
      <ul class="nav-links">
        <!-- Users Dropdown -->
        <li>
          <div class="dropdown">
            <button class="dropdown-btn" @click="toggleDropdown('users')">Manage Users</button>
            <ul v-if="isDropdownOpen.users" class="dropdown-menu">
              <li><router-link to="/admin/users/add">Add User</router-link></li>
              <li><router-link to="/admin/users/edit">Edit User</router-link></li>
              <li><router-link to="/admin/users/delete">Delete User</router-link></li>
              <li><router-link to="/admin/users/list">View Users</router-link></li>
            </ul>
          </div>
        </li>
        <!-- Books Dropdown -->
        <li>
          <div class="dropdown">
            <button class="dropdown-btn" @click="toggleDropdown('books')">Manage Books</button>
            <ul v-if="isDropdownOpen.books" class="dropdown-menu">
              <li><router-link to="/admin/books/add">Add Book</router-link></li>
              <li><router-link to="/admin/books/edit">Edit Book</router-link></li>
              <li><router-link to="/admin/books/delete">Delete Book</router-link></li>
              <li><router-link to="/admin/books/list">View Books</router-link></li>
            </ul>
          </div>
        </li>
      </ul>
      <button class="logout" @click="logout">Logout</button>
    </aside>

    <!-- Main Content -->
    <div class="main-content">
      <!-- Header -->
      <header class="header">
        <h1>Welcome, {{ authStore.user?.username || "Admin" }}</h1>
        <div class="profile-menu">
          <span>{{ authStore.user?.email || "admin@example.com" }}</span>
        </div>
      </header>

      <!-- Dashboard Widgets -->
      <section class="dashboard-widgets">
        <div class="widget">
          <h3>Total Users</h3>
          <p>{{ totalUsers }}</p>
        </div>
        <div class="widget">
          <h3>Books Available</h3>
          <p>{{ numberOfAvailableBooks }}</p>
        </div>
        <div class="widget">
          <h3>Active Borrowers</h3>
          <p>{{ activeBorrowers }}</p>
        </div>
        <div class="widget">
          <h3>Total Returns</h3>
          <p>{{ totalReturns }}</p>
        </div>
      </section>

      <!-- Recent Activities -->
      <section class="data-tables">
        <h2>Recent Activities</h2>
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Username</th>
              <th>Book Title</th>
              <th>Action</th>
              <th>Date</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="transaction in recentActivities" :key="transaction.id">
              <td>{{ transaction.id }}</td>
              <td>{{ transaction.username }}</td>
              <td>{{ transaction.bookTitle }}</td>
              <td>{{ transaction.action }}</td>
              <td>{{ transaction.date }}</td>
            </tr>
          </tbody>
        </table>
      </section>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useAuthStore } from "@/store/auth.store";
import { useBookStore } from "@/store/book.store";
import { Transaction } from "@/types/transaction";
import { fetchNumberOfActiveBorrows, fetchNumberOfReturns, fetchTransactions } from "@/api/transaction.api";
import { fetchNumberOfUsers } from "@/api/user.api";

const authStore = useAuthStore();
const bookStore = useBookStore();

const totalUsers = ref(0);
const numberOfAvailableBooks = ref(0);
const activeBorrowers = ref(0);
const totalReturns = ref(0);
const recentActivities = ref<Transaction[]>([]);

const isDropdownOpen = ref({
  users: false,
  books: false,
});

type DropdownKey = 'users' | 'books';

const fetchDashboardData = async () => {
  try {
    await bookStore.loadAvailableBooks();
    numberOfAvailableBooks.value = bookStore.getAvailableBooks.length;

    totalUsers.value = await fetchNumberOfUsers();
    activeBorrowers.value = await fetchNumberOfActiveBorrows();
    totalReturns.value = await fetchNumberOfReturns();
    recentActivities.value = await fetchTransactions();
  } catch (error) {
    console.error("Failed to load dashboard data:", error);
  }
};

// Toggle dropdown menu
const toggleDropdown = (dropdown: DropdownKey) => {
  isDropdownOpen.value[dropdown] = !isDropdownOpen.value[dropdown];
};

// Logout
const logout = () => {
  authStore.logout();
  window.location.reload();
};

// Fetch data on mount
onMounted(() => {
  fetchDashboardData();
});
</script>


<style scoped>
/* Main Layout */
.admin-dashboard {
  display: flex;
  height: 100vh;
  font-family: "Poppins", Arial, sans-serif;
  background-color: #f3f4f6;
}

/* Sidebar */
.sidebar {
  width: 280px;
  background: linear-gradient(135deg, #d32f2f, #c62828);
  color: white;
  padding: 25px 20px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1);
}

.sidebar .logo {
  font-size: 26px;
  margin-bottom: 30px;
  font-weight: bold;
  text-align: center;
  color: #ffffff;
  letter-spacing: 2px;
  text-transform: uppercase;
  border-bottom: 2px solid rgba(255, 255, 255, 0.3);
  padding-bottom: 15px;
}

.nav-links {
  list-style: none;
  padding: 0;
  margin: 0;
}

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
  display: block;
  width: 100%;
  cursor: pointer;
  border-radius: 8px;
  transition: background-color 0.3s ease;
}

.dropdown-btn:hover {
  background-color: rgba(255, 255, 255, 0.15);
}

.dropdown-menu {
  list-style: none;
  padding-left: 20px;
  margin-top: 10px;
}

.dropdown-menu li {
  margin-bottom: 10px;
}

.dropdown-menu a {
  color: white;
  text-decoration: none;
  padding: 8px 12px;
  display: block;
  border-radius: 8px;
  transition: background-color 0.3s ease;
}

.dropdown-menu a:hover {
  background-color: rgba(255, 255, 255, 0.1);
}

.logout {
  margin-top: auto;
  font-size: 16px;
  padding: 12px 20px;
  background-color: #b71c1c;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  text-align: center;
  transition: background-color 0.3s ease;
}

.logout:hover {
  background-color: #d32f2f;
}

/* Main Content */
.main-content {
  flex-grow: 1;
  padding: 30px;
  overflow-y: auto;
}

/* Header */
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  background-color: #ffffff;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  border-radius: 12px;
  margin-bottom: 30px;
}

.header h1 {
  margin: 0;
  font-size: 28px;
  color: #333333;
  font-weight: bold;
}

.header .profile-menu {
  font-size: 14px;
  color: #666666;
}

/* Widgets Section */
.dashboard-widgets {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(230px, 1fr));
  gap: 25px;
  margin-bottom: 40px;
}

.widget {
  background: linear-gradient(135deg, #d32f2f, #e57373);
  padding: 20px;
  border-radius: 12px;
  text-align: center;
  color: white;
  position: relative;
  overflow: hidden;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
  transition: transform 0.2s, box-shadow 0.2s;
}

.widget:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.2);
}

.widget h3 {
  font-size: 18px;
  font-weight: 500;
  margin-bottom: 12px;
}

.widget p {
  font-size: 24px;
  font-weight: bold;
  margin: 0;
}

/* Data Tables */
.data-tables {
  background-color: #ffffff;
  padding: 20px;
  border-radius: 12px;
  text-align: left;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

.data-tables table {
  width: 100%;
  border-collapse: collapse;
}

.data-tables th,
.data-tables td {
  padding: 12px 15px;
  border-bottom: 1px solid #f1f1f1;
  text-align: center;
  color: #333;
}

.data-tables th {
  background-color: #f5f5f5;
  font-size: 14px;
}

.data-tables td {
  font-size: 14px;
}

@media screen and (max-width: 768px) {
  .sidebar {
    width: 220px;
  }
}
</style>
