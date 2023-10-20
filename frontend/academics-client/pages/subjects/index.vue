<template>
  <div v-if="error">Error: {{ error.message }}</div>
  <div v-else>
    <nuxt-link to="subjects/create">Create a New Subjects</nuxt-link>
    <h2>Subjects</h2>
    <table>
      <tr>
        <th>Code</th>
        <th>Name</th>
        <th>Course Name</th>
        <th>Course year</th>
        <th>Scholar year</th>
        <th>Students</th>
      </tr>
      <tr v-for="subject in subjects">
        <td>{{ subject.code }}</td>
        <td>{{ subject.name }}</td>
        <td>{{ subject.courseName }}</td>
        <td>{{ subject.courseYear }}</td>
        <td>{{ subject.scholarYear }}</td>
        <td>
          <nuxt-link :to="`/subjects/${subject.code}`">Students</nuxt-link>
          |
          <nuxt-link :to="'/subjects/edit/' + subject.code">Editar</nuxt-link>
          |
          <button @click="deleteSubject(subject.code)">Excluir</button>
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
const {data: subjects, error, refresh} = await useFetch(`${api}/subjects`)

const deleteSubject = async (code) => {
  try {
    const response = await fetch(`${api}/subjects/${code}`, {
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