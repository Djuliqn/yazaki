package com.yazaki.yazaki.domain.repository;

import com.yazaki.yazaki.domain.model.DishCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishCounterRepository extends JpaRepository<DishCounter, Long> {

    @Modifying
    @Query(value = "delete from dish_counter where id in ?1", nativeQuery = true)
    void deleteByIdIn(List<Long> ids);
}
