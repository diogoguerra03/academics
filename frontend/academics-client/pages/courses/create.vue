<template>
  <form @submit.prevent="create">
    <div>Code:
      <input v-model.trim="courseForm.code" type="text">
      <span v-if="courseForm.code !== null && !isCodeValid" class="error">ERROR: {{ formFeedback.code }}</span>
    </div>
    <div>Name:
      <input v-model.trim="courseForm.name" type="text">
      <span v-if="courseForm.name !== null && !isNameValid" class="error">ERROR: {{ formFeedback.name }} </span>
    </div>

    <button type="reset">RESET</button>
    <button type="submit" :disabled="!isFormValid">CREATE</button>
    <nuxt-link to="/">Return</nuxt-link>
  </form>
  {{ message }}
</template>
<style scoped>
.error {
  color: red
}
</style>
<script setup>
const courseForm = reactive({
  code: null,
  name: null
})

const formFeedback = reactive({
  code: '',
  name: ''
})

const message = ref('')
const config = useRuntimeConfig()
const api = config.public.API_URL

const isCodeValid = computed(() => {
  if (courseForm.code === null) {
    formFeedback.code = ''
    return false
  }
  if (courseForm.code.length < 3) {
    formFeedback.code = 'Code must be at least 3 characters long.'
    return false
  }
  if (courseForm.code.length > 10) {
    formFeedback.code = 'Code must be at most 10 characters long.'
    return false
  }
  if (!/^\d+$/.test(courseForm.code)) {
    formFeedback.code = 'Code must contain only numbers.'
    return false
  }
  formFeedback.code = ''
  return true
})

const isNameValid = computed(() => {
  if (courseForm.name === null) {
    formFeedback.name = ''
    return false
  }
  if (courseForm.name.length < 3) {
    formFeedback.name = 'Name must be at least 3 characters long.'
    return false
  }
  if (courseForm.name.length > 50) {
    formFeedback.name = 'Name must be at most 50 characters long.'
    return false
  }
  formFeedback.name = ''
  return true
})

const isFormValid = computed(() => {
  return isCodeValid.value && isNameValid.value
})

async function create() {
  const requestOptions = {
    method: 'POST',
    headers: { "Content-Type": "application/json"},
    body: JSON.stringify(courseForm)
  }
  const { error } = await useFetch(`${api}/courses`, requestOptions)
  if (!error.value) navigateTo('/courses')
  message.value = error.value
}
</script>