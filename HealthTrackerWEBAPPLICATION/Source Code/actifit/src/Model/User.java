package Model;

import java.time.LocalDate;
import java.util.*;

public class User implements Comparable<User> {

    public enum Gender {
        M, F, U
    }

    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private int height;
    private Gender gender;
    private Map<LocalDate, Double> weights;
    private List<Activity> activities;
    private List<Goal> goals;
    private List<Food> foods;
    private List<Meal> meals;
    private Map<LocalDate, Map<Integer, Map<Meal, Double>>> consumed;

    /**
     * Default constructor of a User.
     *
     * @param username the user's username
     * @param email the user's email address
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param dateOfBirth the user's date of birth
     * @param height the user's height
     */
    public User(String username, String email, String firstName, String lastName, LocalDate dateOfBirth, int height, Gender gender) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.height = height;
        this.gender = gender;
        this.weights = new LinkedHashMap<>();
        this.activities = new ArrayList<>();
        this.goals = new ArrayList<>();
        this.foods = new ArrayList<>();
        this.meals = new ArrayList<>();
        this.consumed = new LinkedHashMap<>();
    }

    /**
     * Overriding equals to compare two User objects
     * @param o the object to compare to
     * @return if the objects are equal
     */
    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        // Check if o is an instance of User
        if (!(o instanceof User)) {
            return false;
        }

        // Typecast o to User so that we can compare data members
        User c = (User) o;

        // Compare the username (this is good enough for what it is needed for)
        return c.username.equals(this.username);
    }

    /**
     * Overriding compareTo of two User objects
     * @param o the object to compare to
     * @return a comparison of the user's username
     */
    @Override
    public int compareTo(User o) {
        return username.compareTo(o.username);
    }

    /**
     * Method to get a user's username.
     *
     * @return the user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Method to get a user's email address.
     *
     * @return the user's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Method to set a user's email address.
     *
     * @param email the user's email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Method to get a user's first name.
     *
     * @return the user's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Method to set a user's first name.
     *
     * @param firstName the user's first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Method to get a user's last name.
     *
     * @return the user's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Method to set a user's last name.
     *
     * @param lastName the user's last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Method to get a user's full name.
     *
     * @return the user's full name
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Method to get a user's date of birth.
     *
     * @return the user's date of birth
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Method to get a user's height.
     *
     * @return the user's height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Method to set a user's height.
     *
     * @param height the user's height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Method to return a user's gender.
     *
     * @return the users gender
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Method to set a user's gender.
     *
     * @param gender the user's new gender
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Method to get all the weights that the user has logged.
     *
     * @return a map of the weight that was logged with the day
     */
    public Map<LocalDate, Double> getWeights() {
        return weights;
    }

    /**
     * Method to find the weight the user's weight that was most recently logged.
     *
     * @return the users current weight
     */
    public double getMostRecentWeight() {
        return weights.get(Collections.max(weights.keySet()));
    }

    /**
     * Method to log a weight for the user.
     *
     * @param date the date the weight was logged
     * @param weight how much the user weighed on this day
     */
    public void addWeight(LocalDate date, double weight) {
        weights.put(date, weight);
    }

    /**
     * Method to remove a weight from a user's log.
     *
     * @param date the date that the weight was logged to remove
     */
    public void removeWeight(LocalDate date) {
        weights.remove(date);
    }

    /**
     * Method to remove all the weights for a user.
     */
    public void clearWeights() {
        weights.clear();
    }

    /**
     * Method to get a list of the user's activities.
     *
     * @return a list of the user's activities
     */
    public List<Activity> getActivities() {
        return activities;
    }

    /**
     * Method to add an activity to the user's list of activities.
     *
     * @param activity the activity to add
     */
    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * Method to remove an activity from the user's list of activities.
     *
     * @param activity the activity to remove
     */
    public void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    /**
     * Method to remove all the activities for a user.
     */
    public void clearActivities() {
        activities.clear();
    }

    /**
     * Method to get a list of the user's goals.
     *
     * @return a list of the user's goals
     */
    public List<Goal> getGoals() {
        return goals;
    }

    /**
     * Method to add a goal to the user's list of goals.
     *
     * @param goal the goal to add
     */
    public void addGoal(Goal goal) {
        goals.add(goal);
    }

    /**
     * Method to remove a goal from the user's list of goals.
     *
     * @param goal the goal to remove
     */
    public void removeGoal(Goal goal) {
        goals.remove(goal);
    }

    /**
     * Method to remove all the goals for a user.
     */
    public void clearGoals() {
        goals.clear();
    }

    /**
     * Method to get a list of the user's foods.
     *
     * @return a list of the user's foods
     */
    public List<Food> getFoods() {
        return foods;
    }

    /**
     * Method to add a food to the user's list of foods.
     *
     * @param food the food to add
     */
    public void addFood(Food food) {
        foods.add(food);
    }

    /**
     * Method to remove a food from the user's list of foods.
     *
     * @param food the food to remove
     */
    public void removeFood(Food food) {
        foods.remove(food);
    }

    /**
     * Method to remove all the foods for a user.
     */
    public void clearFoods() {
        foods.clear();
    }

    /**
     * Method to get a list of the user's meals.
     *
     * @return a list of the user's meals
     */
    public List<Meal> getMeals() {
        return meals;
    }

    /**
     * Method to add a meal to the user's list of meals.
     *
     * @param meal the meal to add
     */
    public void addMeal(Meal meal) {
        meals.add(meal);
    }

    /**
     * Method to remove a meal from the user's list of meals.
     *
     * @param meal the meal to remove
     */
    public void removeMeal(Meal meal) {
        meals.remove(meal);
    }

    /**
     * Method to remove all the meals for a user.
     */
    public void clearMeals() {
        meals.clear();
    }

    /**
     * Method to get all the meals that the user has eaten.
     *
     * @return the meals that the user has eaten
     */
    public Map<LocalDate, Map<Integer, Map<Meal, Double>>> getConsumed() {
        return consumed;
    }

    /**
     * Method to add a meal to the meals that the user has eaten.
     *
     * @param date the date the user ate the meal
     * @param time the time that the meal was eaten. 1 = breakfast, 2 = lunch, 3 = dinner, 4 = snack
     * @param meal the meal the user ate
     * @param multiplier how much of the meal the user ate. 1 = whole meal
     */
    public void addConsumedMeal(LocalDate date, int time, Meal meal, double multiplier) {
        //TODO not allow a user to add a meal if it is not public or their meal
        consumed.putIfAbsent(date, new LinkedHashMap<>());
        consumed.get(date).putIfAbsent(time, new LinkedHashMap<>());
        consumed.get(date).get(time).putIfAbsent(meal, multiplier);
    }

    /**
     * Method to remove a meal from the meals that the user has eaten.
     *
     * @param date the date the user ate the meal
     * @param time the time that the consumed were eaten. 1 = breakfast, 2 = lunch, 3 = dinner, 4 = snack
     * @param meal the meal the user ate
     */
    public void removeConsumedMeal(LocalDate date, int time, Meal meal) {
        //TODO make this work if user has consumed multiple of the same meal at the same time
        for (Meal mealCheck : consumed.get(date).get(time).keySet()) {
            if (mealCheck.equals(meal)) {
                consumed.get(date).get(time).remove(mealCheck);
                break;
            }
        }
    }

    /**
     * Method to clear all the meals that the user has eaten.
     */
    public void clearConsumedMeals() {
        consumed.clear();
    }
}
