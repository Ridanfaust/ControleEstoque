import type { Produto } from "@/models/Produto";
import type { TipoMovimentacao } from "@/models/TipoMovimentacao";

export interface Movimentacao {
  id?: number;
  produto: Produto;
  tipo: TipoMovimentacao;
  quantidade: number;
  ativo?: boolean;
  dataCadastro?: Date;
  dataAlteracao?: Date;
}
