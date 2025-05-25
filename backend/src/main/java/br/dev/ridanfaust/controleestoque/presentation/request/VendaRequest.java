package br.dev.ridanfaust.controleestoque.presentation.request;

import br.dev.ridanfaust.controleestoque.presentation.dto.TipoMovimentacaoDTO;
import br.dev.ridanfaust.controleestoque.presentation.dto.VendaDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VendaRequest {

    @Valid
    @NotNull(message = "Venda é obrigatório")
    private VendaDTO venda;

    @NotNull(message = "Tipo de movimentação é obrigatório")
    private TipoMovimentacaoDTO tipoMovimentacao;
}
