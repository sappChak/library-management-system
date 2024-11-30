<template>
  <div class="admin-dashboard-container">
    <h1>Admin Dashboard</h1>
    <p>Welcome, Admin {{ authStore.user?.username }}!</p>

    <section>
      <h2>Manage Books</h2>
      <button @click="manageBooks">Manage Books</button>
    </section>

    <section>
      <h2>Manage Users</h2>
      <button @click="manageUsers">Manage Users</button>
    </section>
  </div>
</template>

<script setup lang="ts">
import { useAuthStore } from "@/store/auth.store";
import { useRouter } from "vue-router";

const authStore = useAuthStore();
const router = useRouter();

const manageBooks = () => {
  router.push("/admin/books");
};

const manageUsers = () => {
  router.push("/admin/users");
};
</script>

<style scoped>
/* Styles for AdminDashboard.vue */
</style>
