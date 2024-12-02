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
  background: linear-gradient(135deg, #2a2a2a, #444444);
  /* Dark gradient background */
  padding: 20px;
  border-radius: 12px;
  text-align: center;
  color: white;
  /* White text for contrast */
  position: relative;
  overflow: hidden;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
  /* Slight shadow for depth */
  transition: transform 0.2s, box-shadow 0.2s ease-in-out;
  /* Smooth transition for hover effects */
}

.widget:hover {
  transform: translateY(-5px);
  /* Slight lift effect */
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.2);
  /* Darker shadow on hover */
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
