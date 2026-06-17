import { type FormEvent, useState } from 'react'
import { Button } from '../../../shared/ui/Button'
import { Input } from '../../../shared/ui/Input'
import { Select } from '../../../shared/ui/Select'
import {
  ROLE_LABEL,
  USER_STATUS_LABEL,
  type Role,
  type User,
  type UserCreateRequest,
  type UserStatus,
  type UserUpdateRequest,
} from '../model/userTypes'

type UserFormProps = {
  isSubmitting: boolean
  mode: 'create' | 'edit'
  onCancel: () => void
  onSubmit: (request: UserCreateRequest | UserUpdateRequest) => Promise<void>
  user?: User
}

type FormErrors = Partial<Record<'login' | 'name' | 'password' | 'role' | 'status', string>>

export function UserForm({ isSubmitting, mode, onCancel, onSubmit, user }: UserFormProps) {
  const [name, setName] = useState(user?.name ?? '')
  const [login, setLogin] = useState(user?.login ?? '')
  const [password, setPassword] = useState('')
  const [role, setRole] = useState<Role>(user?.role ?? 'STUDENT')
  const [status, setStatus] = useState<UserStatus>(user?.status ?? 'ACTIVE')
  const [errors, setErrors] = useState<FormErrors>({})

  async function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault()

    const nextErrors: FormErrors = {}
    if (!name.trim()) {
      nextErrors.name = 'Введите имя.'
    }
    if (!login.trim()) {
      nextErrors.login = 'Введите логин.'
    }
    if (mode === 'create' && !password.trim()) {
      nextErrors.password = 'Введите пароль.'
    }

    setErrors(nextErrors)
    if (Object.keys(nextErrors).length > 0) {
      return
    }

    if (mode === 'create') {
      await onSubmit({
        name: name.trim(),
        login: login.trim(),
        password,
        role,
      })
      return
    }

    await onSubmit({
      name: name.trim(),
      login: login.trim(),
      password: password.trim() ? password : null,
      role,
      status,
    })
  }

  return (
    <form className="user-form" onSubmit={handleSubmit}>
      <Input
        error={errors.name}
        label="Имя"
        name="name"
        onChange={(event) => setName(event.target.value)}
        value={name}
      />
      <Input
        error={errors.login}
        label="Логин"
        name="login"
        onChange={(event) => setLogin(event.target.value)}
        value={login}
      />
      <Input
        autoComplete="new-password"
        error={errors.password}
        label={mode === 'create' ? 'Пароль' : 'Новый пароль'}
        name="password"
        onChange={(event) => setPassword(event.target.value)}
        type="password"
        value={password}
      />
      <Select
        error={errors.role}
        label="Роль"
        name="role"
        onChange={(event) => setRole(event.target.value as Role)}
        value={role}
      >
        <option value="STUDENT">{ROLE_LABEL.STUDENT}</option>
        <option value="ADMIN">{ROLE_LABEL.ADMIN}</option>
      </Select>
      {mode === 'edit' ? (
        <Select
          error={errors.status}
          label="Статус"
          name="status"
          onChange={(event) => setStatus(event.target.value as UserStatus)}
          value={status}
        >
          <option value="ACTIVE">{USER_STATUS_LABEL.ACTIVE}</option>
          <option value="ARCHIVED">{USER_STATUS_LABEL.ARCHIVED}</option>
        </Select>
      ) : null}
      <div className="form-actions">
        <Button disabled={isSubmitting} type="submit">
          {isSubmitting ? 'Сохранение...' : 'Сохранить'}
        </Button>
        <Button onClick={onCancel} type="button" variant="secondary">
          Отмена
        </Button>
      </div>
    </form>
  )
}
