<template>
  <div v-if="subject">
    <h2>Details of {{ name }}</h2>
    {{ subject }}
  </div>
  <div v-if="students">
    <h2>Students enrolled in in:</h2>
    <p v-for="student in students" >
      {{ student }}
    </p>
  </div>
  <h2>Error messages:</h2>
  {{ messages }}
</template>
<script setup>
const route = useRoute()
const code = route.params.code
const config = useRuntimeConfig()
const api = config.public.API_URL
const {data: subject, error: subjectErr} = await
    useFetch(`${api}/subjects/${code}`)
const {data: students, error: studentsErr} = await
    useFetch(`${api}/subjects/${code}/students`)
const messages = ref([])
if (studentsErr.value) messages.value.push(studentsErr.value)
if (subjectErr.value) messages.value.push(subjectErr.value)
</script>