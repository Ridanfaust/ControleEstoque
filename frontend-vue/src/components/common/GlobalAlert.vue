<template>
  <Alert
    v-if="alertState.show"
    :variant="alertState.variant"
    :title="alertState.title"
    :message="alertState.message"
    :showLink="false"
  />
</template>

<script setup lang="ts">
import Alert from "@/components/ui/Alert.vue"
import { alertState, useAlertStore } from "@/stores/AlertStore"
import { useRouter } from "vue-router"

const router = useRouter()
const { hideAlert } = useAlertStore()

router.beforeEach((to, from, next) => {
  if (from.path !== to.path && alertState.show) {
    hideAlert()
  }
  next()
})

</script>
