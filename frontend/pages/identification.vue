<template>
  <div>
    <div class="flex justify-between items-center mb-6">
      <h1 class="text-2xl font-bold">Identification</h1>
      <UButton
        icon="i-heroicons-plus"
        @click="isSlideoverOpen = true"
      >
        Add Identification
      </UButton>
    </div>
    <UTable
      :rows="identifications"
      :columns="columns"
    />
  </div>
    <USlideover
      v-model="isSlideoverOpen"
      :ui="{ width: 'w-1/3' }"
    >
      <template #header>
        <div class="text-xl font-bold">Add New Identification</div>
      </template>
      <div class="p-4 space-y-4">
        <UFormGroup label="User ID">
          <UInput v-model="newIdentification.userId" placeholder="Enter user ID" />
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
            @click="addIdentification"
          >
            Add Identification
          </UButton>
        </div>
      </div>
    </USlideover>
</template>

<script setup>
const isSlideoverOpen = ref(false)

const newIdentification = ref({
  userId: '',
  projectId: '27dd42b8-b6a3-4868-9f51-2fbd0ca00300'
})

const addIdentification = async () => {
  try {
    const response = await fetch('http://localhost:8060/event-router/api/v1/identifications', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'x-eb-api-key': 'i31mhdiWYlsEdYqMNu2Rn6NNciWN0QSufwsQP2ddJePEGlgBPBtZHGp3RYlyVfRy'
      },
      body: JSON.stringify(newIdentification.value)
    })

    if (response.ok) {
      isSlideoverOpen.value = false
      newIdentification.value.userId = ''
      await fetchIdentifications()
    }
  } catch (error) {
    console.error('Error adding identification:', error)
  }
}

const columns = [
  {
    key: 'userId',
    label: 'User ID'
  },
  {
    key: 'projectId',
    label: 'Project ID'
  }
]

const identifications = ref([])

const fetchIdentifications = async () => {
  try {
    const response = await fetch(
      'http://localhost:8060/event-manager/api/v1/identifications/projects/27dd42b8-b6a3-4868-9f51-2fbd0ca00300'
    )
    const data = await response.json()
    identifications.value = data
  } catch (error) {
    console.error('Error fetching identifications:', error)
    identifications.value = []
  }
}

onMounted(() => {
  fetchIdentifications()
})
</script>