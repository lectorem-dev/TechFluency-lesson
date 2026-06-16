import axios from 'axios'
import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { downloadCertificate } from '../../features/certificate/api/certificateApi'
import { getProgress } from '../../features/course/api/courseApi'
import type { CourseProgress } from '../../features/course/model/courseTypes'
import { Button } from '../../shared/ui/Button'
import { PageLayout } from '../../shared/ui/PageLayout'

function getErrorMessage(error: unknown, fallback: string) {
  if (axios.isAxiosError(error)) {
    const message = error.response?.data?.message
    if (typeof message === 'string' && message.trim()) {
      return message
    }
  }

  return fallback
}

export function CourseCompletedPage() {
  const navigate = useNavigate()
  const [progress, setProgress] = useState<CourseProgress | null>(null)
  const [loadError, setLoadError] = useState('')
  const [downloadError, setDownloadError] = useState('')
  const [isLoading, setIsLoading] = useState(true)
  const [isDownloading, setIsDownloading] = useState(false)

  useEffect(() => {
    let isMounted = true

    getProgress()
      .then((loadedProgress) => {
        if (isMounted) {
          setProgress(loadedProgress)
          setLoadError('')
        }
      })
      .catch((error: unknown) => {
        if (isMounted) {
          setLoadError(getErrorMessage(error, 'Не удалось загрузить прогресс курса.'))
        }
      })
      .finally(() => {
        if (isMounted) {
          setIsLoading(false)
        }
      })

    return () => {
      isMounted = false
    }
  }, [])

  async function handleDownload() {
    setDownloadError('')
    setIsDownloading(true)

    try {
      await downloadCertificate()
    } catch (error) {
      setDownloadError(getErrorMessage(error, 'Не удалось скачать сертификат.'))
    } finally {
      setIsDownloading(false)
    }
  }

  return (
    <PageLayout title="Поздравляем!">
      {isLoading ? <p className="empty-state">Загрузка...</p> : null}
      {loadError ? <p className="form-error">{loadError}</p> : null}

      {!isLoading && !loadError && progress ? (
        <section className="course-completed">
          {progress.courseCompleted ? (
            <>
              <p>Вы успешно завершили курс.</p>
              {downloadError ? <p className="form-error">{downloadError}</p> : null}
              <div className="form-actions">
                <Button disabled={isDownloading} onClick={handleDownload} type="button">
                  {isDownloading ? 'Скачивание...' : 'Скачать сертификат'}
                </Button>
                <Button onClick={() => navigate('/student')} type="button" variant="secondary">
                  Вернуться к курсу
                </Button>
              </div>
            </>
          ) : (
            <>
              <p>Курс еще не завершен</p>
              <Button onClick={() => navigate('/student')} type="button" variant="secondary">
                Вернуться к курсу
              </Button>
            </>
          )}
        </section>
      ) : null}
    </PageLayout>
  )
}
