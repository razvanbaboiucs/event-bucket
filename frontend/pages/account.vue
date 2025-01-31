<template>
  <div class="space-y-8">
    <div>
      <div class="flex justify-between items-center mb-6">
        <h1 class="text-2xl font-bold">Account Details</h1>
      </div>
      <div class="bg-white rounded-lg shadow p-6 max-w-2xl">
        <div class="space-y-6">
          <UFormGroup label="ID">
            <UInput :model-value="accountDetails?.id || ''" readonly />
          </UFormGroup>
          <UFormGroup label="Team Name">
            <UInput :model-value="accountDetails?.teamName || ''" readonly />
          </UFormGroup>
          <UFormGroup label="Email">
            <UInput :model-value="accountDetails?.email || ''" readonly />
          </UFormGroup>
        </div>
      </div>
    </div>

    <div>
      <div class="flex justify-between items-center mb-6">
        <h1 class="text-2xl font-bold">Projects</h1>
      </div>
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        <div v-for="project in projects" :key="project.projectId" :class="['bg-white rounded-lg shadow p-6', { 'ring-2 ring-green-500': project.projectId === selectedProjectId }]">
          <div class="space-y-4">
            <UFormGroup label="Project ID">
              <UInput :model-value="project.projectId" readonly />
            </UFormGroup>
            <UFormGroup label="Project Name">
              <UInput :model-value="project.projectName" readonly />
            </UFormGroup>
            <UFormGroup label="API Key">
              <div class="flex gap-2">
                <UInput
                  :model-value="project.isApiKeyVisible ? project.apiKey : '••••••••••••••••'"
                  readonly
                  class="flex-1"
                />
                <UButton
                  :icon="project.isApiKeyVisible ? 'i-heroicons-eye-slash' : 'i-heroicons-eye'"
                  @click="() => toggleProjectApiKey(project)"
                >
                  {{ project.isApiKeyVisible ? 'Hide' : 'Reveal' }}
                </UButton>
              </div>
            </UFormGroup>
            <div class="flex justify-end">
              <UButton
                :color="project.projectId === selectedProjectId ? 'green' : 'gray'"
                @click="() => selectProject(project.projectId)"
              >
                {{ project.projectId === selectedProjectId ? 'Selected' : 'Select Project' }}
              </UButton>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
const accountDetails = ref(null)
const projects = ref([])
const selectedProjectId = ref('')

const fetchAccountDetails = async () => {
  try {
    const response = await fetch('http://localhost:8060/account-manager/api/v1/account')
    if (response.ok) {
      accountDetails.value = await response.json()
    }
  } catch (error) {
    console.error('Error fetching account details:', error)
  }
}

const fetchProjects = async () => {
  try {
    const response = await fetch('http://localhost:8060/account-manager/api/v1/account/1')
    if (response.ok) {
      const data = await response.json()
      projects.value = data.map(project => ({
        ...project,
        apiKey: '',
        isApiKeyVisible: false
      }))
    }
  } catch (error) {
    console.error('Error fetching projects:', error)
  }
}

const fetchProjectApiKey = async (projectId) => {
  try {
    const response = await fetch(`http://localhost:8060/account-manager/api/v1/account/projects/${projectId}/apiKey`)
    if (response.ok) {
      return await response.text()
    }
  } catch (error) {
    console.error('Error fetching API key:', error)
  }
  return ''
}

const toggleProjectApiKey = async (project) => {
  if (!project.isApiKeyVisible && !project.apiKey) {
    project.apiKey = await fetchProjectApiKey(project.projectId)
  }
  project.isApiKeyVisible = !project.isApiKeyVisible
}

const selectProject = (projectId) => {
  selectedProjectId.value = projectId
  document.cookie = `selectedProjectId=${projectId}; path=/; max-age=31536000`
}

const initializeSelectedProject = () => {
  const cookies = document.cookie.split('; ')
  const projectCookie = cookies.find(cookie => cookie.startsWith('selectedProjectId='))
  if (projectCookie) {
    selectedProjectId.value = projectCookie.split('=')[1]
  }
}

onMounted(() => {
  initializeSelectedProject()
  fetchAccountDetails()
  fetchProjects()
})
</script>