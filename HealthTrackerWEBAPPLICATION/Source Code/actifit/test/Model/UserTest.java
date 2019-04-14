package Model;

import Controller.ExerciseController;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class UserTest {

    User testUser;

    @Before
    public void setUp() throws Exception {
        testUser = new User("testUsername", "test@test.com", "Namey", "McNameface", LocalDate.now(), 180, User.Gender.M);
    }

    @Test
    public void getUsername() throws Exception {
        assertEquals("testUsername", testUser.getUsername());
    }

    @Test
    public void getEmail() throws Exception {
        assertEquals("test@test.com", testUser.getEmail());
    }

    @Test
    public void setEmail() throws Exception {
        testUser.setEmail("new@email.com");
        assertEquals("new@email.com", testUser.getEmail());
    }

    @Test
    public void getFirstName() throws Exception {
        assertEquals("Namey", testUser.getFirstName());
    }

    @Test
    public void setFirstName() throws Exception {
        testUser.setFirstName("firstName");
        assertEquals("firstName", testUser.getFirstName());
    }

    @Test
    public void getLastName() throws Exception {
        assertEquals("McNameface", testUser.getLastName());
    }

    @Test
    public void setLastName() throws Exception {
        testUser.setLastName("lastName");
        assertEquals("lastName", testUser.getLastName());
    }

    @Test
    public void getFullName() throws Exception {
        assertEquals("Namey McNameface", testUser.getFullName());
    }

    @Test
    public void getDateOfBirth() throws Exception {
        assertTrue(testUser.getDateOfBirth().equals(LocalDate.now()));
    }

    @Test
    public void getHeight() {
        assertEquals(180, testUser.getHeight());
    }

    @Test
    public void setHeight() {
        testUser.setHeight(190);
        assertEquals(190, testUser.getHeight());
    }

    @Test
    public void getGender() {
        assertEquals("M", testUser.getGender().name());
    }

    @Test
    public void setGender() {
        testUser.setGender(User.Gender.F);
        assertEquals("F", testUser.getGender().name());
    }

    @Test
    public void getWeights() {
        testUser.addWeight(LocalDate.of(2000,1,1), 100);
        testUser.addWeight(LocalDate.of(2000,1,2), 200);
        testUser.addWeight(LocalDate.of(2000,1,3), 300);
        assertEquals(3, testUser.getWeights().size());
    }

    @Test
    public void addWeight_getMostRecentWeight() {
        testUser.addWeight(LocalDate.of(2000,1,1), 100);
        testUser.addWeight(LocalDate.of(2000,1,2), 200);
        testUser.addWeight(LocalDate.of(2000,1,3), 300);
        assertEquals(300, testUser.getMostRecentWeight(), 0);
    }

    @Test
    public void removeWeight() {
        testUser.addWeight(LocalDate.of(2000,1,1), 100);
        testUser.addWeight(LocalDate.of(2000,1,2), 200);
        testUser.addWeight(LocalDate.of(2000,1,3), 300);
        assertEquals(300, testUser.getMostRecentWeight(), 0);
    }

    @Test
    public void clearWeights() {
        testUser.addWeight(LocalDate.of(2000,1,1), 100);
        testUser.addWeight(LocalDate.of(2000,1,2), 200);
        testUser.addWeight(LocalDate.of(2000,1,3), 300);
        testUser.clearWeights();
        assertEquals(0, testUser.getWeights().size());
    }

    @Test
    public void addActivity_getActivities() throws SQLException {
        testUser.addActivity(new Activity(ExerciseController.findExerciseById(1), LocalDate.now(), 100, 200));
        assertEquals(1, testUser.getActivities().size());
    }

    @Test
    public void removeActivity() throws SQLException {
        testUser.addActivity(new Activity(ExerciseController.findExerciseById(1), LocalDate.now(), 100, 200));
        Activity activity = new Activity(ExerciseController.findExerciseById(2), LocalDate.now(), 100, 200);
        testUser.addActivity(activity);
        testUser.removeActivity(activity);
        assertEquals(1, testUser.getActivities().size());
    }

    @Test
    public void clearActivities() throws SQLException {
        testUser.addActivity(new Activity(ExerciseController.findExerciseById(1), LocalDate.now(), 100, 200));
        testUser.addActivity(new Activity(ExerciseController.findExerciseById(1), LocalDate.now(), 100, 200));
        testUser.clearActivities();
        assertEquals(0, testUser.getActivities().size());
    }

    @Test
    public void addGoal_getGoals() throws SQLException {
        testUser.addGoal(new WeightLossGoal(LocalDate.now(), 14, 80, 78));
        assertEquals(1, testUser.getGoals().size());
    }

    @Test
    public void removeGoal() throws SQLException {
        Goal testGoal = new WeightLossGoal(LocalDate.now(), 14, 80, 78);
        testUser.addGoal(testGoal);
        testUser.removeGoal(testGoal);
        assertEquals(0, testUser.getGoals().size());
    }

    @Test
    public void clearGoals() throws SQLException {
        testUser.addGoal(new WeightLossGoal(LocalDate.now(), 14, 80, 78));
        testUser.addGoal(new WeightLossGoal(LocalDate.now(), 13, 76, 75));
        testUser.clearGoals();
        assertEquals(0, testUser.getGoals().size());
    }

    @Test
    public void addFood_getFoods() throws SQLException {
        Food testFood = new Food("Test Food", 50, 50, 50, 50, 100, "slice", false);
        testUser.addFood(testFood);
        assertEquals(1, testUser.getFoods().size());
    }

    @Test
    public void removeFood() throws SQLException {
        Food testFood1 = new Food("Test Food 1", 50, 50, 50, 50, 100, "slice", false);
        Food testFood2 = new Food("Test Food 2", 50, 50, 50, 50, 100, "slice", false);
        testUser.addFood(testFood1);
        testUser.addFood(testFood2);
        testUser.removeFood(testFood1);
        assertEquals(1, testUser.getFoods().size());
    }

    @Test
    public void clearFoods() throws SQLException {
        Food testFood1 = new Food("Test Food 1", 50, 50, 50, 50, 100, "slice", false);
        Food testFood2 = new Food("Test Food 2", 50, 50, 50, 50, 100, "slice", false);
        testUser.addFood(testFood1);
        testUser.addFood(testFood2);
        testUser.clearFoods();
        assertEquals(0, testUser.getFoods().size());
    }

    @Test
    public void addMeal_getMeals() throws SQLException {
        Meal testMeal = new Meal("Test Meal", false);
        testUser.addMeal(testMeal);
        assertEquals(1, testUser.getMeals().size());
    }

    @Test
    public void removeMeal() throws SQLException {
        Meal testMeal1 = new Meal("Test Meal 1", false);
        Meal testMeal2 = new Meal("Test Meal 2", false);
        testUser.addMeal(testMeal1);
        testUser.addMeal(testMeal2);
        testUser.removeMeal(testMeal1);
        assertEquals(1, testUser.getMeals().size());
    }

    @Test
    public void clearMeals() throws SQLException {
        Meal testMeal1 = new Meal("Test Meal 1", false);
        Meal testMeal2 = new Meal("Test Meal 2", false);
        testUser.addMeal(testMeal1);
        testUser.addMeal(testMeal2);
        testUser.clearMeals();
        assertEquals(0, testUser.getMeals().size());
    }

    @Test
    public void addConsumedMeal_getConsumed() {
        Meal meal1 = new Meal("Test Meal 1", 991, false);
        Meal meal2 = new Meal("Test Meal 2", 992, false);
        Meal meal3 = new Meal("Test Meal 3", 993, false);
        testUser.addConsumedMeal(LocalDate.of(1990,1,1), 1, meal1, 1);
        testUser.addConsumedMeal(LocalDate.of(1990,1,1), 1, meal2, 1);
        testUser.addConsumedMeal(LocalDate.of(1990,1,1), 1, meal3, 1);
        assertEquals(3, testUser.getConsumed().get(LocalDate.of(1990,1,1)).get(1).size());
    }

    @Test
    public void removeConsumedMeal() {
        Meal meal1 = new Meal("Test Meal 1", 991, false);
        Meal meal2 = new Meal("Test Meal 2", 992, false);
        Meal meal3 = new Meal("Test Meal 3", 993, false);
        testUser.addConsumedMeal(LocalDate.of(1990,1,1), 1, meal1, 1);
        testUser.addConsumedMeal(LocalDate.of(1990,1,1), 1, meal2, 1);
        testUser.addConsumedMeal(LocalDate.of(1990,1,1), 1, meal3, 1);
        testUser.removeConsumedMeal(LocalDate.of(1990,1,1), 1, meal1);
        assertEquals(2, testUser.getConsumed().get(LocalDate.of(1990,1,1)).get(1).size());
    }

    @Test
    public void clearConsumedMeals() throws SQLException {
        Meal meal1 = new Meal("Test Meal 1", 991, false);
        Meal meal2 = new Meal("Test Meal 2", 992, false);
        Meal meal3 = new Meal("Test Meal 3", 993, false);
        testUser.addConsumedMeal(LocalDate.of(1990,1,1), 1, meal1, 1);
        testUser.addConsumedMeal(LocalDate.of(1990,1,1), 1, meal2, 1);
        testUser.addConsumedMeal(LocalDate.of(1990,1,1), 1, meal3, 1);
        testUser.clearConsumedMeals();
        assertEquals(0, testUser.getConsumed().size());
    }
}