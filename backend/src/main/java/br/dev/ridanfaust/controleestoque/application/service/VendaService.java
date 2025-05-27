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
import br.dev.ridanfaust.controleestoque.presentation.dto.ProdutoDTO;
import br.dev.ridanfaust.controleestoque.presentation.dto.SituacaoVendaDTO;
import br.dev.ridanfaust.controleestoque.presentation.dto.VendaDTO;
import br.dev.ridanfaust.controleestoque.presentation.dto.VendaProdutoDTO;
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

    public Page<VendaDTO> listar(Pageable pageable) {
        return vendaRepository.findAllPaginadoByFiltros(pageable).map(this::mapVendaToDTO);
    }

    @Transactional
    public VendaDTO cadastrar(VendaRequest vendaRequest) {
        var erros = validarVenda(vendaRequest);

        if (isNotEmpty(erros)) {
            throw new RegraNegocioException(erros);
        }

        Venda venda = modelMapper.map(vendaRequest.getVenda(), Venda.class);
        venda.getProdutos().forEach(vendaProduto -> vendaProduto.setVenda(venda));

        List<Long> idsProdutos = venda.getProdutos().stream()
                .map(item -> item.getProduto().getId())
                .toList();
        List<Produto> produtos = produtoService.listarPorIds(idsProdutos);

        var movimentacoes = movimentacaoService.salvarMovimentacoes(popularMovimentacoes(vendaRequest, venda, produtos));
        for (Movimentacao mov : movimentacoes) {
            produtoService.atualizarEstoque(mov);
        }

        venda.setSituacao(SituacaoVenda.CONCLUIDA);
        Venda vendaSalva = vendaRepository.save(venda);

        return mapVendaToDTO(vendaSalva);
    }

    public Venda buscarPorId(Long id) {
        return vendaRepository.findCompletoById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado para o ID: " + id));
    }

    public VendaDTO buscarDtoPorId(Long id) {
        Venda venda = buscarPorId(id);

        return mapVendaToDTO(venda);
    }

    @Transactional
    public VendaDTO cancelar(VendaRequest vendaRequest) {
        Venda venda = buscarPorId(vendaRequest.getVenda().getId());

        var erros = validarCancelamento(venda);

        if (isNotEmpty(erros)) {
            throw new RegraNegocioException(erros);
        }

        List<Produto> produtos = venda.getProdutos().stream().map(VendaProduto::getProduto).toList();

        var movimentacoes = movimentacaoService.salvarMovimentacoes(popularMovimentacoes(vendaRequest, venda, produtos));
        for (Movimentacao mov : movimentacoes) {
            produtoService.atualizarEstoque(mov);
        }

        venda.setSituacao(SituacaoVenda.CANCELADA);
        Venda vendaSalva = vendaRepository.save(venda);

        return mapVendaToDTO(vendaSalva);
    }

    private List<Movimentacao> popularMovimentacoes(VendaRequest vendaRequest, Venda venda, List<Produto> produtos) {
        List<Movimentacao> movimentacoes = new ArrayList<>();
        venda.getProdutos().forEach(item -> {
            var produto = produtos.stream().filter(p -> Objects.equals(p.getId(), item.getProduto().getId())).findFirst()
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

    private VendaDTO mapVendaToDTO(Venda venda) {
        if (venda == null) {
            return null;
        }

        VendaDTO dto = new VendaDTO();
        dto.setId(venda.getId());
        dto.setDataCadastro(venda.getDataCadastro());
        dto.setValorTotal(venda.getValorTotal());

        SituacaoVendaDTO situacaoDTO = new SituacaoVendaDTO();
        situacaoDTO.setId(venda.getSituacao().getId());
        situacaoDTO.setDescricao(venda.getSituacao().getDescricao());
        dto.setSituacao(situacaoDTO);

        if (venda.getProdutos() != null) {
            dto.setProdutos(venda.getProdutos().stream()
                    .map(this::mapVendaProdutoToDTO)
                    .toList());
        }

        return dto;
    }

    private VendaProdutoDTO mapVendaProdutoToDTO(VendaProduto vendaProduto) {
        if (vendaProduto == null) {
            return null;
        }

        VendaProdutoDTO dto = new VendaProdutoDTO();
        dto.setId(vendaProduto.getId());
        dto.setQuantidade(vendaProduto.getQuantidade());
        dto.setValorVenda(vendaProduto.getValorVenda());

        if (vendaProduto.getProduto() != null) {
            ProdutoDTO produtoDTO = new ProdutoDTO();
            produtoDTO.setId(vendaProduto.getProduto().getId());
            produtoDTO.setCodigo(vendaProduto.getProduto().getCodigo());
            produtoDTO.setDescricao(vendaProduto.getProduto().getDescricao());
            produtoDTO.setQuantidadeTotal(vendaProduto.getProduto().getQuantidadeTotal());
            produtoDTO.setValorVenda(vendaProduto.getProduto().getValorVenda());
            produtoDTO.setValorFornecedor(vendaProduto.getProduto().getValorFornecedor());
            dto.setProduto(produtoDTO);
        }

        return dto;
    }
}
