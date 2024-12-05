<template>
  <div>
    <section class="dashboard-widgets">
      <Widget title="My Borrowed Books" :value="borrowedBooks" />
      <Widget title="Books Due Soon" :value="dueSoonBooks" />
      <Widget title="Fines Pending" :value="pendingFines" />
    </section>
    <MyRecentActivities />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useAuthStore } from '@/store/auth.store'
import Widget from '@/components/ui/Widget.vue'

const authStore = useAuthStore()

const borrowedBooks = ref(0)
const dueSoonBooks = ref(0)
const pendingFines = ref(0)

const fetchUserDashboardData = async () => {
  try {
    // borrowedBooks.value = await fetchMyBorrowedBooks();
    // dueSoonBooks.value = await fetchBooksDueSoon();
    // pendingFines.value = await fetchPendingFines();
  } catch (error) {
    console.error('Failed to load user dashboard data:', error)
  }
}

onMounted(() => {
  fetchUserDashboardData()
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
