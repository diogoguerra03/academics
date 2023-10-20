<template>
  <div v-if="subject">
    <h2 v-once>Edit Subject - {{ subject.name }}</h2>
    <form @submit.prevent="updateSubject">
      <label for="name">Name:</label>
      <input v-model="subject.name" type="text" id="name" name="name"> <br>
      <div>Course:
        <select v-model="subject.courseCode">
          <option v-for="course in courses" :value="course.code">
            {{ course.name }}
          </option>
        </select>
      </div>
      <label for="courseYear">Course Year:</label>
      <input v-model="subject.courseYear" type="number" id="courseYear" name="courseYear"><br>
      <label for="scholarYear">Scholar Year:</label>
      <input v-model="subject.scholarYear" type="number" id="scholarYear" name="scholarYear">
      <br><button type="submit">Save</button>
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
const subject = ref(null);
const messages = ref([]);

const {data: courses} = await useFetch(`${api}/courses`)


const fetchSubject = async () => {
  try {
    const response = await fetch(`${api}/subjects/${code}`);
    if (response.ok) {
      subject.value = await response.json();
    } else {
      messages.value.push('Failed to fetch course data.');
    }
  } catch (error) {
    messages.value.push('Network error: ' + error.message);
  }
};

const updateSubject = async () => {
  if (!subject.value) {
    messages.value.push('Subject data not loaded.');
    return;
  }

  try {
    const response = await fetch(`${api}/subjects/${code}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(subject.value),
    });

    if (response.ok) {
      messages.value.push('Course updated successfully.');
      push('/subjects');
    } else {
      messages.value.push('Failed to update subject.');
    }
  } catch (error) {
    messages.value.push('Network error: ' + error.message);
  }
};

fetchSubject();
</script>
