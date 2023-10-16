<template>
  <div v-if="error">Error: {{ error.message }}</div>
  <div v-else>
    <nuxt-link to="courses/create">Create a New Course</nuxt-link>
    <h2>Courses</h2>
    <table>
      <tr><th>Code</th><th>Name</th><th>Ações</th></tr>
      <tr v-for="course in courses">
        <td>{{ course.code }}</td>
        <td>{{ course.name }}</td>
        <td>
          <!-- <nuxt-link :to="`/courses/${course.code}/subjects`">Details</nuxt-link> | -->
          <nuxt-link :to="'/courses/edit/' + course.code">Editar</nuxt-link> |
          <button @click="deleteCourse(course.code)">Excluir</button>
        </td>
      </tr>
    </table>
  </div>
  <button @click.prevent="refresh">Refresh Data</button><nuxt-link to="/">Return</nuxt-link>
</template>
<script setup>
const config = useRuntimeConfig()
const api = config.public.API_URL
const {data: courses, error, refresh} = await useFetch(`${api}/courses`)

const deleteCourse = async (code) => {
  try {
    const response = await fetch(`${api}/courses/${code}`, {
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