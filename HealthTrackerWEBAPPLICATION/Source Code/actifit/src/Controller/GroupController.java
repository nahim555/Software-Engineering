package Controller;

import Model.Activity;
import Model.Group;
import Model.User;

import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

public class GroupController {

    /**
     * Method to create a new group
     * @param name the name of the new group
     * @return the new group
     * @throws SQLException
     */
    public static Group newGroup(String name) throws SQLException {
        Connection connection = DatabaseController.getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO groups (name) VALUES (?)",
                Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, name);

        int affectedRows = statement.executeUpdate();
        connection.close();

        if (affectedRows == 0) {
            throw new SQLException("Creating the group failed, no rows were affected");
        }

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return new Group(generatedKeys.getInt("id"), name);
            } else {
                throw new SQLException("Creating the group failed, no ID obtained");
            }
        }
    }

    /**
     * Method to save a group to the database
     * @param group the group to save
     * @throws SQLException
     */
    public static void saveGroup(Group group) throws SQLException {
        Connection connection = DatabaseController.getConnection();

        // Save to the group table
        PreparedStatement statement = connection.prepareStatement("UPDATE groups SET name = ? WHERE id = ?");
        statement.setString(1, group.getName());
        statement.setInt(2, group.getId());

        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Saving the group failed, no rows were affected");
        }

        // Remove all the user to group links
        statement = connection.prepareStatement("DELETE FROM users_groups WHERE group_id = ?");
        statement.setInt(1, group.getId());
        statement.executeUpdate();

        // Save the user to group links
        for (User user : group.getUsers()) {
            statement = connection.prepareStatement("INSERT INTO users_groups (username, group_id) VALUES (?, ?)");
            statement.setString(1, user.getUsername());
            statement.setInt(2, group.getId());
            statement.executeUpdate();
        }

        connection.close();

    }

    /**
     * Method to remove a group from the database
     * @param group the group to remove
     * @throws SQLException
     */
    public static void removeGroup(Group group) throws SQLException {
        Connection connection = DatabaseController.getConnection();

        // Remove all the user to group links
        PreparedStatement statement = connection.prepareStatement("DELETE FROM users_groups WHERE group_id = ?");
        statement.setInt(1, group.getId());
        statement.executeUpdate();

        // Remove the group
        statement = connection.prepareStatement("DELETE FROM groups WHERE id = ?");
        statement.setInt(1, group.getId());
        int affectedRows = statement.executeUpdate();
        connection.close();
        if (affectedRows == 0) {
            throw new SQLException("Removing the group failed, no rows were affected");
        }
    }

    /**
     * Method to find a group by its ID
     * @param id the ID of the group to find
     * @return the group of the given ID
     * @throws SQLException
     */
    public static Group findGroupById(int id) throws SQLException {
        Group group;

        // Fetching the group data from the database
        Connection connection = DatabaseController.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT name FROM groups WHERE id = ?");
        statement.setInt(1, id);

        ResultSet rs = statement.executeQuery();
        if(rs.next()) {
            group = new Group(id, rs.getString("name"));
        } else {
            throw new SQLException("Retrieving the group failed");
        }

        // Adding the users to the group
        statement = connection.prepareStatement("SELECT username FROM users_groups WHERE group_id = ?");
        statement.setInt(1, id);
        rs = statement.executeQuery();

        while (rs.next()) {
            group.addUser(UserController.fetchUser(rs.getString("username")));
        }

        connection.close();

        return group;
    }

    /**
     * Method to get a list of the group ids that the given user is a member of
     * @param user the user to check to get the groups of
     * @return the list of group ids that the user is a member of
     * @throws SQLException
     */
    public static List<Group> getGroups(User user) throws SQLException {

        List<Group> groups = new ArrayList<>();

        // Fetch the group ids of all the groups that the user is in
        Connection connection = DatabaseController.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT group_id FROM users_groups WHERE username = ?");
        statement.setString(1, user.getUsername());
        ResultSet rs = statement.executeQuery();

        // Add the group ids to the list
        while (rs.next()) {
            groups.add(findGroupById(rs.getInt("group_id")));
        }

        connection.close();

        return groups;
    }

    /**
     * Method to get the activities of all of the users in the group
     * @param group the group to get the activities of
     * @return a map of each of the user's and their activities today
     */
    public static Map<User, Activity> getTodaysActivities(Group group) {
        Map<User, Activity> activities =  new TreeMap<>();

        for (User user : group.getUsers()) {
            for (Activity activity : user.getActivities()) {
                if (activity.getDate().equals(LocalDate.now())) {
                    activities.put(user, activity);
                }
            }
        }

        return activities;
    }
}