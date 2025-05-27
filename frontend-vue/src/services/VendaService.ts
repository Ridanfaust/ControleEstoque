import type { Venda } from "@/models/Venda.ts";
import api from "@/services/api.js";
import type { AxiosResponse } from "axios";
import type { VendaRequest } from "@/models/VendaRequest.ts";

export default {
  async salvar(venda: VendaRequest): Promise<AxiosResponse<Venda>> {
    console.log(venda);
    return api.post("/venda/cadastrar", venda);
  },

  async listar(codigo: string, descricao: string, pagina: number, tamanho: number): Promise<AxiosResponse<Venda[]>> {
    const params = new URLSearchParams();
    if (codigo) params.append("codigo", codigo);
    if (descricao) params.append("descricao", descricao);
    if (pagina) params.append("page", String(pagina));
    if (tamanho) params.append("size", String(tamanho));

    return api.get(`/venda/listar?${params.toString()}`);
  },

  async buscarPorId(id: number): Promise<AxiosResponse<Venda>> {
    return api.get(`/venda/${id}`);
  },

  async cancelar(venda: VendaRequest) {
    return api.post(`/venda/cancelar`, venda);
  },
}
