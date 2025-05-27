<template>
  <app-layout>
    <div class="grid grid-cols-12 gap-4 md:gap-6">
      <div class="col-span-12 space-y-6 xl:col-span-7">
        <resumo-financeiro/>
        <vendas-mensais/>
      </div>
      <div class="col-span-12 xl:col-span-5">
        <vendas-recentes/>
      </div>
    </div>
  </app-layout>
</template>

<script>
import AppLayout from '../components/layout/AppLayout.vue'
import ResumoFinanceiro from '@/components/dashboard/ResumoFinanceiro.vue'
import VendasMensais from '@/components/dashboard/VendasMensais.vue'
import VendasRecentes from '@/components/dashboard/VendasRecentes.vue'

export default {
  components: {
    AppLayout: AppLayout,
    ResumoFinanceiro: ResumoFinanceiro,
    VendasMensais: VendasMensais,
    VendasRecentes: VendasRecentes,
  },
  name: 'Ecommerce',
}
</script>
