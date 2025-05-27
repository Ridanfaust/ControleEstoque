import type { Venda } from "@/models/Venda.ts";
import type { TipoMovimentacao } from "@/models/TipoMovimentacao.ts";

export interface VendaRequest {
  venda: Venda
  tipoMovimentacao: TipoMovimentacao
}
