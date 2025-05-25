package br.dev.ridanfaust.controleestoque.presentation.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class MovimentacaoDTO {

    private Long id;

    @NotNull(message = "O produto é obrigatório")
    private ProdutoDTO produto;

    @NotNull(message = "O tipo de movimentação é obrigatório")
    private TipoMovimentacaoDTO tipo;

    @NotEmpty(message = "A quantidade é obrigatória")
    private Integer quantidade;

    private boolean ativo;

    private Date dataCadastro;

    private Date dataAlteracao;

}
