package Controller;

import Model.Group;
import Model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class GroupControllerTest {

    @After
    public void tearDown() throws Exception {
        Connection connection = DatabaseController.getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM groups WHERE name LIKE 'Test Group%'");
        statement.executeUpdate();
        statement = connection.prepareStatement("DELETE FROM users WHERE username LIKE 'TestUser%'");
        statement.executeUpdate();
        statement = connection.prepareStatement("DELETE FROM users_groups WHERE username LIKE 'TestUser%'");
        statement.executeUpdate();
        connection.close();
    }

    @Test
    public void newGroup() throws Exception {
        Group testGroup = GroupController.newGroup("Test Group");
        assertEquals("Test Group", testGroup.getName());
    }

    @Test
    public void saveGroup() throws Exception {
        String newGroupName = "";

        Group testGroup = GroupController.newGroup("Test Group");
        testGroup.setName("Test Group New Name");
        GroupController.saveGroup(testGroup);

        Connection connection = DatabaseController.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT name FROM groups WHERE id = ?");
        statement.setInt(1, testGroup.getId());

        ResultSet rs = statement.executeQuery();
        if(rs.next()) {
            assertEquals("Test Group New Name", rs.getString("name"));
        } else {
            fail();
        }
    }


    @Test
    public void removeGroup() throws Exception {
        Group testGroup = GroupController.newGroup("Test Group");
        GroupController.removeGroup(testGroup);

        Connection connection = DatabaseController.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT name FROM groups WHERE id = ?");
        statement.setInt(1, testGroup.getId());

        ResultSet rs = statement.executeQuery();
        if(rs.next()) {
            fail();
        } else {
            return;
        }
    }

    @Test
    public void findGroupById() throws Exception {
        Group testGroup = GroupController.newGroup("Test Group");
        int groupId = testGroup.getId();
        assertEquals("Test Group", GroupController.findGroupById(groupId).getName());
    }

    @Test
    public void getGroups() throws Exception {
        User testUser = UserController.newUser("TestUser", "test@test.com", "Test", "Smith", LocalDate.now(), 123, 432.1, "password", User.Gender.M);
        Group testGroup1 = GroupController.newGroup("Test Group 1");
        Group testGroup2 = GroupController.newGroup("Test Group 2");
        Group testGroup3 = GroupController.newGroup("Test Group 3");
        testGroup1.addUser(testUser);
        testGroup2.addUser(testUser);
        testGroup3.addUser(testUser);
        GroupController.saveGroup(testGroup1);
        GroupController.saveGroup(testGroup2);
        GroupController.saveGroup(testGroup3);

        assertEquals(3, GroupController.getGroups(testUser).size());
    }
}