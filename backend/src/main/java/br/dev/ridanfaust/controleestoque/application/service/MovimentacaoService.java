package br.dev.ridanfaust.controleestoque.application.service;

import br.dev.ridanfaust.controleestoque.domain.model.Movimentacao;
import br.dev.ridanfaust.controleestoque.domain.repository.MovimentacaoRepository;
import br.dev.ridanfaust.controleestoque.presentation.dto.MovimentacaoDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static br.dev.ridanfaust.controleestoque.shared.util.ValidatorUtil.isNotEmpty;

@Service
@RequiredArgsConstructor
public class MovimentacaoService {

    private final MovimentacaoRepository movimentacaoRepository;
    private final ModelMapper modelMapper;

    public Page<MovimentacaoDTO> listar(Long produtoId, Long tipoMovimentacaoId, Pageable pageable) {
        return movimentacaoRepository.findAllPaginadoByFiltros(produtoId, tipoMovimentacaoId, pageable)
                .map(movimentacao -> modelMapper.map(movimentacao, MovimentacaoDTO.class));
    }

    @Transactional
    public MovimentacaoDTO cadastrar(Movimentacao movimentacao) {
        return modelMapper.map(movimentacaoRepository.save(movimentacao), MovimentacaoDTO.class);
    }

    @Transactional
    public void salvarMovimentacoes(List<Movimentacao> movimentacoesParaSalvar) {
        if (isNotEmpty(movimentacoesParaSalvar)) {
            movimentacaoRepository.saveAll(movimentacoesParaSalvar);
        }
    }
}
