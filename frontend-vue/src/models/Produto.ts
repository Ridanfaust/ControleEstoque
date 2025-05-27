import type { TipoProduto } from "@/models/TipoProduto";

export interface Produto {
  id?: number;
  codigo?: string;
  descricao?: string;
  valorFornecedor?: number;
  valorVenda?: number;
  quantidadeTotal?: number;
  tipo?: TipoProduto;
  ativo?: boolean;
  dataCadastro?: Date;
  dataAlteracao?: Date;
}
