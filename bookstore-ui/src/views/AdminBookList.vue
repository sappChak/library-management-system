<template>
  <div class="admin-book-list">
    <h2>Manage Books</h2>
    <button @click="addNewBook">Add New Book</button>
    <div v-if="isLoading">Loading...</div>
    <div v-if="books.length === 0">No books available</div>

    <div v-for="book in books" :key="book.id" class="book-item">
      <div class="book-details">
        <h3>{{ book.title }}</h3>
        <p>Author: {{ book.author }}</p>
        <p>Available: {{ book.availableCopies }}</p>
      </div>

      <button @click="editBook(book.id)">Edit</button>
      <button @click="deleteBook(book.id)">Delete</button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { fetchAllBooks } from "@/api/book.api";
import { Book } from "@/types";
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";

const books = ref<Book[]>([]);
const isLoading = ref(false);
const router = useRouter();

// Fetch all books
const loadBooks = async () => {
  isLoading.value = true;
  try {
    books.value = await fetchAllBooks();
  } catch (error) {
    console.error("Failed to fetch books", error);
  } finally {
    isLoading.value = false;
  }
};

// Admin actions
const addNewBook = () => {
  router.push("/admin/books/new"); // Navigate to the page to add a new book
};

const editBook = (bookId: string) => {
  router.push(`/admin/books/${bookId}`); // Navigate to edit the specific book
};

const deleteBook = async (bookId: string) => {
  try {
    await fetch(`/api/books/${bookId}`, {
      method: "DELETE",
      headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
    });
    loadBooks(); // Reload books after deletion
  } catch (error) {
    console.error("Failed to delete book", error);
  }
};

// Load books on mount
onMounted(loadBooks);
</script>

<style scoped>
.admin-book-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.book-item {
  display: flex;
  justify-content: space-between;
  border-bottom: 1px solid #ccc;
  padding: 10px 0;
}

.book-details {
  flex: 1;
}

button {
  padding: 8px 16px;
  cursor: pointer;
}
</style>
