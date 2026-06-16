import type { UserStatus } from '../model/userTypes'

type UserStatusBadgeProps = {
  status: UserStatus
}

export function UserStatusBadge({ status }: UserStatusBadgeProps) {
  const label = status === 'ACTIVE' ? 'Активен' : 'В архиве'

  return <span className={`status-badge status-badge--${status.toLowerCase()}`}>{label}</span>
}
