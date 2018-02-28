package com.yazaki.yazaki.domain.service.order;

import java.time.LocalDate;
import java.util.List;

import com.yazaki.yazaki.domain.model.Order;

public interface OrderService {

    List<Order> getAllOrders();

    Order findOrderById(final Long id);
    
    Order findOrderByDate(final LocalDate date);

    Order getLastAddedOrder();

    void saveOrder(final Order order);

    void updateOrder(final Order order);

    void deleteOrder(final Order order);
}
