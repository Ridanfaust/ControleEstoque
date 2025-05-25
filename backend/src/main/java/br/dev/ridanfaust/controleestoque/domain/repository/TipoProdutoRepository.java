package br.dev.ridanfaust.controleestoque.domain.repository;

import br.dev.ridanfaust.controleestoque.domain.model.TipoProduto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoProdutoRepository extends JpaRepository<TipoProduto, Long> {

    @Query("SELECT tp "
            + "FROM TipoProduto tp "
            + "WHERE tp.ativo = TRUE "
            + "AND (:descricao IS NULL OR LOWER(tp.descricao) LIKE %:descricao%) "
            + "ORDER BY tp.descricao")
    Page<TipoProduto> findAllPaginadoByFiltros(@Param("descricao") String descricao, Pageable pageable);

    @Query("SELECT COUNT(tp) > 0 "
            + "FROM TipoProduto tp "
            + "WHERE tp.ativo = TRUE "
            + "AND (:id IS NULL OR tp.id <> :id) "
            + "AND LOWER(tp.descricao) = LOWER(:descricao)")
    boolean existsByDescricao(@Param("id") Long id,
                              @Param("descricao") String descricao);
}
