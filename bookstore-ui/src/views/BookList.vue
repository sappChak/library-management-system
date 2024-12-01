<template>
  <div class="book-list">
    <h2>Available Books</h2>
    <div v-if="isLoading">Loading...</div>
    <div v-if="availableBooks.length === 0">No books available</div>

    <div v-for="book in availableBooks" :key="book.id" class="book-item">
      <div class="book-details">
        <h3>{{ book.title }}</h3>
        <p>Author: {{ book.author }}</p>
        <p>Available: {{ book.availableCopies }}</p>
      </div>

      <!-- User specific actions like borrow and return -->
      <div>
        <button v-if="book.availableCopies > 0" @click="borrowBookHandler(book.id)">Borrow</button>
        <button v-else @click="returnBookHandler(book.id)">Return</button>
      </div>
    </div>

    <h2>Your Borrowed Books</h2>
    <div v-if="borrowedBooks.length === 0">You have not borrowed any books</div>

    <div v-for="book in borrowedBooks" :key="book.id" class="book-item">
      <div class="book-details">
        <h3>{{ book.title }}</h3>
        <p>Author: {{ book.author }}</p>
      </div>

      <!-- User action to return the book -->
      <button @click="returnBookHandler(book.id)">Return</button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { borrowBook, fetchAvailableBooks, fetchBorrowedBooks, returnBook } from "@/api/book.api";
import { useAuthStore } from "@/store/auth.store";
import { Book } from "@/types";
import { ref, onMounted } from "vue";

const authStore = useAuthStore();

const availableBooks = ref<Book[]>([]);
const borrowedBooks = ref<Book[]>([]);
const isLoading = ref(false);

// Fetch books available for borrowing and the books the user has borrowed
const loadBooks = async () => {
  isLoading.value = true;
  try {
    availableBooks.value = await fetchAvailableBooks();
    borrowedBooks.value = await fetchBorrowedBooks();
  } catch (error) {
    console.error("Failed to fetch books", error);
  } finally {
    isLoading.value = false;
  }
};

// Borrow a book
const borrowBookHandler = async (bookId: string) => {
  try {
    await borrowBook(bookId);
    loadBooks(); 
  } catch (error) {
    console.error("Failed to borrow book", error);
  }
};

// Return a book
const returnBookHandler = async (bookId: string) => {
  try {
    await returnBook(bookId);
    loadBooks(); 
  } catch (error) {
    console.error("Failed to return book", error);
  }
};

onMounted(loadBooks);
</script>

<style scoped>
.book-list {
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
