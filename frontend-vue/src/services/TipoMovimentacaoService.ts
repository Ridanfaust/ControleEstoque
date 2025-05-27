import api from "@/services/api.js"
import type { TipoMovimentacao } from "@/models/TipoMovimentacao.ts";
import type { AxiosResponse } from "axios";

const API_BASE_URL = `/tipo-movimentacao`

export default {
  async salvar(tipoMovimentacao: {
    descricao: string,
    venda: boolean,
    naturezaMovimentacao: string
  }): Promise<TipoMovimentacao> {
    return api.post(`${API_BASE_URL}/cadastrar`, tipoMovimentacao)
  },
  async listar(descricao: string, natureza: string, venda: boolean | null, pagina: number, tamanho: number): Promise<AxiosResponse<TipoMovimentacao[]>> {
    const params = new URLSearchParams();

    if (descricao) params.append("descricao", descricao);
    if (natureza) params.append("natureza", natureza);
    if (venda !== null) params.append("venda", String(venda));
    if (pagina !== null) params.append("page", String(pagina));
    if (tamanho !== null) params.append("size", String(tamanho));

    return api.get(`${API_BASE_URL}/listar?${params.toString()}`)
  },
  async buscarPorId(id: number): Promise<AxiosResponse<TipoMovimentacao>> {
    return api.get(`${API_BASE_URL}/${id}`)
  },
  async excluir(id: number) {
    return api.delete(`${API_BASE_URL}/remover/${id}`)
  },
}
