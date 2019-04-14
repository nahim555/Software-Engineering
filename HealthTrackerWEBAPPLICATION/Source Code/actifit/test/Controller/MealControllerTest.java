package Controller;

import Model.Meal;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MealControllerTest {

    Meal testMeal;

    @Before
    public void setUp() throws Exception {
        testMeal = new Meal("pasta", 1, false);
    }

    @Test
    public void calculateCalories() {
        assertEquals(0, MealController.calculateCalories(testMeal), 0);
    }

    @Test
    public void calculateProtein() {
        assertEquals(0, MealController.calculateProtein(testMeal), 0);
    }

    @Test
    public void calculateFat() {
        assertEquals(0, MealController.calculateFat(testMeal), 0);
    }

    @Test
    public void calculateCarbohydrates() {
        assertEquals(0, MealController.calculateCarbohydrates(testMeal), 0);
    }
}