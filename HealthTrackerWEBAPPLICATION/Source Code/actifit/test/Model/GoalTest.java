package Model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;


public class GoalTest {

    Goal testGoal;

    @Before
    public void setUp() {
        testGoal = new WeightLossGoal(LocalDate.now(), 100, 120.0, 100.0);
    }

    @Test
    public void getStartingDate() {
        assertEquals(LocalDate.now(), testGoal.getStartingDate());
    }

    @Test
    public void setStartingDate() {
        testGoal.setStartingDate(LocalDate.now());
        assertEquals(LocalDate.now(), testGoal.getStartingDate());
    }

    @Test
    public void getDuration() {
        assertEquals(100, testGoal.getDuration());
    }

    @Test
    public void setDuration() {
        testGoal.setDuration(100);
        assertEquals(100, testGoal.getDuration());
    }

}
