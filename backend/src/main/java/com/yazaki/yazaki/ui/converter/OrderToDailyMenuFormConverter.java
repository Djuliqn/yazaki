package com.yazaki.yazaki.ui.converter;

import com.yazaki.yazaki.domain.model.Order;
import com.yazaki.yazaki.ui.form.DailyMenuForm;
import org.springframework.stereotype.Component;

import javax.persistence.Converter;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Component
public class OrderToDailyMenuFormConverter {

    public DailyMenuForm toDailyMenuForm(Order order) {
        DailyMenuForm dailyMenuForm = new DailyMenuForm();

        dailyMenuForm.setYear(order.getDate().getYear());
        dailyMenuForm.setMonth(order.getDate().getMonthValue());
        dailyMenuForm.setDay(order.getDate().getDayOfMonth());
        List<Long> dishIds = newArrayList();
        order.getDishCounters().forEach(dishCounter -> dishIds.add(dishCounter.getDish().getId()));
        dailyMenuForm.setDishIds(dishIds);

        return dailyMenuForm;
    }

}
