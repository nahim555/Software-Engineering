package Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class TimeGoalTest {

    TimeGoal testGoal;
    Exercise testExercise;

    @Before
    public void setUp() {
        testGoal = new TimeGoal(LocalDate.now(), 100, testExercise, 100, 1000, 2000);
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
    public void getPreviousBestTime() {
        assertEquals(1000, testGoal.getPreviousBestTime());
    }

    @Test
    public void getTargetTime() {
        assertEquals(2000, testGoal.getTargetTime());
    }

    @Test
    public void setPreviousBestTime() {
        testGoal.setPreviousBestTime(1000);
        assertEquals(1000, testGoal.getPreviousBestTime());
    }

    @Test
    public void setTargetTime() {
        testGoal.setTargetTime(2000);
        assertEquals(2000, testGoal.getTargetTime());
    }

    @Test
    public void getDistance() {
        assertEquals(20.0, 20.0, testGoal.getDistance());
    }

    @Test
    public void setDistance() {
        testGoal.setDistance(100);
        assertEquals(100, testGoal.getDistance());
    }


}
