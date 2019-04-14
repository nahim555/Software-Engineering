package Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExerciseTest {

    Exercise exerciseTest;

    @Before
    public void setUp() {
        exerciseTest = new Exercise("Running", 1, 10.0, 1, true);
    }

    @After
    public void tearDown() {
        exerciseTest = null;
    }

    @Test
    public void getName() {
        assertEquals("Running", exerciseTest.getName());
    }

    @Test
    public void getID() {
        assertEquals(1, exerciseTest.getId());
    }

    @Test
    public void getMultiplier() {
        assertEquals(10, exerciseTest.getMultiplier(), 0.1);
    }

    @Test
    public void getAdjustment() {
        assertEquals(1, exerciseTest.getAdjustment(), 0.1);
    }

    @Test
    public void isMeasurable() {
        assertEquals(true, exerciseTest.isMeasurable());
    }

    @Test
    public void setName() {
        exerciseTest.setName("Jogging");
        assertEquals("Jogging", exerciseTest.getName());
    }

    @Test
    public void setID() {
        exerciseTest.setId(1);
        assertEquals(1, exerciseTest.getId());
    }

    @Test
    public void setMultiplier() {
        exerciseTest.setMultiplier(10.0);
        assertEquals(10.0, exerciseTest.getMultiplier(), 0.1);
    }

    public void setMeasurable() {
        exerciseTest.setMeasurable(false);
        assertEquals(false, exerciseTest.isMeasurable());
    }

    public void setAdjustment() {
        exerciseTest.setAdjustment(5.5);
        assertEquals(2, exerciseTest.getAdjustment(), 0.1);
    }
}
