package br.dev.ridanfaust.controleestoque.domain.repository;

import br.dev.ridanfaust.controleestoque.domain.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("SELECT p "
            + "FROM Produto p "
            + "WHERE p.ativo = TRUE "
            + "AND (:descricao IS NULL OR LOWER(p.descricao) LIKE %:descricao%) "
            + "ORDER BY p.descricao")
    Page<Produto> findAllPaginadoByFiltros(@Param("descricao") String descricao, Pageable pageable);

    @Query("SELECT COUNT(p) > 0 "
            + "FROM Produto p "
            + "WHERE p.ativo = TRUE "
            + "AND (:id IS NULL OR p.id <> :id) "
            + "AND (p.codigo = :codigo OR p.descricao = :descricao) ")
    boolean existsProdutoByCodigoOrDescricao(@Param("id") Long id,
                                             @Param("codigo") String codigo,
                                             @Param("descricao") String descricao);

    @Query("SELECT p "
            + "FROM Produto p "
            + "WHERE p.ativo = TRUE "
            + "AND p.id IN :idsProdutos "
            + "ORDER BY p.descricao")
    List<Produto> findProdutosByIdIn(List<Long> idsProdutos);

    @Query("SELECT p "
            + "FROM Produto p "
            + "JOIN FETCH p.tipo "
            + "WHERE p.ativo = TRUE "
            + "AND p.id = :id")
    Optional<Produto> findCompletoById(Long id);
}
