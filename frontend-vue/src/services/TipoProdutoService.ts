import api from "@/services/api.js"
import type { TipoProduto } from "@/models/TipoProduto.ts";
import type { AxiosResponse } from "axios";

const API_BASE_URL = `/tipo-produto`

export default {
  async salvar(tipoProduto: { descricao: string }): Promise<TipoProduto> {
    return api.post(`${API_BASE_URL}/cadastrar`, tipoProduto)
  },
  async listar(descricao: string, pagina: number, tamanho: number): Promise<AxiosResponse<TipoProduto[]>> {
    return api.get(`${API_BASE_URL}?descricao=${descricao}&page=${pagina}&size=${tamanho}`)
  },
  async buscarPorId(id: number): Promise<AxiosResponse<TipoProduto>> {
    return api.get(`${API_BASE_URL}/${id}`)
  },
  async excluir(id: number) {
    return api.delete(`${API_BASE_URL}/remover/${id}`)
  },
}
