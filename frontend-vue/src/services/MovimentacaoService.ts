import api from "@/services/api.js"
import type { AxiosResponse } from "axios";
import type { Movimentacao } from "@/models/Movimentacao.ts";

const API_BASE_URL = `/movimentacao`

export default {
  async salvar(movimentacao: Movimentacao): Promise<AxiosResponse<Movimentacao>> {
    return api.post(`${API_BASE_URL}/cadastrar`, movimentacao)
  },
  async listar(descricao: string, tipoMovimentacao: string, natureza: string, dataInicio: string, dataFim: string, pagina: number, tamanho: number): Promise<AxiosResponse<Movimentacao[]>> {
    const params: URLSearchParams = new URLSearchParams();
    if (descricao) params.append("produto", descricao);
    if (tipoMovimentacao) params.append("tipoMovimentacao", tipoMovimentacao);
    if (natureza) params.append("natureza", natureza);
    if (dataInicio) params.append("dataInicio", dataInicio);
    if (dataFim) params.append("dataFim", dataFim);
    if (pagina) params.append("page", String(pagina));
    if (tamanho) params.append("size", String(tamanho));

    console.log(params.toString())

    return api.get(`${API_BASE_URL}/listar?${params.toString()}`)
  },
  async buscarPorId(id: number): Promise<AxiosResponse<Movimentacao>> {
    return api.get(`${API_BASE_URL}/${id}`)
  },
  async excluir(id: number): Promise<AxiosResponse> {
    return api.delete(`${API_BASE_URL}/remover/${id}`)
  },
}
