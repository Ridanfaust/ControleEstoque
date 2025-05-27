<template>
  <AppLayout>
    <PageBreadcrumb :pageTitle="currentPageTitle"/>
    <div class="space-y-6">
      <ComponentCard title="Dados do Tipo de Movimentação">
        <div>
          <label class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400">
            Descrição
          </label>
          <input
            type="text"
            v-model="tipoMovimentacaoFormData.descricao"
            placeholder="Descrição do Tipo de Movimentação"
            class="dark:bg-dark-900 h-11 w-full rounded-lg border border-gray-300 bg-transparent px-4 py-2.5 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800"
          />
        </div>

        <div class="grid grid-cols-1 gap-4 lg:grid-cols-2 lg:gap-7 2xl:gap-x-32">
          <div class="mt-5 ml-5 lg:mt-9 lg:ml-5">
            <label
              for="checkboxLabelOne"
              class="flex items-center text-sm font-medium text-gray-700 cursor-pointer select-none dark:text-gray-400"
            >
              <div class="relative">
                <input type="checkbox" id="checkboxLabelOne"
                       v-model="tipoMovimentacaoFormData.venda" class="sr-only"/>
                <div
                  :class="
                tipoMovimentacaoFormData.venda
                  ? 'border-brand-500 bg-brand-500'
                  : 'bg-transparent border-gray-300 dark:border-gray-700'
              "
                  class="mr-3 flex h-5 w-5 items-center justify-center rounded-md border-[1.25px] hover:border-brand-500 dark:hover:border-brand-500"
                >
              <span :class="tipoMovimentacaoFormData.venda ? '' : 'opacity-0'">
                <svg
                  width="14"
                  height="14"
                  viewBox="0 0 14 14"
                  fill="none"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <path
                    d="M11.6666 3.5L5.24992 9.91667L2.33325 7"
                    stroke="white"
                    stroke-width="1.94437"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                  />
                </svg>
              </span>
                </div>
              </div>
              Movimentação de Venda
            </label>
          </div>

          <div v-if="!tipoMovimentacaoFormData.venda">
            <label class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400">
              Natureza da Movimentação
            </label>
            <div class="relative z-20 bg-transparent">
              <select
                v-model="tipoMovimentacaoFormData.naturezaMovimentacao"
                class="dark:bg-dark-900 h-11 w-full appearance-none rounded-lg border border-gray-300 bg-transparent bg-none px-4 py-2.5 pr-11 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800"
                :class="{ 'text-gray-800 dark:text-white/90': tipoMovimentacaoFormData.naturezaMovimentacao }"
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
                <svg
                  class="stroke-current"
                  width="20"
                  height="20"
                  viewBox="0 0 20 20"
                  fill="none"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <path
                    d="M4.79175 7.396L10.0001 12.6043L15.2084 7.396"
                    stroke=""
                    stroke-width="1.5"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                  />
                </svg>
              </span>
            </div>
          </div>
        </div>

        <div class="flex justify-center items-center gap-5">
          <Button size="sm" variant="outline" @click="cancelar"> Cancelar</Button>
          <Button size="sm" variant="primary" @click="salvarTipoMovimentacao"> Salvar</Button>
        </div>
      </ComponentCard>
    </div>
  </AppLayout>
</template>

<script setup>
import {reactive, ref, onMounted} from 'vue'
import {useRouter} from 'vue-router'
import PageBreadcrumb from '@/components/common/PageBreadcrumb.vue'
import AppLayout from '@/components/layout/AppLayout.vue'
import ComponentCard from '@/components/common/ComponentCard.vue'
import Button from "@/components/ui/Button.vue";
import TipoMovimentacaoService from "@/services/TipoMovimentacaoService.js";
import MensagemErroHandler from "@/utils/MensagemErroHandler"
import {useAlertStore} from "@/stores/AlertStore"
import {NaturezaMovimentacaoEnum} from "@/models/enums/NaturezaMovimentacaoEnum.js";

const currentPageTitle = ref('Cadastrar Tipo de Movimentação')
const router = useRouter()
const service = TipoMovimentacaoService
const errorHandler = MensagemErroHandler
const {showSuccess, showError} = useAlertStore()

const naturezaMovimentacaoOptions = Object.entries(NaturezaMovimentacaoEnum);
const tipoMovimentacaoFormData = reactive({
  descricao: '',
  venda: false,
  naturezaMovimentacao: ''
})

const salvarTipoMovimentacao = async () => {
  try {
    if (tipoMovimentacaoFormData.venda) {
      tipoMovimentacaoFormData.naturezaMovimentacao = 'SAIDA'
    }
    const response = await service.salvar(tipoMovimentacaoFormData)
    await router.push('/tipo-movimentacao').then()
    showSuccess(response.data.message || 'Tipo de Movimentação salvo com sucesso!')
  } catch (error) {
    showError(errorHandler.msgErro(error))
  }
}


const cancelar = () => {
  router.push('/tipo-movimentacao')
}

onMounted(() => {
  const query = router.currentRoute.value.query
  if (query.data) {
    const loadedData = JSON.parse(query.data)
    Object.assign(tipoMovimentacaoFormData, loadedData)
    currentPageTitle.value = 'Editar Tipo de Movimentação'
  }
})

</script>
