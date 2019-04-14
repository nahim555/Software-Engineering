package Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class FoodTest {

    Food foodTest;

    @Before
    public void setUp() {
        foodTest = new Food("test", 1, 120.0, 12.5, 23.5, 30.0, 2, "test", true);
    }

    @After
    public void tearDown() {
        foodTest = null;
    }

    @Test
    public void getID() {
        assertEquals(1, foodTest.getId());
    }

    @Test
    public void getName() {
        assertEquals("test", foodTest.getName());
    }

    @Test
    public void setName() {
        foodTest.setName("john");
        assertEquals("john", foodTest.getName());
    }

    @Test
    public void getCalories() {
        assertEquals(120.0, 120.0, foodTest.getCalories());
    }

    @Test
    public void setCalories() {
        foodTest.setCalories(102.0);
        assertEquals(102.0, 120.0, foodTest.getCalories());
    }

    @Test
    public void getProtein() {
        assertEquals(10.0, 10.0, foodTest.getProtein());
    }

    @Test
    public void setProtein() {
        foodTest.setProtein(22.0);
        assertEquals(22.0, 22.0, foodTest.getProtein());
    }

    @Test
    public void getFat() {
        assertEquals(12.0, 12.0, foodTest.getFat());
    }

    @Test
    public void setFat() {
        foodTest.setFat(12.0);
        assertEquals(12.0, 12.0, foodTest.getFat());
    }

    @Test
    public void getCarbohydrates() {
        assertEquals(44.0, 44.0, foodTest.getCarbohydrates());
    }

    @Test
    public void setCarbohydrates() {
        foodTest.setCarbohydrates(44.0);
        assertEquals(44.0, 44.0, foodTest.getCarbohydrates());
    }

    @Test
    public void getServingSize() {
        assertEquals(2, foodTest.getServingSize());
    }

    @Test
    public void setServingSize() {
        foodTest.setServingSize(1);
        assertEquals(1, foodTest.getServingSize());
    }

    @Test
    public void getServingName() {
        assertEquals("test", foodTest.getServingName());
    }

    @Test
    public void setServingName() {
        foodTest.setServingName("test");
        assertEquals("test", foodTest.getServingName());
    }

    @Test
    public void isPublic() {
        assertEquals(true, foodTest.isPublic());
    }

    @Test
    public void setPublic() {
        foodTest.setPublic(true);
        assertEquals(true, foodTest.isPublic());
    }


}
