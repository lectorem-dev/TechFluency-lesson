import { createBrowserRouter, Navigate } from 'react-router-dom'
import { ProtectedRoute } from '../features/auth/ui/ProtectedRoute'
import { RoleRedirect } from '../features/auth/ui/RoleRedirect'
import { AdminUsersPage } from '../pages/admin/AdminUsersPage'
import { HomePage } from '../pages/home/HomePage'
import { LoginPage } from '../pages/login/LoginPage'
import { StudentStubPage } from '../pages/student/StudentStubPage'

export const router = createBrowserRouter([
  {
    path: '/',
    element: <HomePage />,
  },
  {
    path: '/login',
    element: <LoginPage />,
  },
  {
    path: '/admin/users',
    element: (
      <ProtectedRoute allowedRole="ADMIN">
        <AdminUsersPage />
      </ProtectedRoute>
    ),
  },
  {
    path: '/student',
    element: (
      <ProtectedRoute>
        <StudentStubPage />
      </ProtectedRoute>
    ),
  },
  {
    path: '/redirect',
    element: <RoleRedirect />,
  },
  {
    path: '*',
    element: <Navigate to="/" replace />,
  },
])
