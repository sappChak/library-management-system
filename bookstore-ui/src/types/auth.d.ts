import { UserResponse } from './user'

export interface AuthState {
  token: string | null
  user: UserResponse | null
}

export interface LoginCredentials {
  username: string
  password: string
}

export interface RegisterCredentials {
  email: string
  username: string
  password: string
  confirmPassword: string
}

export interface AuthResponse {
  token: string
}
