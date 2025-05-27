<template>
  <div
    class="flex items-center justify-between border-t border-gray-200 bg-white px-4 py-3 sm:px-6 dark:border-gray-700 dark:bg-transparent">
    <!-- Mobile pagination -->
    <div class="flex flex-1 items-center justify-between sm:hidden">
      <Button variant="outline" size="sm" @click="onChangePage(pagina - 1)"
              :disabled="pagina === 1">
        Anterior
      </Button>
      <span class="text-sm text-gray-700 dark:text-gray-300">
        Página {{ pagina }} de {{ totalPaginas }}
      </span>
      <Button variant="outline" size="sm" @click="onChangePage(pagina + 1)"
              :disabled="pagina >= totalPaginas">
        Próxima
      </Button>
    </div>

    <!-- Desktop pagination -->
    <div class="hidden sm:flex sm:flex-1 sm:items-center sm:justify-between">
      <div>
        <p class="text-sm text-gray-700 dark:text-gray-300">
          Mostrando <span class="font-medium">{{ (pagina - 1) * tamanhoPagina + 1 }}</span> a
          <span class="font-medium">{{ Math.min(pagina * tamanhoPagina, totalItens) }}</span> de
          <span class="font-medium">{{ totalItens }}</span> resultados
        </p>
      </div>
      <div>
        <nav class="isolate inline-flex -space-x-px rounded-md shadow-sm" aria-label="Pagination">
          <Button
            variant="outline"
            size="sm"
            class="rounded-r-md"
            @click="onChangePage(pagina - 1)"
            :disabled="pagina === 1"
          >
            <font-awesome-icon icon="chevron-left"/>
          </Button>
          <div v-for="numeroPagina in paginasExibidas" :key="numeroPagina">
            <Button
              :variant="pagina === numeroPagina ? 'primary' : 'outline'"
              size="sm"
              @click="onChangePage(numeroPagina)"
            >
              {{ numeroPagina }}
            </Button>
          </div>
          <Button
            variant="outline"
            size="sm"
            class="rounded-l-md"
            @click="onChangePage(pagina + 1)"
            :disabled="pagina >= totalPaginas"
          >
            <font-awesome-icon icon="chevron-right"/>
          </Button>
        </nav>
      </div>
    </div>
  </div>
</template>

<script setup>
import Button from "@/components/ui/Button.vue"
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";

const props = defineProps({
  pagina: {
    type: Number,
    required: true
  },
  tamanhoPagina: {
    type: Number,
    required: true
  },
  totalItens: {
    type: Number,
    required: true
  },
  totalPaginas: {
    type: Number,
    required: true
  },
  paginasExibidas: {
    type: Array,
    required: true
  }
})

const emit = defineEmits(['change-page'])

const onChangePage = (novaPagina) => {
  if (novaPagina >= 1 && novaPagina <= props.totalPaginas) {
    emit('change-page', novaPagina)
  }
}
</script>
