import { httpClient } from '../../../shared/api/httpClient'

export async function downloadCertificate() {
  const response = await httpClient.get<Blob>('/api/certificate', {
    responseType: 'blob',
  })

  // Для скачивания Blob создаем временную ссылку и имитируем клик по ней.
  const url = window.URL.createObjectURL(response.data)
  const link = document.createElement('a')
  link.href = url
  link.download = 'certificate.pdf'
  document.body.appendChild(link)
  link.click()
  link.remove()
  window.URL.revokeObjectURL(url)
}
