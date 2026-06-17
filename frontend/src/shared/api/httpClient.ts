import axios from 'axios'
import { clearAuth, getToken } from '../../features/auth/model/authStorage'
import { API_BASE_URL } from '../config/env'

export const httpClient = axios.create({
  baseURL: API_BASE_URL,
})

httpClient.interceptors.request.use((config) => {
  const token = getToken()

  if (token) {
    // Каждый защищенный запрос автоматически получает JWT в заголовке Authorization.
    config.headers.Authorization = `Bearer ${token}`
  }

  return config
})

httpClient.interceptors.response.use(
  (response) => response,
  (error: unknown) => {
    if (axios.isAxiosError(error) && error.response?.status === 401) {
      // Если токен истек или стал невалидным, выходим из аккаунта и возвращаем пользователя на экран входа.
      clearAuth()

      if (window.location.pathname !== '/login') {
        window.location.assign('/login')
      }
    }

    return Promise.reject(error)
  },
)
