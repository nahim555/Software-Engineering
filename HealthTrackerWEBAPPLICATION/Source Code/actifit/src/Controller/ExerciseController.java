package Controller;

import Model.Exercise;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExerciseController {

    /**
     * Method to get the MET value of an exercise
     * @param exercise the exercise to get the MET value of
     * @param intensity the intensity or speed (in KM/H) that the activity was completed at
     * @return the MET value
     */
    public static double getMETValue(Exercise exercise, double intensity) {
        return (intensity * exercise.getMultiplier()) + exercise.getAdjustment();
    }

    /**
     * Method to return an exercise given it's name
     * @param name the name of the exercise
     * @return the exercise object
     * @throws SQLException
     */
    public static Exercise findExerciseByName(String name) throws SQLException {
        Exercise exercise;

        // Fetching the exercise data from the database
        Connection connection = DatabaseController.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT name, id, multiplier, adjustment, measurable FROM exercises WHERE name = ?");
        statement.setString(1, name);

        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            exercise = new Exercise(rs.getString("name"), rs.getInt("id"), rs.getDouble("multiplier"),
                    rs.getDouble("adjustment"), rs.getBoolean("measurable"));
        } else {
            throw new SQLException("Retrieving the exercise failed");
        }

        connection.close();

        return exercise;
    }

    /**
     * Method to return an exercise given it's ID
     * @param id the ID of the exercise
     * @return the exercise object
     * @throws SQLException
     */
    public static Exercise findExerciseById(int id) throws SQLException {
        Exercise exercise;

        // Fetching the exercise data from the database
        Connection connection = DatabaseController.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT name, id, multiplier, adjustment, measurable FROM exercises WHERE id = ?");
        statement.setInt(1, id);

        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            exercise = new Exercise(rs.getString("name"), rs.getInt("id"), rs.getDouble("multiplier"),
                    rs.getDouble("adjustment"), rs.getBoolean("measurable"));
        } else {
            throw new SQLException("Retrieving the exercise failed");
        }

        connection.close();

        return exercise;
    }

    /**
     * Method to get all the available exercises
     * @return the list of all the exercises available
     * @throws SQLException
     */
    public static List<Exercise> getExercises() throws SQLException {
        List<Exercise> exercises = new ArrayList<>();

        // Fetch the group ids of all the groups that the user is in
        Connection connection = DatabaseController.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT name, id, multiplier, adjustment, measurable FROM exercises ORDER BY name");
        ResultSet rs = statement.executeQuery();

        // Add the group ids to the list
        while (rs.next()) {
            exercises.add(new Exercise(rs.getString("name"), rs.getInt("id"), rs.getDouble("multiplier"),
                    rs.getDouble("adjustment"), rs.getBoolean("measurable")));
        }

        connection.close();

        return exercises;
    }

    /**
     * Method to create a new exercise and save it to the database
     * @param name the name of the new exercise
     * @param multiplier the amount to mutliply the intensity (speed) by to get the MET value
     * @param adjustment the amount to add to the previous value to the final MET value
     * @return the newly created exercise
     * @throws SQLException
     */
    public static Exercise createNewExercise(String name, double multiplier, double adjustment, boolean measurable) throws SQLException {

        Connection connection = DatabaseController.getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO exercises (name, multiplier, adjustment, measurable) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, name);
        statement.setDouble(2, multiplier);
        statement.setDouble(3, adjustment);
        statement.setBoolean(4, measurable);

        int affectedRows = statement.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating the exercise failed, no rows were affected");
        }

        connection.close();

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return new Exercise(name, generatedKeys.getInt("id"), multiplier, adjustment, measurable);
            } else {
                throw new SQLException("Creating the exercise failed, no ID obtained");
            }
        }
    }
}
