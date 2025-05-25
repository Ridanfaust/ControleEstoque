package br.dev.ridanfaust.controleestoque.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProdutoDTO {

    private Long id;

    @NotBlank(message = "O código do produto é obrigatório")
    private String codigo;

    @NotBlank(message = "A descrição do produto é obrigatória")
    private String descricao;

    @NotNull(message = "O valor de custo é obrigatório")
    private BigDecimal valorFornecedor;

    @NotNull(message = "O valor de venda é obrigatório")
    private BigDecimal valorVenda;

    private Integer quantidadeTotal;

    @NotNull(message = "O tipo de produto é obrigatório")
    private TipoProdutoDTO tipo;

    private boolean ativo;

    private Date dataCadastro;

    private Date dataAlteracao;

}
