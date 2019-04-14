package Controller;

import Model.Food;
import Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FoodController {

    /**
     * Method to find a food by it's ID
     * @param id the ID of the food to find
     * @return the food object of the given ID
     * @throws SQLException
     */
    public static Food findFoodById(int id) throws SQLException {
        Connection connection = DatabaseController.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT name, calories, protein, fat, carbohydrates, " +
                "serving_size, serving_name, public FROM foods WHERE id = ?");
        statement.setInt(1, id);

        ResultSet rs = statement.executeQuery();
        connection.close();

        if (rs.next()) {
            return new Food(rs.getString("name"), id, rs.getDouble("calories"),
                    rs.getDouble("protein"), rs.getDouble("fat"),
                    rs.getDouble("carbohydrates"), rs.getInt("serving_size"),
                    rs.getString("serving_name"),
                    rs.getBoolean("public"));
        } else {
            throw new SQLException("Retrieving the food failed");
        }
    }

    /**
     * Method to get all the public foods in the database
     * @return a list of all the public foods in the database
     * @throws SQLException
     */
    public static List<Food> getPublicFoods() throws SQLException {
        List<Food> foods = new ArrayList<>();

        Connection connection = DatabaseController.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT id, name, calories, protein, fat, carbohydrates, " +
                "serving_size, serving_name FROM foods WHERE public = true");

        ResultSet rs = statement.executeQuery();
        connection.close();

        while (rs.next()) {
            foods.add(new Food(rs.getString("name"), rs.getInt("id"),
                    rs.getDouble("calories"), rs.getDouble("protein"), rs.getDouble("fat"),
                    rs.getDouble("carbohydrates"), rs.getInt("serving_size"),
                    rs.getString("serving_name"),
                    true));
        }

        return foods;
    }

    /**
     * Method to dissociate a food with a user's account (so it still exists but not with a user's account)
     * @param user the user who is currently the author of the food
     * @param food the food to dissociate with it's author
     * @throws SQLException
     */
    public static void dissociateFood(User user, Food food) throws SQLException {
        Connection connection = DatabaseController.getConnection();
        PreparedStatement statement = connection.prepareStatement("UPDATE foods SET author = ? WHERE id = ?");
        statement.setString(1, "ActiFit");
        statement.setInt(2, food.getId());
        int affectedRows = statement.executeUpdate();
        connection.close();
        if (affectedRows == 0) {
            throw new SQLException("Dissociating the food failed, no rows were affected");
        }
        user.removeFood(food);
    }
}
