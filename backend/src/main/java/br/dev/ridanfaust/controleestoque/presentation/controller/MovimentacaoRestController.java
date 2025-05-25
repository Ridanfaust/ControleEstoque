package br.dev.ridanfaust.controleestoque.presentation.controller;

import br.dev.ridanfaust.controleestoque.application.service.MovimentacaoService;
import br.dev.ridanfaust.controleestoque.application.service.ProdutoEstoqueService;
import br.dev.ridanfaust.controleestoque.presentation.dto.MovimentacaoDTO;
import br.dev.ridanfaust.controleestoque.presentation.response.PaginatedResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/movimentacao")
public class MovimentacaoRestController extends AbstractRestController<MovimentacaoDTO> {

    private final MovimentacaoService movimentacaoService;
    private final ProdutoEstoqueService produtoEstoqueService;

    @GetMapping("/listar")
    public ResponseEntity<PaginatedResponse<MovimentacaoDTO>> listar(@RequestParam(required = false) Long produtoId,
                                                                     @RequestParam(required = false) Long tipoMovimentacaoId,
                                                                     Pageable pageable) {
        var page = movimentacaoService.listar(produtoId, tipoMovimentacaoId, pageable);
        return responseListaPaginada(PaginatedResponse.from(page));
    }

    @PostMapping("/movimentar")
    public ResponseEntity<MovimentacaoDTO> movimentar(@Valid @RequestBody MovimentacaoDTO movimentacaoDTO) {
        return ResponseEntity.ok(produtoEstoqueService.movimentarEstoque(movimentacaoDTO));
    }

}
