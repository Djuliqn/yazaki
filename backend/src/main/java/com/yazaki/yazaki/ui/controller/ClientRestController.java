package com.yazaki.yazaki.ui.controller;

import java.util.Objects;

import com.yazaki.yazaki.domain.exception.YazakiException;
import com.yazaki.yazaki.domain.model.Order;
import com.yazaki.yazaki.domain.model.User;
import com.yazaki.yazaki.domain.service.order.OrderService;
import com.yazaki.yazaki.domain.service.user.UserService;
import com.yazaki.yazaki.ui.form.UserExitForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.yazaki.yazaki.domain.model.DishCounter;
import com.yazaki.yazaki.domain.service.dishcounter.DishCounterService;

import javax.validation.Valid;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@RestController
public class ClientRestController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final DishCounterService dishCounterService;
    private final OrderService orderService;
    private final UserService userService;


    @Autowired
    public ClientRestController(final BCryptPasswordEncoder bCryptPasswordEncoder,
                                final DishCounterService dishCounterService,
                                final OrderService orderService,
                                final UserService userService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.dishCounterService = dishCounterService;
        this.orderService = orderService;
        this.userService = userService;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @PutMapping("/update/dish/counter")
    public ResponseEntity<Void> updateDishCounter(@RequestBody final Long id) {
        if(Objects.isNull(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        final DishCounter dishCounter = dishCounterService.findDishCounterById(id);
        dishCounter.setCounter(dishCounter.getCounter() + 1);

        dishCounterService.update(dishCounter);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @GetMapping("/last-added-order")
    public ResponseEntity<Order> getDailyMenu() {
        final Order order = orderService.getLastAddedOrder();

        return ResponseEntity.ok(order);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/check-password{username}{password}")
    public ResponseEntity<Boolean> checkPasswords(@Valid final UserExitForm userExitForm) {
        final User user = (User) userService.loadUserByUsername(userExitForm.getUsername());

        if(!bCryptPasswordEncoder.matches(userExitForm.getPassword(), user.getPassword())) {
            throw new YazakiException("Грешна парола!");
        }

        return ResponseEntity.ok(TRUE);
    }
}

