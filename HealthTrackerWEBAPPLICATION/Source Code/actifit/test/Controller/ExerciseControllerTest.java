package Controller;

import Model.Exercise;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class ExerciseControllerTest {

    Exercise exerciseTest;

    @Before
    public void setUp() throws Exception {
        exerciseTest = new Exercise("Running", 1, 10.0, 1, true);
    }

    @After
    public void tearDown() throws Exception {
        Connection connection = DatabaseController.getConnection();
        connection.prepareStatement("DELETE FROM exercises WHERE name LIKE 'testExercise%'").executeUpdate();
        connection.close();
    }

    @Test
    public void getMETValue() {
        assertEquals(50001, ExerciseController.getMETValue(exerciseTest, 5000), 0.1);
    }

    @Test
    public void findExerciseByName() throws SQLException {
        assertEquals(1, ExerciseController.findExerciseByName("Running").getId());
    }

    @Test
    public void findExerciseById() throws SQLException {
        assertEquals("Running", ExerciseController.findExerciseById(1).getName());
    }

    @Test
    public void getExercises() throws SQLException {
        assertTrue(ExerciseController.getExercises().size() > 0);
    }

    @Test
    public void createNewExercise() throws SQLException {
        ExerciseController.createNewExercise("testExercise", 10.0, 1, true);
    }
}