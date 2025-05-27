<template>
  <AppLayout>
    <PageBreadcrumb :pageTitle="currentPageTitle"/>
    <div class="space-y-5 sm:space-y-6">
      <ComponentCard title="Filtros de Pesquisa">
        <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
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
          <div>
            <label class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400">
              Natureza da Movimentação
            </label>
            <div class="relative z-20 bg-transparent">
              <select
                v-model="filtros.naturezaMovimentacao"
                class="dark:bg-dark-900 h-11 w-full appearance-none rounded-lg border border-gray-300 bg-transparent bg-none px-4 py-2.5 pr-11 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800"
                :class="{ 'text-gray-800 dark:text-white/90': filtros.naturezaMovimentacao }"
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
                class="absolute z-30 text-gray-500 -translate-y-1/2 pointer-events-none right-4 top-1/2 dark:text-gray-400"
              >
                <font-awesome-icon icon="chevron-down"/>
              </span>
            </div>
          </div>

          <div class="flex items-end">
            <Button size="sm" variant="primary" @click="buscar">Buscar</Button>
            <Button size="sm" variant="outline" class="ml-2" @click="limparFiltros">Limpar</Button>
          </div>
        </div>
      </ComponentCard>

      <ComponentCard title="Lista de Tipos de Movimentação">
        <div class="text-right">
          <Button size="sm" variant="primary" class="ml-2" @click="novoCadastro">
            <font-awesome-icon icon="plus"/>
            Cadastrar Novo
          </Button>
        </div>
        <div
          class="overflow-hidden rounded-xl border border-gray-200 bg-white dark:border-gray-800 dark:bg-white/[0.03]">
          <div class="max-w-full overflow-x-auto custom-scrollbar">
            <table class="min-w-full">
              <thead>
              <tr class="border-b border-gray-200 dark:border-gray-700">
                <th class="px-5 py-3 text-left w-4/8 sm:px-6">
                  <p class="font-medium text-gray-500 text-theme-xs dark:text-gray-400">
                    Descrição</p>
                </th>
                <th class="px-5 py-3 text-left w-2/8 sm:px-6">
                  <p class="font-medium text-gray-500 text-theme-xs dark:text-gray-400">Natureza</p>
                </th>
                <th class="px-5 py-3 text-right w-2/8 sm:px-6">
                  <p class="font-medium text-gray-500 text-theme-xs dark:text-gray-400">Ações</p>
                </th>
              </tr>
              </thead>
              <tbody class="divide-y divide-gray-200 dark:divide-gray-700">
              <tr v-if="tiposMovimentacao.length === 0">
                <td colspan="2" class="px-5 py-4 text-center text-gray-500">
                  Nenhum registro encontrado
                </td>
              </tr>
              <tr
                v-for="tipo in tiposMovimentacao"
                :key="tipo.id"
                class="border-t border-gray-100 dark:border-gray-800"
              >
                <td class="px-5 py-4 sm:px-6">
                  <span class="block font-medium text-gray-800 text-theme-sm dark:text-white/90">
                    {{ tipo.descricao }}
                  </span>
                </td>
                <td class="px-5 py-4 sm:px-6">
                  <span class="block font-medium text-gray-800 text-theme-sm dark:text-white/90">
                    {{ tipo.naturezaMovimentacao }}
                  </span>
                </td>
                <td class="px-5 py-4 sm:px-6 text-right">
                  <Button size="sm" variant="success" class="mr-2"
                          @click="editarTipoMovimentacao(tipo.id)">
                    <font-awesome-icon icon="edit"/>
                  </Button>
                  <Button size="sm" variant="danger" @click="excluirTipoMovimentacao(tipo.id)">
                    <font-awesome-icon icon="trash"/>
                  </Button>
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
import {ref, reactive, onMounted} from 'vue'
import {useRouter} from 'vue-router'
import PageBreadcrumb from "@/components/common/PageBreadcrumb.vue"
import AppLayout from "@/components/layout/AppLayout.vue"
import ComponentCard from "@/components/common/ComponentCard.vue"
import Button from "@/components/ui/Button.vue"
import TipoMovimentacaoService from "@/services/TipoMovimentacaoService.js"
import MensagemErroHandler from "@/utils/MensagemErroHandler"
import {useAlertStore} from "@/stores/AlertStore"
import {usePagination} from '@/composables/usePagination'
import Pagination from '@/components/common/Pagination.vue'
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {NaturezaMovimentacaoEnum} from "@/models/enums/NaturezaMovimentacaoEnum.js"

const currentPageTitle = ref("Tipos de Movimentação")
const router = useRouter()
const service = TipoMovimentacaoService
const errorHandler = MensagemErroHandler
const {showSuccess, showError, hideAlert} = useAlertStore()

const naturezaMovimentacaoOptions = Object.entries(NaturezaMovimentacaoEnum);
const filtros = reactive({
  descricao: '',
  naturezaMovimentacao: ''
})

const {
  items: tiposMovimentacao,
  pagina,
  tamanhoPagina,
  totalItens,
  totalPaginas,
  paginasExibidas,
  loadData,
  mudarPagina,
  resetarPagina
} = usePagination(async (page, size, filters) => {
  return await TipoMovimentacaoService.listar(filters.descricao, filters.naturezaMovimentacao, null, page, size)
})

const handleChangePage = (novaPagina) => {
  if (mudarPagina(novaPagina)) {
    loadData(filtros)
  }
  hideAlert()
}

const buscar = () => {
  buscarTiposMovimentacao()
  hideAlert()
}

const buscarTiposMovimentacao = () => {
  loadData(filtros)
}

const limparFiltros = () => {
  filtros.descricao = ''
  filtros.naturezaMovimentacao = ''
  resetarPagina()
  loadData(filtros)
  hideAlert()
}

const novoCadastro = () => {
  router.push('/tipo-movimentacao/cadastrar')
}

const editarTipoMovimentacao = async (id) => {
  try {
    const response = await service.buscarPorId(id)
    const tipoMovimentacao = response.data
    await router.push({
      path: `/tipo-movimentacao/editar/${id}`,
      query: {data: JSON.stringify(tipoMovimentacao)}
    })
  } catch (error) {
    showError(errorHandler.msgErro(error, 'Erro ao carregar tipo de movimentação'))
  }
}

const excluirTipoMovimentacao = async (id) => {
  if (confirm('Tem certeza que deseja excluir este tipo de movimentação?')) {
    try {
      const response = await service.excluir(id)
      showSuccess(response.data.message || 'Tipo de movimentação excluído com sucesso!')
      buscarTiposMovimentacao()
    } catch (error) {
      showError(errorHandler.msgErro(error, 'Erro ao excluir tipo de movimentação'))
    }
  }
}

onMounted(() => {
  buscarTiposMovimentacao()
})
</script>

<style scoped>

</style>
