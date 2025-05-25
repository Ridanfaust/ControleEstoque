package br.dev.ridanfaust.controleestoque.domain.enums;

import lombok.Getter;

@Getter
public enum NaturezaMovimentacaoEnum {
    ENTRADA("Entrada"),
    SAIDA("Saída");

    private final String descricao;

    NaturezaMovimentacaoEnum(String descricao) {
        this.descricao = descricao;
    }
}
