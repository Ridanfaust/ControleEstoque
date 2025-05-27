import { createRouter, createWebHistory } from "vue-router"

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  scrollBehavior(to, from, savedPosition) {
    return savedPosition || { left: 0, top: 0 }
  },
  routes: [
    {
      path: "/tipo-produto",
      name: "Tipo Produto",
      component: () => import("@/views/TipoProduto/TipoProdutoList.vue"),
      meta: {
        title: "Tipo Produto",
      },
    },
    {
      path: "/tipo-produto/cadastrar",
      name: "Cadastrar Tipo Produto",
      component: () => import("@/views/TipoProduto/TipoProdutoForm.vue"),
      meta: {
        title: "Cadastrar Tipo Produto",
      },
    },
    {
      path: "/tipo-produto/editar/:id",
      name: "Editar Tipo Produto",
      component: () => import("@/views/TipoProduto/TipoProdutoForm.vue"),
      meta: {
        title: "Editar Tipo Produto",
      },
    },
    {
      path: "/tipo-movimentacao",
      name: "Tipo Movimentacao",
      component: () => import("@/views/TipoMovimentacao/TipoMovimentacaoList.vue"),
      meta: {
        title: "Tipo Movimentacao",
      },
    },
    {
      path: "/tipo-movimentacao/cadastrar",
      name: "Cadastrar Tipo Movimentacao",
      component: () => import("@/views/TipoMovimentacao/TipoMovimentacaoForm.vue"),
      meta: {
        title: "Cadastrar Tipo Movimentacao",
      },
    },
    {
      path: "/tipo-movimentacao/editar/:id",
      name: "Editar Tipo Movimentacao",
      component: () => import("@/views/TipoMovimentacao/TipoMovimentacaoForm.vue"),
      meta: {
        title: "Editar Tipo Movimentacao",
      },
    },
    {
      path: "/produto",
      name: "Produto",
      component: () => import("@/views/Produto/ProdutoList.vue"),
      meta: {
        title: "Produto",
      },
    },
    {
      path: "/produto/cadastrar",
      name: "Cadastrar Produto",
      component: () => import("@/views/Produto/ProdutoForm.vue"),
      meta: {
        title: "Cadastrar Produto",
      },
    },
    {
      path: "/produto/editar/:id",
      name: "Editar Produto",
      component: () => import("@/views/Produto/ProdutoForm.vue"),
      meta: {
        title: "Editar Produto",
      },
    },
    {
      path: "/movimentacao/cadastrar/:id",
      name: "MovimentacaoForm",
      component: () => import("@/views/Movimentacao/MovimentacaoForm.vue"),
      meta: { requiresAuth: true },
    },
    {
      path: "/movimentacao",
      name: "Movimentacao",
      component: () => import("@/views/Movimentacao/MovimentacaoList.vue"),
      meta: {
        title: "Movimentações",
      },
    },
    {
      path: "/venda/cadastrar",
      name: "VendaForm",
      component: () => import("@/views/Venda/VendaForm.vue"),
      meta: {
        title: "Venda",
      },
    },
    {
      path: "/venda",
      name: "Venda",
      component: () => import("@/views/Venda/VendaList.vue"),
      meta: {
        title: "Vendas",
      },
    },
    {
      path: "/",
      name: "Ecommerce",
      component: () => import("../views/Dashboard.vue"),
      meta: {
        title: "eCommerce Dashboard",
      },
    },
    {
      path: "/:pathMatch(.*)*",
      name: "404 Error",
      component: () => import("../views/Errors/FourZeroFour.vue"),
      meta: {
        title: "404 Error",
      },
    },
  ],
})

export default router

router.beforeEach((to, from, next) => {
  document.title = `${to.meta.title} | Controle Estoque Vue.js`
  next()
})

