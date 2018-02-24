package com.yazaki.yazaki.ui.form;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class DailyMenuForm {

    @NotEmpty(message = "daily.menu.empty.or.null")
    private List<Long> dishIds;

    @NotNull(message = "daily.menu.date.not.null")
    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

}
