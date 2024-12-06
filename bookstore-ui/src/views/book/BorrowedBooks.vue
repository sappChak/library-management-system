<template>
  <div class="books-page">
    <h1 class="page-title">Borrowed Books</h1>
    <div v-if="books.length === 0" class="no-books">
      No borrowed books found.
    </div>
    <ul class="book-list">
      <li v-for="book in books" :key="book.id" class="book-item">
        <div class="book-details">
          <h2 class="book-title">{{ book.title }}</h2>
          <p class="book-author">by {{ book.author }}</p>
          <p class="book-isbn">ISBN: {{ book.isbn }}</p>
        </div>
        <button class="return-btn" @click="returnBook(book.id)">Return</button>
      </li>
    </ul>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { fetchBorrowedBooks, returnBookById } from '@/services/book.service';
import { BookResponse } from '@/types';

const books = ref<BookResponse[]>([]);

const fetchBooks = async () => {
  try {
    books.value = await fetchBorrowedBooks();
  } catch (error) {
    console.error('Error fetching borrowed books:', error);
  }
};

const returnBook = async (bookId: number) => {
  try {
    await returnBookById(bookId);
    books.value = books.value.filter((book) => book.id !== bookId);
  } catch (error) {
    console.error('Error returning book:', error);
    alert('Failed to return the book. Please try again later.');
  }
};

onMounted(() => {
  fetchBooks();
});
</script>

<style scoped>
.books-page {
  background-color: #ffffff;
  color: #000000;
  padding: 40px 20px;
  min-height: 100vh;
  font-family: 'Roboto', sans-serif;
}

.page-title {
  font-size: 2.5rem;
  font-weight: 600;
  margin-bottom: 20px;
  text-align: center;
  color: #000000;
}

.no-books {
  text-align: center;
  font-size: 1.2rem;
  color: #ff4444;
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
  background-color: #f9f9f9;
  border: 1px solid #000000;
  border-radius: 10px;
  padding: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  transition:
    transform 0.3s ease,
    box-shadow 0.3s ease;
}

.book-item:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.15);
}

.book-details {
  max-width: 70%;
}

.book-title {
  font-size: 1.5rem;
  font-weight: 600;
  color: #000000;
  margin-bottom: 5px;
}

.book-author {
  font-size: 1.1rem;
  color: #444444;
  margin-bottom: 10px;
}

.book-isbn,
.book-return-date {
  font-size: 0.95rem;
  color: #666666;
}

.book-return-date span {
  color: #000000;
  font-weight: bold;
}

.return-btn {
  background-color: #000000;
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

.return-btn:hover {
  background-color: #333333;
  transform: translateY(-2px);
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 20px;
  gap: 10px;
}

.pagination-btn {
  background-color: #000000;
  color: #ffffff;
  padding: 10px 15px;
  border: none;
  border-radius: 5px;
  font-size: 1rem;
  font-weight: 500;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.pagination-btn:disabled {
  background-color: #cccccc;
  color: #666666;
  cursor: not-allowed;
}

.pagination-btn:hover:not(:disabled) {
  background-color: #333333;
}

.page-info {
  font-size: 1rem;
  color: #000000;
}

@media (max-width: 768px) {
  .books-page {
    padding: 20px 10px;
  }

  .page-title {
    font-size: 2rem;
  }
}
</style>
