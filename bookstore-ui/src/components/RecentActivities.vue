<template>
  <section class="recent-activities">
    <h2>Recent Activities</h2>
    <table class="activities-table">
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
</template>


<script setup lang="ts">
import { ref, onMounted } from "vue";
import { fetchTransactions } from "@/api/transaction.api";
import { Transaction } from "@/types/transaction";

const recentActivities = ref<Transaction[]>([]);

const fetchRecentActivities = async () => {
  try {
    recentActivities.value = await fetchTransactions();
  } catch (error) {
    console.error("Failed to fetch recent activities:", error);
  }
};

onMounted(() => {
  fetchRecentActivities();
});

</script>

<style scoped>
.recent-activities {
  background-color: #f9fafb;
  padding: 30px;
  border-radius: 15px;
  box-shadow: 0 6px 15px rgba(0, 0, 0, 0.1);
}

.recent-activities h2 {
  margin-bottom: 20px;
  font-size: 24px;
  color: #333;
  font-weight: 600;
  border-bottom: 2px solid #d32f2f;
  padding-bottom: 10px;
  text-transform: uppercase;
  letter-spacing: 1px;
  text-align: center;
  /* Center-align the heading */
}

.activities-table {
  width: 100%;
  border-collapse: collapse;
  overflow: hidden;
  border-radius: 10px;
}

.activities-table thead {
  background-color: #d32f2f;
  color: white;
}

.activities-table th {
  text-align: center;
  /* Center-align the header cells */
  padding: 12px 20px;
  font-size: 14px;
  text-transform: uppercase;
  font-weight: 500;
}

.activities-table tbody {
  background-color: #fff;
}

.activities-table td {
  text-align: center;
  /* Center-align the body cells */
  padding: 12px 20px;
  font-size: 14px;
  color: #555;
}

.activities-table tbody tr {
  transition: background-color 0.2s ease, transform 0.2s ease;
}

.activities-table tbody tr:hover {
  background-color: #f1f5f9;
  transform: translateY(-3px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.activities-table tbody tr:nth-child(even) {
  background-color: #f8f9fa;
}

.activities-table tbody tr:nth-child(odd) {
  background-color: #ffffff;
}

@media (max-width: 768px) {
  .activities-table {
    font-size: 12px;
  }

  .activities-table th,
  .activities-table td {
    padding: 10px 15px;
  }

  .recent-activities h2 {
    font-size: 20px;
  }
}
</style>
