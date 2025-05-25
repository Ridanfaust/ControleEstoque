package br.dev.ridanfaust.controleestoque.application.service;

import br.dev.ridanfaust.controleestoque.domain.exception.RecursoNaoEncontradoException;
import br.dev.ridanfaust.controleestoque.domain.exception.RegraNegocioException;
import br.dev.ridanfaust.controleestoque.domain.model.TipoProduto;
import br.dev.ridanfaust.controleestoque.domain.repository.TipoProdutoRepository;
import br.dev.ridanfaust.controleestoque.presentation.dto.TipoProdutoDTO;
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
public class TipoProdutoService {

    private final TipoProdutoRepository tipoProdutoRepository;
    private final ModelMapper modelMapper;

    public Page<TipoProdutoDTO> listar(String descricao, Pageable pageable) {
        return tipoProdutoRepository.findAllPaginadoByFiltros(lower(descricao), pageable)
                .map(tipoProduto -> modelMapper.map(tipoProduto, TipoProdutoDTO.class));
    }

    public TipoProduto buscarPorId(Long id) {
        return tipoProdutoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("TipoProduto não encontrado para o ID: " + id));
    }

    public TipoProdutoDTO buscarDtoPorId(Long id) {
        TipoProduto tipoProduto = buscarPorId(id);
        return modelMapper.map(tipoProduto, TipoProdutoDTO.class);
    }

    @Transactional
    public TipoProdutoDTO cadastrar(TipoProdutoDTO tipoProdutoDTO) {
        var erros = validar(tipoProdutoDTO);

        if (isNotEmpty(erros)) {
            throw new RegraNegocioException(erros);
        }

        var tipoProduto = modelMapper.map(tipoProdutoDTO, TipoProduto.class);
        return modelMapper.map(tipoProdutoRepository.save(tipoProduto), TipoProdutoDTO.class);
    }

    @Transactional
    public TipoProdutoDTO editar(Long id, TipoProdutoDTO tipoProdutoDTO) {
        var erros = validar(tipoProdutoDTO);

        if (isNotEmpty(erros)) {
            throw new RegraNegocioException(erros);
        }

        TipoProduto tipoProduto = buscarPorId(id);
        tipoProduto.setDescricao(tipoProdutoDTO.getDescricao());

        return modelMapper.map(tipoProdutoRepository.save(tipoProduto), TipoProdutoDTO.class);
    }

    @Transactional
    public void remover(Long id) {
        TipoProduto existente = buscarPorId(id);
        tipoProdutoRepository.delete(existente);
    }

    private List<String> validar(TipoProdutoDTO tipoProduto) {
        List<String> erros = new ArrayList<>();

        if (tipoProdutoRepository.existsByDescricao(tipoProduto.getId(), tipoProduto.getDescricao())) {
            erros.add("Tipo de Produto já cadastrado com a mesma descrição");
        }

        return erros;
    }

}
