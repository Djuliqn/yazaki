package com.yazaki.yazaki.domain.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "AUTHORITIES")
@Data
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @NotNull(message = "Ролята неможе да е празна.")
    @Column(name = "AUTHORITY", unique = true)
    private String name;

    public Authority() {

    }

    public Authority(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
