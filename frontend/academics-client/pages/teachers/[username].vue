<template>
  <div v-if="teacher">
    <h2>Details of {{ username }}</h2>
    {{ teacher }}
  </div>
  <div v-if="subjects">
    <h2>Enrolled in:</h2>
    <p v-for="subject in subjects" >
      {{ subject }}
    </p>
  </div>
  <h2>Error messages:</h2>
  {{ messages }}
</template>
<script setup>
const route = useRoute()
const username = route.params.username
const config = useRuntimeConfig()
const api = config.public.API_URL
const {data: teacher, error: teacherErr} = await
    useFetch(`${api}/teachers/${username}`)
const {data: subjects, error: subjectsErr} = await
    useFetch(`${api}/teachers/${username}/subjects`)
const messages = ref([])
if (teacherErr.value) messages.value.push(teacherErr.value)
if (subjectsErr.value) messages.value.push(subjectsErr.value)
</script>