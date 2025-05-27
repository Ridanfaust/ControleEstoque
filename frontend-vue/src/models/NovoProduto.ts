import type { Produto } from "@/models/Produto.ts";
import type { TipoMovimentacao } from "@/models/TipoMovimentacao.ts";

export interface NovoProduto {
  produto: Produto
  tipoMovimentacao: TipoMovimentacao
}
