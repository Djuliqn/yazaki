package com.yazaki.yazaki.domain.service.dish;

import com.yazaki.yazaki.YazakiApplication;
import com.yazaki.yazaki.domain.exception.YazakiException;
import com.yazaki.yazaki.domain.model.Dish;
import com.yazaki.yazaki.domain.repository.DishRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {YazakiApplication.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DishServiceTest {

    private static final String EXPECTED_DISH_TEST_NAME = "Тест1";
    private static final String EXPECTED_DISH_TEST_DESCRIPTION = "Тест1";

    @Autowired
    private DishService dishService;

    @Mock
    private DishRepository dishRepository;

    private Dish expectedDish;

    @Before
    public void setUp() {
        expectedDish = new Dish();
        expectedDish.setId(1L);
        expectedDish.setName(EXPECTED_DISH_TEST_NAME);
        expectedDish.setDescription(EXPECTED_DISH_TEST_DESCRIPTION);

        dishService.saveDish(expectedDish);
    }

    @Test
    public void verifyGetAllDishes() {
        List<Dish> allDishes = dishService.getAllDishes();

        assertNotNull(allDishes);
        assertEquals(1L, allDishes.size());
    }

    @Test
    public void verifyFindDishById() {
        Dish actual = dishService.findDishById(1L);

        assertNotNull(actual);
        assertEquals(expectedDish.getId(), actual.getId());
        assertEquals(expectedDish.getName(), actual.getName());
        assertEquals(expectedDish.getDescription(), actual.getDescription());
    }

    @Test
    public void verifyDeleteDish() {
        dishService.deleteDish(expectedDish);

        assertNull(dishService.findDishById(1L));
    }

    @Test
    public void verifyDeleteDishById() {
        dishService.deleteDishById(1L);

        assertNull(dishService.findDishById(1L));
    }

    @Test
    public void verifyUpdateDish() {
        Dish expectedUpdateDish = new Dish();
        expectedUpdateDish.setName("Update");
        expectedUpdateDish.setDescription("Update");

        Dish foundDish = dishService.findDishById(1L);

        foundDish.setName(expectedUpdateDish.getName());
        foundDish.setDescription(expectedUpdateDish.getDescription());

        Dish updatedDish = dishService.updateDish(foundDish);

        assertNotNull(updatedDish);
        assertEquals(expectedUpdateDish.getName(), updatedDish.getName());
        assertEquals(expectedUpdateDish.getDescription(), updatedDish.getDescription());
    }

    @Test(expected = YazakiException.class)
    public void shouldThrowExceptionNullRecordInsert() {
        dishService.saveDish(null);
    }

}