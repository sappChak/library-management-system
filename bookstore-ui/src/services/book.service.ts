import { BookResponse, CreateBookRequest } from '@/types'
import httpClient from './http-client.service'

export interface PaginatedBooksResponse {
  data: BookResponse[]
  totalPages: number
  totalElements: number
}

export const fetchAvailableBooks = async (
  page: number,
  size: number,
): Promise<PaginatedBooksResponse> => {
  const response = await httpClient.get('books/available/paginated', {
    params: {
      page: page - 1,
      size,
    },
  })
  return {
    data: response.data.content,
    totalPages: response.data.totalPages,
    totalElements: response.data.totalElements,
  }
}

export const searchBooks = async (
  page: number,
  size: number,
  query: string,
): Promise<PaginatedBooksResponse> => {
  const response = await httpClient.get('books/search', {
    params: {
      page: page - 1,
      size,
      query,
    },
  })
  console.log(response)
  return {
    data: response.data.content,
    totalPages: response.data.totalPages,
    totalElements: response.data.totalElements,
  }
}

export const fetchBorrowedBooks = async (): Promise<BookResponse[]> => {
  const response = await httpClient.get('/books/borrowed')
  const books = await response.data
  return books
}

export const fetchNumberOfAvailableBooks = async (): Promise<number> => {
  const response = await httpClient.get('/books/available/count')
  const count = await response.data
  return count
}

export const fetchAllBooks = async (): Promise<BookResponse[]> => {
  const response = await httpClient.get('/books')
  const books = await response.data
  return books
}

export const borrowBookById = async (bookId: number): Promise<void> => {
  await httpClient.post(`/books/borrow/${bookId}`)
}

export const returnBookById = async (bookId: number): Promise<void> => {
  await httpClient.post(`/books/return/${bookId}`)
}

export const addBook = async (book: CreateBookRequest): Promise<void> => {
  await httpClient.post('/books', book)
}

export const deleteBookById = async (bookId: number): Promise<void> => {
  await httpClient.delete(`/books/${bookId}`)
}
