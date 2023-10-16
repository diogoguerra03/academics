// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  devtools: { enabled: true },
  runtimeConfig: {
	 public: {
		API_URL: process.env.API_URL || 'http://localhost:8080/academics/api'
	 }
}
})

// https://nuxt.com/docs/api/configuration
/*export default {
	devtools: { enabled: true },
	runtimeConfig: {
		public: {
			API_URL: process.env.API_URL || 'http://localhost:8080/academics/api'
		}
	},
	buildModules: ['@nuxt/http'],
	http: {
		baseURL: process.env.API_URL || 'http://localhost:8080/academics/api'
	},
	// ... Outras configurações do seu projeto Nuxt

	// Defina as rotas personalizadas aqui
	router: {
		extendRoutes(routes: Array<any>) { // Forneça um tipo explícito para 'routes'
			// Adicione uma nova rota para CourseSubjects
			routes.push({
				name: 'course-subjects',
				path: '/courses/:code/subjects',
				component: 'pages/courses/_code/subjects.vue'
			})
		}
	}
}*/

