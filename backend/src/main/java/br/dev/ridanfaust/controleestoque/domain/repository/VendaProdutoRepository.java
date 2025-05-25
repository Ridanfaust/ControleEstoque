package br.dev.ridanfaust.controleestoque.domain.repository;

import br.dev.ridanfaust.controleestoque.domain.model.VendaProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaProdutoRepository extends JpaRepository<VendaProduto, Long> {
}
