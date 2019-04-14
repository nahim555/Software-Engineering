package Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class DistanceGoalTest {

    DistanceGoal testGoal;
    Exercise testExercise;

    @Before
    public void setUp() {
        testGoal = new DistanceGoal(LocalDate.now(), 1000, testExercise, 1000, 2000);
    }

    @After
    public void tearDown() {
        testGoal = null;
    }

    @Test
    public void getExercise() {
        assertEquals(testExercise, testGoal.getExercise());
    }

    @Test
    public void setExercise() {
        testGoal.setExercise(testExercise);
        assertEquals(testExercise, testGoal.getExercise());
    }

    @Test
    public void getPreviousBestDistance() {
        assertEquals(1000, testGoal.getPreviousBestDistance());
    }

    @Test
    public void setPreviousBestDistance() {
        testGoal.setPreviousBestDistance(1000);
        assertEquals(1000, testGoal.getPreviousBestDistance());
    }

    @Test
    public void getTargetDistance() {
        assertEquals(2000, testGoal.getTargetDistance());
    }

    @Test
    public void setTargetDistance() {
        testGoal.setTargetDistance(2000);
        assertEquals(2000, testGoal.getTargetDistance());
    }

}
