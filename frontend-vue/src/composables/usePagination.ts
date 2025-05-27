import { ref, computed } from "vue"
import { useAlertStore } from "@/stores/AlertStore"
import MensagemErroHandler from "@/utils/MensagemErroHandler"

export function usePagination<T>(fetchFunction: (page: number, size: number, filters: any) => Promise<any>) {
  const alertStore = useAlertStore()

  // Pagination state
  const items = ref<T[]>([])
  const pagina = ref(1)
  const tamanhoPagina = ref(10)
  const totalItens = ref(0)
  const isLoading = ref(false)

  const totalPaginas = computed(() => Math.ceil(totalItens.value / tamanhoPagina.value))

  // Calculate pages to display in pagination component
  const paginasExibidas = computed(() => {
    const pages = []
    const maxVisiblePages = 5
    let startPage = Math.max(1, pagina.value - Math.floor(maxVisiblePages / 2))
    let endPage = Math.min(totalPaginas.value, startPage + maxVisiblePages - 1)

    if (endPage - startPage + 1 < maxVisiblePages) {
      startPage = Math.max(1, endPage - maxVisiblePages + 1)
    }

    for (let i = startPage; i <= endPage; i++) {
      pages.push(i)
    }

    return pages
  })

  // Load data with pagination
  const loadData = async (filters = {}) => {
    isLoading.value = true
    try {
      const response = await fetchFunction(pagina.value - 1, tamanhoPagina.value, filters)
      items.value = response.data.content || []
      totalItens.value = response.data.totalElements || 0
    } catch (error) {
      alertStore.showError(MensagemErroHandler.msgErro(error, "Erro ao carregar dados"))
    } finally {
      isLoading.value = false
    }
  }

  // Change page
  const mudarPagina = (novaPagina: number) => {
    if (novaPagina >= 1 && novaPagina <= totalPaginas.value) {
      pagina.value = novaPagina
      return true // Indicates page changed
    }
    return false // Page didn't change
  }

  // Change page size
  const mudarTamanhoPagina = (novoTamanho: number) => {
    tamanhoPagina.value = novoTamanho
    pagina.value = 1 // Reset to first page
  }

  // Reset to first page
  const resetarPagina = () => {
    pagina.value = 1
  }

  return {
    items,
    pagina,
    tamanhoPagina,
    totalItens,
    totalPaginas,
    paginasExibidas,
    isLoading,
    loadData,
    mudarPagina,
    mudarTamanhoPagina,
    resetarPagina
  }
}
