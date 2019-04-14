package Controller;

import Model.Goal;
import Model.User;
import Model.WeightLossGoal;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class GoalControllerTest {

    Goal testGoal;
    User testUser;

    @Before
    public void setUp() throws Exception {
        testGoal = new WeightLossGoal(LocalDate.now(), 100, 120.0, 100.0);
        testUser = new User("testUsername", "test@test.com", "Namey", "McNameface", LocalDate.now(), 180, User.Gender.M);

    }

    @Test
    public void getDaysRemaining() {
        assertEquals(100, GoalController.getDaysRemaining(testGoal));
    }
}