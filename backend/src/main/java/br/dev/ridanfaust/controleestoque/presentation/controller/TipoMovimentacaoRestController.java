package br.dev.ridanfaust.controleestoque.presentation.controller;

import br.dev.ridanfaust.controleestoque.application.service.TipoMovimentacaoService;
import br.dev.ridanfaust.controleestoque.presentation.dto.TipoMovimentacaoDTO;
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
@RequestMapping("/api/tipo-movimentacao")
@RequiredArgsConstructor
public class TipoMovimentacaoRestController extends AbstractRestController<TipoMovimentacaoDTO> {

    private final TipoMovimentacaoService tipoMovimentacaoService;

    @GetMapping("/listar")
    public ResponseEntity<PaginatedResponse<TipoMovimentacaoDTO>> listar(@RequestParam(required = false) String descricao,
                                                                         @RequestParam(required = false) String natureza,
                                                                         @RequestParam(required = false) Boolean venda,
                                                                         Pageable pageable) {
        var page = tipoMovimentacaoService.listar(descricao, natureza, venda, pageable);
        return responseListaPaginada(PaginatedResponse.from(page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoMovimentacaoDTO> buscarPorId(@PathVariable Long id) {
        return responseSucessoComItem(tipoMovimentacaoService.buscarDtoPorId(id));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<TipoMovimentacaoDTO> cadastrar(@Valid @RequestBody TipoMovimentacaoDTO tipoMovimentacao) {
        TipoMovimentacaoDTO itemCriado = tipoMovimentacaoService.cadastrar(tipoMovimentacao);
        return responseItemCriado(itemCriado, "/api/tipo-movimentacao/{id}", itemCriado.getId().toString());
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<TipoMovimentacaoDTO> editar(@PathVariable Long id,
                                                      @Valid @RequestBody TipoMovimentacaoDTO tipoMovimentacao) {
        return responseSucessoComItem(tipoMovimentacaoService.editar(id, tipoMovimentacao));
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        tipoMovimentacaoService.remover(id);
        return responseSemConteudo();
    }

}
