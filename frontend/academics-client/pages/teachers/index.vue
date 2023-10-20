<template>
  <div v-if="error">Error: {{ error.message }}</div>
  <div v-else>
    <nuxt-link to="teachers/create">Create a New Teacher</nuxt-link>
    <h2>Teachers</h2>
    <table>
      <tr>
        <th>Username</th>
        <th>Name</th>
        <th>E-mail</th>
        <th>Office</th>
        <th>actions</th>
      </tr>
      <tr v-for="teacher in teachers">
        <td>{{ teacher.username }}</td>
        <td>{{ teacher.name }}</td>
        <td>{{ teacher.email }}</td>
        <td>{{ teacher.office }}</td>
        <td>
          <nuxt-link :to="`/teachers/${teacher.username}`">Details</nuxt-link>
          |
          <nuxt-link :to="'/teachers/edit/' + teacher.username">Editar</nuxt-link>
          |
          <button @click="deleteTeacher(teacher.username)">Excluir</button>
        </td>
      </tr>
    </table>
  </div>
  <button @click.prevent="refresh">Refresh Data</button>
  <nuxt-link to="/">Return</nuxt-link>
</template>
<script setup>
const config = useRuntimeConfig()
const api = config.public.API_URL
const {data: teachers, error, refresh} = await useFetch(`${api}/teachers`)

const deleteTeacher = async (username) => {
  try {
    const response = await fetch(`${api}/teachers/${username}`, {
      method: "DELETE",
    });

    if (response.status === 200) {
      // Curso excluído com sucesso, atualize a lista
      refresh();
    } else {
      // Lidar com erros aqui, por exemplo, exibir uma mensagem de erro
      console.error("Erro ao excluir o curso");
    }
  } catch (error) {
    // Lidar com erros de rede aqui
    console.error("Erro de rede:", error);
  }
}
</script>