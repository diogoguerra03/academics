<template>
  <div v-if="error">Error: {{ error.message }}</div>
  <div v-else>
    <nuxt-link to="students/create">Create a New Student</nuxt-link>
    <h2>Students</h2>
    <table>
      <tr>
        <th>Username</th>
        <th>Name</th>
        <th>E-mail</th>
        <th>Curso</th>
        <th>actions</th>
      </tr>
      <tr v-for="student in students">
        <td>{{ student.username }}</td>
        <td>{{ student.name }}</td>
        <td>{{ student.email }}</td>
        <td>{{ student.courseName }}</td>
        <td>
          <nuxt-link :to="`/students/${student.username}`">Details</nuxt-link>
          |
          <nuxt-link :to="'/students/edit/' + student.username">Editar</nuxt-link>
          |
          <button @click="deleteStudent(student.username)">Excluir</button>
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
const {data: students, error, refresh} = await useFetch(`${api}/students`)

const deleteStudent = async (username) => {
  try {
    const response = await fetch(`${api}/students/${username}`, {
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