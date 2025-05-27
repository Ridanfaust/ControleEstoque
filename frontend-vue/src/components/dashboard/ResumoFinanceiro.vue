<template>
  <div class="grid grid-cols-1 gap-4 sm:grid-cols-2 md:gap-6">
    <div v-if="isLoading" class="col-span-full flex justify-center items-center py-10">
      <div class="animate-spin rounded-full h-8 w-8 border-b-2 border-primary-500"></div>
    </div>

    <div v-else-if="hasError" class="col-span-full flex justify-center items-center py-10">
      <p class="text-error-600 dark:text-error-500">Falha ao carregar métricas</p>
    </div>

    <div class="col-span-full mt-2">
      <div
        class="rounded-2xl border border-gray-200 bg-white p-5 dark:border-gray-800 dark:bg-white/[0.03] md:p-6">
        <h3 class="text-lg font-semibold text-gray-800 dark:text-white/90 mb-4">Resumo
          Financeiro</h3>

        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div class="p-4 bg-gray-50 dark:bg-gray-800/50 rounded-lg">
            <div class="text-sm font-medium text-gray-600 dark:text-gray-400 mb-1">
              Vendas Realizadas
            </div>
            <div class="text-xl font-bold text-gray-800 dark:text-white">
              {{ ordersCount }}
            </div>
          </div>
          <div class="p-4 bg-gray-50 dark:bg-gray-800/50 rounded-lg">
            <div class="text-sm font-medium text-gray-600 dark:text-gray-400 mb-1">
              Total de Vendas
            </div>
            <div class="text-xl font-bold text-gray-800 dark:text-white">
              {{ formatCurrency(totalSales) }}
            </div>
          </div>
          <div class="p-4 bg-gray-50 dark:bg-gray-800/50 rounded-lg">
            <div class="text-sm font-medium text-gray-600 dark:text-gray-400 mb-1">
              Total de Custos
            </div>
            <div class="text-xl font-bold text-gray-800 dark:text-white">
              {{ formatCurrency(totalCosts) }}
            </div>
          </div>
          <div class="p-4 bg-gray-50 dark:bg-gray-800/50 rounded-lg">
            <div class="text-sm font-medium text-gray-600 dark:text-gray-400 mb-1">
              Total de Lucro
            </div>
            <div class="text-xl font-bold"
                 :class="{'text-emerald-600 dark:text-emerald-400': totalProfit > 0, 'text-red-600 dark:text-red-400': totalProfit < 0}">
              {{ formatCurrency(totalProfit) }}
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted} from 'vue'
import VendaService from '@/services/VendaService'
import {formatCurrency} from '@/utils/CurrencyUtil.js'

const isLoading = ref(true)
const hasError = ref(false)
const ordersCount = ref(0)
const totalSales = ref(0)
const totalCosts = ref(0)
const totalProfit = ref(0)

const fetchMetrics = async () => {
  isLoading.value = true
  hasError.value = false

  try {
    const response = await VendaService.listar('', '', 0, 1000)

    if (response && response.data) {
      const orders = response.data.content

      ordersCount.value = orders.length

      let salesTotal = 0
      let costsTotal = 0

      orders.forEach(order => {
        if (order.situacao && order.situacao.descricao !== 'CANCELADA') {
          salesTotal += order.valorTotal || 0

          if (order.produtos && order.produtos.length > 0) {
            order.produtos.forEach(item => {
              costsTotal += (item.produto.valorFornecedor || 0) * item.quantidade
            })
          }
        }
      })

      totalSales.value = salesTotal
      totalCosts.value = costsTotal
      totalProfit.value = salesTotal - costsTotal
    }
  } catch (error) {
    console.error('Erro ao carregar métricas:', error)
    hasError.value = true
  } finally {
    isLoading.value = false
  }
}

// Load data when component mounts
onMounted(() => {
  fetchMetrics()
})
</script>
