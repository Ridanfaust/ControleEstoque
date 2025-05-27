package br.dev.ridanfaust.controleestoque.application.service;

import br.dev.ridanfaust.controleestoque.domain.exception.RecursoNaoEncontradoException;
import br.dev.ridanfaust.controleestoque.domain.exception.RegraNegocioException;
import br.dev.ridanfaust.controleestoque.domain.model.Movimentacao;
import br.dev.ridanfaust.controleestoque.domain.model.Produto;
import br.dev.ridanfaust.controleestoque.domain.model.TipoMovimentacao;
import br.dev.ridanfaust.controleestoque.domain.repository.ProdutoRepository;
import br.dev.ridanfaust.controleestoque.presentation.dto.ProdutoDTO;
import br.dev.ridanfaust.controleestoque.presentation.request.NovoProdutoRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static br.dev.ridanfaust.controleestoque.shared.util.StringFilterUtil.lower;
import static br.dev.ridanfaust.controleestoque.shared.util.ValidatorUtil.isEmpty;
import static br.dev.ridanfaust.controleestoque.shared.util.ValidatorUtil.isMenorQueZero;
import static br.dev.ridanfaust.controleestoque.shared.util.ValidatorUtil.isNotEmpty;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final MovimentacaoService movimentacaoService;
    private final ProdutoRepository produtoRepository;
    private final ModelMapper modelMapper;
    private final TipoMovimentacaoService tipoMovimentacaoService;

    public Page<ProdutoDTO> listar(String codigo, String descricao, String tipo, Pageable pageable) {
        return produtoRepository.findAllPaginadoByFiltros(lower(codigo), lower(descricao), lower(tipo), pageable)
                .map(produto -> modelMapper.map(produto, ProdutoDTO.class));
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.findCompletoById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado para o ID: " + id));
    }

    public ProdutoDTO buscarDtoPorId(Long id) {
        Produto produto = buscarPorId(id);

        return modelMapper.map(produto, ProdutoDTO.class);
    }

    @Transactional
    public ProdutoDTO cadastrar(NovoProdutoRequest produtoRequest) {
        var erros = validarCadastro(produtoRequest);

        if (isNotEmpty(erros)) {
            throw new RegraNegocioException(erros);
        }

        Produto produto = modelMapper.map(produtoRequest.getProduto(), Produto.class);
        TipoMovimentacao tipoMovimentacao = modelMapper.map(produtoRequest.getTipoMovimentacao(), TipoMovimentacao.class);

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setProduto(produto);
        movimentacao.setTipo(tipoMovimentacao);
        movimentacao.setQuantidade(produto.getQuantidadeTotal());

        Produto produtoSalvo = produtoRepository.save(produto);
        movimentacaoService.cadastrar(movimentacao);

        return modelMapper.map(produtoSalvo, ProdutoDTO.class);
    }

    @Transactional
    public ProdutoDTO editar(Long id, ProdutoDTO produtoDTO) {
        var erros = validarProduto(produtoDTO);

        if (isNotEmpty(erros)) {
            throw new RegraNegocioException(erros);
        }

        Produto produto = buscarPorId(id);

        produto.setCodigo(produtoDTO.getCodigo());
        produto.setDescricao(produtoDTO.getDescricao());

        return modelMapper.map(produtoRepository.save(produto), ProdutoDTO.class);
    }

    @Transactional
    public ProdutoDTO atualizarEstoque(Movimentacao movimentacao) {
        Long id = movimentacao.getProduto().getId();

        movimentacao.setTipo(tipoMovimentacaoService.buscarPorId(movimentacao.getTipo().getId()));

        if (movimentacao.getTipo().isEntrada()) {
            return adicionarEstoque(id, movimentacao.getQuantidade());
        }

        return removerEstoque(id, movimentacao.getQuantidade());
    }

    private ProdutoDTO adicionarEstoque(Long id, Integer quantidade) {
        Produto produto = buscarPorId(id);

        produto.setQuantidadeTotal(produto.getQuantidadeTotal() + quantidade);
        return modelMapper.map(produtoRepository.save(produto), ProdutoDTO.class);
    }

    private ProdutoDTO removerEstoque(Long id, Integer quantidade) {
        Produto produto = buscarPorId(id);

        var erros = validarSaldoProduto(produto, quantidade);

        if (isNotEmpty(erros)) {
            throw new RegraNegocioException(erros);
        }

        produto.setQuantidadeTotal(produto.getQuantidadeTotal() - quantidade);
        return modelMapper.map(produtoRepository.save(produto), ProdutoDTO.class);
    }

    @Transactional
    public void remover(Long id) {
        Produto existente = buscarPorId(id);

        produtoRepository.delete(existente);
    }

    private List<String> validarCadastro(NovoProdutoRequest produtoRequest) {
        var erros = validarProduto(produtoRequest.getProduto());

        var tipoMovimentacao = tipoMovimentacaoService.buscarPorId(produtoRequest.getTipoMovimentacao().getId());
        if (tipoMovimentacao.isSaida()) {
            erros.add("Não é permitido cadastrar um produto com movimentação de saída");
        }

        return erros;
    }

    private List<String> validarProduto(ProdutoDTO produto) {
        List<String> erros = new ArrayList<>();

        if (produtoRepository.existsProdutoByCodigoOrDescricao(produto.getId(), produto.getCodigo(), produto.getDescricao())) {
            erros.add("Produto já cadastrado com o mesmo código ou descrição");
        }

        if (isEmpty(produto.getId()) && (isEmpty(produto.getQuantidadeTotal()) || isMenorQueZero(produto.getQuantidadeTotal()))) {
            erros.add("A quantidade inicial deve ser maior que zero");
        }

        return erros;
    }

    private List<String> validarSaldoProduto(Produto produto, Integer quantidade) {
        List<String> erros = new ArrayList<>();

        if (quantidade > produto.getQuantidadeTotal()) {
            erros.add("Não é permitido remover mais produtos do que o disponível em estoque");
        }

        return erros;
    }

    public List<Produto> listarPorIds(List<Long> idsProdutos) {
        return produtoRepository.findProdutosByIdIn(idsProdutos);
    }
}
