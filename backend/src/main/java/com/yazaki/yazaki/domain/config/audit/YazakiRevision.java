package com.yazaki.yazaki.domain.config.audit;

import com.yazaki.yazaki.ui.converter.LocalDatePersistanceConverter;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@RevisionEntity( YazakiRevisionListener.class )
public class YazakiRevision extends DefaultRevisionEntity {

    private static final long serialVersionUID = -1775820601594652032L;

    @Column(name = "amended_by", nullable = false)
    private String username;

    @Convert(converter = LocalDatePersistanceConverter.class)
    @Column(name = "amended_at", nullable = false)
    private LocalDate LocalDate;

    public String getUsername() {
        return username;
    }

    public void setUsername( String username ) {
        this.username = username;
    }

    public LocalDate getLocalDate() {
        return LocalDate;
    }

    public void setLocalDate(LocalDate localDate) {
        LocalDate = localDate;
    }
}
