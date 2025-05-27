<template>
  <AppLayout>
    <PageBreadcrumb :pageTitle="currentPageTitle"/>
    <div class="space-y-5 sm:space-y-6">
      <!-- Search and Filter Section -->
      <ComponentCard title="Buscar Produtos">
        <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
          <div>
            <label class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400">
              Buscar Produto
            </label>
            <input
              type="text"
              v-model="filtro"
              placeholder="Código, nome ou categoria"
              class="dark:bg-dark-900 h-11 w-full rounded-lg border border-gray-300 bg-transparent px-4 py-2.5 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800"
              @input="buscarProdutos"
            />
          </div>
        </div>
      </ComponentCard>

      <div class="grid grid-cols-1 lg:grid-cols-12 gap-5">
        <!-- Products List -->
        <div class="lg:col-span-5">
          <ComponentCard title="Produtos Disponíveis">
            <div
              class="overflow-hidden rounded-xl border border-gray-200 bg-white dark:border-gray-800 dark:bg-white/[0.03]">
              <div class="max-w-full overflow-x-auto custom-scrollbar">
                <table class="min-w-full">
                  <thead>
                  <tr class="border-b border-gray-200 dark:border-gray-700">
                    <th class="px-5 py-3 text-left sm:px-6">
                      <p class="font-medium text-gray-500 text-theme-xs dark:text-gray-400">
                        Código</p>
                    </th>
                    <th class="px-5 py-3 text-left sm:px-6">
                      <p class="font-medium text-gray-500 text-theme-xs dark:text-gray-400">
                        Produto</p>
                    </th>
                    <th class="px-5 py-3 text-left sm:px-6">
                      <p class="font-medium text-gray-500 text-theme-xs dark:text-gray-400">
                        Valor</p>
                    </th>
                    <th class="px-5 py-3 text-right sm:px-6">
                      <p class="font-medium text-gray-500 text-theme-xs dark:text-gray-400">
                        Ações</p>
                    </th>
                  </tr>
                  </thead>
                  <tbody class="divide-y divide-gray-200 dark:divide-gray-700">
                  <tr v-if="produtosFiltrados.length === 0">
                    <td colspan="4" class="px-5 py-4 text-center text-gray-500">
                      Nenhum produto encontrado
                    </td>
                  </tr>
                  <tr
                    v-for="produto in produtosFiltrados"
                    :key="produto.id"
                    class="border-t border-gray-100 dark:border-gray-800"
                  >
                    <td class="px-5 py-4 sm:px-6">
                      <span
                        class="block font-medium text-gray-800 text-theme-sm dark:text-white/90">
                        {{ produto.codigo }}
                      </span>
                    </td>
                    <td class="px-5 py-4 sm:px-6">
                      <span
                        class="block font-medium text-gray-800 text-theme-sm dark:text-white/90">
                        {{ produto.descricao }}
                      </span>
                    </td>
                    <td class="px-5 py-4 sm:px-6">
                      <span
                        class="block font-medium text-gray-800 text-theme-sm dark:text-white/90">
                        {{ formatCurrency(produto.valorVenda) }}
                      </span>
                    </td>
                    <td class="px-5 py-4 sm:px-6 text-right">
                      <Button size="sm" variant="primary" @click="adicionarProduto(produto)">
                        <font-awesome-icon icon="plus"/>
                      </Button>
                    </td>
                  </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </ComponentCard>
        </div>

        <!-- Shopping Cart -->
        <div class="lg:col-span-7">
          <ComponentCard title="Itens da Venda">
            <div
              class="overflow-hidden rounded-xl border border-gray-200 bg-white dark:border-gray-800 dark:bg-white/[0.03]">
              <div class="max-w-full overflow-x-auto custom-scrollbar">
                <table class="min-w-full">
                  <thead>
                  <tr class="border-b border-gray-200 dark:border-gray-700">
                    <th class="px-5 py-3 text-left sm:px-6">
                      <p class="font-medium text-gray-500 text-theme-xs dark:text-gray-400">
                        Produto</p>
                    </th>
                    <th class="px-5 py-3 text-center w-24 sm:px-6">
                      <p class="font-medium text-gray-500 text-theme-xs dark:text-gray-400">
                        Qtde</p>
                    </th>
                    <th class="px-5 py-3 text-left sm:px-6">
                      <p class="font-medium text-gray-500 text-theme-xs dark:text-gray-400">
                        Valor Unit.</p>
                    </th>
                    <th class="px-5 py-3 text-left sm:px-6">
                      <p class="font-medium text-gray-500 text-theme-xs dark:text-gray-400">
                        Subtotal</p>
                    </th>
                    <th class="px-5 py-3 text-right sm:px-6">
                      <p class="font-medium text-gray-500 text-theme-xs dark:text-gray-400">
                        Ações</p>
                    </th>
                  </tr>
                  </thead>
                  <tbody class="divide-y divide-gray-200 dark:divide-gray-700">
                  <tr v-if="itensVenda.length === 0">
                    <td colspan="5" class="px-5 py-4 text-center text-gray-500">
                      Nenhum item adicionado à venda
                    </td>
                  </tr>
                  <tr
                    v-for="(item, index) in itensVenda"
                    :key="index"
                    class="border-t border-gray-100 dark:border-gray-800"
                  >
                    <td class="px-5 py-4 sm:px-6">
                      <span
                        class="block font-medium text-gray-800 text-theme-sm dark:text-white/90">
                        {{ item.produto.descricao }}
                      </span>
                    </td>
                    <td class="px-5 py-4 sm:px-6">
                      <QuantityInput v-model="item.quantidade" @change="calcularTotal()"/>

                    </td>
                    <td class="px-5 py-4 sm:px-6">
                      <input
                        type="text"
                        v-model="item.valorEditado"
                        class="block w-36 mx-auto text-center dark:bg-dark-900 rounded-lg border border-gray-300 bg-transparent px-4 py-2.5 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800"
                        @input="formatarValor(item)"
                        @blur="calcularTotal()"
                      />
                    </td>
                    <td class="px-5 py-4 sm:px-6">
                      <span
                        class="block font-medium text-gray-800 text-theme-sm dark:text-white/90">
                        {{ formatCurrency(item.subtotal) }}
                      </span>
                    </td>
                    <td class="px-5 py-4 sm:px-6 text-right">
                      <Button size="sm" variant="danger" @click="removerItem(index)">
                        <font-awesome-icon icon="trash"/>
                      </Button>
                    </td>
                  </tr>
                  </tbody>
                </table>
              </div>
            </div>

            <div>
              <label class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400">
                Tipo de Venda
              </label>
              <div class="relative z-20 bg-transparent">
                <select
                  v-model="selectedTipoMovimentacao"
                  class="dark:bg-dark-900 h-11 w-full appearance-none rounded-lg border border-gray-300 bg-transparent bg-none px-4 py-2.5 pr-11 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800"
                >
                  <option v-for="tipo in tiposMovimentacaoOptions"
                          :key="tipo.id"
                          :value="tipo.id">
                    {{ tipo.descricao }}
                  </option>
                </select>
                <span
                  class="absolute z-30 text-gray-500 -translate-y-1/2 pointer-events-none right-4 top-1/2 dark:text-gray-400"
                >
                <font-awesome-icon icon="chevron-down"/>
              </span>
              </div>
            </div>

            <div class="mt-6 flex justify-between items-center">
              <div class="text-xl font-bold dark:text-white/90">
                Total da Venda: {{ formatCurrency(totalVenda) }}
              </div>
              <Button class="lg" variant="success" @click="finalizarVenda"
                      :disabled="itensVenda.length === 0">
                <font-awesome-icon icon="check" class="mr-2"/>
                Finalizar Venda
              </Button>
            </div>
          </ComponentCard>
        </div>
      </div>
    </div>
  </AppLayout>
</template>

<script setup>
import {ref, computed, onMounted} from 'vue'
import {useRouter} from 'vue-router'
import PageBreadcrumb from "@/components/common/PageBreadcrumb.vue"
import AppLayout from "@/components/layout/AppLayout.vue"
import ComponentCard from "@/components/common/ComponentCard.vue"
import Button from "@/components/ui/Button.vue"
import {useAlertStore} from "@/stores/AlertStore"
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome"
import {formatCurrency, formatCurrencyInput, parseCurrency} from "@/utils/CurrencyUtil.ts"
import ProdutoService from "@/services/ProdutoService.js"
import MensagemErroHandler from "@/utils/MensagemErroHandler"
import QuantityInput from "@/components/forms/QuantityInput.vue";
import TipoMovimentacaoService from "@/services/TipoMovimentacaoService.js";
import VendaService from "@/services/VendaService.js";

const currentPageTitle = ref("Vendas")
const router = useRouter()
const errorHandler = MensagemErroHandler
const {showSuccess, showError} = useAlertStore()

const produtos = ref([])
const filtro = ref('')
const itensVenda = ref([])
const totalVenda = ref(0)

const produtosFiltrados = computed(() => {
  if (!filtro.value) return produtos.value;

  const termoBusca = filtro.value.toLowerCase();
  return produtos.value.filter(produto =>
    produto.codigo.toLowerCase().includes(termoBusca) ||
    produto.descricao.toLowerCase().includes(termoBusca) ||
    (produto.tipo?.descricao?.toLowerCase().includes(termoBusca) ?? false)
  );
})

const buscarProdutos = async () => {
  try {
    const response = await ProdutoService.listar('', '', '', 0, 10)
    produtos.value = response.data.content
  } catch (error) {
    showError(errorHandler.msgErro(error, 'Erro ao buscar produtos'))
  }
}

const adicionarProduto = (produto) => {
  const itemExistente = itensVenda.value.find(item => item.produto.id === produto.id)

  if (itemExistente) {
    itemExistente.quantidade++
    calcularTotal()
    showSuccess('Quantidade atualizada!')
    return
  }

  itensVenda.value.push({
    produto: produto,
    quantidade: 1,
    valorOriginal: produto.valorVenda,
    valorEditado: formatCurrency(produto.valorVenda.toString()),
    subtotal: produto.valorVenda
  })

  calcularTotal()
  showSuccess('Produto adicionado à venda!')
}

const removerItem = (index) => {
  itensVenda.value.splice(index, 1)
  calcularTotal()
  showSuccess('Item removido da venda')
}

const formatarValor = (item) => {
  item.valorEditado = formatCurrencyInput(item.valorEditado.replace(/\D/g, ''))
  const valorNumerico = parseCurrency(item.valorEditado)
  item.subtotal = valorNumerico * item.quantidade
  calcularTotal()
}

const calcularTotal = () => {
  let total = 0

  for (const item of itensVenda.value) {
    const valorNumerico = typeof item.valorEditado === 'string'
      ? parseCurrency(item.valorEditado)
      : item.valorOriginal

    item.subtotal = valorNumerico * item.quantidade
    total += item.subtotal
  }

  totalVenda.value = total
}

const tiposMovimentacaoOptions = ref([])
const selectedTipoMovimentacao = ref(null)

const carregarTiposMovimentacao = async () => {
  try {
    const response = await TipoMovimentacaoService.listar('', 'SAIDA', true, 0, 100)
    tiposMovimentacaoOptions.value = response.data.content
    if (tiposMovimentacaoOptions.value.length > 0) {
      selectedTipoMovimentacao.value = tiposMovimentacaoOptions.value[0].id
    }
  } catch (error) {
    showError(errorHandler.msgErro(error, 'Erro ao carregar tipos de movimentação'))
  }
}

const finalizarVenda = async () => {
  if (itensVenda.value.length === 0) {
    showError('Adicione produtos antes de finalizar a venda')
    return
  }

  if (!selectedTipoMovimentacao.value) {
    showError('Selecione o tipo de movimentação')
    return
  }

  const vendaRequest = {
    venda: {
      valorTotal: totalVenda.value,
      produtos: itensVenda.value.map(item => ({
        produto: {
          id: item.produto.id
        },
        quantidade: item.quantidade,
        valorVenda: typeof item.valorEditado === 'string'
          ? parseCurrency(item.valorEditado)
          : item.valorOriginal,
      })),
    },
    tipoMovimentacao: {
      id: selectedTipoMovimentacao.value
    }
  }

  try {
    const response = await VendaService.salvar(vendaRequest)
    showSuccess(response.data.message || 'Venda finalizada com sucesso!')
    itensVenda.value = []
    totalVenda.value = 0
    router.push('/produto')
  } catch (error) {
    showError(errorHandler.msgErro(error, 'Erro ao finalizar venda'))
  }
}

onMounted(() => {
  buscarProdutos()
  carregarTiposMovimentacao()
})
</script>
