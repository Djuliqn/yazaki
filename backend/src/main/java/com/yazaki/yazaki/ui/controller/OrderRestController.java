package com.yazaki.yazaki.ui.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import com.google.common.collect.Lists;
import com.yazaki.yazaki.domain.exception.YazakiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.yazaki.yazaki.domain.core.ErrorBuilder;
import com.yazaki.yazaki.domain.exception.RecordNotFoundException;
import com.yazaki.yazaki.domain.model.Dish;
import com.yazaki.yazaki.domain.model.DishCounter;
import com.yazaki.yazaki.domain.model.Order;
import com.yazaki.yazaki.domain.service.dish.DishService;
import com.yazaki.yazaki.domain.service.order.OrderService;
import com.yazaki.yazaki.ui.form.DailyMenuForm;
import com.yazaki.yazaki.ui.form.StatisticForm;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class OrderRestController {

    private final DishService dishService;
    private final OrderService orderService;

    @Autowired
    public OrderRestController(final DishService dishService, final OrderService orderService) {
        this.dishService = dishService;
        this.orderService = orderService;
    }



    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    @GetMapping("/daily-menu")
    public ResponseEntity<List<Dish>> getAllDishes() {
        log.info("Order rest controller find all dishes");
        List<Dish> dishes = dishService.getAllDishes();

        return ResponseEntity.ok(dishes);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    @PostMapping("/daily-menu/save")
    public ResponseEntity<Void> saveDailyMenu(@RequestBody @Valid final DailyMenuForm dailyMenuForm, final BindingResult result) {
        if (result.hasErrors()) {
        	log.error("Errors in DailyMenuForm validations.");
            throw new YazakiException(ErrorBuilder.buildErrorMessage(result));
        }

        log.info("Execute operation for saving the daily menu");
        final Order order = convertToOrderWithRelationShips(dailyMenuForm);
        orderService.saveOrder(order);
        log.info("Successfully saved order.");

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    @GetMapping("/statistics")
    public ResponseEntity<Order> getStatisticsFromSelectedDate(@Valid final StatisticForm statisticForm, final BindingResult result) {

        if (result.hasErrors()) {
            log.error("Errors in statistic form.");
            throw new YazakiException(ErrorBuilder.buildErrorMessage(result));
        }

        final LocalDate date = LocalDate.of(statisticForm.getYear(), statisticForm.getMonth(), statisticForm.getDay());
        final Order order = orderService.findOrderByDate(date);

        if (Objects.isNull(order)) {
            throw new RecordNotFoundException("Няма намерени записи за тази дата.");
        }

        return ResponseEntity.ok(order);
    }

    private Order convertToOrderWithRelationShips(final DailyMenuForm dailyMenuForm) {
        final Order order = new Order();
        List<DishCounter> dishCounters = new ArrayList<>();

        order.setDate(LocalDate.of(dailyMenuForm.getYear(), dailyMenuForm.getMonth(), dailyMenuForm.getDay()));

        dailyMenuForm.getDishIds().forEach(id -> {
            final Dish dish = dishService.findDishById(id);
            final DishCounter dishCounter = new DishCounter();

            dishCounter.setDish(dish);
            dishCounter.setCounter(0L);
            dishCounter.setOrder(order);

            dishCounters.add(dishCounter);
        });

        order.setDishCounters(dishCounters);

        return order;
    }
}
