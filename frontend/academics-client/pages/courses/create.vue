<template>
  <form @submit.prevent="create">
    <div>Code:
      <input v-model="courseForm.code" type="text"></div>
    <div>Name:
      <input v-model="courseForm.name" type="text"></div>

    <button type="reset">RESET</button>
    <button type="submit">CREATE</button>
    <nuxt-link to="/">Return</nuxt-link>
  </form>
  {{ message }}
</template>
<script setup>
const courseForm = reactive({
  code: '',
  name: ''
})
const message = ref('')
const config = useRuntimeConfig()
const api = config.public.API_URL
async function create() {
  const requestOptions = {
    method: 'POST',
    headers: { "Content-Type": "application/json"},
    body: JSON.stringify(courseForm)
  }
  const { error } = await useFetch(`${api}/courses`, requestOptions)
  if (!error.value) navigateTo('/courses')
  message.value = error.value
}
</script>