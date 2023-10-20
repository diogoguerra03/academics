<template>
  <div v-if="error">Error: {{ error.message }}</div>
  <div v-else>
    <nuxt-link to="teachers/create">Create a New Teacher</nuxt-link>
    <h2>Teachers</h2>
    <table>
      <tr><th>Username</th><th>Name</th><th>E-mail</th><th>Office</th><th>actions</th></tr>
      <tr v-for="teacher in teachers">
        <td>{{ teacher.username }}</td>
        <td>{{ teacher.name }}</td>
        <td>{{ teacher.email }}</td>
        <td>{{ teacher.office }}</td>
        <td><nuxt-link :to="`/teachers/${teacher.username}`">Details</nuxt-link> </td>
      </tr>
    </table>
  </div>
  <button @click.prevent="refresh">Refresh Data</button><nuxt-link to="/">Return</nuxt-link>
</template>
<script setup>
const config = useRuntimeConfig()
const api = config.public.API_URL
const {data: teachers, error, refresh} = await useFetch(`${api}/teachers`)
</script>