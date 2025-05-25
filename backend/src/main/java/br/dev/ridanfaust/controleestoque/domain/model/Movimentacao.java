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

@Getter
@Setter
@Entity
@Table(name = "movimentacao", schema = "estoque")
@SQLDelete(sql = "UPDATE estoque.movimentacao SET ativo = FALSE WHERE id = ?")
public class Movimentacao extends Persistable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movimentacao_seq_generator")
    @SequenceGenerator(name = "movimentacao_seq_generator", sequenceName = "movimentacao_seq", allocationSize = 1, schema = "estoque")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Produto produto;

    @ManyToOne(fetch = FetchType.LAZY)
    private TipoMovimentacao tipo;

    private Integer quantidade;

}
