package Model;

import Controller.MealController;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class MealTest {

    Meal testMeal;
    Food testFood;

    @Before
    public void setUp() {
        testMeal = new Meal("pasta", 1, false);
        testFood = new Food("pasta", 1, 22.0, 22.0, 12.0, 30.0, 1, "test", true);
    }

    @Test
    public void getName() {
        assertEquals("pasta", testMeal.getName());
    }

    @Test
    public void setName() {
        testMeal.setName("pasta");
        assertEquals("pasta", testMeal.getName());
    }

    @Test
    public void getID() {
        assertEquals(1, testMeal.getId());
    }

    @Test
    public void getFoods() throws SQLException {
        for (Meal meal : MealController.getPublicMeals()) {
            if (meal.isPublic()) {
                MealController.getPublicMeals();
            } else
                meal.getFoods();
        }
    }

    @Test
    public void addFood() {
        testFood = new Food("pasta", 1, 22.0, 22.0, 12.0, 30.0, 1, "test", true);
        testMeal.addFood(testFood, 20);
    }

    @Test
    public void removeFood() {
        testFood = new Food("pasta", 1, 22.0, 22.0, 12.0, 30.0, 1, "test", true);
        testMeal.removeFood(testFood);
    }

    @Test
    public void isPublic() {
        assertFalse(testMeal.isPublic());
    }

    @Test
    public void setPublic() {
        testMeal.setPublic(true);
        assertTrue(testMeal.isPublic());
    }
}
