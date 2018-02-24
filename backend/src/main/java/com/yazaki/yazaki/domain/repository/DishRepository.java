package com.yazaki.yazaki.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yazaki.yazaki.domain.model.Dish;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {

    Dish findDishByName(final String name);
}
