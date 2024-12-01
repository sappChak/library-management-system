<template>
  <div class="dropdown">
    <button class="dropdown-btn" @click="toggleDropdown">{{ title }}</button>
    <ul v-if="isOpen" class="dropdown-menu">
      <slot />
    </ul>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";

defineProps({
  title: String,
});

const isOpen = ref(false);
const toggleDropdown = () => {
  isOpen.value = !isOpen.value;
};
</script>

<style scoped>
.dropdown {
  margin-bottom: 15px;
}

.dropdown-btn {
  background-color: transparent;
  color: white;
  border: none;
  padding: 12px 18px;
  font-size: 16px;
  text-align: left;
  display: block;
  width: 100%;
  cursor: pointer;
  border-radius: 8px;
  transition: background-color 0.3s ease;
}

.dropdown-btn:hover {
  background-color: rgba(255, 255, 255, 0.15);
}

.dropdown-menu {
  list-style: none;
  padding-left: 20px;
  margin-top: 10px;
}

.dropdown-menu li {
  margin-bottom: 10px;
}

.dropdown-menu a {
  color: white;
  text-decoration: none;
  padding: 8px 12px;
  display: block;
  border-radius: 8px;
  transition: background-color 0.3s ease;
}

.dropdown-menu a:hover {
  background-color: rgba(255, 255, 255, 0.1);
}
</style>
