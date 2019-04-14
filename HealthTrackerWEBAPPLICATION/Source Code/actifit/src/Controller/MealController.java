package Controller;

import Model.Food;
import Model.Meal;
import Model.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class MealController {

    /**
     * Method to save a meal to the database
     * @param meal the meal to save
     * @throws SQLException
     */
    public static void saveMeal(Meal meal) throws SQLException {
        Connection connection = DatabaseController.getConnection();

        // Save to the meal table
        PreparedStatement statement = connection.prepareStatement("UPDATE meals SET name = ?, public = ? WHERE id = ?");
        statement.setString(1, meal.getName());
        statement.setBoolean(2, meal.isPublic());
        statement.setInt(3, meal.getId());

        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Saving the meal failed, no rows were affected");
        }

        // Remove all the food to meal links
        statement = connection.prepareStatement("DELETE FROM meals_foods WHERE meal = ?");
        statement.setInt(1, meal.getId());
        statement.executeUpdate();

        // Save the food to meal links
        for (Map.Entry<Food, Integer> food : meal.getFoods().entrySet()) {
            statement = connection.prepareStatement("INSERT INTO meals_foods (meal, food, quantity) VALUES (?, ?, ?)");
            statement.setInt(1, meal.getId());
            statement.setInt(2, food.getKey().getId());
            statement.setInt(3, food.getValue());
            statement.executeUpdate();
        }

        connection.close();
    }

    /**
     * Method to remove a meal from the database
     * @param meal the meal to remove from the database
     * @throws SQLException
     */
    public static void removeMeal(Meal meal) throws SQLException {
        Connection connection = DatabaseController.getConnection();

        // Remove all the food to meal links
        PreparedStatement statement = connection.prepareStatement("DELETE FROM meals_foods WHERE meal = ?");
        statement.setInt(1, meal.getId());
        statement.executeUpdate();

        // Remove all the user to meal links
        statement = connection.prepareStatement("DELETE FROM users_meals WHERE meal = ?");
        statement.setInt(1, meal.getId());
        statement.executeUpdate();

        // Remove the meal
        statement = connection.prepareStatement("DELETE FROM meals WHERE id = ?");
        statement.setInt(1, meal.getId());
        int affectedRows = statement.executeUpdate();
        connection.close();
        if (affectedRows == 0) {
            throw new SQLException("Removing the meal failed, no rows were affected");
        }
    }

    /**
     * Method to find a meal by it's ID
     * @param id the ID of the meal to find
     * @return the meal of the given ID
     * @throws SQLException
     */
    public static Meal findMealById(int id) throws SQLException {
        Meal meal;

        // Fetching the meal data from the database
        Connection connection = DatabaseController.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT name, public FROM meals WHERE id = ?");
        statement.setInt(1, id);

        ResultSet rs = statement.executeQuery();
        if(rs.next()) {
            meal = new Meal(rs.getString("name"), id,
                    rs.getBoolean("public"));
        } else {
            throw new SQLException("Retrieving the meal failed");
        }

        // Adding the foods to the meal
        statement = connection.prepareStatement("SELECT food, quantity FROM meals_foods WHERE meal = ?");
        statement.setInt(1, id);
        rs = statement.executeQuery();

        while (rs.next()) {
            meal.addFood(FoodController.findFoodById(rs.getInt("food")), rs.getInt("quantity"));
        }

        connection.close();

        return meal;
    }

    /**
     * Method to find the number of calories in a meal
     * @param meal the meal to find the calories of
     * @return the number of calories in the meal
     */
    public static double calculateCalories(Meal meal) {
        double calories = 0;
        for (Map.Entry<Food, Integer> food : meal.getFoods().entrySet()) {
            calories += food.getKey().getCalories() * food.getValue() / 100;
        }
        return calories;
    }

    /**
     * Method to find the amount of protein in a meal
     * @param meal the meal to find the protein content of
     * @return the number of grams of protein in the meal
     */
    public static double calculateProtein(Meal meal) {
        double protein = 0;
        for (Map.Entry<Food, Integer> food : meal.getFoods().entrySet()) {
            protein += food.getKey().getProtein() * food.getValue() / 100;
        }
        return protein;
    }

    /**
     * Method to find the amount of fat in a meal
     * @param meal the meal to find the fat content of
     * @return the number of grams of fat in the meal
     */
    public static double calculateFat(Meal meal) {
        double fat = 0;
        for (Map.Entry<Food, Integer> food : meal.getFoods().entrySet()) {
            fat += food.getKey().getFat() * food.getValue() / 100;
        }
        return fat;
    }

    /**
     * Method to find the amount of carbohydrates in a meal
     * @param meal the meal to find the carbohydrate content of
     * @return the number of grams of carbohydrates in the meal
     */
    public static double calculateCarbohydrates(Meal meal) {
        double carbohydrates = 0;
        for (Map.Entry<Food, Integer> food : meal.getFoods().entrySet()) {
            carbohydrates += food.getKey().getCarbohydrates() * food.getValue() / 100;
        }
        return carbohydrates;
    }

    /**
     * Method to get all of the public meals in the database
     * @return a list of all of the public meals in the database
     * @throws SQLException
     */
    public static List<Meal> getPublicMeals () throws SQLException {
        List<Meal> meals = new ArrayList<>();

        Connection connection = DatabaseController.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT id FROM meals WHERE public = true ORDER BY name");

        ResultSet rs = statement.executeQuery();
        connection.close();

        while (rs.next()) {
            meals.add(findMealById(rs.getInt("id")));
        }

        return meals;
    }

    /**
     * Method to find the meals that a user has eaten on a specific day
     * @param user the user to find the meals eaten by
     * @param date the date to find the meals eaten on
     * @return a map of the meals eaten with the quantity
     * @throws SQLException
     */
    public static Map<Meal, Double> getUsersEatenMeals (User user, LocalDate date) throws SQLException {
        Map<Meal, Double> meals = new HashMap<>();

        if (!user.getConsumed().keySet().contains(date)) {
            return meals;
        }

        for (Map.Entry<Integer, Map<Meal, Double>> day : user.getConsumed().get(date).entrySet()) {
            for (Map.Entry<Meal, Double> meal : day.getValue().entrySet()) {
                meals.put(meal.getKey(), meal.getValue());
            }

        }

        return meals;
    }

    /**
     * Method to find the meals that a user has eaten at a time on a specific day
     * @param user the user to find the meals eaten by
     * @param date the date to find the meals eaten on
     * @param time the time that the meals were eaten. 1 = breakfast, 2 = lunch, 3 = dinner, 4 = snack
     * @return a map of the meals eaten with the quantity
     * @throws SQLException
     */
    public static Map<Meal, Double> getUsersEatenMeals (User user, LocalDate date, int time) throws SQLException {
        Map<Meal, Double> meals = new HashMap<>();

        if (!user.getConsumed().keySet().contains(date)) {
            return meals;
        } else if (!user.getConsumed().get(date).keySet().contains(time)) {
            return meals;
        }

        for (Map.Entry<Meal, Double> meal : user.getConsumed().get(date).get(time).entrySet()) {
            meals.put(meal.getKey(), meal.getValue());
        }

        return meals;
    }

    /**
     * Method to find the number of calories that a user has consumed on a specific day
     * @param user the user to find the calories consumed for
     * @param date the date to find the calories consumed on
     * @return  the number of calories that the user consumed on a day
     * @throws SQLException
     */
    public static int getUsersConsumedCalories (User user, LocalDate date) throws SQLException {
        int calories = 0;

        for (Map.Entry<Meal, Double> meal : getUsersEatenMeals(user, date).entrySet()) {
            calories += MealController.calculateCalories(meal.getKey()) * meal.getValue();
        }

        return calories;
    }

    /**
     * Method to find the number of calories that a user has consumed for the past days
     * @param user the user to find the calories consumed for
     * @param days the number of days to return
     * @return a map of the day and calories consumed on that day
     * @throws SQLException
     */
    public static Map<LocalDate, Integer> getUsersConsumedCalories (User user, int days) throws SQLException {
        Map<LocalDate, Integer> calories = new TreeMap<>();

        for (int i = days; i >= 0; i--) {
            calories.put(LocalDate.now().minusDays(i), getUsersConsumedCalories(user, LocalDate.now().minusDays(i)));
        }

        return calories;
    }

    /**
     * Method to find the amount of protein that a user has consumed on a specific day
     * @param user the user to find the protein consumed for
     * @param date the date to find the protein consumed on
     * @return  the amount of protein that the user consumed on a day
     * @throws SQLException
     */
    public static int getUsersConsumedProtein (User user, LocalDate date) throws SQLException {
        int protein = 0;

        for (Map.Entry<Meal, Double> meal : getUsersEatenMeals(user, date).entrySet()) {
            protein += MealController.calculateProtein(meal.getKey()) * meal.getValue();
        }

        return protein;
    }

    /**
     * Method to find the amount of fat that a user has consumed on a specific day
     * @param user the user to find the fat consumed for
     * @param date the date to find the fat consumed on
     * @return  the amount of fat that the user consumed on a day
     * @throws SQLException
     */
    public static int getUsersConsumedFat (User user, LocalDate date) throws SQLException {
        int fat = 0;

        for (Map.Entry<Meal, Double> meal : getUsersEatenMeals(user, date).entrySet()) {
            fat += MealController.calculateFat(meal.getKey()) * meal.getValue();
        }

        return fat;
    }

    /**
     * Method to find the amount of carbohydrates that a user has consumed on a specific day
     * @param user the user to find the carbohydrates consumed for
     * @param date the date to find the carbohydrates consumed on
     * @return  the amount of carbohydrates that the user consumed on a day
     * @throws SQLException
     */
    public static int getUsersConsumedCarbohydrates (User user, LocalDate date) throws SQLException {
        int carbohydrates = 0;

        for (Map.Entry<Meal, Double> meal : getUsersEatenMeals(user, date).entrySet()) {
            carbohydrates += MealController.calculateCarbohydrates(meal.getKey()) * meal.getValue();
        }

        return carbohydrates;
    }

    /**
     * Method to dissociate a meal with a user's account (so it still exists but not with a user's account)
     * @param user the user who is currently the author of the meal
     * @param meal the meal to dissociate with it's author
     * @throws SQLException
     */
    public static void dissociateMeal(User user, Meal meal) throws SQLException {
        Connection connection = DatabaseController.getConnection();
        PreparedStatement statement = connection.prepareStatement("UPDATE meals SET author = ? WHERE id = ?");
        statement.setString(1, "ActiFit");
        statement.setInt(2, meal.getId());
        int affectedRows = statement.executeUpdate();
        connection.close();
        if (affectedRows == 0) {
            throw new SQLException("Dissociating the meal failed, no rows were affected");
        }
        user.removeMeal(meal);
    }
}
