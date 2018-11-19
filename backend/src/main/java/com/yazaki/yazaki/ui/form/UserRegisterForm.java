package com.yazaki.yazaki.ui.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.yazaki.yazaki.domain.annotation.password.PasswordMatching;

import lombok.Data;

@Data
@PasswordMatching(password = "password", confirmPassword = "confirmPassword")
public class UserRegisterForm {

    private Long id;

    @NotNull(message = "{NotNull.userRegisterForm.username}")
    @Size(min = 4, max = 20, message = "{Size.userRegisterForm.username}")
    private String username;

    @NotNull(message = "{NotNull.userRegisterForm.password}")
    @Size(min = 4, max = 20, message = "{Size.userRegisterForm.password}")
    private String password;

    @NotNull(message = "{NotNull.userRegisterForm.confirmPassword}")
    @Size(min = 4, max = 20, message = "{Size.userRegisterForm.confirmPassword}")
    private String confirmPassword;
}
