package br.dev.ridanfaust.controleestoque.domain.repository;

import br.dev.ridanfaust.controleestoque.domain.model.Venda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

    @Query("SELECT v "
            + "FROM Venda v "
            + "WHERE v.ativo = TRUE "
            + "AND (:descricao IS NULL OR LOWER(v.descricao) LIKE %:descricao%) "
            + "ORDER BY v.descricao")
    Page<Venda> findAllPaginadoByFiltros(@Param("descricao") String descricao, Pageable pageable);

    @Query("SELECT v "
            + "FROM Venda v "
            + "JOIN FETCH v.produtos "
            + "JOIN FETCH v.situacao "
            + "WHERE v.ativo = TRUE "
            + "AND v.id = :id")
    Optional<Venda> findCompletoById(@Param("id") Long id);

}
