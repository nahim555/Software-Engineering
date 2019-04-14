package Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class WeightLossGoalTest {

    WeightLossGoal testGoal;

    @Before
    public void setUp() {
        testGoal = new WeightLossGoal(LocalDate.now(), 120, 160.0, 120.0);
    }

    @After
    public void tearDown() {
        testGoal = null;
    }

    @Test
    public void getStartingWeight() {
        assertEquals(160.0, 160.0, testGoal.getStartingWeight());
    }

    @Test
    public void getTargetWeight() {
        assertEquals(120.0, 120.0, testGoal.getTargetWeight());
    }

    @Test
    public void setStartingWeight() {
        testGoal.setStartingWeight(120);
        assertEquals(140.0, 140.0, testGoal.getStartingWeight());
    }

    @Test
    public void setTargetWeight() {
        testGoal.setTargetWeight(120);
        assertEquals(120.0, 120.0, testGoal.getTargetWeight());
    }
}
