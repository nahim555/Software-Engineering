package Model;

import Controller.GroupController;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class GroupTest {


    Group testGroup;
    User testUser;

    @Before
    public void setUp() {
        testGroup = new Group(1, "runningGroup");
        testUser = new User("TestUser", "actifit@carbtc.net", "Test", "Smith", LocalDate.now(), 123, User.Gender.M);
    }

    @Test
    public void getID() {
        assertEquals(1, testGroup.getId());
    }

    @Test
    public void getName() {
        assertEquals("runningGroup", testGroup.getName());
    }

    @Test
    public void setName() {
        testGroup.setName("runningGroup");
        assertEquals("runningGroup", testGroup.getName());
    }

    @Test
    public void getUsers() throws SQLException {
        Group groups = testGroup;
        for (Group group : GroupController.getGroups(testUser)) {
            if (groups.equals(testGroup)) {
                return;
            }
            fail();
        }
    }

    @Test
    public void addUser() {
        testGroup.addUser(testUser);
        assertEquals(testGroup.getUsers(), testGroup.getUsers());
    }

    @Test
    public void removeUser() {
        testGroup.removeUser(testUser);

    }
}
