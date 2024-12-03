import { defineStore } from 'pinia'
import { login, getUserProfile, register } from '@/services/auth.service'
import { AuthState, LoginCredentials, RegisterCredentials } from '@/types'

export const useAuthStore = defineStore('auth', {
  state: (): AuthState => ({
    token: localStorage.getItem('token') || null,
    user: JSON.parse(localStorage.getItem('user') || 'null'),
  }),

  actions: {
    async login(credentials: LoginCredentials) {
      try {
        const token = await login(credentials)
        this.token = token
        localStorage.setItem('token', token)

        await this.fetchUserProfile()
      } catch (error) {
        console.error('Login failed:', error)
        throw error
      }
    },

    async register(credentials: RegisterCredentials) {
      try {
        await register(credentials)
      } catch (error) {
        console.error('Registration failed:', error)
        throw error
      }
    },

    async fetchUserProfile() {
      try {
        const user = await getUserProfile()
        this.user = user
        localStorage.setItem('user', JSON.stringify(user))
      } catch (error) {
        console.error('Failed to fetch user profile:', error)
        throw error
      }
    },

    logout() {
      this.token = null
      this.user = null
      localStorage.removeItem('token')
      localStorage.removeItem('user')
    },
  },

  getters: {
    isAuthenticated: (state) => !!state.token,
    hasRole: (state) => (role: string) => {
      return state.user?.roles.includes(role) || false
    },
    isAdmin: (state) => state.user?.roles.includes('ROLE_ADMIN'),
  },
})
