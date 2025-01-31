<template>
  <div>
    <div class="flex justify-between items-center mb-6">
      <h1 class="text-2xl font-bold">Insights</h1>
      <UButton
        icon="i-heroicons-plus"
        @click="isSlideoverOpen = true"
      >
        Add Insight
      </UButton>
    </div>
    <UTable
      :rows="insights"
      :columns="columns"
      hover
    >
      <template #id-data="{ row }">
        <button
          class="text-blue-600 hover:underline"
          @click="handleRowClick(row)"
        >
          {{ row.id }}
        </button>
      </template>
    </UTable>
    <USlideover
      v-model="isSlideoverOpen"
      :ui="{ width: 'w-1/3' }"
    >
      <template #header>
        <div class="text-xl font-bold">Add New Insight</div>
      </template>
      <div class="p-4 space-y-4">
        <UFormGroup label="Insight ID">
          <UInput v-model="newInsight.id" placeholder="Enter insight ID" />
        </UFormGroup>
        <UFormGroup label="Title">
          <UInput v-model="newInsight.title" placeholder="Enter title" />
        </UFormGroup>
        <UFormGroup label="Value">
          <UInput v-model="newInsight.value" placeholder="Enter value" />
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
            @click="addInsight"
          >
            Add Insight
          </UButton>
        </div>
      </div>
    </USlideover>
  </div>
    <USlideover
      v-model="isMutationSlideoverOpen"
      :ui="{ width: 'w-1/3' }"
    >
      <template #header>
        <div class="text-xl font-bold">Mutate Insight</div>
      </template>
      <div class="p-4 space-y-4">
        <UFormGroup label="Mutation Type">
          <USelect
            v-model="mutationType"
            :options="['INCREMENT', 'DECREMENT']"
          />
        </UFormGroup>
        <UFormGroup label="Mutation Value">
          <UInput
            v-model="mutationValue"
            type="number"
            placeholder="Enter mutation value"
          />
        </UFormGroup>
        <div class="flex justify-end gap-2">
          <UButton
            color="gray"
            variant="soft"
            @click="isMutationSlideoverOpen = false"
          >
            Cancel
          </UButton>
          <UButton
            color="primary"
            @click="sendMutation"
          >
            Send Mutation
          </UButton>
        </div>
      </div>
    </USlideover>
</template>

<script setup>
const isSlideoverOpen = ref(false)
const isMutationSlideoverOpen = ref(false)
const selectedInsight = ref(null)
const mutationType = ref('INCREMENT')
const mutationValue = ref('')

import { getSelectedProjectId, getApiKey } from '../utils/projectId'

const handleRowClick = (row) => {
  selectedInsight.value = row
  isMutationSlideoverOpen.value = true
}

const sendMutation = async () => {
  try {
    const response = await fetch('http://localhost:8060/event-router/api/v1/insights/mutation', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'x-eb-api-key': getApiKey()
      },
      body: JSON.stringify({
        id: selectedInsight.value.id,
        projectId: getSelectedProjectId(),
        mutationType: mutationType.value,
        mutationValue: parseFloat(mutationValue.value)
      })
    })

    if (response.ok) {
      isMutationSlideoverOpen.value = false
      mutationType.value = 'INCREMENT'
      mutationValue.value = ''
      selectedInsight.value = null
      await fetchInsights()
    }
  } catch (error) {
    console.error('Error sending mutation:', error)
  }
}

const newInsight = ref({
  id: '',
  title: '',
  value: '',
  projectId: getSelectedProjectId()
})

const addInsight = async () => {
  try {
    const response = await fetch('http://localhost:8060/event-manager/api/v1/insights', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(newInsight.value)
    })

    if (response.ok) {
      isSlideoverOpen.value = false
      newInsight.value = {
        id: '',
        title: '',
        value: '',
        projectId: '27dd42b8-b6a3-4868-9f51-2fbd0ca00300'
      }
      await fetchInsights()
    }
  } catch (error) {
    console.error('Error adding insight:', error)
  }
}

const columns = [
  {
    key: 'id',
    label: 'Insight ID'
  },
  {
    key: 'projectId',
    label: 'Project ID'
  },
  {
    key: 'value',
    label: 'Value'
  },
  {
    key: 'title',
    label: 'Title'
  }
]

const insights = ref([])

const fetchInsights = async () => {
  try {
    const response = await fetch(
      `http://localhost:8060/event-manager/api/v1/insights/${getSelectedProjectId()}`
    )
    const data = await response.json()
    insights.value = data
  } catch (error) {
    console.error('Error fetching insights:', error)
    insights.value = []
  }
}

onMounted(() => {
  fetchInsights()
})
</script>