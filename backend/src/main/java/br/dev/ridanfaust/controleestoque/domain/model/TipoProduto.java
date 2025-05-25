package br.dev.ridanfaust.controleestoque.domain.model;

import br.dev.ridanfaust.controleestoque.domain.model.shared.Persistable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

@Getter
@Setter
@Entity
@Table(name = "tipo_produto", schema = "estoque")
@SQLDelete(sql = "UPDATE estoque.tipo_produto SET ativo = FALSE WHERE id = ?")
public class TipoProduto extends Persistable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo_produto_seq_generator")
    @SequenceGenerator(name = "tipo_produto_seq_generator", sequenceName = "tipo_produto_seq", allocationSize = 1, schema = "estoque")
    private Long id;

    String descricao;

}
