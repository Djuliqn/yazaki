package com.yazaki.yazaki.domain.service.order;

import com.yazaki.yazaki.domain.model.Order;

import java.util.List;

public interface OrderService {

    List<Order> getAllOrders();

    Order findOrderById(final Long id);

    Order getLastAddedOrder();

    void saveOrder(final Order order);

    void updateOrder(final Order order);

    void deleteOrder(final Order order);
}
