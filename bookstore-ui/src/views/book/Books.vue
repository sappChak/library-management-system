<template>
  <div class="books-page">
    <h1 class="page-title">Available Books</h1>
    <div v-if="books.length === 0" class="no-books">
      No books available for borrowing at the moment.
    </div>
    <ul class="book-list">
      <li v-for="book in books" :key="book.id" class="book-item">
        <div class="book-details">
          <h2 class="book-title">{{ book.title }}</h2>
          <p class="book-author">by {{ book.author }}</p>
          <p class="book-isbn">ISBN: {{ book.isbn }}</p>
          <p class="book-available-copies">
            Available Copies: <span>{{ book.availableCopies }}</span>
          </p>
        </div>
        <button class="borrow-btn" @click="borrowBook(book.id)">Borrow</button>
      </li>
    </ul>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { fetchAvailableBooks, borrowBookById } from '@/services/book.service'
import { BookResponse } from '@/types'

const books = ref<BookResponse[]>([])

const fetchBooks = async () => {
  try {
    books.value = await fetchAvailableBooks()
  } catch (error) {
    console.error('Error fetching books:', error)
  }
}

const borrowBook = async (bookId: number) => {
  try {
    await borrowBookById(bookId)
    books.value = books.value.filter((book) => book.id !== bookId)
  } catch (error) {
    console.error('Error borrowing book:', error)
    alert('Failed to borrow the book. Please try again later.')
  }
}

onMounted(() => {
  fetchBooks()
})
</script>

<style scoped>
.books-page {
  background-color: #1e1e1e;
  color: #eaeaea;
  padding: 40px 20px;
  min-height: 100vh;
  font-family: 'Roboto', sans-serif;
}

.page-title {
  font-size: 2.5rem;
  font-weight: 600;
  margin-bottom: 20px;
  text-align: center;
  color: #ffffff;
}

.no-books {
  text-align: center;
  font-size: 1.2rem;
  color: #ff6f61;
  margin-top: 50px;
}

.book-list {
  list-style: none;
  padding: 0;
  margin: 0;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.book-item {
  background-color: #2c2c2c;
  border-radius: 10px;
  padding: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.3);
  transition:
    transform 0.3s ease,
    box-shadow 0.3s ease;
}

.book-item:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.4);
}

.book-details {
  max-width: 70%;
}

.book-title {
  font-size: 1.5rem;
  font-weight: 500;
  color: #ffffff;
  margin-bottom: 5px;
}

.book-author {
  font-size: 1.1rem;
  color: #b3b3b3;
  margin-bottom: 10px;
}

.book-isbn,
.book-available-copies {
  font-size: 0.95rem;
  color: #d6d6d6;
}

.book-available-copies span {
  color: #ff6f61;
  font-weight: bold;
}

.borrow-btn {
  background-color: #ff6f61;
  color: #ffffff;
  padding: 10px 20px;
  border: none;
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 500;
  cursor: pointer;
  transition:
    background-color 0.3s ease,
    transform 0.2s ease;
}

.borrow-btn:hover {
  background-color: #ff3d2e;
  transform: translateY(-2px);
}
</style>
