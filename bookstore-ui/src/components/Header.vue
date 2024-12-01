<template>
  <header class="header">
    <h1>Welcome, {{ username || "Admin" }}</h1>
    <div class="profile-menu">
      <span>{{ email || "admin@example.com" }}</span>
    </div>
  </header>
</template>

<script setup lang="ts">
defineProps({
  username: String,
  email: String,
});
</script>

<style scoped>
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
</style>
