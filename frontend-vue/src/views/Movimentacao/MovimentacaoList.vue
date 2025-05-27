<template>
  <AppLayout>
    <PageBreadcrumb :pageTitle="currentPageTitle"/>
    <div class="space-y-5 sm:space-y-6">
      <ComponentCard title="Filtros de Pesquisa">
        <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
          <div>
            <label class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400">
              Produto
            </label>
            <input
              type="text"
              v-model="filtros.produto"
              placeholder="Buscar por produto"
              class="dark:bg-dark-900 h-11 w-full rounded-lg border border-gray-300 bg-transparent px-4 py-2.5 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800"
            />
          </div>
          <div>
            <label class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400">
              Tipo da Movimentação
            </label>
            <input
              type="text"
              v-model="filtros.tipoMovimentacao"
              placeholder="Buscar por tipo de movimentação"
              class="dark:bg-dark-900 h-11 w-full rounded-lg border border-gray-300 bg-transparent px-4 py-2.5 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800"
            />
          </div>
          <div>
            <label class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400">
              Natureza da Movimentação
            </label>
            <div class="relative z-20 bg-transparent">
              <select
                v-model="filtros.natureza"
                class="dark:bg-dark-900 h-11 w-full appearance-none rounded-lg border border-gray-300 bg-transparent bg-none px-4 py-2.5 pr-11 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800"
                :class="{ 'text-gray-800 dark:text-white/90': filtros.natureza }"
              >
                <option value="" selected>Selecione</option>
                <option
                  v-for="[key, value] in naturezaMovimentacaoOptions"
                  :key="key"
                  :value="key"
                >
                  {{ value }}
                </option>
              </select>
              <span
                class="absolute text-gray-500 -translate-y-1/2 pointer-events-none right-3 top-1/2 dark:text-gray-400"
              >
                <font-awesome-icon icon="chevron-down"/>
              </span>
            </div>
          </div>

          <div class="grid grid-cols-2 md:grid-cols-2 gap-4">
            <div>
              <label class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400">
                Data Início
              </label>
              <div class="relative">
                <flat-pickr
                  v-model="filtros.dataInicio"
                  :config="flatpickrConfig"
                  class="dark:bg-dark-900 h-11 w-full appearance-none rounded-lg border border-gray-300 bg-transparent bg-none px-4 py-2.5 pl-4 pr-11 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800"
                  placeholder="Selecione a data de início"
                />
                <span
                  class="absolute text-gray-500 -translate-y-1/2 pointer-events-none right-3 top-1/2 dark:text-gray-400"
                >
                <font-awesome-icon icon="calendar"/>
              </span>
              </div>
            </div>

            <div>
              <label class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400">
                Data Fim
              </label>
              <div class="relative">
                <flat-pickr
                  v-model="filtros.dataFim"
                  :config="flatpickrConfig"
                  class="dark:bg-dark-900 h-11 w-full appearance-none rounded-lg border border-gray-300 bg-transparent bg-none px-4 py-2.5 pl-4 pr-11 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800"
                  placeholder="Selecione a data fim"
                />
                <span
                  class="absolute text-gray-500 -translate-y-1/2 pointer-events-none right-3 top-1/2 dark:text-gray-400"
                >
                  <font-awesome-icon icon="calendar"/>
                </span>
              </div>
            </div>
          </div>

          <div class="flex items-end">
            <Button size="sm" variant="primary" @click="buscar">Buscar</Button>
            <Button size="sm" variant="outline" class="ml-2" @click="limparFiltros">Limpar</Button>
          </div>
        </div>
      </ComponentCard>

      <ComponentCard title="Relatório de Movimentações">
        <div
          class="overflow-hidden rounded-xl border border-gray-200 bg-white dark:border-gray-800 dark:bg-white/[0.03]">
          <div class="max-w-full overflow-x-auto custom-scrollbar">
            <table class="min-w-full">
              <thead>
              <tr class="border-b border-gray-200 dark:border-gray-700">
                <th class="px-5 py-3 text-left w-3/8 sm:px-6">
                  <p class="font-medium text-gray-500 text-theme-xs dark:text-gray-400">Produto</p>
                </th>
                <th class="px-5 py-3 text-left w-2/8 sm:px-6">
                  <p class="font-medium text-gray-500 text-theme-xs dark:text-gray-400">
                    Movimentação</p>
                </th>
                <th class="px-5 py-3 text-left w-1/8 sm:px-6">
                  <p class="font-medium text-gray-500 text-theme-xs dark:text-gray-400">Natureza</p>
                </th>
                <th class="px-5 py-3 text-left w-1/8 sm:px-6">
                  <p class="font-medium text-center text-gray-500 text-theme-xs dark:text-gray-400">
                    Quantidade</p>
                </th>
                <th class="px-5 py-3 text-left w-2/8 sm:px-6">
                  <p class="font-medium text-gray-500 text-theme-xs dark:text-gray-400">Data</p>
                </th>
              </tr>
              </thead>
              <tbody class="divide-y divide-gray-200 dark:divide-gray-700">
              <tr v-if="movimentacoes.length === 0">
                <td colspan="4" class="px-5 py-4 text-center text-gray-500">
                  Nenhum registro encontrado
                </td>
              </tr>
              <tr
                v-for="movimentacao in movimentacoes"
                :key="movimentacao.id"
                class="border-t border-gray-100 dark:border-gray-800"
              >
                <td class="px-5 py-4 sm:px-6">
                  <span class="block font-medium text-gray-800 text-theme-sm dark:text-white/90">
                    {{ movimentacao.produto.descricao }}
                  </span>
                </td>
                <td class="px-5 py-4 sm:px-6">
                  <span class="block font-medium text-gray-800 text-theme-sm dark:text-white/90">
                    {{ movimentacao.tipo.descricao }}
                  </span>
                </td>
                <td class="px-5 py-4 sm:px-6">
                  <span class="block font-medium text-gray-800 text-theme-sm dark:text-white/90">
                    {{ movimentacao.tipo.naturezaMovimentacao }}
                  </span>
                </td>
                <td class="px-5 py-4 sm:px-6">
                  <span
                    class="text-center block font-medium text-gray-800 text-theme-sm dark:text-white/90">
                    <Badge
                      :color="movimentacao.tipo.naturezaMovimentacao === 'ENTRADA' ? 'success' : 'error'"
                      variant="light"
                    >
                    {{ movimentacao.quantidade }}
                  </Badge>
                  </span>
                </td>
                <td class="px-5 py-4 sm:px-6">
                  <span class="block font-medium text-gray-800 text-theme-sm dark:text-white/90">
                      {{ formatarData(movimentacao.dataCadastro) }}
                  </span>
                </td>
              </tr>
              </tbody>
            </table>
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
import {onMounted, reactive, ref} from 'vue'
import {useRouter} from 'vue-router'
import PageBreadcrumb from "@/components/common/PageBreadcrumb.vue"
import AppLayout from "@/components/layout/AppLayout.vue"
import ComponentCard from "@/components/common/ComponentCard.vue"
import Button from "@/components/ui/Button.vue"
import MovimentacaoService from "@/services/MovimentacaoService.js"
import MensagemErroHandler from "@/utils/MensagemErroHandler"
import {useAlertStore} from "@/stores/AlertStore"
import {usePagination} from '@/composables/usePagination'
import Pagination from '@/components/common/Pagination.vue'
import {NaturezaMovimentacaoEnum} from "@/models/enums/NaturezaMovimentacaoEnum.js"
import {formatarData} from "@/utils/DateUtil.ts"
import Badge from "@/components/ui/Badge.vue";
import flatPickr from 'vue-flatpickr-component'
import 'flatpickr/dist/flatpickr.css'
import {Portuguese} from 'flatpickr/dist/l10n/pt'
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";

const currentPageTitle = ref("Movimentações")
const service = MovimentacaoService
const errorHandler = MensagemErroHandler
const {showSuccess, showError, hideAlert} = useAlertStore()

const naturezaMovimentacaoOptions = Object.entries(NaturezaMovimentacaoEnum);
const filtros = reactive({
  produto: '',
  tipoMovimentacao: '',
  natureza: '',
  dataInicio: '',
  dataFim: ''
})

const flatpickrConfig = {
  dateFormat: 'd-m-Y',
  altInput: true,
  altFormat: 'd/m/Y',
  wrap: true,
  locale: Portuguese,
}

const {
  items: movimentacoes,
  pagina,
  tamanhoPagina,
  totalItens,
  totalPaginas,
  paginasExibidas,
  loadData,
  mudarPagina,
  resetarPagina
} = usePagination(async (page, size, filters) => {
  return await MovimentacaoService.listar(filters.produto, filters.tipoMovimentacao, filters.natureza, filters.dataInicio, filters.dataFim, page, size)
})

const handleChangePage = (novaPagina) => {
  if (mudarPagina(novaPagina)) {
    loadData(filtros.toString())
  }
  hideAlert()
}

const buscar = () => {
  buscarMovimentacoes()
  hideAlert()
}

const buscarMovimentacoes = () => {
  loadData(filtros)
}

const limparFiltros = () => {
  filtros.produto = ''
  filtros.natureza = ''
  resetarPagina()
  loadData(filtros)
  hideAlert()
}

const excluirMovimentacao = async (id) => {
  if (confirm('Tem certeza que deseja excluir esta movimentação?')) {
    try {
      const response = await service.excluir(id)
      showSuccess(response.data.message || 'Movimentação excluída com sucesso!')
      buscarMovimentacoes()
    } catch (error) {
      showError(errorHandler.msgErro(error, 'Erro ao excluir movimentação'))
    }
  }
}

onMounted(() => {
  buscarMovimentacoes()
})
</script>

<style scoped>

</style>
