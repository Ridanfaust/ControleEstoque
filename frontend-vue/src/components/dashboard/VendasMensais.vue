<template>
  <div
    class="overflow-hidden rounded-2xl border border-gray-200 bg-white px-5 pt-5 dark:border-gray-800 dark:bg-white/[0.03] sm:px-6 sm:pt-6"
  >
    <div class="flex items-center justify-between">
      <h3 class="text-lg font-semibold text-gray-800 dark:text-white/90">Vendas Mensais</h3>
    </div>

    <div class="max-w-full overflow-x-auto custom-scrollbar">
      <div id="chartOne" class="-ml-5 min-w-[650px] xl:min-w-full pl-2">
        <div v-if="isLoading" class="flex justify-center items-center h-[180px]">
          <div class="animate-spin rounded-full h-8 w-8 border-b-2 border-primary-500"></div>
        </div>
        <div v-else-if="hasError" class="flex justify-center items-center h-[180px]">
          <p class="text-error-600 dark:text-error-500">Falha ao carregar dados das vendas</p>
        </div>
        <VueApexCharts v-else type="bar" height="180" :options="chartOptions" :series="series"/>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted, computed} from 'vue'
import VendaService from '@/services/VendaService'
import VueApexCharts from 'vue3-apexcharts'
import {formatCurrency} from "@/utils/CurrencyUtil.js";
import {useRouter} from "vue-router";

const router = useRouter()

const isLoading = ref(true)
const hasError = ref(false)
const monthlyData = ref([0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0])

const series = computed(() => [
  {
    name: 'Vendas',
    data: monthlyData.value,
  },
])

const chartOptions = ref({
  colors: ['#465fff'],
  chart: {
    fontFamily: 'Outfit, sans-serif',
    type: 'bar',
    toolbar: {
      show: false,
    },
  },
  plotOptions: {
    bar: {
      horizontal: false,
      columnWidth: '39%',
      borderRadius: 5,
      borderRadiusApplication: 'end',
    },
  },
  dataLabels: {
    enabled: false,
  },
  stroke: {
    show: true,
    width: 4,
    colors: ['transparent'],
  },
  xaxis: {
    categories: [
      'Jan',
      'Fev',
      'Mar',
      'Abr',
      'Mai',
      'Jun',
      'Jul',
      'Ago',
      'Set',
      'Out',
      'Nov',
      'Dez',
    ],
    axisBorder: {
      show: false,
    },
    axisTicks: {
      show: false,
    },
  },
  legend: {
    show: true,
    position: 'top',
    horizontalAlign: 'left',
    fontFamily: 'Outfit',
    markers: {
      radius: 99,
    },
  },
  yaxis: {
    title: false,
  },
  grid: {
    yaxis: {
      lines: {
        show: true,
      },
    },
  },
  fill: {
    opacity: 1,
  },
  tooltip: {
    x: {
      show: false,
    },
    y: {
      formatter: function (val) {
        return formatCurrency(val)
      },
    },
  },
})

const fetchSalesData = async () => {
  isLoading.value = true
  hasError.value = false

  try {
    const currentYear = new Date().getFullYear()

    const response = await VendaService.listar('', '', 0, 1000)

    if (response && response.data) {
      const monthlySales = Array(12).fill(0)

      response.data.content.forEach(venda => {
        const saleDate = new Date(venda.dataCadastro)
        const saleYear = saleDate.getFullYear()

        if (saleYear === currentYear) {
          const month = saleDate.getMonth()
          monthlySales[month] += venda.valorTotal || 0
        }
      })

      monthlyData.value = monthlySales
    }
  } catch (error) {
    console.error('Error fetching sales data:', error)
    hasError.value = true
  } finally {
    isLoading.value = false
  }
}

onMounted(() => {
  fetchSalesData()
})
</script>
