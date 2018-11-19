package com.yazaki.yazaki.domain.repository;

import com.yazaki.yazaki.YazakiApplication;
import com.yazaki.yazaki.domain.model.Dish;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = YazakiApplication.class)
public class DishRepositoryTest {

    private static final String TEST = "Тест";

    @Autowired
    private DishRepository dishRepository;

    @Before
    public void setUp() {
        Dish dish = new Dish();
        dish.setId(1L);
        dish.setName(TEST);
        dish.setDescription(TEST);



        dishRepository.save(dish);
    }

    @Test
    public void verifyFindDishByName() {
        Long expectedId = 1L;

        Dish dish = dishRepository.findDishByName(TEST);

        assertNotNull(dish);
        assertEquals(TEST, dish.getName());
        assertEquals(TEST, dish.getDescription());
        assertEquals(expectedId, dish.getId());
    }

    @Test
    public void verifyFindAll() {
        List<Dish> allDishes = dishRepository.findAll();

        assertNotNull(allDishes);
        assertEquals(1, allDishes.size());
    }

}