package com.yazaki.yazaki.domain.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ROLES")
@Data
public class  Role implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @NotNull(message = "Ролята неможе да е празна.")
    @Column(name = "AUTHORITY", unique = true)
    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }
}
