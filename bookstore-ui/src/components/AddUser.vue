<template>
  <div>
    <h2>Add User</h2>
    <form>
      <label>
        Username:
        <input type="text" v-model="username" />
      </label>
      <button type="submit">Submit</button>
    </form>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";

const username = ref("");
</script>

<style scoped>
/* Add specific styles here */
</style>

