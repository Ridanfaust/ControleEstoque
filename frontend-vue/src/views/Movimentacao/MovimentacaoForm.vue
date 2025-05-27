<template>
  <AppLayout>
    <PageBreadcrumb :pageTitle="currentPageTitle"/>
    <div class="space-y-6">
      <ComponentCard title="Movimentação de Produtos">
        <ProdutoInfoCard :produto="movimentacaoFormData.produto"/>
        <div>
          <label class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400">
            Natureza da Movimentação
          </label>
          <div class="relative z-20 bg-transparent">
            <select
              v-model="movimentacaoFormData.naturezaMovimentacao"
              class="dark:bg-dark-900 h-11 w-full appearance-none rounded-lg border border-gray-300 bg-transparent bg-none px-4 py-2.5 pr-11 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800"
              :class="{ 'text-gray-800 dark:text-white/90': movimentacaoFormData.naturezaMovimentacao }"
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
          </div>
        </div>

        <div>
          <label class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400">
            Tipo de Movimentação
          </label>
          <div class="relative z-20 bg-transparent">
            <select
              v-model="movimentacaoFormData.tipoMovimentacao"
              class="dark:bg-dark-900 h-11 w-full appearance-none rounded-lg border border-gray-300 bg-transparent bg-none px-4 py-2.5 pr-11 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800"
              :class="{ 'text-gray-800 dark:text-white/90': movimentacaoFormData.tipoMovimentacao }"
            >
              <option value="0" selected>Selecione</option>
              <option
                v-for="tipo in tipoMovimentacaoOptions"
                :key="tipo.id"
                :value="tipo.id"
              >
                {{ tipo.descricao }}
              </option>
            </select>
          </div>
        </div>

        <div>
          <label class="mb-1.5 block text-sm font-medium text-gray-700 dark:text-gray-400">
            Quantidade
          </label>
          <input
            type="number"
            v-model="movimentacaoFormData.quantidade"
            placeholder="Quantidade"
            class="dark:bg-dark-900 h-11 w-full rounded-lg border border-gray-300 bg-transparent px-4 py-2.5 text-sm text-gray-800 shadow-theme-xs placeholder:text-gray-400 focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90 dark:placeholder:text-white/30 dark:focus:border-brand-800"
          />
        </div>

        <div class="flex justify-center items-center gap-5">
          <Button size="sm" variant="outline" @click="cancelar"> Cancelar</Button>
          <Button size="sm" variant="primary" @click="salvarMovimentacao"> Salvar</Button>
        </div>
      </ComponentCard>
    </div>
  </AppLayout>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref, watch } from "vue";
import { useRouter } from "vue-router";
import PageBreadcrumb from "@/components/common/PageBreadcrumb.vue";
import AppLayout from "@/components/layout/AppLayout.vue";
import ComponentCard from "@/components/common/ComponentCard.vue";
import Button from "@/components/ui/Button.vue";
import MovimentacaoService from "@/services/MovimentacaoService.ts";
import MensagemErroHandler from "@/utils/MensagemErroHandler";
import { useAlertStore } from "@/stores/AlertStore";
import { NaturezaMovimentacaoEnum } from "@/models/enums/NaturezaMovimentacaoEnum.js";
import ProdutoInfoCard from "@/components/cards/ProdutoInfoCard.vue";
import tipoMovimentacaoService from "@/services/TipoMovimentacaoService.ts";
import type { Movimentacao } from "@/models/Movimentacao.ts";

const currentPageTitle = ref("Movimentação de Produtos");
const router = useRouter();
const service = MovimentacaoService;
const errorHandler = MensagemErroHandler;
const { showSuccess, showError } = useAlertStore();

let produtoOptions = ref([]);
const naturezaMovimentacaoOptions = Object.entries(NaturezaMovimentacaoEnum);
let tipoMovimentacaoOptions = ref([]);
const movimentacaoFormData = reactive({
  produto: {
    id: 0,
    codigo: "",
    descricao: "",
    tipo: {
      id: "",
      descricao: ""
    },
    valorFornecedor: 0,
    valorVenda: 0,
    estoque: 0
  },
  naturezaMovimentacao: "",
  tipoMovimentacao: 0,
  quantidade: 0,
});

const carregarTiposMovimentacao = async () => {
  if (!movimentacaoFormData.naturezaMovimentacao) {
    tipoMovimentacaoOptions.value = [];
    return;
  }

  try {
    const response = await tipoMovimentacaoService.listar("", movimentacaoFormData.naturezaMovimentacao, null, 0, 100);
    tipoMovimentacaoOptions.value = response.data.content;
  } catch (error) {
    showError(errorHandler.msgErro(error));
  }
};

watch(() => movimentacaoFormData.naturezaMovimentacao, carregarTiposMovimentacao);

const salvarMovimentacao = async () => {
  try {
    const novaMovimentacao: Movimentacao = {
      produto: { id: movimentacaoFormData.produto.id },
      tipo: { id: movimentacaoFormData.tipoMovimentacao },
      quantidade: movimentacaoFormData.quantidade,
    };

    const response = await service.salvar(novaMovimentacao);
    await router.push("/produto").then();
    showSuccess(response.data.message || "Movimentação salva com sucesso!");
  } catch (error) {
    showError(errorHandler.msgErro(error));
  }
};

const cancelar = () => {
  router.push("/movimentacao");
};

onMounted(() => {
  const query = router.currentRoute.value.query
  if (query.data) {
    const loadedData = JSON.parse(query.data)
    Object.assign(movimentacaoFormData.produto, loadedData)
  }
});
</script>
