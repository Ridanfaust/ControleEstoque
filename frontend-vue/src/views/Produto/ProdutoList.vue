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
              placeholder="Buscar por descricao"
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
              placeholder="Buscar por descricao"
              class="dark:bg-dark-900 h-11 w-full rounded-lg border border-gray-300 bg-transparent px-4 py-2.5 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800"
            />
          </div>
          <div>
            <label class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400">
              Tipo
            </label>
            <input
              type="text"
              v-model="filtros.tipo"
              placeholder="Buscar por descricao"
              class="dark:bg-dark-900 h-11 w-full rounded-lg border border-gray-300 bg-transparent px-4 py-2.5 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800"
            />
          </div>
          <div class="flex items-end">
            <Button size="sm" variant="primary" @click="buscar">Buscar</Button>
            <Button size="sm" variant="outline" class="ml-2" @click="limparFiltros">Limpar</Button>
          </div>
        </div>
      </ComponentCard>

      <ComponentCard title="Lista de Produtos">
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
                <th class="px-5 py-3 text-left w-1/12 sm:px-6">
                  <p class="font-medium text-gray-500 text-theme-xs dark:text-gray-400">
                    Código</p>
                </th>
                <th class="px-5 py-3 text-left w-2/12 sm:px-6">
                  <p class="font-medium text-gray-500 text-theme-xs dark:text-gray-400">
                    Descrição</p>
                </th>
                <th class="px-5 py-3 text-left w-2/12 sm:px-6">
                  <p class="font-medium text-gray-500 text-theme-xs dark:text-gray-400">
                    Tipo</p>
                </th>
                <th class="px-5 py-3 text-left w-2/12 sm:px-6">
                  <p class="font-medium text-gray-500 text-theme-xs dark:text-gray-400">Valor no
                    Fornecedor</p>
                </th>
                <th class="px-5 py-3 text-left w-2/12 sm:px-6">
                  <p class="font-medium text-gray-500 text-theme-xs dark:text-gray-400">Valor de
                    Venda</p>
                </th>
                <th class="px-5 py-3 text-left w-1/12 sm:px-6">
                  <p class="font-medium text-center text-gray-500 text-theme-xs dark:text-gray-400">
                    Estoque</p>
                </th>
                <th class="px-5 py-3 text-right w-2/12 sm:px-6">
                  <p class="font-medium text-gray-500 text-theme-xs dark:text-gray-400">Ações</p>
                </th>
              </tr>
              </thead>
              <tbody class="divide-y divide-gray-200 dark:divide-gray-700">
              <tr v-if="produtos.length === 0">
                <td colspan="2" class="px-5 py-4 text-center text-gray-500">
                  Nenhum registro encontrado
                </td>
              </tr>
              <tr
                v-for="produto in produtos"
                :key="produto.id"
                class="border-t border-gray-100 dark:border-gray-800"
              >
                <td class="px-5 py-4 sm:px-6">
                  <span class="block font-medium text-gray-800 text-theme-sm dark:text-white/90">
                    {{ produto.codigo }}
                  </span>
                </td>
                <td class="px-5 py-4 sm:px-6">
                  <span class="block font-medium text-gray-800 text-theme-sm dark:text-white/90">
                    {{ produto.descricao }}
                  </span>
                </td>
                <td class="px-5 py-4 sm:px-6">
                  <span class="block font-medium text-gray-800 text-theme-sm dark:text-white/90">
                    {{ produto.tipo.descricao }}
                  </span>
                </td>
                <td class="px-5 py-4 sm:px-6">
                  <span class="block font-medium text-gray-800 text-theme-sm dark:text-white/90">
                    {{ formatCurrency(produto.valorFornecedor) }}
                  </span>
                </td>
                <td class="px-5 py-4 sm:px-6">
                  <span class="block font-medium text-gray-800 text-theme-sm dark:text-white/90">
                    {{ formatCurrency(produto.valorVenda) }}
                  </span>
                </td>
                <td class="px-5 py-4 sm:px-6">
                  <span
                    class="block text-center font-medium text-gray-800 text-theme-sm dark:text-white/90">
                    {{ produto.quantidadeTotal }}
                  </span>
                </td>
                <td class="px-5 py-4 sm:px-6 text-right">
                  <Button size="sm" variant="primary" class="mr-2"
                          @click="movimentarEstoque(produto.id)">
                    <font-awesome-icon icon="arrow-right-arrow-left"/>
                  </Button>
                  <Button size="sm" variant="success" class="mr-2"
                          @click="editarProduto(produto.id)">
                    <font-awesome-icon icon="edit"/>
                  </Button>
                  <Button size="sm" variant="danger" @click="excluirProduto(produto.id)">
                    <font-awesome-icon icon="trash"/>
                  </Button>
                </td>
              </tr>
              </tbody>
            </table>
          </div>

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
import ProdutoService from "@/services/ProdutoService.js"
import MensagemErroHandler from "@/utils/MensagemErroHandler"
import {useAlertStore} from "@/stores/AlertStore"
import {usePagination} from '@/composables/usePagination'
import Pagination from '@/components/common/Pagination.vue'
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {formatCurrency} from "@/utils/CurrencyUtil.ts";

const currentPageTitle = ref("Produtos")
const router = useRouter()
const service = ProdutoService
const errorHandler = MensagemErroHandler
const {showSuccess, showError, hideAlert} = useAlertStore()

const filtros = reactive({
  codigo: '',
  descricao: '',
  tipo: ''
})

const {
  items: produtos,
  pagina,
  tamanhoPagina,
  totalItens,
  totalPaginas,
  paginasExibidas,
  loadData,
  mudarPagina,
  resetarPagina
} = usePagination(async (page, size, filters) => {
  return await ProdutoService.listar(filters.codigo, filters.descricao, filters.tipo, page, size)
})

const handleChangePage = (novaPagina) => {
  if (mudarPagina(novaPagina)) {
    loadData(filtros)
  }
  hideAlert()
}

const buscar = () => {
  buscarProdutos()
  hideAlert()
}

const buscarProdutos = () => {
  loadData(filtros)
}

const limparFiltros = () => {
  filtros.codigo = ''
  filtros.descricao = ''
  filtros.tipo = ''
  resetarPagina()
  loadData(filtros)
  hideAlert()
}

const novoCadastro = () => {
  router.push('/produto/cadastrar')
}

const editarProduto = async (id) => {
  try {
    const response = await service.buscarPorId(id)
    const produto = response.data
    console.log(produto)
    await router.push({
      path: `/produto/editar/${id}`,
      query: {data: JSON.stringify(produto)}
    })
  } catch (error) {
    showError(errorHandler.msgErro(error, 'Erro ao carregar produto'))
  }
}

const movimentarEstoque = async (id) => {
  try {
    const response = await service.buscarPorId(id)
    const produto = response.data
    await router.push({
      path: `/movimentacao/cadastrar/${id}`,
      query: {data: JSON.stringify(produto)}
    })
  } catch (error) {
    showError(errorHandler.msgErro(error, 'Erro ao carregar produto'))
  }
}

const excluirProduto = async (id) => {
  if (confirm('Tem certeza que deseja excluir este produto?')) {
    try {
      const response = await service.excluir(id)
      showSuccess(response.data.message || 'Produto excluído com sucesso!')
      buscarProdutos()
    } catch (error) {
      showError(errorHandler.msgErro(error, 'Erro ao excluir produto'))
    }
  }
}

onMounted(() => {
  buscarProdutos()
})
</script>

<style scoped>

</style>
