<template>
  <div class="add-book">
    <h2 class="form-title">Add Book</h2>
    <form @submit.prevent="submitBook" class="form">
      <div class="form-group">
        <label for="title" class="form-label">Title:</label>
        <input
          type="text"
          id="title"
          v-model="book.title"
          class="form-input"
          placeholder="Enter book title"
          required
        />
      </div>
      <div class="form-group">
        <label for="author" class="form-label">Author:</label>
        <input
          type="text"
          id="author"
          v-model="book.author"
          class="form-input"
          placeholder="Enter author name"
          required
        />
      </div>
      <div class="form-group">
        <label for="isbn" class="form-label">ISBN:</label>
        <input
          type="text"
          id="isbn"
          v-model="book.isbn"
          class="form-input"
          placeholder="Enter ISBN"
          required
        />
      </div>
      <div class="form-group">
        <label for="availableCopies" class="form-label"
          >Available Copies:</label
        >
        <input
          type="number"
          id="availableCopies"
          v-model="book.availableCopies"
          class="form-input"
          placeholder="Enter available copies"
          min="1"
          required
        />
      </div>
      <button type="submit" class="submit-btn">Add Book</button>
    </form>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { addBook } from '@/services/book.service';
import { CreateBookRequest } from '@/types/book';

const book = ref<CreateBookRequest>({
  title: '',
  author: '',
  isbn: '', // Added ISBN field
  availableCopies: 1,
});

const submitBook = async () => {
  try {
    await addBook(book.value);
    alert('Book added successfully!');
    book.value = { title: '', author: '', isbn: '', availableCopies: 1 }; // Reset the form
  } catch (error) {
    console.error('Failed to add book:', error);
    alert('Error adding the book. Please try again.');
  }
};
</script>

<style scoped>
.form-title {
  font-size: 28px;
  color: #333333;
  text-align: left;
  margin-bottom: 24px;
  font-family: 'Roboto', sans-serif;
  font-weight: 700;
}

.form {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.form-group {
  display: flex;
  flex-direction: column;
}

.form-label {
  font-size: 16px;
  color: #666666;
  margin-bottom: 8px;
  font-family: 'Roboto', sans-serif;
}

.form-input {
  padding: 12px 14px;
  font-size: 16px;
  color: #333333;
  background-color: #f9f9f9;
  border: 1px solid #dddddd;
  border-radius: 8px;
  transition:
    border-color 0.3s,
    box-shadow 0.3s;
  font-family: 'Roboto', sans-serif;
}

.form-input:focus {
  border-color: #333333;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
  outline: none;
}

.submit-btn {
  padding: 12px 20px;
  font-size: 16px;
  font-weight: 700;
  color: #ffffff;
  background-color: #333333;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition:
    background-color 0.3s,
    transform 0.2s;
  font-family: 'Roboto', sans-serif;
}

.submit-btn:hover {
  background-color: #000000;
  transform: translateY(-2px);
}

.submit-btn:active {
  transform: translateY(0);
}

@media screen and (max-width: 480px) {
  .form-title {
    font-size: 24px;
  }

  .form-input {
    font-size: 14px;
    padding: 10px 12px;
  }

  .submit-btn {
    font-size: 14px;
    padding: 10px 16px;
  }
}
</style>
