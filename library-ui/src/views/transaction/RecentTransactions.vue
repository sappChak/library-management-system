<template>
  <section class="recent-transactions">
    <h2>Recent Transactions</h2>
    <table class="transactions-table">
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
        <tr v-for="transaction in recentTransactions" :key="transaction.id">
          <td>{{ transaction.id }}</td>
          <td>{{ transaction.username }}</td>
          <td>{{ transaction.bookTitle }}</td>
          <td>{{ transaction.action }}</td>
          <td>{{ transaction.date }}</td>
        </tr>
      </tbody>
    </table>
  </section>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { fetchTransactions } from '@/services/transaction.service';
import { Transaction } from '@/types/transaction';

const recentTransactions = ref<Transaction[]>([]);

const fetchRecentActivities = async () => {
  try {
    recentTransactions.value = await fetchTransactions();
  } catch (error) {
    console.error('Failed to fetch recent activities:', error);
  }
};

onMounted(() => {
  fetchRecentActivities();
});
</script>

<style scoped>
.recent-transactions {
  background-color: #1c1c1c;
  color: #f5f5f5;
  padding: 30px;
  border-radius: 15px;
  box-shadow: 0 6px 15px rgba(0, 0, 0, 0.2);
}

.recent-transactions h2 {
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

.transactions-table {
  width: 100%;
  border-collapse: collapse;
  overflow: hidden;
  border-radius: 10px;
}

.transactions-table thead {
  background-color: #333333;
  color: #f5f5f5;
}

.transactions-table th {
  text-align: center;
  padding: 14px 20px;
  font-size: 15px;
  text-transform: uppercase;
  font-weight: 600;
}

.transactions-table tbody {
  background-color: #2a2a2a;
}

.transactions-table td {
  text-align: center;
  padding: 14px 20px;
  font-size: 14px;
  color: #f5f5f5;
}

.transactions-table tbody tr {
  transition:
    background-color 0.3s ease,
    transform 0.2s ease;
}

.transactions-table tbody tr:hover {
  background-color: #444444;
  transform: translateY(-2px);
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

.transactions-table tbody tr:nth-child(even) {
  background-color: #3a3a3a;
}

.transactions-table tbody tr:nth-child(odd) {
  background-color: #2a2a2a;
}

@media (max-width: 768px) {
  .transactions-table {
    font-size: 13px;
  }

  .transactions-table th,
  .transactions-table td {
    padding: 10px 15px;
  }

  .recent-transactions h2 {
    font-size: 22px;
  }
}
</style>
