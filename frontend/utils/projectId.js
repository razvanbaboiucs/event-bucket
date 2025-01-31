export const getSelectedProjectId = () => {
  const cookies = document.cookie.split('; ')
  const projectCookie = cookies.find(cookie => cookie.startsWith('selectedProjectId='))
  return projectCookie ? projectCookie.split('=')[1] : ''
}

export const setSelectedProjectId = (projectId) => {
  document.cookie = `selectedProjectId=${projectId}; path=/; max-age=31536000`
}

export const getApiKey = () => {
  const cookies = document.cookie.split('; ')
  const apiKeyCookie = cookies.find(cookie => cookie.startsWith('apiKey='))
  return apiKeyCookie ? apiKeyCookie.split('=')[1] : ''
}

export const setApiKey = (apiKey) => {
  document.cookie = `apiKey=${apiKey}; path=/; max-age=31536000`
}

