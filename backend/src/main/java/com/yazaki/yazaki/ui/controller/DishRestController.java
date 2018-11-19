package com.yazaki.yazaki.ui.controller;

import com.yazaki.yazaki.domain.core.ErrorBuilder;
import com.yazaki.yazaki.domain.exception.YazakiException;
import com.yazaki.yazaki.domain.model.Dish;
import com.yazaki.yazaki.domain.service.dish.DishService;
import com.yazaki.yazaki.ui.converter.DishAuditToDishAuditFormConverter;
import com.yazaki.yazaki.ui.form.DishAudit;
import com.yazaki.yazaki.ui.form.DishAuditForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newArrayListWithCapacity;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishRestController {

    private final DishService dishService;
    private final DishAuditToDishAuditFormConverter converter;

    @Autowired
    public DishRestController(final DishService dishService, final DishAuditToDishAuditFormConverter converter) {
        this.dishService = dishService;
        this.converter = converter;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    @GetMapping("/all")
    public ResponseEntity<List<Dish>> getAllDishes() {
        log.info("Rest controller find all dishes");
        List<Dish> dishes = dishService.getAllDishes();

        return ResponseEntity.ok(dishes);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    @PostMapping("/save")
    public ResponseEntity<Dish> saveDish(@RequestBody @Valid final Dish dish, final BindingResult result) {
        if (result.hasErrors()) {
            log.error("Error in save dish");
            throw new YazakiException(ErrorBuilder.buildErrorMessage(result));
        }

        log.info("Rest controller save dish started");
        final Dish savedDish = dishService.saveDish(dish);
        log.info("Rest controller save dish operation completed");

        return ResponseEntity.status(HttpStatus.CREATED).body(savedDish);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDish(@PathVariable final Long id) {
        log.info("Proccesing for delete dish start...");
        dishService.deleteDishById(id);
        log.info("Dish deleted sucessfully.");

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    @PutMapping("/update")
    public ResponseEntity<Dish> updateDish(@RequestBody @Valid final Dish dish, final BindingResult result) {
        if (result.hasErrors()) {
            log.error("Error in update dish.");
            throw new YazakiException(ErrorBuilder.buildErrorMessage(result));
        }

        log.info("Rest controller update dish started");
        final Dish updatedDish = dishService.updateDish(dish);
        log.info("Rest controller update dish operation completed");

        return ResponseEntity.status(HttpStatus.OK).body(updatedDish);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/load-dish-audit")
    public ResponseEntity<List<DishAuditForm>> getDishAuditRecords() {
        List<DishAudit> allDishAudits = dishService.findAllDishAudits();

        List<DishAuditForm> dishAuditForms = allDishAudits.stream()
                                                          .map(converter::toDishAuditForm)
                                                          .collect(Collectors.toList());

        return ResponseEntity.ok(dishAuditForms);
    }
}
