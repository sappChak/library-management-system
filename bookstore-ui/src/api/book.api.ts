import { BookResponse, CreateBookRequest } from "@/types";
import httpClient from "./http-client";

// Books for users to borrow
export const fetchAvailableBooks = async (): Promise<BookResponse[]> => {
  const response = await httpClient.get("/books/available");
  const books = await response.data;
  return books;
};

// Books that the user has borrowed
export const fetchBorrowedBooks = async (): Promise<BookResponse[]> => {
  const response = await httpClient.get("/books/borrowed");
  const books = await response.data;
  return books;
};

export const fetchNumberOfAvailableBooks = async (): Promise<number> => {
  const response = await httpClient.get("/books/available/count");
  const count = await response.data;
  return count;
};

// All books in the library (for admins)
export const fetchAllBooks = async (): Promise<BookResponse[]> => {
  const response = await httpClient.get("/books");
  const books = await response.data;
  return books;
};

export const borrowBook = async (bookId: string): Promise<void> => {
  await httpClient.post(`/books/borrow/${bookId}`);
};

export const returnBook = async (bookId: string): Promise<void> => {
  await httpClient.post(`/books/return/${bookId}`);
};

export const addBook = async (book: CreateBookRequest): Promise<void> => {
  await httpClient.post("/books", book);
};

export const deleteBook = async (bookId: string): Promise<void> => {
  await httpClient.delete(`/books/${bookId}`);
};
