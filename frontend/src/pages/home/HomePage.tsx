import { useNavigate } from 'react-router-dom'
import { Button } from '../../shared/ui/Button'

export function HomePage() {
  const navigate = useNavigate()

  return (
    <main className="home-page">
      <Button onClick={() => navigate('/login')} type="button">
        Войти
      </Button>
    </main>
  )
}
