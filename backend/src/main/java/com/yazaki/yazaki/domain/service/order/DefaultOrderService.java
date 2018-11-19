package com.yazaki.yazaki.domain.service.order;

import com.yazaki.yazaki.domain.core.MessageSourceWrapper;
import com.yazaki.yazaki.domain.exception.OrderDateExistsException;
import com.yazaki.yazaki.domain.exception.OrderIllegalArgumentException;
import com.yazaki.yazaki.domain.exception.RecordNotFoundException;
import com.yazaki.yazaki.domain.model.Order;
import com.yazaki.yazaki.domain.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
@Primary
@Transactional
public class DefaultOrderService implements OrderService {

	private static final Logger logger = LoggerFactory.getLogger(DefaultOrderService.class);

	private final OrderRepository orderRepository;
	private final MessageSourceWrapper messageSourceWrapper;

	@Autowired
	public DefaultOrderService(final OrderRepository orderRepository, final MessageSourceWrapper messageSourceWrapper) {
		this.orderRepository = orderRepository;
		this.messageSourceWrapper = messageSourceWrapper;
	}

	@Override
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	@Override
	public Order findOrderById(final Long id) {
		return executeOperation(id, orderRepository::findOne, new RecordNotFoundException());
	}

	@Override
	public void saveOrder(final Order order) {
		try {
			orderRepository.save(order);

		} catch (IllegalArgumentException ex) {
			logger.error("Method: saveOrder, ThrowedException: IllegalArgumentException perhaps objects is null");
			throw new OrderIllegalArgumentException(messageSourceWrapper.getMessage("order.is.null"));

		} catch (DataIntegrityViolationException ex) {
			logger.error(
					"Method: saveOrder, ThrowedException: OrderDateExistsException, because of unique constraint. Date property is unique");
			throw new OrderDateExistsException(messageSourceWrapper.getMessage("order.date.exists"));
		}
	}

	@Override
	public Order findOrderByDate(final LocalDate date) {
		return orderRepository.findOrderByDate(date);
	}

	@Override
	public void updateOrder(final Order order) {
		executeOperation(order, orderRepository::save, new OrderDateExistsException());
	}

	@Override
	public void deleteOrder(final Order order) {
		executeOperation(order, orderRepository::delete, new RecordNotFoundException());
	}

	@Override
	public Order getLastAddedOrder() {
		return orderRepository.findTopByOrderByIdDesc();
	}

	private void executeOperation(final Order order, final Consumer<Order> block,
			final RuntimeException throwedException) {
		try {
			block.accept(order);
		} catch (Exception exception) {
			throw throwedException;
		}
	}

	private Order executeOperation(final Long id, final Function<Long, Order> block,
			final RuntimeException throwedException) {
		try {
			return block.apply(id);
		} catch (Exception exception) {
			throw throwedException;
		}
	}

}
