<template>
  <div>
    <Header :username="username" :email="email" />
    <section class="dashboard-widgets">
      <DashboardWidget title="Total Users" :value="totalUsers" />
      <DashboardWidget title="Books Available" :value="numberOfAvailableBooks" />
      <DashboardWidget title="Active Borrowers" :value="activeBorrowers" />
      <DashboardWidget title="Total Returns" :value="totalReturns" />
    </section>
    <RecentTransactions />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import {
  fetchNumberOfActiveBorrows,
  fetchNumberOfReturns,
  fetchNumberOfUsers,
  fetchNumberOfAvailableBooks,
} from '@/services'
import { useAuthStore } from '@/store/auth.store'
import DashboardWidget from '@/components/ui/Widget.vue'
import RecentTransactions from '@/views/transaction/RecentTransactions.vue'
import Header from '@/components/layout/Header.vue'

const authStore = useAuthStore()

const totalUsers = ref(0)
const numberOfAvailableBooks = ref(0)
const activeBorrowers = ref(0)
const totalReturns = ref(0)

const username = authStore.user?.username || 'Admin'
const email = authStore.user?.email || ''

const fetchDashboardData = async () => {
  try {
    totalUsers.value = await fetchNumberOfUsers()
    activeBorrowers.value = await fetchNumberOfActiveBorrows()
    numberOfAvailableBooks.value = await fetchNumberOfAvailableBooks()
    totalReturns.value = await fetchNumberOfReturns()
  } catch (error) {
    console.error('Failed to load dashboard data:', error)
  }
}

onMounted(() => {
  fetchDashboardData()
})
</script>

<style scoped>
.dashboard-widgets {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(230px, 1fr));
  gap: 25px;
  margin-bottom: 40px;
}
</style>
