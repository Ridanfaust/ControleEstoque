package br.dev.ridanfaust.controleestoque.presentation.controller;

import br.dev.ridanfaust.controleestoque.application.service.VendaService;
import br.dev.ridanfaust.controleestoque.presentation.dto.VendaDTO;
import br.dev.ridanfaust.controleestoque.presentation.request.VendaRequest;
import br.dev.ridanfaust.controleestoque.presentation.response.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/venda")
public class VendaRestController extends AbstractRestController<VendaDTO> {

    private final VendaService vendaService;

    @PostMapping("/cadastrar")
    public ResponseEntity<VendaDTO> cadastrar(@RequestBody VendaRequest vendaRequest) {
        VendaDTO itemCriado = vendaService.cadastrar(vendaRequest);
        return responseItemCriado(itemCriado, "/api/venda/{id}", itemCriado.getId().toString());
    }

    @GetMapping("/listar")
    public ResponseEntity<PaginatedResponse<VendaDTO>> listar(Pageable pageable) {
        var page = vendaService.listar(pageable);
        return responseListaPaginada(PaginatedResponse.from(page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendaDTO> buscarPorId(@PathVariable Long id) {
        return responseSucessoComItem(vendaService.buscarDtoPorId(id));
    }

    @PostMapping("/cancelar")
    public ResponseEntity<VendaDTO> cancelar(@RequestBody VendaRequest vendaRequest) {
        return responseSucessoComItem(vendaService.cancelar(vendaRequest));
    }
}
