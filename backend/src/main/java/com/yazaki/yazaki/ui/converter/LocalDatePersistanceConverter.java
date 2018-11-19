package com.yazaki.yazaki.ui.converter;

import javax.persistence.AttributeConverter;
import java.sql.Date;
import java.time.LocalDate;

public class LocalDatePersistanceConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(final LocalDate entityValue) {
         return Date.valueOf(entityValue);
    }

    @Override
    public LocalDate convertToEntityAttribute(final Date dataBaseValue) {
        return dataBaseValue.toLocalDate();
    }
}
