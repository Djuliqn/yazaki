package com.yazaki.yazaki.ui.converter;

import com.yazaki.yazaki.ui.form.DishAudit;
import com.yazaki.yazaki.ui.form.DishAuditForm;
import org.springframework.stereotype.Component;

@Component
public class DishAuditToDishAuditFormConverter {

    public DishAuditForm toDishAuditForm(DishAudit dishAudit) {
        DishAuditForm dishAuditForm = new DishAuditForm();

        dishAuditForm.setAmended_at(dishAudit.getAmended_at());
        dishAuditForm.setDescription(dishAudit.getDescription());
        dishAuditForm.setName(dishAudit.getName());
        dishAuditForm.setOperation(ConverterHelper.getOperation(dishAudit.getRevType()));
        dishAuditForm.setUsername(dishAudit.getUsername());

        return dishAuditForm;
    }
}
