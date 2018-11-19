package com.yazaki.yazaki.ui.controller;

import com.yazaki.yazaki.domain.config.authentication.JwtTokenUtil;
import com.yazaki.yazaki.domain.core.ErrorBuilder;
import com.yazaki.yazaki.domain.exception.RecordNotFoundException;
import com.yazaki.yazaki.domain.exception.UserException;
import com.yazaki.yazaki.domain.exception.YazakiException;
import com.yazaki.yazaki.domain.model.*;
import com.yazaki.yazaki.domain.service.authority.AuthorityService;
import com.yazaki.yazaki.domain.service.dish.DishService;
import com.yazaki.yazaki.domain.service.dishcounter.DishCounterService;
import com.yazaki.yazaki.domain.service.order.OrderService;
import com.yazaki.yazaki.domain.service.user.UserService;
import com.yazaki.yazaki.ui.converter.UserAuditToUserAuditFormConverter;
import com.yazaki.yazaki.ui.form.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newArrayListWithCapacity;

@Slf4j
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final ModelMapper mapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final OrderService orderService;
    private final DishService dishService;
    private final AuthorityService authorityService;
    private final DishCounterService dishCounterService;
    private final UserAuditToUserAuditFormConverter converter;

    @Autowired
    public UserController(final UserService userService,
                          final ModelMapper mapper,
                          final BCryptPasswordEncoder bCryptPasswordEncoder,
                          final AuthenticationManager authenticationManager,
                          final JwtTokenUtil jwtTokenUtil,
                          final OrderService orderService,
                          final DishService dishService,
                          final AuthorityService authorityService,
                          final DishCounterService dishCounterService,
                          final UserAuditToUserAuditFormConverter converter) {
        this.userService = userService;
        this.mapper = mapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.orderService = orderService;
        this.dishService = dishService;
        this.authorityService = authorityService;
        this.dishCounterService = dishCounterService;
        this.converter = converter;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("Rest controller find all users");
        List<User> users = userService.getAllUsers();
        List<User> usersWithoutMainAdmin = newArrayList();
        for (User user : users) {
            if(user.getUsername().equals("Джулиян")) {
                continue;
            }

            usersWithoutMainAdmin.add(user);
        }

        return ResponseEntity.ok(usersWithoutMainAdmin);
    }

    @CrossOrigin("http://localhost:4200")
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid final UserRegisterForm userRegisterForm, final BindingResult result) {

        if(result.hasErrors()) {
        	log.error("Errors in register method validations.");
        	throw new UserException(ErrorBuilder.buildErrorMessage(result));
        }

        userRegisterForm.setPassword(bCryptPasswordEncoder.encode(userRegisterForm.getPassword()));
        final User user = mapper.map(userRegisterForm, User.class);
        userService.saveUser(user);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable final Long id) {
        log.info("Proccessing delete for user start...");
        userService.deleteUserById(id);
        log.info("User deleted successfully.");

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody @Valid final UserUpdateForm userUpdateForm, final BindingResult result) {
        if(result.hasErrors()) {
            log.error("Error in update dish.");
            throw new YazakiException(ErrorBuilder.buildErrorMessage(result));
        }

        log.info("Rest controller update user started");
        User user = userService.findById(userUpdateForm.getId());
        Authority authorityByName = authorityService.findAuthorityByName(userUpdateForm.getRole());
        user.setUsername(userUpdateForm.getUsername());
        user.setAuthority(authorityByName);

        final User updatedUser = userService.updateUser(user);
        log.info("Rest controller update user operation completed");

        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody @Valid final User user, final BindingResult result) {
    	if(result.hasErrors()) {
    		log.error("Erros in login method validations.");
    		throw new UserException(ErrorBuilder.buildErrorMessage(result));
    	}
    	
    	final User foundUser = (User) userService.loadUserByUsername(user.getUsername());
    	
    	if(!bCryptPasswordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
    		throw new UserException("Грешна парола.");
    	}
    	
    	return ResponseEntity.ok(foundUser);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/update-daily")
    public ResponseEntity<Void> updateDailyMenu(@RequestBody @Valid DailyMenuForm dailyMenuForm, final BindingResult result) {
        log.info("updateDailyMenu()");
        if (result.hasErrors()) {
            log.error("Errors in daily menu form.");
            throw new YazakiException(ErrorBuilder.buildErrorMessage(result));
        }

        LocalDate dailyMenuDate = LocalDate.of(dailyMenuForm.getYear(), dailyMenuForm.getMonth(), dailyMenuForm.getDay());
        Order order = orderService.findOrderByDate(dailyMenuDate);
        List<Dish> dishes = dishService.findDishesByIds(dailyMenuForm.getDishIds());
        List<Long> dishCounterIds = order.getDishCounters().stream().map(DishCounter::getId).collect(Collectors.toList());
        dishCounterService.deleteByIds(dishCounterIds);

        List<DishCounter> dishCounters = newArrayList();

        for (Dish dish : dishes) {
            DishCounter dishCounter = new DishCounter();

            dishCounter.setOrder(order);
            dishCounter.setDish(dish);
            dishCounter.setCounter(0L);
            dishCounters.add(dishCounter);
        }

        order.setDishCounters(dishCounters);

        orderService.updateOrder(order);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/load-daily-menu")
    public ResponseEntity<DailyMenuForm> loadDailyMenu(final StatisticForm statisticForm, final BindingResult result) {
        log.info("loadDailyMenu()");
        if(result.hasErrors()) {
            log.error("Errors occurred.");
            throw new YazakiException(ErrorBuilder.buildErrorMessage(result));
        }

        Order orderByDate = orderService.findOrderByDate(LocalDate.of(statisticForm.getYear(), statisticForm.getMonth(), statisticForm.getDay()));

        if (Objects.isNull(orderByDate)) {
            throw new RecordNotFoundException("Няма намерени записи за тази дата.");
        }

        for (DishCounter dishCounter : orderByDate.getDishCounters()) {
            if(dishCounter.getCounter() > 0) {
                throw new YazakiException("Дневното меню неможе да се редактира. Има вече записани поръчки.");
            }
        }

        DailyMenuForm dailyMenuForm = new DailyMenuForm();
        List<Long> dishIds = orderByDate.getDishCounters().stream().map(dishCounter -> dishCounter.getDish().getId()).collect(Collectors.toList());

        dailyMenuForm.setDishIds(dishIds);
        dailyMenuForm.setDay(orderByDate.getDate().getDayOfMonth());
        dailyMenuForm.setMonth(orderByDate.getDate().getMonthValue());
        dailyMenuForm.setYear(orderByDate.getDate().getYear());
        dailyMenuForm.setOrderId(orderByDate.getId());

        return ResponseEntity.ok(dailyMenuForm);
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/load-audit-users")
    public ResponseEntity<List<UserAuditForm>> getUserAuditRecords() {
        List<UserAudit> allUserAudits = userService.findAllUserAudits();
        List<UserAuditForm> userAuditForms = newArrayListWithCapacity(allUserAudits.size());

        for (UserAudit dishAudit : allUserAudits) {
            userAuditForms.add(converter.toDishAuditForm(dishAudit));
        }

        return ResponseEntity.ok(newArrayList(userAuditForms));
    }
}
