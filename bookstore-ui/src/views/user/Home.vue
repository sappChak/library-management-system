<template>
  <div>
    <section class="dashboard-widgets">
      <Widget title="My Borrowed Books" :value="borrowedBooks" />
      <Widget title="Books Due Soon" :value="dueSoonBooks" />
    </section>
    <MyRecentTransactions />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { fetchBorrowedBooksCount } from '@/services';
import Widget from '@/components/ui/Widget.vue';
import MyRecentTransactions from '@/views/user/MyRecentTransactions.vue';

const borrowedBooks = ref(0);
const dueSoonBooks = ref(1);

const fetchUserDashboardData = async () => {
  try {
    borrowedBooks.value = await fetchBorrowedBooksCount();
  } catch (error) {
    console.error('Failed to load user dashboard data:', error);
  }
};

onMounted(() => {
  fetchUserDashboardData();
});
</script>

<style scoped>
.dashboard-widgets {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(230px, 1fr));
  gap: 25px;
  margin-bottom: 40px;
}
</style>
