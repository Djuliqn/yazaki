package com.yazaki.yazaki.domain.repository;

import com.yazaki.yazaki.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findTopByOrderByIdDesc();
}
