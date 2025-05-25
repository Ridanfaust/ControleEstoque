package br.dev.ridanfaust.controleestoque.presentation.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class VendaDTO {

    private Long id;

    private String descricao;

    @NotNull(message = "É necessário informar o valor total da venda")
    private BigDecimal valorTotal;

    @NotEmpty(message = "É necessário selecionar pelo menos 1 item")
    private List<VendaProdutoDTO> produtos;

    private boolean ativo;

    private Date dataCadastro;

    private Date dataAlteracao;

}
