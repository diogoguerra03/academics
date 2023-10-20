<template>
  <div v-if="teacher">
    <h2 v-once>Edit Teacher - {{ teacher.name }}</h2>
    <form @submit.prevent="updateTeacher">
      <label for="name">Name:</label>
      <input v-model="teacher.name" type="text" id="name" name="name"> <br>
      <label for="email">E-mail:</label>
      <input v-model="teacher.email" type="text" id="email" name="email"> <br>
      <div>Office:
        <input v-model="teacher.office" type="text" id="office" name="office">
      </div>
      <br>

      <button type="submit">Save</button>
    </form>
    <nuxt-link to="/courses">Back to Courses</nuxt-link>
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
const teacher = ref(null);
const messages = ref([]);

const {data: courses} = await useFetch(`${api}/courses`)


const fetchTeacher = async () => {
  try {
    const response = await fetch(`${api}/teachers/${username}`);
    if (response.ok) {
      teacher.value = await response.json();
    } else {
      messages.value.push('Failed to fetch course data.');
    }
  } catch (error) {
    messages.value.push('Network error: ' + error.message);
  }
};

const updateTeacher = async () => {
  if (!teacher.value) {
    messages.value.push('Course data not loaded.');
    return;
  }

  try {
    const response = await fetch(`${api}/teachers/${username}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(teacher.value),
    });

    if (response.ok) {
      messages.value.push('Course updated successfully.');
      push('/teachers');
    } else {
      messages.value.push('Failed to update course.');
    }
  } catch (error) {
    messages.value.push('Network error: ' + error.message);
  }
};

fetchTeacher();
</script>
