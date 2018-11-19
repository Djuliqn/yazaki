package com.yazaki.yazaki.ui.form;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import lombok.Data;

@Data
public class DailyMenuForm {

	private Long orderId;

    @NotEmpty(message = "{NotEmpty.dailyMenuForm.dishIds}")
    private List<Long> dishIds;

	@NotNull(message = "{NotNull.dailyMenuForm.year}")
	private Integer year;
	
	@NotNull(message = "{NotNull.dailyMenuForm.month}")
	@Range(min = 1, max = 12, message = "{Range.dailyMenuForm.month}")
	private Integer month;
	
	@NotNull(message = "{NotNull.dailyMenuForm.day}")
	@Range(min = 1, max = 31, message = "{Range.dailyMenuForm.day}")
	private Integer day;

}
