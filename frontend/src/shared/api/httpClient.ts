import axios from 'axios'
import { clearAuth, getToken } from '../../features/auth/model/authStorage'
import { API_BASE_URL } from '../config/env'

export const httpClient = axios.create({
  baseURL: API_BASE_URL,
})

httpClient.interceptors.request.use((config) => {
  const token = getToken()

  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }

  return config
})

httpClient.interceptors.response.use(
  (response) => response,
  (error: unknown) => {
    if (axios.isAxiosError(error) && error.response?.status === 401) {
      clearAuth()

      if (window.location.pathname !== '/login') {
        window.location.assign('/login')
      }
    }

    return Promise.reject(error)
  },
)
