import type { ReactNode, SelectHTMLAttributes } from 'react'

type SelectProps = SelectHTMLAttributes<HTMLSelectElement> & {
  children: ReactNode
  label: string
  error?: string
}

export function Select({ children, error, id, label, ...props }: SelectProps) {
  const selectId = id ?? props.name

  return (
    <label className="field" htmlFor={selectId}>
      <span className="field__label">{label}</span>
      <select className="select" id={selectId} {...props}>
        {children}
      </select>
      {error ? <span className="field__error">{error}</span> : null}
    </label>
  )
}
