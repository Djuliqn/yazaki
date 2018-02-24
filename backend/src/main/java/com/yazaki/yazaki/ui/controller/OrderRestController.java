package com.yazaki.yazaki.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.yazaki.yazaki.domain.model.DishCounter;
import com.yazaki.yazaki.domain.model.Order;
import com.yazaki.yazaki.domain.service.order.OrderService;
import com.yazaki.yazaki.ui.form.DailyMenuForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yazaki.yazaki.domain.model.Dish;
import com.yazaki.yazaki.domain.service.dish.DishService;

import javax.validation.Valid;

@RestController
@RequestMapping("/daily-menu")
public class OrderRestController {

    private static final Logger logger = LoggerFactory.getLogger(OrderRestController.class);

    private final DishService dishService;
    private final OrderService orderService;

    @Autowired
    public OrderRestController(final DishService dishService, final OrderService orderService) {
        this.dishService = dishService;
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<Dish>> getAllDishes() {
        logger.info("Order rest controller find all dishes");
        List<Dish> dishes = dishService.getAllDishes();

        return ResponseEntity.status(HttpStatus.OK).body(dishes);
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveDailyMenu(@RequestBody @Valid final DailyMenuForm dailyMenuForm) {
        logger.info("Execute operation for saving the daily menu");
        final Order order = convertToOrderWithRelationShips(dailyMenuForm);
        orderService.saveOrder(order);
        logger.info("Successfuly saved order.");
        
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private Order convertToOrderWithRelationShips(final DailyMenuForm dailyMenuForm) {
        final Order order = new Order();
        List<DishCounter> dishCounters = new ArrayList<>();

        order.setDate(dailyMenuForm.getDate());
        
        for(Long id : dailyMenuForm.getDishIds()) {
			final Dish dish = dishService.findDishById(id);
			final DishCounter dishCounter = new DishCounter();
			
			dishCounter.setDish(dish);
			dishCounter.setCounter(0L);
			dishCounter.setOrder(order);
			
			dishCounters.add(dishCounter);
        }
        
        order.setDishCounters(dishCounters);
		
		return order;
    }
}
