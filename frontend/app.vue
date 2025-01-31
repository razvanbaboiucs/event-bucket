<template>
  <div class="min-h-screen bg-gray-50">
    <UContainer class="py-8">
      <nav class="mb-8">
        <UHorizontalNavigation :links="links" class="border-b border-gray-200 dark:border-gray-800" />
      </nav>
      <NuxtPage />
    </UContainer>
  </div>
</template>

<script setup>
const links = [
  {
    name: 'events',
    label: 'Events',
    to: '/',
    exact: true
  },
  {
    name: 'identification',
    label: 'Identification',
    to: '/identification',
    exact: true
  },
  {
    name: 'insights',
    label: 'Insights',
    to: '/insights',
    exact: true
  },
  {
    name: 'account',
    label: 'Account',
    to: '/account',
    exact: true
  }
]
</script>