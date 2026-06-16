export type Role = 'ADMIN' | 'STUDENT'

export type UserStatus = 'ACTIVE' | 'ARCHIVED'

export type User = {
  id: number
  name: string
  login: string
  role: Role
  status: UserStatus
  createdAt: string
  updatedAt: string
}

export type UserCreateRequest = {
  name: string
  login: string
  password: string
  role: Role
}

export type UserUpdateRequest = {
  name: string
  login: string
  password?: string | null
  role: Role
  status: UserStatus
}
