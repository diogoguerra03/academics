<template>
  <form @submit.prevent="create">
    <div>Username:
      <input v-model="teacherForm.username" type="text"></div>
    <div>Password:
      <input v-model="teacherForm.password" type="password"></div>
    <div>Name:
      <input v-model="teacherForm.name" type="text"></div>
    <div>E-mail:
      <input v-model="teacherForm.email" type="text"></div>
    <div>Office:
      <input v-model="teacherForm.office" type="text">
    </div>
    <button type="reset">RESET</button>
    <button type="submit">CREATE</button>
    <nuxt-link to="/">Return</nuxt-link>
  </form>
  {{ message }}
</template>
<script setup>
const teacherForm = reactive({
  username: '',
  password: '',
  email: '',
  name: '',
  office: ''
})
const message = ref('')
const config = useRuntimeConfig()
const api = config.public.API_URL
const {data: courses} = await useFetch(`${api}/courses`)

async function create() {
  const requestOptions = {
    method: 'POST',
    headers: {"Content-Type": "application/json"},
    body: JSON.stringify(teacherForm)
  }
  const {error} = await useFetch(`${api}/teachers`, requestOptions)
  if (!error.value) navigateTo('/teachers')
  message.value = error.value
}
</script>