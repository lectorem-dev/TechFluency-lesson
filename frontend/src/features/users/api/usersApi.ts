import { httpClient } from '../../../shared/api/httpClient'
import type { User, UserCreateRequest, UserUpdateRequest } from '../model/userTypes'

export async function getUsers() {
  const response = await httpClient.get<User[]>('/api/admin/users')
  return response.data
}

export async function getUser(id: number) {
  const response = await httpClient.get<User>(`/api/admin/users/${id}`)
  return response.data
}

export async function createUser(request: UserCreateRequest) {
  const response = await httpClient.post<User>('/api/admin/users', request)
  return response.data
}

export async function updateUser(id: number, request: UserUpdateRequest) {
  const response = await httpClient.put<User>(`/api/admin/users/${id}`, request)
  return response.data
}

export async function archiveUser(id: number) {
  await httpClient.delete(`/api/admin/users/${id}`)
}
