package com.yazaki.yazaki.ui.form;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import lombok.Data;

@Data
public class DailyMenuForm {

    @NotEmpty(message = "{NotEmpty.dailyMenuForm.dishIds}")
    private List<Long> dishIds;

	@NotNull(message = "{NotNull.dailyMenuForm.year}")
	private Short year;
	
	@NotNull(message = "{NotNull.dailyMenuForm.month}")
	@Range(min = 1, max = 12, message = "{Range.dailyMenuForm.month}")
	private Short month;
	
	@NotNull(message = "{NotNull.dailyMenuForm.day}")
	@Range(min = 1, max = 31, message = "{Range.dailyMenuForm.day}")
	private Short day;
}
