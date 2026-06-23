import axios from 'axios'
import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { getCourse, getLessons, getProgress } from '../../features/course/api/courseApi'
import type { CourseSummary, LessonListItem } from '../../features/course/model/courseTypes'
import { CourseProgressBar } from '../../features/course/ui/CourseProgressBar'
import { LessonList } from '../../features/course/ui/LessonList'
import { Button } from '../../shared/ui/Button'
import { PageLayout } from '../../shared/ui/PageLayout'

function getErrorMessage(error: unknown) {
  if (axios.isAxiosError(error)) {
    const message = error.response?.data?.message
    if (typeof message === 'string' && message.trim()) {
      return message
    }
  }

  return 'Не удалось загрузить курс.'
}

export function StudentDashboardPage() {
  const navigate = useNavigate()
  const [course, setCourse] = useState<CourseSummary | null>(null)
  const [lessons, setLessons] = useState<LessonListItem[]>([])
  const [courseCompleted, setCourseCompleted] = useState(false)
  const [error, setError] = useState('')
  const [isLoading, setIsLoading] = useState(true)

  useEffect(() => {
    let isMounted = true

    Promise.all([getCourse(), getLessons(), getProgress()])
      .then(([loadedCourse, loadedLessons, loadedProgress]) => {
        if (isMounted) {
          setCourse(loadedCourse)
          setLessons(loadedLessons)
          setCourseCompleted(loadedProgress.courseCompleted)
          setError('')
        }
      })
      .catch((loadError: unknown) => {
        if (isMounted) {
          setError(getErrorMessage(loadError))
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

  return (
    <PageLayout title="Кабинет студента">
      {isLoading ? <p className="empty-state">Загрузка...</p> : null}
      {error ? <p className="form-error">{error}</p> : null}

      {!isLoading && !error && course ? (
        <div className="student-dashboard">
          <CourseProgressBar
            completedLessons={course.completedLessons}
            progressPercent={course.progressPercent}
            totalLessons={course.totalLessons}
          />

          {courseCompleted ? (
            <section className="certificate-callout">
              <p>Курс завершен — можно скачать сертификат.</p>
              <Button onClick={() => navigate('/student/completed')} type="button">
                Сертификат
              </Button>
            </section>
          ) : null}

          <LessonList lessons={lessons} />
        </div>
      ) : null}
    </PageLayout>
  )
}
