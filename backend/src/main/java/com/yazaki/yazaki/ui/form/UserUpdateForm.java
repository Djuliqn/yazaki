package com.yazaki.yazaki.ui.form;

import org.hibernate.envers.Audited;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserUpdateForm {

    private Long id;

    @NotNull(message = "Моля попълнете името на потребителя.")
    @Size(min = 4, max = 20, message = "Потебителското име трябва да е между 4 и 20 символа.")
    @Audited
    private String username;

    @NotNull(message = "Ролята неможе да е празна.")
    private String role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NotNull String username) {
        this.username = username;
    }

    @NotNull
    public String getRole() {
        return role;
    }

    public void setRole(@NotNull String role) {
        this.role = role;
    }
}
