package br.dev.ridanfaust.controleestoque.domain.repository;

import br.dev.ridanfaust.controleestoque.domain.model.TipoMovimentacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoMovimentacaoRepository extends JpaRepository<TipoMovimentacao, Long> {

    @Query("SELECT tm "
            + "FROM TipoMovimentacao tm "
            + "WHERE tm.ativo = TRUE "
            + "AND (:descricao IS NULL OR LOWER(tm.descricao) LIKE %:descricao%) "
            + "AND (:natureza IS NULL OR LOWER(tm.naturezaMovimentacao) = :natureza) "
            + "AND (:venda IS NULL OR tm.venda = :venda) "
            + "ORDER BY tm.descricao")
    Page<TipoMovimentacao> findAllPaginadoByFiltros(@Param("descricao") String descricao,
                                                    @Param("natureza") String natureza,
                                                    @Param("venda") Boolean venda,
                                                    Pageable pageable);

    @Query("SELECT COUNT(tm) > 0 "
            + "FROM TipoMovimentacao tm "
            + "WHERE tm.ativo = TRUE "
            + "AND (:id IS NULL OR tm.id <> :id) "
            + "AND LOWER(tm.descricao) = :descricao")
    boolean existsByDescricao(@Param("id") Long id,
                              @Param("descricao") String descricao);

}
