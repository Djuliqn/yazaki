package com.yazaki.yazaki.ui.converter;

import com.yazaki.yazaki.ui.form.UserAudit;
import com.yazaki.yazaki.ui.form.UserAuditForm;
import org.springframework.stereotype.Component;

@Component
public class UserAuditToUserAuditFormConverter {

    public UserAuditForm toDishAuditForm(UserAudit userAudit) {
        UserAuditForm userAuditForm = new UserAuditForm();

        userAuditForm.setAmended_at(userAudit.getAmended_at());
        userAuditForm.setAmended_by(userAudit.getAmended_by());
        userAuditForm.setUsername(userAudit.getUsername());
        userAuditForm.setOperation(ConverterHelper.getOperation(userAudit.getRevType()));
        userAuditForm.setRole(ConverterHelper.getRole(userAudit.getAuthorityId()));

        return userAuditForm;
    }
}
