import type { VendaProduto } from "@/models/VendaProduto";
import type { SituacaoVenda } from "@/models/SituacaoVenda.ts";

export interface Venda {
  id?: number;
  valorTotal: number;
  produtos: VendaProduto[];
  situacao: SituacaoVenda;
  ativo?: boolean;
  dataCadastro?: Date;
  dataAlteracao?: Date;
}
