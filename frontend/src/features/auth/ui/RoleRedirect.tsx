import { Navigate } from 'react-router-dom'
import { getAuthUser } from '../model/authStorage'

export function RoleRedirect() {
  const user = getAuthUser()

  if (!user) {
    return <Navigate to="/login" replace />
  }

  // После входа сразу отправляем пользователя в его раздел без отдельной страницы выбора.
  if (user.role === 'ADMIN') {
    return <Navigate to="/admin/users" replace />
  }

  return <Navigate to="/student" replace />
}
