import type { AuthUser, LoginResponse } from './authTypes'

const TOKEN_KEY = 'lesson_token'
const USER_KEY = 'lesson_user'

export function saveAuth(response: LoginResponse) {
  const user: AuthUser = {
    userId: response.userId,
    login: response.login,
    name: response.name,
    role: response.role,
  }

  // Токен и краткий профиль храним раздельно: токен нужен для запросов, профиль — для интерфейса.
  localStorage.setItem(TOKEN_KEY, response.token)
  localStorage.setItem(USER_KEY, JSON.stringify(user))
}

export function getToken() {
  return localStorage.getItem(TOKEN_KEY)
}

export function getAuthUser(): AuthUser | null {
  const rawUser = localStorage.getItem(USER_KEY)

  if (!rawUser) {
    return null
  }

  try {
    return JSON.parse(rawUser) as AuthUser
  } catch {
    // Если профиль в localStorage поврежден, очищаем обе записи и просим пользователя войти заново.
    clearAuth()
    return null
  }
}

export function clearAuth() {
  localStorage.removeItem(TOKEN_KEY)
  localStorage.removeItem(USER_KEY)
}
