package com.yazaki.yazaki.domain.model;

import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USERS")
@Data
public class User implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @NotNull(message = "Моля попълнете името на потребителя.")
    @Column(name = "USERNAME", unique = true)
    @Size(min = 4, max = 20, message = "Потебителското име трябва да е между 4 и 20 символа.")
    private String username;

    @NotNull(message = "Моля попълнете парола.")
    @Column(name = "PASSWORD")
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ROLE_ID")
    private Role role;

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
    public Set<Role> getAuthorities() {
        Set<Role> authorities = new HashSet<>();
        authorities.add(role);

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

    public Role getRole() {
        return role;
    }

    public void setRole(final Role role) {
        this.role = role;
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
