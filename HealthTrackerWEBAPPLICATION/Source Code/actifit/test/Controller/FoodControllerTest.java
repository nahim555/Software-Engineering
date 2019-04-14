package Controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class FoodControllerTest {

    @Test
    public void findFoodById() throws SQLException {
        assertEquals("McCain Home Chips", FoodController.findFoodById(103).getName());
    }

    @Test
    public void getPublicFoods() throws SQLException {
        assertTrue(0 < FoodController.getPublicFoods().size());
    }
}