<template>
  <div
    class="overflow-hidden rounded-2xl border border-gray-200 bg-white px-4 pb-3 pt-4 dark:border-gray-800 dark:bg-white/[0.03] sm:px-6"
  >
    <div class="flex flex-col gap-2 mb-4 sm:flex-row sm:items-center sm:justify-between">
      <div>
        <h3 class="text-lg font-semibold text-gray-800 dark:text-white/90">Vendas Recentes</h3>
      </div>
    </div>

    <div class="max-w-full overflow-x-auto custom-scrollbar">
      <div v-if="isLoading" class="flex justify-center items-center py-10">
        <div class="animate-spin rounded-full h-8 w-8 border-b-2 border-primary-500"></div>
      </div>

      <div v-else-if="hasError" class="flex justify-center items-center py-10">
        <p class="text-error-600 dark:text-error-500">Falha ao carregar pedidos recentes</p>
      </div>

      <table v-else class="min-w-full">
        <thead>
        <tr class="border-t border-gray-100 dark:border-gray-800">
          <th class="py-3 text-left">
            <p class="font-medium text-gray-500 text-theme-xs dark:text-gray-400">Pedido</p>
          </th>
          <th class="py-3 text-left">
            <p class="font-medium text-gray-500 text-theme-xs dark:text-gray-400">Data</p>
          </th>
          <th class="py-3 text-left">
            <p class="font-medium text-gray-500 text-theme-xs dark:text-gray-400">Valor</p>
          </th>
          <th class="py-3 text-left">
            <p class="font-medium text-gray-500 text-theme-xs dark:text-gray-400">Status</p>
          </th>
        </tr>
        </thead>
        <tbody>
        <tr
          v-for="(venda, index) in recentOrders"
          :key="venda.id"
          class="border-t border-gray-100 dark:border-gray-800"
        >
          <td class="py-3 whitespace-nowrap">
            <div class="flex items-center gap-3">
              <div
                class="h-[40px] w-[40px] overflow-hidden rounded-md flex items-center justify-center bg-gray-100 dark:bg-gray-800">
                <svg
                  class="h-5 w-5 text-gray-500 dark:text-gray-400"
                  xmlns="http://www.w3.org/2000/svg"
                  viewBox="0 0 24 24"
                  fill="none"
                  stroke="currentColor"
                  stroke-width="2"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                >
                  <rect x="2" y="4" width="20" height="16" rx="2"/>
                  <path d="M12 16v.01"/>
                  <path d="M6 10h12"/>
                </svg>
              </div>
              <div>
                <p class="font-medium text-gray-800 text-theme-sm dark:text-white/90">
                  Pedido #{{ venda.id }}
                </p>
                <span class="text-gray-500 text-theme-xs dark:text-gray-400">
                    {{ venda.produtos?.length || 0 }} itens
                  </span>
              </div>
            </div>
          </td>
          <td class="py-3 whitespace-nowrap">
            <p class="text-gray-500 text-theme-sm dark:text-gray-400">
              {{ formatarData(venda.dataCadastro) }}
            </p>
          </td>
          <td class="py-3 whitespace-nowrap">
            <p class="text-gray-500 text-theme-sm dark:text-gray-400">
              {{ formatCurrency(venda.valorTotal) }}
            </p>
          </td>
          <td class="py-3 whitespace-nowrap">
              <span
                :class="{
                  'rounded-full px-2 py-0.5 text-theme-xs font-medium': true,
                  'bg-success-50 text-success-600 dark:bg-success-500/15 dark:text-success-500':
                    venda.situacao?.descricao === 'CONCLUÍDA',
                  'bg-warning-50 text-warning-600 dark:bg-warning-500/15 dark:text-orange-400':
                    !venda.situacao || venda.situacao?.descricao !== 'CONCLUÍDA' && venda.situacao?.descricao !== 'CANCELADA',
                  'bg-error-50 text-error-600 dark:bg-error-500/15 dark:text-error-500':
                    venda.situacao?.descricao === 'CANCELADA',
                }"
              >
                {{ venda.situacao?.descricao || 'PENDENTE' }}
              </span>
          </td>
        </tr>
        <tr v-if="recentOrders.length === 0">
          <td colspan="4" class="py-8 text-center text-gray-500 dark:text-gray-400">
            Nenhum pedido recente encontrado
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted} from 'vue'
import {useRouter} from 'vue-router'
import VendaService from '@/services/VendaService'
import {formatCurrency} from '@/utils/CurrencyUtil.js'

const router = useRouter()
const isLoading = ref(true)
const hasError = ref(false)
const recentOrders = ref([])

const formatarData = (dataString) => {
  if (!dataString) return '-'

  const data = new Date(dataString)
  return data.toLocaleDateString('pt-BR', {
    day: '2-digit',
    month: '2-digit',
    year: 'numeric'
  })
}

const fetchRecentOrders = async () => {
  isLoading.value = true
  hasError.value = false

  try {
    // Buscar vendas e limitar aos 5 mais recentes
    const response = await VendaService.listar('', '', 0, 5)

    if (response && response.data && response.data.content) {
      // Ordenar por data decrescente para garantir que os mais recentes apareçam primeiro
      recentOrders.value = response.data.content
        .sort((a, b) => new Date(b.dataCadastro) - new Date(a.dataCadastro))
        .slice(0, 5)
    } else {
      recentOrders.value = []
    }
  } catch (error) {
    console.error('Erro ao buscar pedidos recentes:', error)
    hasError.value = true
  } finally {
    isLoading.value = false
  }
}

const navigateToOrders = () => {
  router.push('/venda')
}

const refreshOrders = () => {
  fetchRecentOrders()
}

// Carregar dados quando o componente for montado
onMounted(() => {
  fetchRecentOrders()
})
</script>
