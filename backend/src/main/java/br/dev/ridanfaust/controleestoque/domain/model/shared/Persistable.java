package br.dev.ridanfaust.controleestoque.domain.model.shared;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public class Persistable {

    private boolean ativo;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAlteracao;

    @PrePersist
    public void prePersist() {
        this.ativo = true;
    }
}
