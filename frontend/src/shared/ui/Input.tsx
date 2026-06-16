import type { InputHTMLAttributes } from 'react'

type InputProps = InputHTMLAttributes<HTMLInputElement> & {
  label: string
  error?: string
}

export function Input({ error, id, label, ...props }: InputProps) {
  const inputId = id ?? props.name

  return (
    <label className="field" htmlFor={inputId}>
      <span className="field__label">{label}</span>
      <input className="input" id={inputId} {...props} />
      {error ? <span className="field__error">{error}</span> : null}
    </label>
  )
}
