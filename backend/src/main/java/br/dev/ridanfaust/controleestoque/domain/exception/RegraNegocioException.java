package br.dev.ridanfaust.controleestoque.domain.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class RegraNegocioException extends GenericException {

    private final List<String> mensagens;

    public RegraNegocioException(List<String> mensagens) {
        super(String.join(", ", mensagens));
        this.mensagens = mensagens;
    }

}
