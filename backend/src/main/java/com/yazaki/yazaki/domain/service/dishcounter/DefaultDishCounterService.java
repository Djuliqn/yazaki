package com.yazaki.yazaki.domain.service.dishcounter;

import com.yazaki.yazaki.domain.model.DishCounter;
import com.yazaki.yazaki.domain.repository.DishCounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Primary
@Transactional
public class DefaultDishCounterService implements DishCounterService {

    private final DishCounterRepository dishCounterRepository;

    @Autowired
    public DefaultDishCounterService(final DishCounterRepository dishCounterRepository) {
        this.dishCounterRepository = dishCounterRepository;
    }

    @Override
    public void update(final DishCounter dishCounter) {
        dishCounterRepository.save(dishCounter);
    }

    @Override
    public DishCounter findDishCounterById(final Long id) {
        return dishCounterRepository.getOne(id);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        dishCounterRepository.deleteByIdIn(ids);
    }
}
