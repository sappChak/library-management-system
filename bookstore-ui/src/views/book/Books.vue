<template>
  <div class="books-page">
    <h1 class="page-title">Available Books</h1>
    <div class="search-bar">
      <input
        v-model="searchQuery"
        @input="onSearchInput"
        type="text"
        class="search-input"
        placeholder="Search by title, author, or ISBN..."
        aria-label="Search books"
      />
    </div>
    <div v-if="filteredBooks.length === 0" class="no-books">
      No books available for borrowing at the moment.
    </div>
    <ul class="book-list">
      <li v-for="book in filteredBooks" :key="book.id" class="book-item">
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
    <div class="pagination">
      <button
        class="pagination-btn"
        :disabled="currentPage === 1"
        @click="changePage(currentPage - 1)"
      >
        Previous
      </button>
      <span class="page-info">Page {{ currentPage }} of {{ totalPages }}</span>
      <button
        class="pagination-btn"
        :disabled="currentPage === totalPages || filteredBooks.length === 0"
        @click="changePage(currentPage + 1)"
      >
        Next
      </button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import {
  borrowBookById,
  fetchAvailableBooks,
  searchBooks,
} from '@/services/book.service';
import { debounce } from 'lodash';
import { BookResponse } from '@/types';

const books = ref<BookResponse[]>([]);
const filteredBooks = ref<BookResponse[]>([]);
const searchQuery = ref('');
const currentPage = ref(1);
const totalPages = ref(0);
const pageSize = 10;
let isSearching = ref(false);

const fetchBooks = async (page: number) => {
  try {
    const response = await fetchAvailableBooks(page, pageSize);
    books.value = response.data;
    totalPages.value = response.totalPages;
    filteredBooks.value = books.value;
  } catch (error) {
    console.error('Error fetching books:', error);
  }
};

const filterBooks = async () => {
  if (!searchQuery.value.trim()) {
    isSearching.value = false;
    fetchBooks(currentPage.value);
    return;
  }

  isSearching.value = true;
  try {
    const response = await searchBooks(
      currentPage.value,
      pageSize,
      searchQuery.value,
    );
    filteredBooks.value = response.data;
    totalPages.value = response.totalPages;
  } catch (error) {
    console.error('Error searching books:', error);
    alert('Failed to fetch search results. Please try again later.');
  }
};

const borrowBook = async (bookId: number) => {
  try {
    await borrowBookById(bookId);
    books.value = books.value.filter((book) => book.id !== bookId);
    filteredBooks.value = filteredBooks.value.filter(
      (book) => book.id !== bookId,
    );
  } catch (error) {
    console.error('Error borrowing book:', error);
    alert('Failed to borrow the book. Please try again later.');
  }
};

const changePage = (page: number) => {
  if (page > 0 && page <= totalPages.value) {
    currentPage.value = page;
    if (isSearching.value) {
      filterBooks();
    } else {
      fetchBooks(page);
    }
  }
};

const debouncedFilterBooks = debounce(() => {
  filterBooks();
}, 300);

const onSearchInput = () => {
  debouncedFilterBooks();
};

onMounted(() => {
  fetchBooks(currentPage.value);
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
  margin-bottom: 40px;
  text-align: center;
  color: #000000;
}

.search-bar {
  display: flex;
  justify-content: center;
  margin-bottom: 50px;
}

.search-input {
  width: 80%;
  max-width: 500px;
  padding: 10px 15px;
  border: 1px solid #000000;
  border-radius: 8px;
  font-size: 1rem;
  background-color: #f9f9f9;
  color: #000000;
}

.search-input:focus {
  border-color: #666666;
  outline: none;
  box-shadow: 0 0 8px rgba(0, 0, 0, 0.2);
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
.book-available-copies {
  font-size: 0.95rem;
  color: #666666;
}

.book-available-copies span {
  color: #000000;
  font-weight: bold;
}

.borrow-btn {
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

.borrow-btn:hover {
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
  .search-input {
    width: 90%;
  }

  .book-details {
    max-width: 100%;
  }

  .books-page {
    padding: 20px 10px;
  }

  .page-title {
    font-size: 2rem;
  }
}
</style>
