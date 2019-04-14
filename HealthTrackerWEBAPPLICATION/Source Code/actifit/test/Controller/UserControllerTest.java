package Controller;

import Model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class UserControllerTest {

    User testUser;

    @Before
    public void setUp() throws Exception {
        testUser = UserController.newUser("TestUser", "actifit@carbtc.net", "Test", "Smith", LocalDate.of(1960, 01, 01), 123, 432.1, "password", User.Gender.M);
    }

    @After
    public void tearDown() throws Exception {
        Connection connection = DatabaseController.getConnection();
        connection.prepareStatement("DELETE FROM users WHERE username LIKE 'TestUser%'").executeUpdate();
        connection.prepareStatement("DELETE FROM users_weights WHERE username LIKE 'TestUser%'").executeUpdate();
        connection.prepareStatement("DELETE FROM activities WHERE username LIKE 'TestUser%'").executeUpdate();
        connection.prepareStatement("DELETE FROM goals_weightloss WHERE username LIKE 'TestUser%'").executeUpdate();
        connection.prepareStatement("DELETE FROM foods WHERE author LIKE 'TestUser%'").executeUpdate();
        connection.prepareStatement("DELETE FROM meals WHERE author LIKE 'TestUser%'").executeUpdate();
        connection.close();
    }

    @Test
    public void newUser() {
        assertEquals("TestUser", testUser.getUsername());
    }

    @Test
    public void fetchUser() throws SQLException {
        assertEquals("TestUser", UserController.fetchUser("TestUser").getUsername());
    }

    @Test
    public void persistUser() throws SQLException {
        testUser.setFirstName("New");
        UserController.persistUser(testUser);
        assertEquals("New", UserController.fetchUser("TestUser").getFirstName());
    }

    @Test
    public void removeUser() throws SQLException {
        UserController.removeUser(testUser);
        Connection connection = DatabaseController.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username LIKE 'TestUser%'");
        ResultSet rs = statement.executeQuery();
        connection.close();
        if(rs.next()) {
            fail();
        }
    }

    @Test
    public void persistWeights_fetchWeights() throws SQLException {
        User testUser2 = UserController.fetchUser("TestUser");

        testUser.addWeight(LocalDate.of(2000, 1, 1), 100.1);
        testUser.addWeight(LocalDate.of(2000, 1, 2), 200.2);
        testUser.addWeight(LocalDate.of(2000, 1, 3), 300.3);
        UserController.persistWeights(testUser);

        UserController.fetchWeights(testUser2);

        assertEquals(4, testUser2.getWeights().size());
    }

    @Test
    public void persistActivities_fetchActivities() throws SQLException {
        User testUser2 = UserController.fetchUser("TestUser");

        testUser.addActivity(new Activity(ExerciseController.findExerciseById(1), LocalDate.now(), 60, 10));
        UserController.persistActivities(testUser);

        UserController.fetchActivities(testUser2);

        assertEquals(1, testUser2.getActivities().size());
    }

    @Test
    public void persistGoals_fetchGoals() throws SQLException {
        User testUser2 = UserController.fetchUser("TestUser");

        testUser.addGoal(new WeightLossGoal(LocalDate.now(), 14, 89, 88));
        UserController.persistGoals(testUser);

        UserController.fetchGoals(testUser2);

        assertEquals(1, testUser2.getGoals().size());
    }

    @Test
    public void persistFoods_fetchFoods() throws SQLException {
        User testUser2 = UserController.fetchUser("TestUser");

        testUser.addFood(new Food("testFood", 50.1, 50.1, 50.1, 50.1, 50, "slice", false));
        UserController.persistFoods(testUser);

        UserController.fetchFoods(testUser2);

        assertEquals(1, testUser2.getFoods().size());
    }

    @Test
    public void persistMeals_fetchMeals() throws SQLException {
        User testUser2 = UserController.fetchUser("TestUser");

        testUser.addMeal(new Meal("testMeal", false));
        UserController.persistMeals(testUser);

        UserController.fetchMeals(testUser2);

        assertEquals(1, testUser2.getMeals().size());
    }

    @Test
    public void persistConsumedMeals_fetchConsumedMeals() throws SQLException {
        User testUser2 = UserController.fetchUser("TestUser");
        Meal meal = MealController.findMealById(101);
        testUser.addConsumedMeal(LocalDate.now(), 1, meal, 1);
        UserController.persistConsumedMeals(testUser);

        UserController.fetchConsumedMeals(testUser2);

        assertEquals(1, testUser2.getConsumed().size());

    }

    @Test
    public void changePassword() throws SQLException {
        UserController.changePassword(testUser, "newpass12");
        assertTrue(AuthenticationController.checkCredentials(testUser.getUsername(), "newpass12"));
    }

    @Test
    public void sendNewAccountEmail() {
        UserController.sendNewAccountEmail(testUser);
    }

    @Test
    public void getWeightAtDate() throws SQLException {
        testUser.addWeight(LocalDate.now(), 110);
        UserController.persistWeights(testUser);
        assertEquals(110, UserController.getWeightAtDate(testUser, LocalDate.now()), 0.1);
    }

    @Test
    public void findBMR() throws SQLException {
        testUser.addWeight(LocalDate.now(), 110);
        UserController.persistWeights(testUser);
        assertEquals(1583, UserController.findBMR(testUser));
    }

    @Test
    public void getCalorieGoal() throws SQLException {
        testUser.addWeight(LocalDate.now(), 110);
        UserController.persistWeights(testUser);
        assertEquals(1583, UserController.getCalorieGoal(testUser));
    }

    @Test
    public void getAge() {
        assertEquals(58, UserController.getAge(testUser));
    }

    @Test
    public void sendPasswordResetEmail() {
        UserController.sendPasswordResetEmail(testUser);
    }
}