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
@Table(name = "produto", schema = "estoque")
@SQLDelete(sql = "UPDATE estoque.produto SET ativo = FALSE WHERE id = ?")
public class Produto extends Persistable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "produto_seq_generator")
    @SequenceGenerator(name = "produto_seq_generator", sequenceName = "produto_seq", allocationSize = 1, schema = "estoque")
    private Long id;

    private String codigo;

    private String descricao;

    private BigDecimal valorFornecedor;

    private BigDecimal valorVenda;

    private Integer quantidadeTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    private TipoProduto tipo;

}
