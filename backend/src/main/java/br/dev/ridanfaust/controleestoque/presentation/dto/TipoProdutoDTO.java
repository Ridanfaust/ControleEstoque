package br.dev.ridanfaust.controleestoque.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

@Data
public class TipoProdutoDTO {

    private Long id;

    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    private boolean ativo;

    private Date dataCadastro;

    private Date dataAlteracao;

}
