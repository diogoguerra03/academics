<template>
  <form @submit.prevent="create">
    <div>Code:
      <input v-model="subjectForm.code" type="text"></div>
    <div>Name:
      <input v-model="subjectForm.name" type="text"></div>
    <div>Course Year:
      <input v-model="subjectForm.courseYear" type="text"></div>
    <div>Scholar Year:
      <input v-model="subjectForm.scholarYear" type="text"></div>
    <div>Course:
      <select v-model="subjectForm.courseCode">
        <option v-for="course in courses" :value="course.code">
          {{ course.name }}
        </option>
      </select>
    </div>
    <button type="reset">RESET</button>
    <button type="submit">CREATE</button>
    <nuxt-link to="/">Return</nuxt-link>
  </form>
  {{ message }}
</template>
<script setup>
const subjectForm = reactive({
  code: '',
  name: '',
  courseYear: '',
  scholarYear: '',
  courseCode: ''
})
const message = ref('')
const config = useRuntimeConfig()
const api = config.public.API_URL
const {data: courses} = await useFetch(`${api}/courses`)

async function create() {
  const requestOptions = {
    method: 'POST',
    headers: {"Content-Type": "application/json"},
    body: JSON.stringify(subjectForm)
  }
  const {error} = await useFetch(`${api}/subjects`, requestOptions)
  if (!error.value) navigateTo('/subjects')
  message.value = error.value
}
</script>