import api from "@/services/api.js"
import type { AxiosResponse } from "axios";
import type { Produto } from "@/models/Produto.ts";
import type { NovoProduto } from "@/models/NovoProduto.ts";

const API_BASE_URL = `/produto`

export default {
  async salvar(produto: NovoProduto): Promise<AxiosResponse<Produto>> {
    return api.post(`${API_BASE_URL}/cadastrar`, produto)
  },
  async listar(codigo: string, descricao: string, tipo: string, pagina: number, tamanho: number): Promise<AxiosResponse<Produto[]>> {
    const params = new URLSearchParams();
    if (codigo) params.append("codigo", codigo);
    if (descricao) params.append("descricao", descricao);
    if (tipo) params.append("tipo", tipo);
    if (pagina) params.append("page", String(pagina));
    if (tamanho) params.append("size", String(tamanho));

    return api.get(`${API_BASE_URL}/listar?${params.toString()}`)
  },
  async buscarPorId(id: number): Promise<AxiosResponse<Produto>> {
    return api.get(`${API_BASE_URL}/${id}`)
  },
  async excluir(id: number): Promise<AxiosResponse> {
    return api.delete(`${API_BASE_URL}/remover/${id}`)
  },
}
