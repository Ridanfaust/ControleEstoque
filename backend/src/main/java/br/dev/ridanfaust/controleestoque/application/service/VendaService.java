package br.dev.ridanfaust.controleestoque.application.service;

import br.dev.ridanfaust.controleestoque.domain.exception.RecursoNaoEncontradoException;
import br.dev.ridanfaust.controleestoque.domain.exception.RegraNegocioException;
import br.dev.ridanfaust.controleestoque.domain.model.Movimentacao;
import br.dev.ridanfaust.controleestoque.domain.model.Produto;
import br.dev.ridanfaust.controleestoque.domain.model.SituacaoVenda;
import br.dev.ridanfaust.controleestoque.domain.model.TipoMovimentacao;
import br.dev.ridanfaust.controleestoque.domain.model.Venda;
import br.dev.ridanfaust.controleestoque.domain.model.VendaProduto;
import br.dev.ridanfaust.controleestoque.domain.repository.VendaRepository;
import br.dev.ridanfaust.controleestoque.presentation.dto.VendaDTO;
import br.dev.ridanfaust.controleestoque.presentation.request.VendaRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static br.dev.ridanfaust.controleestoque.shared.util.ValidatorUtil.isNotEmpty;

@Service
@RequiredArgsConstructor
public class VendaService {

    private final MovimentacaoService movimentacaoService;
    private final VendaRepository vendaRepository;
    private final ProdutoService produtoService;
    private final ModelMapper modelMapper;

    public Page<VendaDTO> listar(String descricao, Pageable pageable) {
        return vendaRepository.findAllPaginadoByFiltros(descricao, pageable)
                .map(produto -> modelMapper.map(produto, VendaDTO.class));
    }

    @Transactional
    public VendaDTO cadastrar(VendaRequest vendaRequest) {
        var erros = validarVenda(vendaRequest);

        if (isNotEmpty(erros)) {
            throw new RegraNegocioException(erros);
        }

        Venda venda = modelMapper.map(vendaRequest.getVenda(), Venda.class);

        List<Long> idsProdutos = venda.getProdutos().stream()
                .map(item -> item.getProduto().getId())
                .toList();
        List<Produto> produtos = produtoService.listarPorIds(idsProdutos);

        movimentacaoService.salvarMovimentacoes(popularMovimentacoes(vendaRequest, venda, produtos));

        venda.setSituacao(SituacaoVenda.CONCLUIDA);
        Venda vendaSalva = vendaRepository.save(venda);

        return modelMapper.map(vendaSalva, VendaDTO.class);
    }

    public Venda buscarPorId(Long id) {
        return vendaRepository.findCompletoById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado para o ID: " + id));
    }

    public VendaDTO buscarDtoPorId(Long id) {
        Venda produto = buscarPorId(id);

        return modelMapper.map(produto, VendaDTO.class);
    }

    @Transactional
    public VendaDTO cancelar(VendaRequest vendaRequest) {
        Venda venda = buscarPorId(vendaRequest.getVenda().getId());

        var erros = validarCancelamento(venda);

        if (isNotEmpty(erros)) {
            throw new RegraNegocioException(erros);
        }

        List<Produto> produtos = venda.getProdutos().stream().map(VendaProduto::getProduto).toList();

        movimentacaoService.salvarMovimentacoes(popularMovimentacoes(vendaRequest, venda, produtos));

        venda.setSituacao(SituacaoVenda.CANCELADA);
        Venda vendaSalva = vendaRepository.save(venda);

        return modelMapper.map(vendaSalva, VendaDTO.class);
    }

    private List<Movimentacao> popularMovimentacoes(VendaRequest vendaRequest, Venda venda, List<Produto> produtos) {
        List<Movimentacao> movimentacoes = new ArrayList<>();
        venda.getProdutos().forEach(item -> {
            var produto = produtos.stream().filter(p -> Objects.equals(p.getId(), item.getId())).findFirst()
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado para o ID: " + item.getId()));

            Movimentacao movimentacao = new Movimentacao();
            movimentacao.setProduto(produto);
            movimentacao.setTipo(modelMapper.map(vendaRequest.getTipoMovimentacao(), TipoMovimentacao.class));
            movimentacao.setQuantidade(item.getQuantidade());
            movimentacoes.add(movimentacao);
        });

        return movimentacoes;
    }

    private List<String> validarVenda(VendaRequest vendaRequest) {
        List<String> erros = new ArrayList<>();

        if (vendaRequest.getTipoMovimentacao().isEntrada()) {
            erros.add("Não é possível cadastrar uma venda com tipo de movimentação de entrada");
        }

        return erros;
    }

    private List<String> validarCancelamento(Venda venda) {
        List<String> erros = new ArrayList<>();

        if (venda.getSituacao().equals(SituacaoVenda.CANCELADA)) {
            erros.add("A venda já está cancelada");
        }

        return erros;
    }
}
