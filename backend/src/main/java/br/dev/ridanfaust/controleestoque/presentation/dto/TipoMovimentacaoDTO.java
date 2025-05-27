package br.dev.ridanfaust.controleestoque.presentation.dto;

import br.dev.ridanfaust.controleestoque.domain.enums.NaturezaMovimentacaoEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TipoMovimentacaoDTO {

    private Long id;

    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    private boolean venda;

    @NotNull(message = "A natureza da movimentação é obrigatória")
    private String naturezaMovimentacao;

    private boolean ativo;

    private String dataCadastro;

    private String dataAlteracao;

    public boolean isEntrada() {
        return NaturezaMovimentacaoEnum.ENTRADA.getDescricao().equals(naturezaMovimentacao);
    }

    public boolean isSaida() {
        return NaturezaMovimentacaoEnum.SAIDA.getDescricao().equals(naturezaMovimentacao);
    }

}
