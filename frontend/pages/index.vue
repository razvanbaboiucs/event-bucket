<template>
  <div>
    <div class="mb-6 space-y-4">
      <div class="flex justify-between items-center">
        <h1 class="text-2xl font-bold">Events</h1>
        <UButton
          icon="i-heroicons-plus"
          @click="isSlideoverOpen = true"
        >
          Add Event
        </UButton>
      </div>
      <div class="flex gap-4">
        <UInput
          v-model="eventIdFilter"
          placeholder="Filter by Event ID"
          class="max-w-xs"
        />
        <UInput
          v-model="userIdFilter"
          placeholder="Filter by User ID"
          class="max-w-xs"
        />
      </div>
    </div>

    <UTable
      :rows="events"
      :columns="columns"
    />

    <USlideover
      v-model="isSlideoverOpen"
      :ui="{ width: 'w-1/3' }"
    >
      <template #header>
        <div class="text-xl font-bold">Add New Event</div>
      </template>
      <div class="p-4 space-y-4">
        <UFormGroup label="Deduplication ID">
          <UInput v-model="newEvent.deduplicationId" placeholder="Enter deduplication ID" />
        </UFormGroup>
        <UFormGroup label="Event ID">
          <UInput v-model="newEvent.id" placeholder="Enter event ID" />
        </UFormGroup>
        <UFormGroup label="Title">
          <UInput v-model="newEvent.title" placeholder="Enter title" />
        </UFormGroup>
        <UFormGroup label="Description">
          <UInput v-model="newEvent.description" placeholder="Enter description" />
        </UFormGroup>
        <UFormGroup label="User ID">
          <UInput v-model="newEvent.userId" placeholder="Enter user ID" />
        </UFormGroup>
        <div class="flex justify-end gap-2">
          <UButton
            color="gray"
            variant="soft"
            @click="isSlideoverOpen = false"
          >
            Cancel
          </UButton>
          <UButton
            color="primary"
            @click="addEvent"
          >
            Add Event
          </UButton>
        </div>
      </div>
    </USlideover>
  </div>
</template>

<script setup>
import { getSelectedProjectId, getApiKey } from '../utils/projectId'
const eventIdFilter = ref('')
const userIdFilter = ref('')
const isSlideoverOpen = ref(false)

const newEvent = ref({
  deduplicationId: '',
  id: '',
  title: '',
  description: '',
  userId: '',
  projectId: getSelectedProjectId()
})

const addEvent = async () => {
  try {
    const response = await fetch('http://localhost:8060/event-router/api/v1/events', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'x-eb-api-key': getApiKey()
      },
      body: JSON.stringify(newEvent.value)
    })

    if (response.ok) {
      isSlideoverOpen.value = false
      newEvent.value = {
        deduplicationId: '',
        id: '',
        title: '',
        description: '',
        userId: '',
        projectId: '27dd42b8-b6a3-4868-9f51-2fbd0ca00300'
      }
      await fetchEvents()
    }
  } catch (error) {
    console.error('Error adding event:', error)
  }
}

const columns = [
  {
    key: 'id',
    label: 'Event ID'
  },
  {
    key: 'userId',
    label: 'User ID'
  },
  {
    key: 'projectId',
    label: 'Project ID'
  },
  {
    key: 'title',
    label: 'Title'
  },
  {
    key: 'description',
    label: 'Description'
  }
]

const events = ref([])

const fetchEvents = async () => {
  try {
    const queryParams = new URLSearchParams()
    if (eventIdFilter.value) queryParams.append('id', eventIdFilter.value)
    if (userIdFilter.value) queryParams.append('userId', userIdFilter.value)

    const response = await fetch(
      `http://localhost:8060/event-manager/api/v1/events/projects/${getSelectedProjectId()}/filter?${queryParams.toString()}`
    )
    const data = await response.json()
    events.value = data
  } catch (error) {
    console.error('Error fetching events:', error)
    events.value = []
  }
}

watch([eventIdFilter, userIdFilter], () => {
  fetchEvents()
})

onMounted(() => {
  fetchEvents()
})
</script>