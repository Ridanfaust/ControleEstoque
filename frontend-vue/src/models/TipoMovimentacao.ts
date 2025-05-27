import { NaturezaMovimentacaoEnum } from "@/models/enums/NaturezaMovimentacaoEnum";

export interface TipoMovimentacao {
  id?: number;
  descricao?: string;
  naturezaMovimentacao?: NaturezaMovimentacaoEnum;
  ativo?: boolean;
  dataCadastro?: string;
  dataAlteracao?: string;
}
