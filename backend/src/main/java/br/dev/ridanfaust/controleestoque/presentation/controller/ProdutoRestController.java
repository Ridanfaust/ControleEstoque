package br.dev.ridanfaust.controleestoque.presentation.controller;

import br.dev.ridanfaust.controleestoque.application.service.ProdutoService;
import br.dev.ridanfaust.controleestoque.presentation.dto.ProdutoDTO;
import br.dev.ridanfaust.controleestoque.presentation.request.NovoProdutoRequest;
import br.dev.ridanfaust.controleestoque.presentation.response.PaginatedResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/produto")
@RequiredArgsConstructor
public class ProdutoRestController extends AbstractRestController<ProdutoDTO> {

    private final ProdutoService produtoService;

    @GetMapping("/listar")
    public ResponseEntity<PaginatedResponse<ProdutoDTO>> listar(@RequestParam(required = false) String descricao,
                                                                Pageable pageable) {
        var page = produtoService.listar(descricao, pageable);
        return responseListaPaginada(PaginatedResponse.from(page));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<ProdutoDTO> cadastrar(@Valid @RequestBody NovoProdutoRequest produtoRequest) {
        ProdutoDTO itemCriado = produtoService.cadastrar(produtoRequest);
        return responseItemCriado(itemCriado, "/api/produto/{id}", itemCriado.getId().toString());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> buscarPorId(@PathVariable Long id) {
        return responseSucessoComItem(produtoService.buscarDtoPorId(id));
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<ProdutoDTO> editar(@PathVariable Long id,
                                             @Valid @RequestBody ProdutoDTO produto) {
        return responseSucessoComItem(produtoService.editar(id, produto));
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        produtoService.remover(id);
        return responseSemConteudo();
    }

}

