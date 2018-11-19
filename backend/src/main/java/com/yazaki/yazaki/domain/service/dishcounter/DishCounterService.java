package com.yazaki.yazaki.domain.service.dishcounter;

import com.yazaki.yazaki.domain.model.DishCounter;

import java.util.List;

public interface DishCounterService {

    void update(DishCounter dishCounter);

    DishCounter findDishCounterById(Long id);

    void deleteByIds(List<Long> ids);
}
