import type { ReactNode } from 'react'
import { Navigate } from 'react-router-dom'
import type { Role } from '../../users/model/userTypes'
import { getAuthUser, getToken } from '../model/authStorage'

type ProtectedRouteProps = {
  allowedRole?: Role
  children: ReactNode
}

export function ProtectedRoute({ allowedRole, children }: ProtectedRouteProps) {
  const token = getToken()
  const user = getAuthUser()

  if (!token || !user) {
    return <Navigate to="/login" replace />
  }

  if (allowedRole && user.role !== allowedRole) {
    return <Navigate to="/student" replace />
  }

  return children
}
