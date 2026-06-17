import { USER_STATUS_LABEL, type UserStatus } from '../model/userTypes'

type UserStatusBadgeProps = {
  status: UserStatus
}

export function UserStatusBadge({ status }: UserStatusBadgeProps) {
  return (
    <span className={`status-badge status-badge--${status.toLowerCase()}`}>
      {USER_STATUS_LABEL[status]}
    </span>
  )
}
