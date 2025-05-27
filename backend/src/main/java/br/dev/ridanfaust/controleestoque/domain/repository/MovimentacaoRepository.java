package br.dev.ridanfaust.controleestoque.domain.repository;

import br.dev.ridanfaust.controleestoque.domain.model.Movimentacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

    @Query("SELECT m "
            + "FROM Movimentacao m "
            + "JOIN FETCH m.produto p "
            + "JOIN FETCH m.tipo t "
            + "WHERE m.ativo = TRUE "
            + "AND (:produto IS NULL OR LOWER(p.descricao) LIKE %:produto%) "
            + "AND (:tipoMovimentacao IS NULL OR LOWER(t.descricao) LIKE %:tipoMovimentacao%) "
            + "AND (:natureza IS NULL OR LOWER(t.naturezaMovimentacao) = :natureza) "
            + "AND (CAST(m.dataCadastro as date) >= CAST(:dataInicio as date)) "
            + "AND (CAST(m.dataCadastro as date) <= CAST(:dataFim as date)) "
            + "ORDER BY m.dataCadastro DESC")
    Page<Movimentacao> findAllPaginadoByFiltros(@Param("produto") String produto,
                                                @Param("tipoMovimentacao") String tipoMovimentacao,
                                                @Param("natureza") String natureza,
                                                @Param("dataInicio") LocalDate dataInicio,
                                                @Param("dataFim") LocalDate dataFim,
                                                Pageable pageable);

}
