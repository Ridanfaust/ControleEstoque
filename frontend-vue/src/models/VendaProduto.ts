import type { Produto } from "@/models/Produto.ts";


export interface VendaProduto {
  id?: number;
  produto: Produto;
  valorVenda: number;
  quantidade: number;
  ativo?: boolean;
  dataCadastro?: Date;
  dataAlteracao?: Date;
}
