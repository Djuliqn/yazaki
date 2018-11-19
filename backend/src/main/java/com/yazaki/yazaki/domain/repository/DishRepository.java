package com.yazaki.yazaki.domain.repository;

import com.yazaki.yazaki.ui.form.DishAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.yazaki.yazaki.domain.model.Dish;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
    String FIND_ALL_DISH_AUDITS = "SELECT yr.amended_at, da.description, da.name, da.revtype, yr.amended_by" +
            " FROM dishes_audit da" +
            " JOIN yazaki_revision yr on yr.id = da.rev";

    Dish findDishByName(final String name);

    List<Dish> findByIdIn(List<Long>ids);

    @Query(value = FIND_ALL_DISH_AUDITS, nativeQuery = true)
    List<DishAudit>  findAllDishAudits();
}
