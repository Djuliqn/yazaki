package com.yazaki.yazaki.domain.service.dish;

import com.yazaki.yazaki.domain.model.Dish;
import com.yazaki.yazaki.ui.form.DishAudit;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DishService {

    List<Dish> getAllDishes();

    Dish findDishById(final Long id);

    Dish saveDish(final Dish dish);

    void deleteDish(final Dish dish);

    void deleteDishById(final Long id);

    Dish updateDish(final Dish dish);

    List<Dish> findDishesByIds(List<Long> dishIds);


    List<DishAudit> findAllDishAudits();
}
