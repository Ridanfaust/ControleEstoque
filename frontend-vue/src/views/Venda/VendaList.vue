<template>
  <AppLayout>
    <PageBreadcrumb :pageTitle="currentPageTitle"/>
    <div class="space-y-5 sm:space-y-6">
      <ComponentCard title="Filtros de Pesquisa">
        <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
          <div>
            <label class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400">
              Código
            </label>
            <input
              type="text"
              v-model="filtros.codigo"
              placeholder="Buscar por código"
              class="dark:bg-dark-900 h-11 w-full rounded-lg border border-gray-300 bg-transparent px-4 py-2.5 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800"
            />
          </div>
          <div>
            <label class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400">
              Descrição
            </label>
            <input
              type="text"
              v-model="filtros.descricao"
              placeholder="Buscar por descrição"
              class="dark:bg-dark-900 h-11 w-full rounded-lg border border-gray-300 bg-transparent px-4 py-2.5 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800"
            />
          </div>

          <div class="flex items-end">
            <Button size="sm" variant="primary" @click="buscar">Buscar</Button>
            <Button size="sm" variant="outline" class="ml-2" @click="limparFiltros">Limpar</Button>
          </div>
        </div>
      </ComponentCard>

      <ComponentCard title="Resumo Financeiro (baseado nos resultados mostrados abaixo)">
        <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
          <div class="p-4 bg-gray-50 dark:bg-gray-800/50 rounded-lg">
            <div class="text-sm font-medium text-gray-600 dark:text-gray-400 mb-1">
              Total de Vendas
            </div>
            <div class="text-xl font-bold text-gray-800 dark:text-white">
              {{ formatarMoeda(totalVendas) }}
            </div>
          </div>
          <div class="p-4 bg-gray-50 dark:bg-gray-800/50 rounded-lg">
            <div class="text-sm font-medium text-gray-600 dark:text-gray-400 mb-1">
              Total de Custos
            </div>
            <div class="text-xl font-bold text-gray-800 dark:text-white">
              {{ formatarMoeda(totalCustos) }}
            </div>
          </div>
          <div class="p-4 bg-gray-50 dark:bg-gray-800/50 rounded-lg">
            <div class="text-sm font-medium text-gray-600 dark:text-gray-400 mb-1">
              Total de Lucro
            </div>
            <div class="text-xl font-bold"
                 :class="{'text-emerald-600 dark:text-emerald-400': totalLucro > 0, 'text-red-600 dark:text-red-400': totalLucro < 0}">
              {{ formatarMoeda(totalLucro) }}
            </div>
          </div>
        </div>
      </ComponentCard>

      <ComponentCard title="Relatório de Vendas">
        <div
          class="overflow-hidden rounded-xl border border-gray-200 bg-white dark:border-gray-800 dark:bg-white/[0.03]">
          <div class="max-w-full overflow-x-auto custom-scrollbar">
            <table class="min-w-full">
              <thead>
              <tr class="border-b border-gray-200 dark:border-gray-700">
                <th class="px-5 py-3 text-left w-3/12 sm:px-6">
                  <p class="font-medium text-gray-500 text-theme-xs dark:text-gray-400">Venda</p>
                </th>
                <th class="px-5 py-3 text-left w-2/12 sm:px-6">
                  <p class="font-medium text-gray-500 text-theme-xs dark:text-gray-400">Situação</p>
                </th>
                <th class="px-5 py-3 text-left w-2/12 sm:px-6">
                  <p class="font-medium text-gray-500 text-theme-xs dark:text-gray-400">Data</p>
                </th>
                <th class="px-5 py-3 text-left w-2/12 sm:px-6">
                  <p class="font-medium text-right text-gray-500 text-theme-xs dark:text-gray-400">
                    Total</p>
                </th>
                <th class="px-5 py-3 text-center w-2/12 sm:px-6">
                  <p class="font-medium text-right text-gray-500 text-theme-xs dark:text-gray-400">
                    Ações</p>
                </th>
              </tr>
              </thead>
              <tbody class="divide-y divide-gray-200 dark:divide-gray-700">
              <tr v-if="vendas.length === 0">
                <td colspan="6" class="px-5 py-4 text-center text-gray-500">
                  Nenhum registro encontrado
                </td>
              </tr>
              <template v-for="venda in vendas" :key="venda.id">
                <tr
                  class="border-t border-gray-100 dark:border-gray-800 hover:bg-gray-50 dark:hover:bg-gray-800/50">
                  <td class="px-5 py-4 sm:px-6">
                    <div class="flex items-center">
                      <button
                        @click="toggleProdutos(venda.id)"
                        class="mr-2 text-gray-500 hover:text-gray-700 dark:text-gray-400 dark:hover:text-white"
                      >
                        <font-awesome-icon
                          :icon="expandedVendas.includes(venda.id) ? 'chevron-down' : 'chevron-right'"
                        />
                      </button>
                      <span class="font-medium text-gray-800 text-theme-sm dark:text-white/90">
                        Venda #{{ venda.id }}
                      </span>
                    </div>
                  </td>
                  <td class="px-5 py-4 sm:px-6">
                    <Badge
                      :color="getBadgeColor(venda.situacao.descricao)"
                      variant="light"
                    >
                      {{ venda.situacao.descricao }}
                    </Badge>
                  </td>
                  <td class="px-5 py-4 sm:px-6">
                    <span class="block font-medium text-gray-800 text-theme-sm dark:text-white/90">
                      {{ formatarData(venda.dataCadastro) }}
                    </span>
                  </td>
                  <td class="px-5 py-4 sm:px-6">
                    <span
                      class="block text-right font-medium text-gray-800 text-theme-sm dark:text-white/90">
                      {{ formatarMoeda(venda.valorTotal) }}
                    </span>
                  </td>
                  <td class="px-5 py-4 text-right sm:px-6">
                    <Button
                      v-if="venda.situacao !== 'CANCELADA'"
                      size="sm"
                      variant="danger"
                      @click="cancelarVenda(venda)"
                    >
                      <font-awesome-icon icon="ban"/>
                    </Button>
                  </td>
                </tr>

                <tr
                  v-if="expandedVendas.includes(venda.id)"
                  class="bg-gray-50 dark:bg-gray-800/30"
                >
                  <td colspan="6" class="px-0 py-0">
                    <div class="border-l-4 border-brand-500 ml-10 pl-2">
                      <table class="min-w-full">
                        <thead>
                        <tr class="bg-gray-100 dark:bg-gray-800/80 text-xs">
                          <th class="px-5 py-2 text-left sm:px-6">
                            <p class="font-medium text-gray-500 text-theme-xs dark:text-gray-400">
                              Produto</p>
                          </th>
                          <th class="px-5 py-2 text-left sm:px-6">
                            <p class="font-medium text-gray-500 text-theme-xs dark:text-gray-400">
                              Quantidade</p>
                          </th>
                          <th class="px-5 py-2 text-left sm:px-6">
                            <p
                              class="font-medium text-right text-gray-500 text-theme-xs dark:text-gray-400">
                              Valor Unitário</p>
                          </th>
                          <th class="px-5 py-2 text-right sm:px-6">
                            <p class="font-medium text-gray-500 text-theme-xs dark:text-gray-400">
                              Subtotal</p>
                          </th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr v-for="item in venda.produtos" :key="item.id"
                            class="border-b border-gray-100 dark:border-gray-700/30">
                          <td class="px-5 py-3 sm:px-6">
                              <span
                                class="block font-medium text-gray-700 text-theme-xs dark:text-white/80">
                                {{ item.produto.descricao }}
                              </span>
                          </td>
                          <td class="px-5 py-3 sm:px-6">
                              <span
                                class="block font-medium text-gray-700 text-theme-xs dark:text-white/80">
                                {{ item.quantidade }}
                              </span>
                          </td>
                          <td class="px-5 py-3 sm:px-6">
                              <span
                                class="block text-right font-medium text-gray-700 text-theme-xs dark:text-white/80">
                                {{ formatarMoeda(item.valorVenda) }}
                              </span>
                          </td>
                          <td class="px-5 py-3 text-right sm:px-6">
                              <span
                                class="block font-medium text-gray-700 text-theme-xs dark:text-white/80">
                                {{ formatarMoeda(item.valorVenda * item.quantidade) }}
                              </span>
                          </td>
                        </tr>
                        </tbody>
                      </table>
                    </div>
                  </td>
                </tr>
              </template>
              </tbody>
            </table>
          </div>
          <div>

          </div>
          <!-- Paginação -->
          <Pagination
            :pagina="pagina"
            :tamanhoPagina="tamanhoPagina"
            :totalItens="totalItens"
            :totalPaginas="totalPaginas"
            :paginasExibidas="paginasExibidas"
            @change-page="handleChangePage"
          />
        </div>
      </ComponentCard>
    </div>
  </AppLayout>
</template>

<script setup>
import {onMounted, reactive, ref, computed, watch} from 'vue'
import PageBreadcrumb from "@/components/common/PageBreadcrumb.vue"
import AppLayout from "@/components/layout/AppLayout.vue"
import ComponentCard from "@/components/common/ComponentCard.vue"
import Button from "@/components/ui/Button.vue"
import VendaService from "@/services/VendaService.ts"
import MensagemErroHandler from "@/utils/MensagemErroHandler"
import {useAlertStore} from "@/stores/AlertStore"
import {usePagination} from '@/composables/usePagination'
import Pagination from '@/components/common/Pagination.vue'
import {formatarData} from "@/utils/DateUtil.ts"
import Badge from "@/components/ui/Badge.vue"
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome"

const currentPageTitle = ref("Vendas")
const service = VendaService
const errorHandler = MensagemErroHandler
const {showSuccess, showError, hideAlert} = useAlertStore()

const expandedVendas = ref([])

const filtros = reactive({
  codigo: '',
  descricao: ''
})

const {
  items: vendas,
  pagina,
  tamanhoPagina,
  totalItens,
  totalPaginas,
  paginasExibidas,
  loadData,
  mudarPagina,
  resetarPagina
} = usePagination(async (page, size, filters) => {
  return await VendaService.listar(filters.codigo, filters.descricao, page, size)
})

const formatarMoeda = (valor) => {
  return new Intl.NumberFormat('pt-BR', {
    style: 'currency',
    currency: 'BRL'
  }).format(valor)
}

const totalVendas = ref(0)
const totalCustos = ref(0)
const totalLucro = ref(0)

const calcularResumoFinanceiro = () => {
  let somaVendas = 0
  let somaCustos = 0

  vendas.value.forEach(venda => {
    if (venda.situacao.descricao !== 'CANCELADA') {
      somaVendas += venda.valorTotal || 0

      if (venda.produtos && venda.produtos.length > 0) {
        venda.produtos.forEach(item => {
          somaCustos += (item.produto.valorFornecedor || 0) * item.quantidade
        })
      }
    }
  })

  totalVendas.value = somaVendas
  totalCustos.value = somaCustos
  totalLucro.value = somaVendas - somaCustos
}

watch(() => vendas.value, () => {
  calcularResumoFinanceiro()
}, {deep: true})

const toggleProdutos = (vendaId) => {
  const index = expandedVendas.value.indexOf(vendaId)
  if (index === -1) {
    expandedVendas.value.push(vendaId)
  } else {
    expandedVendas.value.splice(index, 1)
  }
}

const getBadgeColor = (situacao) => {
  switch (situacao) {
    case 'CONCLUÍDA':
      return 'success'
    case 'CANCELADA':
      return 'error'
    default:
      return 'gray'
  }
}

const handleChangePage = (novaPagina) => {
  if (mudarPagina(novaPagina)) {
    loadData(filtros)
  }
  hideAlert()
}

const buscar = () => {
  resetarPagina()
  loadData(filtros)
  hideAlert()
}

const limparFiltros = () => {
  filtros.codigo = ''
  filtros.descricao = ''
  resetarPagina()
  loadData(filtros)
  hideAlert()
}

const cancelarVenda = async (venda) => {
  if (confirm('Tem certeza que deseja cancelar esta venda?')) {
    try {
      const response = await service.cancelar(venda)
      showSuccess(response.data.message || 'Venda cancelada com sucesso!')
      buscar()
    } catch (error) {
      showError(errorHandler.msgErro(error, 'Erro ao cancelar venda'))
    }
  }
}

onMounted(() => {
  buscar()
})
</script>

