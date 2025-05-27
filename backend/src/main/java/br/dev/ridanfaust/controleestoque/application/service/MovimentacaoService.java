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

import java.time.LocalDate;
import java.util.List;

import static br.dev.ridanfaust.controleestoque.shared.util.StringFilterUtil.lower;
import static br.dev.ridanfaust.controleestoque.shared.util.ValidatorUtil.isNotEmpty;

@Service
@RequiredArgsConstructor
public class MovimentacaoService {

    private final MovimentacaoRepository movimentacaoRepository;
    private final ModelMapper modelMapper;

    public Page<MovimentacaoDTO> listar(String produto, String tipoMovimentacao, String natureza, LocalDate dataInicio, LocalDate dataFim, Pageable pageable) {
        if (dataInicio == null) {
            dataInicio = LocalDate.ofEpochDay(0);
        }
        if (dataFim == null) {
            dataFim = LocalDate.now();
        }
        return movimentacaoRepository.findAllPaginadoByFiltros(produto, tipoMovimentacao, lower(natureza), dataInicio, dataFim, pageable)
                .map(movimentacao -> modelMapper.map(movimentacao, MovimentacaoDTO.class));
    }

    @Transactional
    public MovimentacaoDTO cadastrar(Movimentacao movimentacao) {
        return modelMapper.map(movimentacaoRepository.save(movimentacao), MovimentacaoDTO.class);
    }

    @Transactional
    public List<Movimentacao> salvarMovimentacoes(List<Movimentacao> movimentacoesParaSalvar) {
        if (isNotEmpty(movimentacoesParaSalvar)) {
            return movimentacaoRepository.saveAll(movimentacoesParaSalvar);
        }
        throw new IllegalArgumentException("A lista de movimentações não pode ser vazia.");
    }
}
