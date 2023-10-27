<template>
  <div v-if="student">
    <h2 v-once>Edit Student - {{ student.name }}</h2>
    <form @submit.prevent="updateStudent">
      <label for="name">Name:</label>
      <input v-model="student.name" type="text" id="name" name="name"> <br>
      <label for="email">E-mail:</label>
      <input v-model="student.email" type="text" id="email" name="email"> <br>
      <div>Course:
        <select v-model="student.courseCode">
          <option v-for="course in courses" :value="course.code">
            {{ course.name }}
          </option>
        </select>
      </div>
      <br>

      <button type="submit">Save</button>
    </form>
    <nuxt-link to="/students">Back to students</nuxt-link>
  </div>
  <h2>Error messages:</h2>
  {{ messages }}
</template>

<script setup>
const route = useRoute();
const username = route.params.username;
const config = useRuntimeConfig();
const api = config.public.API_URL;

const { push } = useRouter();
const student = ref(null);
const messages = ref([]);

const {data: courses} = await useFetch(`${api}/courses`)


const fetchStudent = async () => {
  try {
    const response = await fetch(`${api}/students/${username}`);
    if (response.ok) {
      student.value = await response.json();
    } else {
      messages.value.push('Failed to fetch course data.');
    }
  } catch (error) {
    messages.value.push('Network error: ' + error.message);
  }
};

const updateStudent = async () => {
  if (!student.value) {
    messages.value.push('Course data not loaded.');
    return;
  }

  try {
    const response = await fetch(`${api}/students/${username}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(student.value),
    });

    if (response.ok) {
      messages.value.push('Student updated successfully.');
    } else {
      messages.value.push('Failed to update course.');
    }
  } catch (error) {
    messages.value.push('Network error: ' + error.message);
  }
};

fetchStudent();
</script>
