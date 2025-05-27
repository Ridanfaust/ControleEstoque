package br.dev.ridanfaust.controleestoque.domain.model;

import br.dev.ridanfaust.controleestoque.domain.model.shared.Persistable;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "venda_produto", schema = "estoque")
@SQLDelete(sql = "UPDATE estoque.venda_produto SET ativo = FALSE WHERE id = ?")
public class VendaProduto extends Persistable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "venda_produto_seq_generator")
    @SequenceGenerator(name = "venda_produto_seq_generator", sequenceName = "venda_produto_seq", allocationSize = 1, schema = "estoque")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Produto produto;

    @ManyToOne(fetch = FetchType.LAZY)
    private Venda venda;

    private BigDecimal valorVenda;

    private Integer quantidade;

}
