package com.yazaki.yazaki.ui.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class UserExitForm {

    @NotBlank
    private String username;

    @NotBlank(message = "Паролата неможе да е празна.")
    private String password;

}
