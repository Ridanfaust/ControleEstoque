import "./assets/main.css"
import "swiper/css"
import "swiper/css/navigation"
import "swiper/css/pagination"
import "jsvectormap/dist/jsvectormap.css"
import "flatpickr/dist/flatpickr.css"

import { createApp } from "vue"
import App from "./App.vue"
import router from "./router"
import VueApexCharts from "vue3-apexcharts"
import { library } from "@fortawesome/fontawesome-svg-core"
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome"
import { fas } from "@fortawesome/free-solid-svg-icons"

library.add(fas)

const app = createApp(App)
app.component("font-awesome-icon", FontAwesomeIcon)

app.use(router)
app.use(VueApexCharts)

app.mount("#app")
