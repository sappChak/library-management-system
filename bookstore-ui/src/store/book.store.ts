import { defineStore } from "pinia";
import {
  fetchAvailableBooks,
  fetchBorrowedBooks,
  fetchNumberOfAvailableBooks,
  fetchAllBooks,
  borrowBook,
  returnBook,
  addBook,
} from "@/api/book.api";
import { Book } from "@/types";

export const useBookStore = defineStore("book", {
  state: () => ({
    availableBooks: [] as Book[],
    borrowedBooks: [] as Book[],
    allBooks: [] as Book[],
    totalAvailableBooks: 0,
  }),

  actions: {
    async loadAvailableBooks() {
      try {
        this.availableBooks = await fetchAvailableBooks();
      } catch (error) {
        console.error("Failed to load available books:", error);
      }
    },

    async loadBorrowedBooks() {
      try {
        this.borrowedBooks = await fetchBorrowedBooks();
      } catch (error) {
        console.error("Failed to load borrowed books:", error);
      }
    },

    async loadTotalAvailableBooks() {
      try {
        this.totalAvailableBooks = await fetchNumberOfAvailableBooks();
      } catch (error) {
        console.error("Failed to load total available books:", error);
      }
    },

    async loadAllBooks() {
      try {
        this.allBooks = await fetchAllBooks();
      } catch (error) {
        console.error("Failed to load all books:", error);
      }
    },

    async borrowBook(bookId: string) {
      try {
        await borrowBook(bookId);
        await this.loadAvailableBooks(); // Refresh the list after borrowing
        await this.loadBorrowedBooks(); // Refresh user's borrowed books
      } catch (error) {
        console.error("Failed to borrow book:", error);
      }
    },

    async returnBook(bookId: string) {
      try {
        await returnBook(bookId);
        await this.loadAvailableBooks(); // Refresh the list after returning
        await this.loadBorrowedBooks(); // Refresh user's borrowed books
      } catch (error) {
        console.error("Failed to return book:", error);
      }
    },

    async addBook(book: Book) {
      try {
        await addBook(book);
        await this.loadAllBooks(); // Refresh the admin's list of all books
      } catch (error) {
        console.error("Failed to add book:", error);
      }
    },
  },

  getters: {
    getAvailableBooks: (state) => state.availableBooks,
    getBorrowedBooks: (state) => state.borrowedBooks,
    getAllBooks: (state) => state.allBooks,
    getTotalAvailableBooks: (state) => state.totalAvailableBooks,
  },
});
