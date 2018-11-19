package com.yazaki.yazaki.ui.form;

import com.yazaki.yazaki.domain.model.Dish;
import com.yazaki.yazaki.domain.model.User;
import com.yazaki.yazaki.ui.converter.LocalDatePersistanceConverter;
import org.springframework.data.rest.core.config.Projection;

import javax.persistence.Convert;
import java.time.LocalDate;

@Projection(name = "dishAudit", types = {Dish.class, User.class})
public interface DishAudit {

    String getDescription();

    @Convert(converter = LocalDatePersistanceConverter.class)
    LocalDate getAmended_at();

    String getUsername();

    Byte getRevType();

    String getName();
}
