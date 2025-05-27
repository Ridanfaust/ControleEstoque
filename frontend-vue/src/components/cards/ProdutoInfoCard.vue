<template>
  <div>
    <div class="p-5 mb-6 border border-gray-200 rounded-2xl dark:border-gray-800 lg:p-6">
      <div class="flex flex-col gap-6 lg:flex-row lg:items-start lg:justify-between">
        <div>
          <h4 class="text-lg font-semibold text-gray-800 dark:text-white/90 lg:mb-6">
            Dados do Produto
          </h4>

          <div class="grid grid-cols-1 gap-4 lg:grid-cols-3 lg:gap-7 2xl:gap-x-32">
            <div>
              <p class="mb-2 text-xs leading-normal text-gray-500 dark:text-gray-400">Código</p>
              <p class="text-sm font-medium text-gray-800 dark:text-white/90">{{
                  produto.codigo
                }}</p>
            </div>

            <div>
              <p class="mb-2 text-xs leading-normal text-gray-500 dark:text-gray-400">Descrição</p>
              <p class="text-sm font-medium text-gray-800 dark:text-white/90">{{
                  produto.descricao
                }}</p>
            </div>

            <div>
              <p class="mb-2 text-xs leading-normal text-gray-500 dark:text-gray-400">
                Categoria
              </p>
              <p class="text-sm font-medium text-gray-800 dark:text-white/90">
                {{ produto.tipo?.descricao }}
              </p>
            </div>

            <div>
              <p class="mb-2 text-xs leading-normal text-gray-500 dark:text-gray-400">Valor no
                Fornecedor</p>
              <p class="text-sm font-medium text-gray-800 dark:text-white/90">
                {{ formatCurrency(produto?.valorFornecedor) }}</p>
            </div>

            <div>
              <p class="mb-2 text-xs leading-normal text-gray-500 dark:text-gray-400">Valor de
                Venda</p>
              <p class="text-sm font-medium text-gray-800 dark:text-white/90">
                {{ formatCurrency(produto?.valorVenda) }}</p>
            </div>

            <div>
              <p class="mb-2 text-xs leading-normal text-gray-500 dark:text-gray-400">Quantidade em
                Estoque</p>
              <p class="text-sm font-medium text-gray-800 dark:text-white/90">
                {{ produto.quantidadeTotal }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { Produto } from "@/models/Produto.ts";
import { formatCurrency } from "@/utils/CurrencyUtil.ts";

interface Props {
  produto: Produto;
}

defineProps<Props>()
</script>
