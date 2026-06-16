import { Button } from '../../../shared/ui/Button'
import type { User } from '../model/userTypes'
import { UserStatusBadge } from './UserStatusBadge'

type UsersTableProps = {
  onArchive: (user: User) => void
  onEdit: (user: User) => void
  users: User[]
}

function formatDate(value: string) {
  return new Intl.DateTimeFormat('ru-RU', {
    dateStyle: 'short',
    timeStyle: 'short',
  }).format(new Date(value))
}

export function UsersTable({ onArchive, onEdit, users }: UsersTableProps) {
  if (users.length === 0) {
    return <p className="empty-state">Пользователи не найдены.</p>
  }

  return (
    <div className="table-wrap">
      <table className="users-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Имя</th>
            <th>Логин</th>
            <th>Роль</th>
            <th>Статус</th>
            <th>Дата создания</th>
            <th>Действия</th>
          </tr>
        </thead>
        <tbody>
          {users.map((user) => (
            <tr key={user.id}>
              <td>{user.id}</td>
              <td>{user.name}</td>
              <td>{user.login}</td>
              <td>{user.role}</td>
              <td>
                <UserStatusBadge status={user.status} />
              </td>
              <td>{formatDate(user.createdAt)}</td>
              <td>
                <div className="table-actions">
                  <Button onClick={() => onEdit(user)} type="button" variant="secondary">
                    Редактировать
                  </Button>
                  <Button
                    disabled={user.status === 'ARCHIVED'}
                    onClick={() => onArchive(user)}
                    type="button"
                    variant="danger"
                  >
                    В архив
                  </Button>
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}
