<template>
  <div class="widget">
    <h3>{{ title }}</h3>
    <p>{{ value }}</p>
  </div>
</template>

<script setup lang="ts">
const props = defineProps({
  title: String,
  value: [String, Number],
});
</script>

<style scoped>
/* Widgets Section */

.dashboard-widgets {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(230px, 1fr));
  gap: 25px;
  margin-bottom: 40px;
}

.widget {
  background: linear-gradient(135deg, #d32f2f, #e57373);
  padding: 20px;
  border-radius: 12px;
  text-align: center;
  color: white;
  position: relative;
  overflow: hidden;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
  transition: transform 0.2s, box-shadow 0.2s;
}

.widget:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.2);
}

.widget h3 {
  font-size: 18px;
  font-weight: 500;
  margin-bottom: 12px;
}

.widget p {
  font-size: 24px;
  font-weight: bold;
  margin: 0;
}
</style>
