package br.dev.ridanfaust.controleestoque.application.service;

import br.dev.ridanfaust.controleestoque.domain.enums.NaturezaMovimentacaoEnum;
import br.dev.ridanfaust.controleestoque.domain.exception.RecursoNaoEncontradoException;
import br.dev.ridanfaust.controleestoque.domain.exception.RegraNegocioException;
import br.dev.ridanfaust.controleestoque.domain.model.TipoMovimentacao;
import br.dev.ridanfaust.controleestoque.domain.repository.TipoMovimentacaoRepository;
import br.dev.ridanfaust.controleestoque.presentation.dto.TipoMovimentacaoDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static br.dev.ridanfaust.controleestoque.shared.util.StringFilterUtil.lower;
import static br.dev.ridanfaust.controleestoque.shared.util.ValidatorUtil.isNotEmpty;

@Service
@RequiredArgsConstructor
public class TipoMovimentacaoService {

    private final TipoMovimentacaoRepository tipoMovimentacaoRepository;
    private final ModelMapper modelMapper;

    public Page<TipoMovimentacaoDTO> listar(String descricao, String natureza, Boolean venda, Pageable pageable) {
        return tipoMovimentacaoRepository.findAllPaginadoByFiltros(lower(descricao), lower(natureza), venda, pageable)
                .map(tipoMovimentacao -> modelMapper.map(tipoMovimentacao, TipoMovimentacaoDTO.class));
    }

    public TipoMovimentacao buscarPorId(Long id) {
        return tipoMovimentacaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("TipoMovimentacao não encontrado para o ID: " + id));
    }

    public TipoMovimentacaoDTO buscarDtoPorId(Long id) {
        TipoMovimentacao tipoMovimentacao = buscarPorId(id);
        return modelMapper.map(tipoMovimentacao, TipoMovimentacaoDTO.class);
    }

    @Transactional
    public TipoMovimentacaoDTO cadastrar(TipoMovimentacaoDTO tipoMovimentacaoDTO) {
        var erros = validar(tipoMovimentacaoDTO);

        if (isNotEmpty(erros)) {
            throw new RegraNegocioException(erros);
        }

        var tipoMovimentacao = modelMapper.map(tipoMovimentacaoDTO, TipoMovimentacao.class);
        return modelMapper.map(tipoMovimentacaoRepository.save(tipoMovimentacao), TipoMovimentacaoDTO.class);
    }

    @Transactional
    public TipoMovimentacaoDTO editar(Long id, TipoMovimentacaoDTO tipoMovimentacaoDTO) {
        var erros = validar(tipoMovimentacaoDTO);

        if (isNotEmpty(erros)) {
            throw new RegraNegocioException(erros);
        }

        TipoMovimentacao tipoMovimentacao = buscarPorId(id);
        tipoMovimentacao.setDescricao(tipoMovimentacaoDTO.getDescricao());
        tipoMovimentacao.setNaturezaMovimentacao(NaturezaMovimentacaoEnum.valueOf(tipoMovimentacaoDTO.getNaturezaMovimentacao()));

        return modelMapper.map(tipoMovimentacaoRepository.save(tipoMovimentacao), TipoMovimentacaoDTO.class);
    }

    @Transactional
    public void remover(Long id) {
        TipoMovimentacao existente = buscarPorId(id);
        tipoMovimentacaoRepository.delete(existente);
    }

    private List<String> validar(TipoMovimentacaoDTO tipoMovimentacao) {
        List<String> erros = new ArrayList<>();

        String tipoDescricao = tipoMovimentacao.getDescricao();
        if (tipoMovimentacaoRepository.existsByDescricao(tipoMovimentacao.getId(), lower(tipoDescricao))) {
            erros.add("Já existe um tipo de movimentação com a descrição: " + tipoDescricao);
        }

        return erros;
    }

}
