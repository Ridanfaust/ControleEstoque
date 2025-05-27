package br.dev.ridanfaust.controleestoque.domain.model;

import br.dev.ridanfaust.controleestoque.domain.enums.NaturezaMovimentacaoEnum;
import br.dev.ridanfaust.controleestoque.domain.model.shared.Persistable;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "tipo_movimentacao", schema = "estoque")
@SQLDelete(sql = "UPDATE estoque.tipo_movimentacao SET ativo = FALSE WHERE id = ?")
public class TipoMovimentacao extends Persistable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo_movimentacao_seq_generator")
    @SequenceGenerator(name = "tipo_movimentacao_seq_generator", sequenceName = "tipo_movimentacao_seq", allocationSize = 1, schema = "estoque")
    private Long id;

    private String descricao;

    private boolean venda;

    @Enumerated(value = EnumType.STRING)
    private NaturezaMovimentacaoEnum naturezaMovimentacao;

    public boolean isEntrada() {
        return NaturezaMovimentacaoEnum.ENTRADA.equals(naturezaMovimentacao);
    }

    public boolean isSaida() {
        return NaturezaMovimentacaoEnum.SAIDA.equals(naturezaMovimentacao);
    }

}
