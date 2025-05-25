package br.dev.ridanfaust.controleestoque.application.service;

import br.dev.ridanfaust.controleestoque.domain.model.Movimentacao;
import br.dev.ridanfaust.controleestoque.domain.repository.MovimentacaoRepository;
import br.dev.ridanfaust.controleestoque.presentation.dto.MovimentacaoDTO;
import br.dev.ridanfaust.controleestoque.presentation.dto.ProdutoDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProdutoEstoqueService {

    private final MovimentacaoRepository movimentacaoRepository;
    private final ProdutoService produtoService;
    private final ModelMapper modelMapper;

    @Transactional
    public MovimentacaoDTO movimentarEstoque(MovimentacaoDTO movimentacaoDTO) {
        Movimentacao movimentacao = modelMapper.map(movimentacaoDTO, Movimentacao.class);
        Movimentacao movimentacaoSalva = movimentacaoRepository.save(movimentacao);

        ProdutoDTO produtoAtualizado = produtoService.atualizarEstoque(movimentacao);
        movimentacaoSalva.getProduto().setQuantidadeTotal(produtoAtualizado.getQuantidadeTotal());

        return modelMapper.map(movimentacaoSalva, MovimentacaoDTO.class);
    }

}
