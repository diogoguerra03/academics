<template>
  <div v-if="course">
    <h2 v-once>Edit Course - {{ course.name }}</h2>
    <form @submit.prevent="updateCourse">
      <label for="name">Name:</label>
      <input v-model="course.name" type="text" id="name" name="name">
      <button type="submit">Save</button>
    </form>
    <nuxt-link to="/courses">Back to Courses</nuxt-link>
  </div>
  <h2>Error messages:</h2>
  {{ messages }}
</template>

<script setup>
const route = useRoute();
const code = route.params.code;
const config = useRuntimeConfig();
const api = config.public.API_URL;

const { push } = useRouter();
const course = ref(null);
const messages = ref([]);

const fetchCourse = async () => {
  try {
    const response = await fetch(`${api}/courses/${code}`);
    if (response.ok) {
      course.value = await response.json();
    } else {
      messages.value.push('Failed to fetch course data.');
    }
  } catch (error) {
    messages.value.push('Network error: ' + error.message);
  }
};

const updateCourse = async () => {
  if (!course.value) {
    messages.value.push('Course data not loaded.');
    return;
  }

  try {
    const response = await fetch(`${api}/courses/${code}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ name: course.value.name }),
    });

    if (response.ok) {
      messages.value.push('Course updated successfully.');
      push('/courses');
    } else {
      messages.value.push('Failed to update course.');
    }
  } catch (error) {
    messages.value.push('Network error: ' + error.message);
  }
};

fetchCourse();
</script>
