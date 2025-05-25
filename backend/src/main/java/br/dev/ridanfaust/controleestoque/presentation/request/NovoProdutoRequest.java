package br.dev.ridanfaust.controleestoque.presentation.request;

import br.dev.ridanfaust.controleestoque.presentation.dto.ProdutoDTO;
import br.dev.ridanfaust.controleestoque.presentation.dto.TipoMovimentacaoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NovoProdutoRequest {

    @Valid
    @NotNull(message = "O produto é obrigatório")
    private ProdutoDTO produto;

    @NotNull(message = "Tipo de movimentação é obrigatório")
    private TipoMovimentacaoDTO tipoMovimentacao;

}
