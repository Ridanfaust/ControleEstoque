package br.dev.ridanfaust.controleestoque.presentation.controller;

import br.dev.ridanfaust.controleestoque.application.service.TipoProdutoService;
import br.dev.ridanfaust.controleestoque.presentation.dto.TipoProdutoDTO;
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
@RequestMapping("/api/tipo-produto")
@RequiredArgsConstructor
public class TipoProdutoRestController extends AbstractRestController<TipoProdutoDTO> {

    private final TipoProdutoService tipoProdutoService;

    @GetMapping()
    public ResponseEntity<PaginatedResponse<TipoProdutoDTO>> listar(@RequestParam(required = false) String descricao, Pageable pageable) {
        var page = tipoProdutoService.listar(descricao, pageable);
        return responseListaPaginada(PaginatedResponse.from(page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoProdutoDTO> buscarPorId(@PathVariable Long id) {
        return responseSucessoComItem(tipoProdutoService.buscarDtoPorId(id));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<TipoProdutoDTO> cadastrar(@Valid @RequestBody TipoProdutoDTO tipoProduto) {
        TipoProdutoDTO itemCriado = tipoProdutoService.cadastrar(tipoProduto);
        return responseItemCriado(itemCriado, "/api/tipo-produto/{id}", itemCriado.getId().toString());
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<TipoProdutoDTO> editar(@PathVariable Long id,
                                                 @Valid @RequestBody TipoProdutoDTO produtoTipo) {
        return responseSucessoComItem(tipoProdutoService.editar(id, produtoTipo));
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        tipoProdutoService.remover(id);
        return responseSemConteudo();
    }

}
