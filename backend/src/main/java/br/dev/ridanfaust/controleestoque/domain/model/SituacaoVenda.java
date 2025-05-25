package br.dev.ridanfaust.controleestoque.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "situacao_venda", schema = "estoque")
public class SituacaoVenda {

    public static final SituacaoVenda CONCLUIDA = new SituacaoVenda(1L, "CONCLUÍDA");
    public static final SituacaoVenda CANCELADA = new SituacaoVenda(2L, "CANCELADA");

    @Id
    private Long id;

    private String descricao;

}
