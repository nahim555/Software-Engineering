package Controller;

import Model.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.sql.*;

public class UserController {

    /**
     * Method to add a new user to the database
     * @param username
     * @param email
     * @param firstName
     * @param lastName
     * @param dateOfBirth
     * @param height
     * @param weight
     * @param password
     * @param gender
     * @return an object of the new user
     * @throws Exception
     */
    public static User newUser(String username, String email, String firstName, String lastName, LocalDate dateOfBirth,
                               int height, double weight, String password, User.Gender gender) throws Exception {

        // Check that the username is available
        if (!AuthenticationController.validateUsername(username)) {
            throw new Exception("Creating the user failed, the username is not available");
        }

        // Attempt to the user to the database
        Connection connection = DatabaseController.getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO users (username, email, " +
                "first_name, last_name, date_of_birth, height, gender, password_hash) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        statement.setString(1, username);
        statement.setString(2, email);
        statement.setString(3, firstName);
        statement.setString(4, lastName);
        statement.setDate(5, Date.valueOf(dateOfBirth));
        statement.setInt(6, height);
        statement.setString(7, gender.name());
        statement.setString(8, AuthenticationController.hashPassword(password));
        int affectedRows = statement.executeUpdate();
        connection.close();

        // Check if the user was added
        if (affectedRows == 0) {
            throw new SQLException("Creating the user failed, no rows were affected");
        }

        // Create the user object
        User user = new User(username, email, firstName, lastName, dateOfBirth, height, gender);

        // Add the users current weight
        user.addWeight(LocalDate.now(), weight);
        persistWeights(user);

        // Send the user a welcome email
        sendNewAccountEmail(user);

        return user;
    }

    /**
     * Method to fetch a user from the database
     * @param username the username of the user to fetch
     * @return the user of the given username
     * @throws SQLException
     */
    public static User fetchUser(String username) throws SQLException {
        Connection connection = DatabaseController.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT username, email, first_name, last_name, date_of_birth, height, gender FROM users WHERE username ILIKE ?");
        statement.setString(1, username);
        ResultSet rs = statement.executeQuery();
        connection.close();

        if (rs.next()) {
            User user = new User(
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getDate("date_of_birth").toLocalDate(),
                    rs.getInt("height"),
                    User.Gender.valueOf(rs.getString("gender"))
                    );

            // Get all the other information for the user
            fetchWeights(user);
            fetchActivities(user);
            fetchGoals(user);
            fetchConsumedMeals(user);
            fetchFoods(user);
            fetchMeals(user);

            return user;
        } else {
            throw new SQLException("Fetching the user failed, no rows were returned");
        }
    }

    /**
     * Method to save a users state to the database
     * @param user the user to save
     * @throws SQLException
     */
    public static void persistUser(User user) throws SQLException {
        Connection connection = DatabaseController.getConnection();
        PreparedStatement statement = connection.prepareStatement("UPDATE users SET email = ?, first_name = ?, last_name = ?, date_of_birth = ?, height = ?, gender = ? WHERE username = ?");
        statement.setString(1, user.getEmail());
        statement.setString(2, user.getFirstName());
        statement.setString(3, user.getLastName());
        statement.setDate(4, Date.valueOf(user.getDateOfBirth()));
        statement.setInt(5, user.getHeight());
        statement.setString(6, user.getGender().name());
        statement.setString(7, user.getUsername());
        int affectedRows = statement.executeUpdate();
        connection.close();
        if (affectedRows == 0) {
            throw new SQLException("Persisting the user failed, no rows were affected");
        }
    }

    /**
     * Method to remove a user from the database
     * @param user the user to remove
     * @throws SQLException
     */
    public static void removeUser(User user) throws SQLException {
        removeWeights(user);
        removeActivities(user);
        removeGoals(user);
        removeConsumedMeals(user);
        removeMeals(user);
        removeFoods(user);

        Connection connection = DatabaseController.getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE username = ?");
        statement.setString(1, user.getUsername());
        int affectedRows = statement.executeUpdate();
        connection.close();
        if (affectedRows == 0) {
            throw new SQLException("Removing the user failed, no rows were affected");
        }
    }

    /**
     * Method to update the user's weights with what is stored on the database
     * @param user the user to get the weights of
     * @throws SQLException
     */
    public static void fetchWeights(User user) throws SQLException {
        user.clearWeights();

        Connection connection = DatabaseController.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT weight, recorded FROM users_weights WHERE username = ? ORDER BY recorded");
        statement.setString(1, user.getUsername());
        ResultSet rs = statement.executeQuery();
        connection.close();

        while (rs.next()) {
            user.addWeight(rs.getDate("recorded").toLocalDate(), rs.getDouble("weight"));
        }
    }

    /**
     * Method to save the user's weights to the database
     * @param user the user to save the weights of
     * @throws SQLException
     */
    public static void persistWeights(User user) throws SQLException {
        removeWeights(user);

        Connection connection = DatabaseController.getConnection();

        for (Map.Entry<LocalDate, Double> weight : user.getWeights().entrySet()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users_weights (username, weight, recorded) VALUES (?, ?, ?)");
            statement.setString(1, user.getUsername());
            statement.setDouble(2, weight.getValue());
            statement.setDate(3, Date.valueOf(weight.getKey()));

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Persisting the user's weights failed, no rows were affected");
            }
        }

        connection.close();
    }

    /**
     * Method to remove all of a user's weights from the database
     * @param user the user to remove the weights of
     * @throws SQLException
     */
    private static void removeWeights(User user) throws SQLException {
        Connection connection = DatabaseController.getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM users_weights WHERE username = ?");
        statement.setString(1, user.getUsername());
        statement.executeUpdate();
        connection.close();
    }

    /**
     * Method to update the user's activities with what is stored on the database
     * @param user the user to get the activities of
     * @throws SQLException
     */
    public static void fetchActivities(User user) throws SQLException {
        user.clearActivities();

        Connection connection = DatabaseController.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT exercise, duration, intensity, timestamp FROM activities WHERE username = ?");
        statement.setString(1, user.getUsername());
        ResultSet rs = statement.executeQuery();
        connection.close();

        while (rs.next()) {
            user.addActivity(new Activity(
                    ExerciseController.findExerciseById(rs.getInt("exercise")),
                    rs.getDate("timestamp").toLocalDate(),
                    rs.getInt("duration"),
                    rs.getInt("intensity")
            ));
        }
    }

    /**
     * Method to save the user's activities to the database
     * @param user the user to save the activities of
     * @throws SQLException
     */
    public static void persistActivities(User user) throws SQLException {
        removeActivities(user);

        Connection connection = DatabaseController.getConnection();

        for (Activity activity : user.getActivities()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO activities (username, exercise, duration, intensity, timestamp) VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, user.getUsername());
            statement.setInt(2, activity.getExercise().getId());
            statement.setInt(3, activity.getDuration());
            statement.setInt(4, activity.getIntensity());
            statement.setDate(5, Date.valueOf(activity.getDate()));

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Persisting the user's activities failed, no rows were affected");
            }
        }

        connection.close();
    }

    /**
     * Method to remove all of a user's activities from the database
     * @param user the user to remove the activities of
     * @throws SQLException
     */
    private static void removeActivities(User user) throws SQLException {
        Connection connection = DatabaseController.getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM activities WHERE username = ?");
        statement.setString(1, user.getUsername());
        statement.executeUpdate();
        connection.close();
    }

    /**
     * Method to update the user's goals with what is stored on the database
     * @param user the user to get the goals of
     * @throws SQLException
     */
    public static void fetchGoals(User user) throws SQLException {
        user.clearGoals();

        Connection connection = DatabaseController.getConnection();

        // Get the weight loss goals

        PreparedStatement statement = connection.prepareStatement("SELECT starting_date, duration, starting_weight, target_weight FROM goals_weightloss WHERE username = ?");
        statement.setString(1, user.getUsername());
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            user.addGoal(new WeightLossGoal(
                    rs.getDate("starting_date").toLocalDate(),
                    rs.getInt("duration"),
                    rs.getDouble("starting_weight"),
                    rs.getDouble("target_weight")
            ));
        }

        // Get the distance goals

        statement = connection.prepareStatement("SELECT exercise, starting_date, duration, starting_distance, target_distance FROM goals_distance WHERE username = ?");
        statement.setString(1, user.getUsername());
        rs = statement.executeQuery();

        while (rs.next()) {
            user.addGoal(new DistanceGoal(
                    rs.getDate("starting_date").toLocalDate(),
                    rs.getInt("duration"),
                    ExerciseController.findExerciseById(rs.getInt("exercise")),
                    rs.getInt("starting_distance"),
                    rs.getInt("target_distance")
            ));
        }

        // Get the time goals

        statement = connection.prepareStatement("SELECT exercise, starting_date, duration, distance, starting_time, target_time FROM goals_time WHERE username = ?");
        statement.setString(1, user.getUsername());
        rs = statement.executeQuery();

        while (rs.next()) {
            user.addGoal(new TimeGoal(
                    rs.getDate("starting_date").toLocalDate(),
                    rs.getInt("duration"),
                    ExerciseController.findExerciseById(rs.getInt("exercise")),
                    rs.getInt("distance"),
                    rs.getInt("starting_time"),
                    rs.getInt("target_time")
            ));
        }

        connection.close();
    }

    /**
     * Method to save the user's goals to the database
     * @param user the user to save the goals of
     * @throws SQLException
     */
    public static void persistGoals(User user) throws SQLException {
        removeGoals(user);

        Connection connection = DatabaseController.getConnection();

        for (Goal goal : user.getGoals()) {

            if (goal instanceof WeightLossGoal) {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO goals_weightloss (username, starting_date, duration, starting_weight, target_weight) VALUES (?, ?, ?, ?, ?)");
                statement.setString(1, user.getUsername());
                statement.setDate(2, Date.valueOf(goal.getStartingDate()));
                statement.setInt(3, goal.getDuration());
                statement.setDouble(4, ((WeightLossGoal) goal).getStartingWeight());
                statement.setDouble(5, ((WeightLossGoal) goal).getTargetWeight());
                statement.executeUpdate();
            } else if (goal instanceof DistanceGoal) {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO goals_distance (username, exercise, starting_date, duration, starting_distance, target_distance) VALUES (?, ?, ?, ?, ?, ?)");
                statement.setString(1, user.getUsername());
                statement.setInt(2, ((DistanceGoal) goal).getExercise().getId());
                statement.setDate(3, Date.valueOf(goal.getStartingDate()));
                statement.setInt(4, goal.getDuration());
                statement.setInt(5, ((DistanceGoal) goal).getPreviousBestDistance());
                statement.setInt(6, ((DistanceGoal) goal).getTargetDistance());
                statement.executeUpdate();
            } else if (goal instanceof TimeGoal) {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO goals_time (username, exercise, starting_date, duration, distance, starting_time, target_time) VALUES (?, ?, ?, ?, ?, ?, ?)");
                statement.setString(1, user.getUsername());
                statement.setInt(2, ((TimeGoal) goal).getExercise().getId());
                statement.setDate(3, Date.valueOf(goal.getStartingDate()));
                statement.setInt(4, goal.getDuration());
                statement.setInt(5, ((TimeGoal) goal).getDistance());
                statement.setInt(6, ((TimeGoal) goal).getPreviousBestTime());
                statement.setInt(7, ((TimeGoal) goal).getTargetTime());
                statement.executeUpdate();
            }
        }

        connection.close();
    }

    /**
     * Method to remove all of a user's goals from the database
     * @param user the user to remove the goals of
     * @throws SQLException
     */
    private static void removeGoals(User user) throws SQLException {
        Connection connection = DatabaseController.getConnection();

        PreparedStatement statement = connection.prepareStatement("DELETE FROM goals_weightloss WHERE username = ?");
        statement.setString(1, user.getUsername());
        statement.executeUpdate();

        statement = connection.prepareStatement("DELETE FROM goals_distance WHERE username = ?");
        statement.setString(1, user.getUsername());
        statement.executeUpdate();

        statement = connection.prepareStatement("DELETE FROM goals_time WHERE username = ?");
        statement.setString(1, user.getUsername());
        statement.executeUpdate();

        connection.close();
    }

    /**
     * Method to update the user's foods with what is stored on the database
     * @param user the user to get the foods of
     * @throws SQLException
     */
    public static void fetchFoods(User user) throws SQLException {
        user.clearFoods();

        Connection connection = DatabaseController.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT id, name, calories, protein, fat, carbohydrates, serving_size, serving_name, public FROM foods WHERE author = ? ORDER BY name");
        statement.setString(1, user.getUsername());
        ResultSet rs = statement.executeQuery();
        connection.close();

        while (rs.next()) {
            user.addFood(new Food(
                    rs.getString("name"),
                    rs.getInt("id"),
                    rs.getDouble("calories"),
                    rs.getDouble("protein"),
                    rs.getDouble("fat"),
                    rs.getDouble("carbohydrates"),
                    rs.getInt("serving_size"),
                    rs.getString("serving_name"),
                    rs.getBoolean("public")
            ));
        }
    }

    /**
     * Method to save the user's foods to the database
     * @param user the user to save the foods of
     * @throws SQLException
     */
    public static void persistFoods(User user) throws SQLException {
        Connection connection = DatabaseController.getConnection();

        // Getting the IDs of the foods in the User object that aren't new
        ArrayList<Integer> updateIDs = new ArrayList<>();
        for (Food food : user.getFoods()) {
            if (food.getId() != 0) {
                updateIDs.add(food.getId());
            }
        }

        // Getting the IDs of the foods currently in the database
        ArrayList<Integer> removeIDs = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT id FROM foods WHERE author = ?");
        statement.setString(1, user.getUsername());
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            removeIDs.add(rs.getInt("id"));
        }

        // Getting the list of IDs to remove and update
        removeIDs.removeAll(updateIDs);
        updateIDs.removeAll(removeIDs);

        // Removing foods
        for (Integer id : removeIDs) {
            statement = connection.prepareStatement("DELETE FROM foods WHERE id = ?");
            statement.setInt(1, id);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Persisting the user's foods failed, no rows were affected");
            }
        }

        // Updating foods
        for (Integer id : updateIDs) {
            for (Food food : user.getFoods()) {
                if (food.getId() == id) {
                    statement = connection.prepareStatement("UPDATE foods SET name = ?, calories = ?, protein = ?, fat = ?, carbohydrates = ?, serving_size = ?, serving_name = ?, author = ?, public = ? WHERE id = ?");
                    statement.setString(1, food.getName());
                    statement.setDouble(2, food.getCalories());
                    statement.setDouble(3, food.getProtein());
                    statement.setDouble(4, food.getFat());
                    statement.setDouble(5, food.getCarbohydrates());
                    statement.setInt(6, food.getServingSize());
                    statement.setString(7, food.getServingName());
                    statement.setString(8, user.getUsername());
                    statement.setBoolean(9, food.isPublic());
                    statement.setInt(10, id);

                    int affectedRows = statement.executeUpdate();
                    if (affectedRows == 0) {
                        throw new SQLException("Persisting the user's foods failed, no rows were affected");
                    }
                    break;
                }
            }

        }

        // Inserting new foods to the database
        for (Food food : user.getFoods()) {
            if (food.getId() == 0) {
                statement = connection.prepareStatement("INSERT INTO foods (name, calories, protein, fat, carbohydrates, serving_size, serving_name, author, public) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
                statement.setString(1, food.getName());
                statement.setDouble(2, food.getCalories());
                statement.setDouble(3, food.getProtein());
                statement.setDouble(4, food.getFat());
                statement.setDouble(5, food.getCarbohydrates());
                statement.setInt(6, food.getServingSize());
                statement.setString(7, food.getServingName());
                statement.setString(8, user.getUsername());
                statement.setBoolean(9, food.isPublic());

                int affectedRows = statement.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Persisting the user's foods failed, no rows were affected");
                }
            }
        }

        connection.close();
    }

    /**
     * Method to remove all of a user's foods from the database
     * @param user the user to remove the foods of
     * @throws SQLException
     */
    private static void removeFoods(User user) throws SQLException {
        Connection connection = DatabaseController.getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM foods WHERE author = ?");
        statement.setString(1, user.getUsername());
        statement.executeUpdate();
        connection.close();
    }

    /**
     * Method to update the user's meals with what is stored on the database
     * @param user the user to get the meals of
     * @throws SQLException
     */
    public static void fetchMeals(User user) throws SQLException {
        user.clearMeals();

        Connection connection = DatabaseController.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT id FROM meals WHERE author = ? ORDER BY name");
        statement.setString(1, user.getUsername());
        ResultSet rs = statement.executeQuery();
        connection.close();

        while (rs.next()) {
            user.addMeal(MealController.findMealById(rs.getInt("id")));
        }
    }

    /**
     * Method to save the user's meals to the database
     * @param user the user to save the meals of
     * @throws SQLException
     */
    public static void persistMeals(User user) throws SQLException {
        Connection connection = DatabaseController.getConnection();

        // Getting the IDs of the meals in the User object that aren't new
        ArrayList<Integer> updateIDs = new ArrayList<>();
        for (Meal meal : user.getMeals()) {
            if (meal.getId() != 0) {
                updateIDs.add(meal.getId());
            }
        }

        // Getting the IDs of the meals currently in the database
        ArrayList<Integer> removeIDs = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT id FROM meals WHERE author = ?");
        statement.setString(1, user.getUsername());
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            removeIDs.add(rs.getInt("id"));
        }

        // Getting the list of IDs to remove and update
        removeIDs.removeAll(updateIDs);
        updateIDs.removeAll(removeIDs);

        // Removing meals
        for (Integer id : removeIDs) {
            statement = connection.prepareStatement("DELETE FROM meals WHERE id = ?");
            statement.setInt(1, id);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Persisting the user's meals failed, no rows were affected");
            }
        }

        // Updating meals
        for (Integer id : updateIDs) {
            for (Meal meal : user.getMeals()) {
                if (meal.getId() == id) {
                    statement = connection.prepareStatement("UPDATE meals SET name = ?, author = ?, public = ? WHERE id = ?");
                    statement.setString(1, meal.getName());
                    statement.setString(2, user.getUsername());
                    statement.setBoolean(3, meal.isPublic());
                    statement.setInt(4, id);

                    int affectedRows = statement.executeUpdate();
                    if (affectedRows == 0) {
                        throw new SQLException("Persisting the user's meals failed, no rows were affected");
                    }
                    break;
                }
            }

        }

        // Inserting new meals to the database
        for (Meal meal : user.getMeals()) {
            if (meal.getId() == 0) {
                statement = connection.prepareStatement("INSERT INTO meals (name, author, public) VALUES (?, ?, ?)");
                statement.setString(1, meal.getName());
                statement.setString(2, user.getUsername());
                statement.setBoolean(3, meal.isPublic());

                int affectedRows = statement.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Persisting the user's meals failed, no rows were affected");
                }
            }
        }

        connection.close();
    }

    /**
     * Method to remove all of a user's meals from the database
     * @param user the user to remove the meals of
     * @throws SQLException
     */
    private static void removeMeals(User user) throws SQLException {
        for (Meal meal : user.getMeals()) {
            MealController.removeMeal(meal);
        }
    }

    /**
     * Method to update the user's meals eaten with what is stored on the database
     * @param user the user to get the meals eaten for
     * @throws SQLException
     */
    public static void fetchConsumedMeals(User user) throws SQLException {
        user.clearConsumedMeals();

        Connection connection = DatabaseController.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT meal, quantity, time, eaten FROM users_meals WHERE username = ?");
        statement.setString(1, user.getUsername());
        ResultSet rs = statement.executeQuery();
        connection.close();

        while (rs.next()) {
            user.addConsumedMeal(rs.getDate("eaten").toLocalDate(), rs.getInt("time"), MealController.findMealById(rs.getInt("meal")), rs.getDouble("quantity"));
        }
    }

    /**
     * Method to save the user's meals eaten to the database
     * @param user the user to save the meals eaten of
     * @throws SQLException
     */
    public static void persistConsumedMeals(User user) throws SQLException {
        removeConsumedMeals(user);

        Connection connection = DatabaseController.getConnection();

        for (Map.Entry<LocalDate, Map<Integer, Map<Meal, Double>>> entry1 : user.getConsumed().entrySet()) {
            for (Map.Entry<Integer, Map<Meal, Double>> entry2 : entry1.getValue().entrySet()) {
                for (Map.Entry<Meal, Double> entry3 : entry2.getValue().entrySet()) {
                    PreparedStatement statement = connection.prepareStatement("INSERT INTO users_meals (username, meal, quantity, time, eaten) VALUES (?, ?, ?, ?, ?)");
                    statement.setString(1, user.getUsername());
                    statement.setInt(2, entry3.getKey().getId());
                    statement.setDouble(3, entry3.getValue());
                    statement.setInt(4, entry2.getKey());
                    statement.setDate(5, Date.valueOf(entry1.getKey()));

                    int affectedRows = statement.executeUpdate();
                    if (affectedRows == 0) {
                        throw new SQLException("Persisting the user's eaten meals failed, no rows were affected");
                    }
                }
            }
        }
        connection.close();
    }

    /**
     * Method to remove all of a user's meals eaten from the database
     * @param user the user to remove the meals eaten of
     * @throws SQLException
     */
    private static void removeConsumedMeals(User user) throws SQLException {
        Connection connection = DatabaseController.getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM users_meals WHERE username = ?");
        statement.setString(1, user.getUsername());
        statement.executeUpdate();
        connection.close();
    }

    /**
     * Method to change a user's password
     * @param user the user whose password we should change
     * @param newPassword the new password which will be hashed and saved to the database
     * @throws SQLException
     */
    public static void changePassword(User user, String newPassword) throws SQLException {
        Connection connection = DatabaseController.getConnection();

        // Save to the user table
        PreparedStatement statement = connection.prepareStatement("UPDATE users SET password_hash = ? WHERE username = ?");
        statement.setString(1, AuthenticationController.hashPassword(newPassword));
        statement.setString(2, user.getUsername());

        int affectedRows = statement.executeUpdate();
        connection.close();
        if (affectedRows == 0) {
            throw new SQLException("Changing the user's password failed, no rows were affected");
        }
    }

    /**
     * Method to send the user an email when they first register
     * @param user the user to send the email to
     */
    public static void sendNewAccountEmail(User user) {
        StringBuilder content = new StringBuilder();
        content.append("Hi ").append(user.getFirstName()).append("!");
        content.append("\n\n");
        content.append("Thank you for registering with ActiFit!");
        content.append("\n\n");
        content.append("Your username is ").append(user.getUsername());

        Controller.sendEmail("no-reply", user.getEmail(), "Welcome to ActiFit!", content.toString());
    }

    /**
     * Method to find the date logged that is closest to a given date
     * @param user the user to find the weight of
     * @param date the date to find the weight closest to
     * @return the weight of the user at this date (or as close as we know)
     */
    public static double getWeightAtDate(User user, LocalDate date) {
        LocalDate closest = Collections.min(user.getWeights().keySet(), new Comparator<LocalDate>() {
            public int compare(LocalDate d1, LocalDate d2) {
                long diff1 = Math.abs(d1.toEpochDay() - date.toEpochDay());
                long diff2 = Math.abs(d2.toEpochDay() - date.toEpochDay());
                return Long.compare(diff1, diff2);
            }
        });
        return user.getWeights().get(closest);
    }

    /**
     * Method to find the BMR of a user
     * @param user the user to find the BMR of
     * @return the BMR of the user in kcal/day
     */
    public static int findBMR(User user) {
        return (int) Controller.calculateBMR(
                user.getHeight(), user.getMostRecentWeight(), user.getGender(), user.getDateOfBirth());
    }

    /**
     * Method to find the daily calorie goal of a user
     * @param user the user to find the calorie goal of
     * @return the number of calories that user should aim to consume in a day
     */
    public static int getCalorieGoal(User user) {
        int bmr = findBMR(user);

        for (Goal goal : user.getGoals()) {
            if (goal instanceof WeightLossGoal) {
                bmr -= GoalController.calculateDailyCalorieDeficit(user, (WeightLossGoal) goal);
                break;
            }
        }

        // If the calorie deficit is too low (less than 75% of user's BMR), then set it to 75% of user's BMR
        if ((double) bmr < 0.75 * (double) findBMR(user)) {
            bmr = (int) (0.75 * (double) findBMR(user));
        }

        return bmr;
    }

    /**
     * Method to find the age of a user
     * @param user the user to find the age of
     * @return the age of the user in years
     */
    public static int getAge(User user) {
        return Period.between(user.getDateOfBirth(), LocalDate.now()).getYears();
    }

    /**
     * Method to send a password reset email to a user
     * @param user the user to send the password reset email to
     * @return the password reset token that was generated
     */
    public static String sendPasswordResetEmail(User user) {
        String token = Controller.generateRandomString();

        StringBuilder content = new StringBuilder();
        content.append("Hi ").append(user.getFirstName()).append("!");
        content.append("\n\n");
        content.append("Here is your password reset token:");
        content.append("\n\n");
        content.append(token);

        Controller.sendEmail("no-reply", user.getEmail(), "ActiFit password reset token", content.toString());

        return token;
    }
}
