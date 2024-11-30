export interface AuthState {
  token: string | null;
  user: User | null;
}

export interface LoginCredentials {
  username: string;
  password: string;
}

export interface AuthResponse {
  token: string;
}
