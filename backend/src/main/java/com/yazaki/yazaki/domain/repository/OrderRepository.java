package com.yazaki.yazaki.domain.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yazaki.yazaki.domain.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findTopByOrderByIdDesc();
    
    Order findOrderByDate(final LocalDate date);
}
