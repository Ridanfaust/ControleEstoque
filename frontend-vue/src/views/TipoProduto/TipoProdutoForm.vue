<template>
  <AppLayout>
    <PageBreadcrumb :pageTitle="currentPageTitle"/>
    <div class="space-y-6">
      <ComponentCard title="Dados do Tipo de Produto">
        <div>
          <label class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400">
            Descrição
          </label>
          <input
            type="text"
            v-model="tipoProdutoFormData.descricao"
            placeholder="Descrição do Tipo de Produto"
            class="dark:bg-dark-900 h-11 w-full rounded-lg border border-gray-300 bg-transparent px-4 py-2.5 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800"
          />
        </div>

        <div class="flex justify-center items-center gap-5">
          <Button size="sm" variant="outline" @click="cancelar"> Cancelar</Button>
          <Button size="sm" variant="primary" @click="salvarTipoProduto"> Salvar</Button>
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
import TipoProdutoService from "@/services/TipoProdutoService.js";
import MensagemErroHandler from "@/utils/MensagemErroHandler"
import {useAlertStore} from "@/stores/AlertStore"

const currentPageTitle = ref('Cadastrar Tipo de Produto')
const router = useRouter()
const service = TipoProdutoService
const errorHandler = MensagemErroHandler
const {showSuccess, showError} = useAlertStore()

const tipoProdutoFormData = reactive({
  descricao: '',
})

const salvarTipoProduto = async () => {
  try {
    const response = await service.salvar(tipoProdutoFormData)
    await router.push('/tipo-produto').then()
    showSuccess(response.data.message || 'Tipo de Produto salvo com sucesso!')
  } catch (error) {
    showError(errorHandler.msgErro(error))
  }
}

const cancelar = () => {
  router.push('/tipo-produto')
}

onMounted(() => {
  const query = router.currentRoute.value.query
  if (query.data) {
    const loadedData = JSON.parse(query.data)
    Object.assign(tipoProdutoFormData, loadedData)
    currentPageTitle.value = 'Editar Tipo de Produto'
  }
})

</script>

