import type { ReactNode } from 'react'
import { Button } from './Button'

type ModalProps = {
  children: ReactNode
  onClose: () => void
  title: string
}

export function Modal({ children, onClose, title }: ModalProps) {
  return (
    <div className="modal-backdrop" role="presentation">
      <div aria-modal="true" className="modal" role="dialog">
        <div className="modal__header">
          <h2>{title}</h2>
          <Button aria-label="Закрыть" onClick={onClose} type="button" variant="ghost">
            Закрыть
          </Button>
        </div>
        {children}
      </div>
    </div>
  )
}
