<template>
  <div class="admin-dashboard">
    <!-- Sidebar -->
    <Sidebar @logout="logout" />
    <!-- Main Content -->
    <div class="main-content">
      <Header :username="authStore.user?.username" :email="authStore.user?.email" />
      <section class="dashboard-widgets">
        <DashboardWidget title="Total Users" :value="totalUsers" />
        <DashboardWidget title="Books Available" :value="numberOfAvailableBooks" />
        <DashboardWidget title="Active Borrowers" :value="activeBorrowers" />
        <DashboardWidget title="Total Returns" :value="totalReturns" />
      </section>
      <RecentActivities />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useAuthStore } from "@/store/auth.store";
import { useBookStore } from "@/store/book.store";

import RecentActivities from "@/components/RecentActivities.vue";
import DashboardWidget from "@/components/DashboardWidget.vue";
import Header from "@/components/Header.vue";
import Sidebar from "@/components/Sidebar.vue";
import { fetchNumberOfActiveBorrows, fetchNumberOfReturns, fetchNumberOfUsers } from "@/api";

const authStore = useAuthStore();
const bookStore = useBookStore();

const totalUsers = ref(0);
const numberOfAvailableBooks = ref(0);
const activeBorrowers = ref(0);
const totalReturns = ref(0);

const fetchDashboardData = async () => {
  try {
    await bookStore.loadAvailableBooks();
    numberOfAvailableBooks.value = bookStore.getAvailableBooks.length;

    totalUsers.value = await fetchNumberOfUsers();
    activeBorrowers.value = await fetchNumberOfActiveBorrows();
    totalReturns.value = await fetchNumberOfReturns();
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
.admin-dashboard {
  display: flex;
  height: 100vh;
  font-family: "Poppins", Arial, sans-serif;
  /* background-color: #f3f4f6; */
}

.dashboard-widgets {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(230px, 1fr));
  gap: 25px;
  margin-bottom: 40px;
}

.main-content {
  flex-grow: 1;
  padding: 30px;
  overflow-y: auto;
}
</style>
