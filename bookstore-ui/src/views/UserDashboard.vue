<template>
  <div class="dashboard-container">
    <h1>User Dashboard</h1>
    <p>Welcome, {{ authStore.user?.username }}!</p>

    <section>
      <h2>Books</h2>
      <!-- Books section where users can browse, borrow, or return books -->
      <button @click="viewBooks">Browse Books</button>
    </section>
  </div>
</template>

<script setup lang="ts">
import { useAuthStore } from "@/store/auth.store";
import { useRouter } from "vue-router";

const authStore = useAuthStore();
const router = useRouter();

const viewBooks = () => {
  router.push("/books");
};
</script>

<style scoped>
/* Styles for UserDashboard.vue */
</style>
