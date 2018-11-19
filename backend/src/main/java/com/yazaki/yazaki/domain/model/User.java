package com.yazaki.yazaki.domain.model;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

@Entity
@Table(name = "USERS")
public class User implements UserDetails, Serializable {

    private static final long serialVersionUID = -5145891056124252270L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @NotNull(message = "Моля попълнете името на потребителя.")
    @Column(name = "USERNAME", unique = true)
    @Size(min = 4, max = 20, message = "Потебителското име трябва да е между 4 и 20 символа.")
    @Audited
    private String username;

    @NotNull(message = "Моля попълнете парола.")
    @Column(name = "PASSWORD")
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AUTHORITY_ID")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Authority authority;

    @NotNull
    @Column(name = "IS_ACCOUNT_NON_EXPIRED")
    private boolean isAccountNonExpired;

    @NotNull
    @Column(name = "IS_ACCOUNT_NON_LOCKED")
    private boolean isAccountNonLocked;

    @NotNull
    @Column(name = "IS_CREDENTIALS_NON_EXPIRED")
    private boolean isCredentialsNonExpired;

    @NotNull
    @Column(name = "IS_ENABLED")
    private boolean isEnabled;

    public User() {
        isAccountNonExpired = true;
        isAccountNonLocked = true;
        isCredentialsNonExpired = true;
        isEnabled = true;
    }

    @Override
    public Set<Authority> getAuthorities() {
        Set<Authority> authorities = newHashSet();
        authorities.add(authority);

        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(final Authority authority) {
        this.authority = authority;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setAccountNonExpired(final boolean isAccountNonExpired) {
        this.isAccountNonExpired = isAccountNonExpired;
    }

    public void setAccountNonLocked(final boolean isAccountNonLocked) {
        this.isAccountNonLocked = isAccountNonLocked;
    }

    public void setCredentialsNonExpired(final boolean isCredentialsNonExpired) {
        this.isCredentialsNonExpired = isCredentialsNonExpired;
    }

    public void setEnabled(final boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
}
