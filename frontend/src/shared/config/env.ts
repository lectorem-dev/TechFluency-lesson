const DEFAULT_API_BASE_URL = 'http://localhost:8080'

function normalizeApiBaseUrl(value: string) {
  const normalized = value.trim().replace(/\/+$/, '')

  if (normalized === '/api') {
    return ''
  }

  if (normalized.endsWith('/api')) {
    return normalized.slice(0, -4)
  }

  return normalized
}

export const API_BASE_URL = normalizeApiBaseUrl(
  import.meta.env.VITE_API_BASE_URL ?? DEFAULT_API_BASE_URL,
)
