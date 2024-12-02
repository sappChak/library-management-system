<template>
  <section class="view-books">
    <h2>Books</h2>
    <table class="books-table">
      <thead>
        <tr>
          <th>ID</th>
          <th>Title</th>
          <th>Author</th>
          <th>Available Copies</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="book in books" :key="book.id">
          <td>{{ book.id }}</td>
          <td>{{ book.title }}</td>
          <td>{{ book.author }}</td>
          <td>{{ book.availableCopies }}</td>
        </tr>
      </tbody>
    </table>
  </section>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { fetchAvailableBooks } from "@/api/book.api";
import { Book } from "@/types/book";

const books = ref<Book[]>([]);

const fetchBooksData = async () => {
  try {
    books.value = await fetchAvailableBooks();
  } catch (error) {
    console.error("Failed to fetch books:", error);
  }
};

onMounted(() => {
  fetchBooksData();
});
</script>

<style scoped>
.view-books {
  background-color: #1c1c1c;
  color: #f5f5f5;
  padding: 30px;
  border-radius: 15px;
  box-shadow: 0 6px 15px rgba(0, 0, 0, 0.2);
}

.view-books h2 {
  margin-bottom: 20px;
  font-size: 26px;
  color: #ffffff;
  font-weight: 600;
  border-bottom: 2px solid #ffffff;
  padding-bottom: 10px;
  text-transform: uppercase;
  letter-spacing: 1px;
  text-align: center;
}

.books-table {
  width: 100%;
  border-collapse: collapse;
  overflow: hidden;
  border-radius: 10px;
}

.books-table thead {
  background-color: #333333;
  color: #f5f5f5;
}

.books-table th {
  text-align: center;
  padding: 14px 20px;
  font-size: 15px;
  text-transform: uppercase;
  font-weight: 600;
}

.books-table tbody {
  background-color: #2a2a2a;
}

.books-table td {
  text-align: center;
  padding: 14px 20px;
  font-size: 14px;
  color: #f5f5f5;
}

.books-table tbody tr {
  transition: background-color 0.3s ease, transform 0.2s ease;
}

.books-table tbody tr:hover {
  background-color: #444444;
  transform: translateY(-2px);
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

.books-table tbody tr:nth-child(even) {
  background-color: #3a3a3a;
}

.books-table tbody tr:nth-child(odd) {
  background-color: #2a2a2a;
}

@media (max-width: 768px) {
  .books-table {
    font-size: 13px;
  }

  .books-table th,
  .books-table td {
    padding: 10px 15px;
  }

  .view-books h2 {
    font-size: 22px;
  }
}
</style>
