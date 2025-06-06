package br.dev.ridanfaust.controleestoque.presentation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class VendaProdutoDTO {

    private Long id;

    @NotNull(message = "O produto é obrigatório")
    private ProdutoDTO produto;

    @NotNull(message = "O valor do produto na venda é obrigatório")
    private BigDecimal valorVenda;

    @NotNull(message = "A quantidade do produto na venda é obrigatória")
    private Integer quantidade;

    private boolean ativo;

    private Date dataCadastro;

    private Date dataAlteracao;

}
