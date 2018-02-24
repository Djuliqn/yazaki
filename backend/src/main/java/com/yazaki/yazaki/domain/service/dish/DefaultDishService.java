package com.yazaki.yazaki.domain.service.dish;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.yazaki.yazaki.domain.core.MessageSourceWrapper;
import com.yazaki.yazaki.domain.exception.DishExistsException;
import com.yazaki.yazaki.domain.exception.DishIllegalArgumentException;
import com.yazaki.yazaki.domain.exception.RecordNotFoundException;
import com.yazaki.yazaki.domain.model.Dish;
import com.yazaki.yazaki.domain.repository.DishRepository;

@Service
@Primary
@Transactional
public class DefaultDishService implements DishService {

	private static final Logger logger = LoggerFactory.getLogger(DefaultDishService.class);

	private final DishRepository dishRepository;
	private final MessageSourceWrapper messageSourceWrapper;

	@Autowired
	public DefaultDishService(final DishRepository dishRepository, final MessageSourceWrapper messageSourceWrapper) {
		this.dishRepository = dishRepository;
		this.messageSourceWrapper = messageSourceWrapper;
	}

	@Override
	public List<Dish> getAllDishes() {
		return dishRepository.findAll();
	}

	@Override
	public Dish findDishById(final Long id) {
		try {
			return dishRepository.findOne(id);
		} catch (IllegalArgumentException ex) {
			throw new RecordNotFoundException(messageSourceWrapper.getMessage("dish.is.null"));
		}
	}

	@Override
	public Dish saveDish(final Dish dish) {
		try {
			return dishRepository.save(dish);

		} catch (IllegalArgumentException ex) {
			logger.error("Method: saveDish, ThrowedException: IllegalArgumentException perhaps objects is null");
			throw new DishIllegalArgumentException(messageSourceWrapper.getMessage("dish.is.null"));

		} catch (DataIntegrityViolationException ex) {
			logger.error("Method: saveDish, ThrowedException: DishExistsException, because of unique constraint.");
			throw new DishExistsException(messageSourceWrapper.getMessage("dish.name.exists"));

		}
	}

	@Override
	public void deleteDish(final Dish dish) {
		try {
			Assert.notNull(dish, messageSourceWrapper.getMessage("dish.is.null"));
			dishRepository.delete(dish);
		} catch (IllegalArgumentException ex) {

			throw new RecordNotFoundException(ex.getMessage());
		}
	}

	@Override
	public Dish updateDish(final Dish dish) {
		try {

			Assert.notNull(dish, messageSourceWrapper.getMessage("dish.is.null"));
			checkUniqueConstraintName(dish);

			return dishRepository.save(dish);

		} catch (IllegalArgumentException ex) {
			logger.error("Method: updateDish, ThrowedException: IllegalArgumentException perhaps objects is null");
			throw new DishIllegalArgumentException(ex.getMessage());
		}
	}

	@Override
	public void deleteDishById(final Long id) {
		try {
			dishRepository.delete(id);
		} catch (EmptyResultDataAccessException | IllegalArgumentException ex) {
			logger.error(
					"Method: deleteDishById, Throwed exception: RecordNotFoundException, because passes id is null or id doesnt exists.");
			throw new RecordNotFoundException(messageSourceWrapper.getMessage("dish.not.found"));
		}
	}

	private void checkUniqueConstraintName(final Dish dish) {
		final Dish foundDish = dishRepository.findDishByName(dish.getName());

		if(foundDish == null) {
			return;
		}
		
		if (dish.getId() != foundDish.getId()) {
			logger.error("Method: updateDish, Unique constraint of column name.");
			throw new DishExistsException(messageSourceWrapper.getMessage("dish.name.exists"));
		}
	}
}
