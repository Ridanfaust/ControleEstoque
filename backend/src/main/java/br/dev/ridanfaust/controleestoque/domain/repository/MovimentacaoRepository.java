package br.dev.ridanfaust.controleestoque.domain.repository;

import br.dev.ridanfaust.controleestoque.domain.model.Movimentacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

    @Query("SELECT m "
            + "FROM Movimentacao m "
            + "JOIN FETCH m.produto p "
            + "JOIN FETCH m.tipo t "
            + "WHERE m.ativo = TRUE "
            + "AND (:produtoId IS NULL OR p.id = :produtoId) "
            + "AND (:tipoMovimentacaoId IS NULL OR t.id = :tipoMovimentacaoId) "
            + "ORDER BY m.dataCadastro DESC")
    Page<Movimentacao> findAllPaginadoByFiltros(@Param("produtoId") Long produtoId, @Param("tipoMovimentacaoId") Long tipoMovimentacaoId, Pageable pageable);

}
