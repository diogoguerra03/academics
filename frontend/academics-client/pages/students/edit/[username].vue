<template>
  <div v-if="student">
    <h2 v-once>Edit Student - {{ student.name }}</h2>
    <form @submit.prevent="updateStudent">
      <label for="name">Name:</label>
      <input v-model.trim="studentForm.name" type="text">
      <span v-if="studentForm.name !== null && !isNameValid" class="error">
 ERROR: {{ formFeedback.name }}</span> <br>
      <label for="email">E-mail:</label>
      <input v-model.trim="studentForm.email" type="text" id="email" name="email">
      <span v-if="studentForm.email !== null && !isEmailValid" class="error">
 ERROR: {{ formFeedback.email }}</span> <br>
      <div>Course:
        <select v-model.trim="studentForm.courseCode">
          <option v-for="course in courses" :value="course.code">
            {{ course.name }}
          </option>
        </select>
        <span v-if="studentForm.courseCode !== null && !isCourseValid" class="error">
 ERROR: {{ formFeedback.courseCode }}</span>
      </div>
      <br>

      <button type="submit" :disabled="!isFormValid">Save</button>
    </form>
    <nuxt-link to="/students">Back to students</nuxt-link>
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
const username = route.params.username;
const config = useRuntimeConfig();
const api = config.public.API_URL;

const { push } = useRouter();
const student = ref(null);
const messages = ref([]);

const {data: courses} = await useFetch(`${api}/courses`)

const studentForm = reactive({
  name: null,
  email: null,
  courseCode: null,
});

const formFeedback = reactive({
  name: '',
  email: '',
  courseCode: '',
});

// Field validation rules...
const isNameValid = computed(() => {
  if (!studentForm.name) {
    formFeedback.name = 'name is required'
    return false
  }
  if (studentForm.name.length < 3) {
    formFeedback.name = 'name must be at least 3 characters'
    return false
  }
  if (studentForm.name.length > 15) {
    formFeedback.name = 'name must be at most 15 characters'
    return false
  }

  return true
})
const isEmailValid = computed(() => {
  if (!studentForm.email) {
    formFeedback.email = 'email is required'
    return false
  }
  if (!studentForm.email.includes('@')) {
    formFeedback.email = 'email must be valid'
    return false
  }
  if (studentForm.email.length > 50) {
    formFeedback.email = 'email must be at most 50 characters'
    return false
  }
  if (studentForm.email.length < 3) {
    formFeedback.email = 'email must be at least 3 characters'
    return false
  }

  return true
})
const isCourseValid = computed(() => {
  if (!studentForm.courseCode) {
    formFeedback.courseCode = 'course is required'
    return false
  }
  // TODO
  return true
})
// Form validation rule...
const isFormValid = computed(() => {
  return isNameValid.value
      && isEmailValid.value
      && isCourseValid.value
})

const fetchStudent = async () => {
  try {
    const response = await fetch(`${api}/students/${username}`);
    if (response.ok) {
      student.value = await response.json();
      studentForm.name = student.value.name;
      studentForm.email = student.value.email;
      studentForm.courseCode = student.value.courseCode;
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

  // Atualizar os campos do estudante com os valores do formulário
  student.value.name = studentForm.name;
  student.value.email = studentForm.email;
  student.value.courseCode = studentForm.courseCode;

  try {
    const response = await fetch(`${api}/students/${username}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(student.value), // Enviar o objeto atualizado
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
