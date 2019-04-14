package Controller;

import Model.Activity;
import Model.Exercise;
import Model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ActivityControllerTest {

    User testUser;
    Exercise exercise;
    Activity activity;

    @Before
    public void setUp() throws Exception {
        testUser = UserController.newUser("TestUser", "actifit@carbtc.net", "Test", "Smith", LocalDate.now(), 123, 432.1, "password", User.Gender.M);
        testUser.addWeight(LocalDate.of(2018, 1, 1), 85);
        exercise = ExerciseController.findExerciseById(1);
        activity = new Activity(exercise, LocalDate.of(2018, 1, 1),1800,5000);
        testUser.addActivity(activity);
    }

    @After
    public void tearDown() throws SQLException {
        Connection connection = DatabaseController.getConnection();
        connection.prepareStatement("DELETE FROM users WHERE username LIKE 'TestUser%'").executeUpdate();
        connection.prepareStatement("DELETE FROM activities WHERE username LIKE 'TestUser%'").executeUpdate();
        connection.close();
    }

    @Test
    public void getActivitiesForDate() {
        List<Activity> list = ActivityController.getActivitiesForDate(testUser, LocalDate.of(2018, 1, 1));
        assertEquals(1, list.size());
    }

    @Test
    public void getCaloriesBurned() throws SQLException {
        assertEquals(416, ActivityController.getCaloriesBurned(testUser, activity));
    }

    @Test
    public void getCaloriesBurnedForDate() throws SQLException {
        assertEquals(416, ActivityController.getCaloriesBurnedForDate(testUser, LocalDate.of(2018, 1, 1)));
    }

    @Test
    public void getUsersBurnedCalories() throws SQLException {
        testUser.addWeight(LocalDate.now(), 85);
        activity = new Activity(exercise, LocalDate.now(),1800,5000);
        testUser.addActivity(activity);
        Map<LocalDate, Integer> calories = ActivityController.getUsersBurnedCalories(testUser, 0);
        assertEquals(416, (int) calories.values().toArray()[0]);
    }

    @Test
    public void getDistancePersonalBest() {
        assertEquals(5000, ActivityController.getDistancePersonalBest(testUser, exercise));
    }

    @Test
    public void getSpeedPersonalBest() {
        assertEquals(10, ActivityController.getSpeedPersonalBest(testUser, exercise), 0.1);
    }

    @Test
    public void getAllDistancePersonalBests() throws SQLException {
        Map<Exercise, Integer> pbs = ActivityController.getAllDistancePersonalBests(testUser);
        assertEquals(5000, (int) pbs.values().toArray()[0]);
    }

    @Test
    public void getAllSpeedPersonalBests() throws SQLException {
        Map<Exercise, Double> pbs = ActivityController.getAllSpeedPersonalBests(testUser);
        assertEquals(10, (double) pbs.values().toArray()[0], 0.1);
    }

    @Test
    public void calculateFastestForDistance() {
        assertEquals(1800, ActivityController.calculateFastestForDistance(testUser, exercise, 5000), 0.1);
    }
}