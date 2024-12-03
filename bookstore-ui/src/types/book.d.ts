export interface BookResponse {
  id: number
  title: string
  author: string
  isbn: string
  availableCopies: number
}

export interface CreateBookRequest {
  title: string
  author: string
  isbn: string
  availableCopies: number
}
