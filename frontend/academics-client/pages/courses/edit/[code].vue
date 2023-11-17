<template>
  <div v-if="course">
    <h2 v-once>Edit Course - {{ course.name }}</h2>
    <form @submit.prevent="updateCourse">
      <label for="name">Name:</label>
      <div>
      <input v-model.trim="course.name" type="text" id="name" name="name">
      <span v-if="course.name !== null && !isNameValid" class="error">{{ formFeedback.name }}</span>
      </div>
      <button type="submit" :disabled="!isFormValid">Save</button>
    </form>
    <nuxt-link to="/courses">Back to Courses</nuxt-link>
  </div>
  <h2>Error messages:</h2>
  {{ messages }}
</template>
<style scoped>
.error {
  color: red
}
</style>
<script setup>
const route = useRoute();
const code = route.params.code;
const config = useRuntimeConfig();
const api = config.public.API_URL;

const formFeedback = reactive({
  name: '',
});

const { push } = useRouter();
const course = ref(null);
const messages = ref([]);

const isNameValid = computed(() => {
  if (course.value.name === null) {
    formFeedback.name = '';
    return false;
  }
  if (course.value.name.length < 3) {
    formFeedback.name = 'Name must be at least 3 characters long.';
    return false;
  }
  if (course.value.name.length > 50) {
    formFeedback.name = 'Name must be at most 50 characters long.';
    return false;
  }
  formFeedback.name = '';
  return true;
});

const isFormValid = computed(() => {
  return isNameValid.value;
});

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
