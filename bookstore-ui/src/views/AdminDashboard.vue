<template>
  <div class="admin-dashboard">
    <!-- Sidebar -->
    <aside class="sidebar">
      <h2 class="logo">Library System</h2>
      <ul class="nav-links">
        <li><router-link to="/admin">Dashboard</router-link></li>
        <li><router-link to="/admin/users">Users</router-link></li>
        <li><router-link to="/admin/categories">Categories</router-link></li>
        <li><router-link to="/admin/books">Books</router-link></li>
        <li><router-link to="/admin/borrow">Book Borrow</router-link></li>
        <li><router-link to="/admin/return">Book Return</router-link></li>
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

const logout = () => {
  authStore.logout();
  window.location.reload();
};

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
  background: linear-gradient(135deg, #2e7d32, #388e3c);
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

.sidebar .nav-links {
  list-style: none;
  padding: 0;
  margin: 0;
}

.sidebar .nav-links li {
  margin-bottom: 15px;
}

.sidebar .nav-links a {
  color: white;
  text-decoration: none;
  font-size: 16px;
  padding: 12px 18px;
  display: block;
  border-radius: 8px;
  transition: background-color 0.3s ease, transform 0.3s ease;
}

.sidebar .nav-links a:hover {
  background-color: rgba(255, 255, 255, 0.15);
  transform: scale(1.05);
}

.sidebar .logout {
  margin-top: auto;
  font-size: 16px;
  padding: 12px 20px;
  background-color: #d32f2f;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  text-align: center;
  transition: background-color 0.3s, transform 0.3s;
}

.sidebar .logout:hover {
  background-color: #b71c1c;
  transform: translateY(-3px);
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
  background: linear-gradient(135deg, #4caf50, #81c784);
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
  box-shadow: 0 6px 15px rgba(0, 0, 0, 0.3);
}

.widget::before {
  content: "";
  position: absolute;
  top: -40px;
  right: -40px;
  width: 100px;
  height: 100px;
  background: rgba(255, 255, 255, 0.15);
  border-radius: 50%;
  z-index: 0;
}

.widget h3 {
  margin: 0 0 12px;
  font-size: 18px;
  font-weight: bold;
  position: relative;
  z-index: 1;
}

.widget p {
  font-size: 26px;
  font-weight: bold;
  margin: 0;
  position: relative;
  z-index: 1;
}

/* Data Tables */
.data-tables {
  background-color: #ffffff;
  padding: 30px;
  border-radius: 12px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

.data-tables h2 {
  margin: 0 0 20px;
  font-size: 22px;
  color: #333333;
  font-weight: bold;
  border-bottom: 3px solid #4caf50;
  display: inline-block;
  padding-bottom: 5px;
}

.data-tables table {
  width: 100%;
  border-collapse: collapse;
  font-size: 15px;
}

.data-tables table th,
.data-tables table td {
  padding: 15px;
  text-align: left;
  border-bottom: 1px solid #eeeeee;
  text-align: center;
}

.data-tables table th {
  background-color: #4caf50;
  color: white;
  font-weight: bold;
}

.data-tables table td {
  color: #555555;
}

.data-tables table tr:hover {
  background-color: #f9fbe7;
}

/* Media Queries for Responsiveness */
@media screen and (max-width: 768px) {
  .sidebar {
    width: 220px;
  }

  .dashboard-widgets {
    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  }
}
</style>
