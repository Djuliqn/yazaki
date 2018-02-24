package com.yazaki.yazaki.ui.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.yazaki.yazaki.domain.model.Dish;
import com.yazaki.yazaki.domain.service.dish.DishService;

@RestController
@RequestMapping("/dish")
public class DishRestController {

    private static final Logger logger = LoggerFactory.getLogger(DishRestController.class);

    private final DishService dishService;

    @Autowired
    public DishRestController(final DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Dish>> getAllDishes() {
        logger.info("Rest controller find all dishes");
        List<Dish> dishes = dishService.getAllDishes();

        return ResponseEntity.status(HttpStatus.OK).body(dishes);
    }

    @PostMapping("/save")
    public ResponseEntity<Dish> saveDish(@RequestBody @Valid final Dish dish) {
        logger.info("Rest controller save dish started");
        final Dish savedDish = dishService.saveDish(dish);
        logger.info("Rest controller save dish operation completed");

        return ResponseEntity.status(HttpStatus.CREATED).body(savedDish);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDish(@PathVariable final Long id) {
        logger.info("Proccesing for delete dish start...");
        dishService.deleteDishById(id);
        logger.info("Dish deleted sucessfully.");

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Dish> updateDish(@RequestBody @Valid final Dish dish) {
        logger.info("Rest controller update dish started");
        final Dish updatedDish = dishService.updateDish(dish);
        logger.info("Rest controller update dish operation completed");

        return ResponseEntity.status(HttpStatus.OK).body(updatedDish);
    }

}
