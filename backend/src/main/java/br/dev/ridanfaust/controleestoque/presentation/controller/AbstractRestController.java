package br.dev.ridanfaust.controleestoque.presentation.controller;

import br.dev.ridanfaust.controleestoque.presentation.response.PaginatedResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public abstract class AbstractRestController<T> {

    protected ResponseEntity<T> responseItemCriado(T obj, String caminho, String id) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path(caminho).buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    protected ResponseEntity<T> responseSucessoComItem(T obj) {
        return ResponseEntity.status(HttpStatus.OK).body(obj);
    }

    protected ResponseEntity<Void> responseSemConteudo() {
        return ResponseEntity.noContent().build();
    }

    protected ResponseEntity<PaginatedResponse<T>> responseListaPaginada(PaginatedResponse<T> itens) {
        return ResponseEntity.status(HttpStatus.OK).body(itens);
    }

}
