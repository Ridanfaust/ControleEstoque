<template>
  <AppLayout>
    <PageBreadcrumb :pageTitle="currentPageTitle"/>
    <div class="space-y-6">
      <ComponentCard title="Dados do Produto">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400">
              Código
            </label>
            <input
              type="text"
              v-model="produtoFormData.codigo"
              placeholder="Código do Produto"
              class="dark:bg-dark-900 h-11 w-full rounded-lg border border-gray-300 bg-transparent px-4 py-2.5 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800"
            />
          </div>

          <div>
            <label class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400">
              Tipo de Produto
            </label>
            <div class="relative z-20 bg-transparent">
              <select
                v-model="produtoFormData.tipoProduto"
                class="dark:bg-dark-900 h-11 w-full appearance-none rounded-lg border border-gray-300 bg-transparent bg-none px-4 py-2.5 pr-11 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800"
                :class="{ 'text-gray-800 dark:text-white/90': produtoFormData.tipoProduto }"
              >
                <option value="" selected>Selecione</option>
                <option
                  v-for="tipo in tipoProdutoOptions"
                  :key="tipo.id"
                  :value="tipo.id"
                >
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
        </div>

        <div>
          <label class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400">
            Descrição
          </label>
          <input
            type="text"
            v-model="produtoFormData.descricao"
            placeholder="Descrição do Produto"
            class="dark:bg-dark-900 h-11 w-full rounded-lg border border-gray-300 bg-transparent px-4 py-2.5 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800"
          />
        </div>

        <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
          <div>
            <label class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400">
              Valor no Fornecedor
            </label>
            <input
              type="text"
              v-model="produtoFormData.valorFornecedor"
              placeholder="R$ XX,XX"
              @input="() => handleInput('valorFornecedor')"
              class="dark:bg-dark-900 h-11 w-full rounded-lg border border-gray-300 bg-transparent px-4 py-2.5 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800"
            />
          </div>

          <div>
            <label class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400">
              Valor de Venda
            </label>
            <input
              type="text"
              v-model="produtoFormData.valorVenda"
              placeholder="R$ XX,XX"
              @input="() => handleInput('valorVenda')"
              class="dark:bg-dark-900 h-11 w-full rounded-lg border border-gray-300 bg-transparent px-4 py-2.5 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800"
            />
          </div>

          <div v-if="!isEdicao">
            <label class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400">
              Estoque Inicial
            </label>
            <input
              type="number"
              v-model="produtoFormData.quantidadeTotal"
              placeholder="Descrição do Produto"
              class="dark:bg-dark-900 h-11 w-full rounded-lg border border-gray-300 bg-transparent px-4 py-2.5 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800"
            />
          </div>
          <div v-if="isEdicao">
            <label class="mb-4 block text-sm font-medium text-gray-700 dark:text-gray-400">
              Estoque Atual
            </label>
            <output
              class="dark:text-white text-5xl h-11 w-full bg-transparent px-4 py-2.5 text-sm text-gray-800 shadow-theme-xs">
              {{ produtoFormData.quantidadeTotal }}
              {{ produtoFormData.quantidadeTotal > 1 ? 'unidades' : 'unidade' }}
            </output>
          </div>
        </div>

        <div v-if="!isEdicao">
          <label class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400">
            Movimentação de Entrada
          </label>
          <div class="relative z-20 bg-transparent">
            <select
              v-model="produtoFormData.tipoMovimentacao"
              class="dark:bg-dark-900 h-11 w-full appearance-none rounded-lg border border-gray-300 bg-transparent bg-none px-4 py-2.5 pr-11 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800"
              :class="{ 'text-gray-800 dark:text-white/90': produtoFormData.tipoMovimentacao }"
            >
              <option value="" selected>Selecione</option>
              <option
                v-for="tipo in tipoMovimentacaoEntradaOptions"
                :key="tipo.id"
                :value="tipo.id"
              >
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

        <div class="flex justify-center items-center gap-5">
          <Button size="sm" variant="outline" @click="cancelar"> Cancelar</Button>
          <Button size="sm" variant="primary" @click="salvarProduto"> Salvar</Button>
        </div>
      </ComponentCard>
    </div>
  </AppLayout>
</template>

<script setup>
import {onMounted, reactive, ref} from 'vue'
import {useRouter} from 'vue-router'
import PageBreadcrumb from '@/components/common/PageBreadcrumb.vue'
import AppLayout from '@/components/layout/AppLayout.vue'
import ComponentCard from '@/components/common/ComponentCard.vue'
import Button from "@/components/ui/Button.vue";
import ProdutoService from "@/services/ProdutoService.js";
import MensagemErroHandler from "@/utils/MensagemErroHandler"
import {useAlertStore} from "@/stores/AlertStore"
import tipoProdutoService from "@/services/TipoProdutoService.js";
import tipoMovimentacaoService from "@/services/TipoMovimentacaoService.js";
import {formatCurrency, formatCurrencyInput, parseCurrency} from "@/utils/CurrencyUtil.js";

const currentPageTitle = ref('Cadastrar Produto')
const router = useRouter()
const service = ProdutoService
const errorHandler = MensagemErroHandler
const {showSuccess, showError} = useAlertStore()

let isEdicao = ref(false)
let tipoProdutoOptions = ref([])
let tipoMovimentacaoEntradaOptions = ref([])
const produtoFormData = reactive({
  codigo: '',
  descricao: '',
  tipoProduto: '',
  tipoMovimentacao: '',
  valorFornecedor: '',
  valorVenda: '',
  quantidadeTotal: 0,
})

const handleInput = (field) => {
  const rawValue = produtoFormData[field].replace(/\D/g, '');
  produtoFormData[field] = formatCurrencyInput(rawValue);
};

const salvarProduto = async () => {
  try {
    const valorFornecedorNumerico = parseCurrency(produtoFormData.valorFornecedor);
    const valorVendaNumerico = parseCurrency(produtoFormData.valorVenda);

    const novoProduto = {
      produto: {
        codigo: produtoFormData.codigo,
        descricao: produtoFormData.descricao,
        valorFornecedor: valorFornecedorNumerico,
        valorVenda: valorVendaNumerico,
        quantidadeTotal: produtoFormData.quantidadeTotal,
        tipo: {id: produtoFormData.tipoProduto},
      },
      tipoMovimentacao: {id: produtoFormData.tipoMovimentacao},
    };

    const response = await service.salvar(novoProduto)
    await router.push('/produto').then()
    showSuccess(response.data.message || 'Produto salvo com sucesso!')
  } catch (error) {
    showError(errorHandler.msgErro(error))
  }
}

const cancelar = () => {
  router.push('/produto')
}

onMounted(() => {
  tipoProdutoService.listar('').then(response => {
    tipoProdutoOptions.value = response.data.content.map(item => ({
      id: item.id,
      descricao: item.descricao
    }))
  }).catch(error => {
    showError(errorHandler.msgErro(error))
  })

  tipoMovimentacaoService.listar('', 'ENTRADA', false).then(response => {
    tipoMovimentacaoEntradaOptions.value = response.data.content.map(item => ({
      id: item.id,
      descricao: item.descricao
    }))
  }).catch(error => {
    showError(errorHandler.msgErro(error))
  })

  const query = router.currentRoute.value.query
  if (query.data) {
    const loadedData = JSON.parse(query.data)
    Object.assign(produtoFormData, loadedData)
    produtoFormData.tipoProduto = loadedData.tipo.id
    produtoFormData.id = loadedData.id
    produtoFormData.valorFornecedor = formatCurrency(loadedData.valorFornecedor.toString())
    produtoFormData.valorVenda = formatCurrency(loadedData.valorVenda.toString())
    currentPageTitle.value = 'Editar Produto'
    isEdicao.value = true
  }
})

</script>
