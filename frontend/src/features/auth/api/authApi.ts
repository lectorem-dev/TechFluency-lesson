import { httpClient } from '../../../shared/api/httpClient'
import type { LoginRequest, LoginResponse } from '../model/authTypes'

export async function login(request: LoginRequest) {
  const response = await httpClient.post<LoginResponse>('/api/auth/login', request)
  return response.data
}
