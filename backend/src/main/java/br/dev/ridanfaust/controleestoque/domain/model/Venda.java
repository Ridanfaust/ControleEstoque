package br.dev.ridanfaust.controleestoque.domain.model;

import br.dev.ridanfaust.controleestoque.domain.model.shared.Persistable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "venda", schema = "estoque")
@SQLDelete(sql = "UPDATE estoque.venda SET ativo = false WHERE id = ?")
public class Venda extends Persistable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "venda_seq_generator")
    @SequenceGenerator(name = "venda_seq_generator", sequenceName = "venda_seq", allocationSize = 1, schema = "estoque")
    private Long id;

    private BigDecimal valorTotal;

    @ManyToOne
    private SituacaoVenda situacao;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<VendaProduto> produtos;

}
