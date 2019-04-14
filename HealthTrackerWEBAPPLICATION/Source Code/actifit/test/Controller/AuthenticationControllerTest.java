package Controller;

import Model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class AuthenticationControllerTest {

    User testUser;

    @Before
    public void setUp() throws Exception {
        testUser = UserController.newUser("TestUser", "test@test.com", "Test", "Smith", LocalDate.now(), 123, 432.1, "password", User.Gender.M);
    }

    @After
    public void tearDown() throws Exception {
        Connection connection = DatabaseController.getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE username LIKE 'TestUser%'");
        statement.executeUpdate();
        connection.close();
    }

    @Test
    public void hashPassword_DifferentStringGivesDifferentHash() {
        String testString1 = "password";
        String hash1 = AuthenticationController.hashPassword(testString1);
        String testString2 = "notpassword";
        String hash2 = AuthenticationController.hashPassword(testString2);
        assertNotEquals(hash1, hash2);
    }

    @Test
    public void hashPassword_HashNotEqualInput() {
        String testString1 = "password";
        String hash1 = AuthenticationController.hashPassword(testString1);
        assertNotEquals(hash1, testString1);
    }

    @Test
    public void checkCredentials() throws SQLException {
        boolean result = AuthenticationController.checkCredentials("TestUser", "password");
        assertTrue(result);
    }

    @Test
    public void validateEmailValid() throws SQLException {
        boolean test = AuthenticationController.validateEmail("nottest@test.com");
        assertTrue(test);
    }

    @Test
    public void validateEmailInvalid() throws SQLException {
        boolean test = AuthenticationController.validateEmail("test@test.com");
        assertFalse(test);
    }

    @Test
    public void validateUsernameValid() throws SQLException {
        boolean result = AuthenticationController.validateUsername("NotTestUser");
        assertTrue(result);
    }

    @Test
    public void validateUsernameInvalid() throws SQLException {
        boolean result = AuthenticationController.validateUsername("testUser");
        assertFalse(result);
    }

}