package Controller;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthenticationController {

    /**
     * Method to hash a given password string.
     * @param password  a user password string
     * @return          a hashed password string
     */
    public static String hashPassword(String password) {
        String secret = "c73etUxu7exetEFu";
        Key key = new SecretKeySpec(secret.getBytes(), "AES");
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        byte [] encryptedData = new byte[0];
        try {
            encryptedData = cipher.doFinal(password.getBytes());
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return new String(encryptedData);
    }

    /**
     * Method to check a user's credentials for login validation. Comparing the given password and username to check
     * whether they match the stored details for an account.
     * @param username      a username string
     * @param password      a password string
     * @return              a boolean of whether the password and username match a user's stored information
     */
    public static boolean checkCredentials(String username, String password) throws SQLException {
        String encrypted = hashPassword(password);
        String usernameLowercase = username.toLowerCase();
        Connection connection = DatabaseController.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT username FROM users " +
                "WHERE password_hash = ? AND username ILIKE ?");
        statement.setString(1, encrypted);
        statement.setString(2, usernameLowercase);
        ResultSet rs = statement.executeQuery();
        connection.close();
        try {
            while (rs.next()){
                return true;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Method to check the validity of an email address, checking first whether it has already been used to create
     * a user account and then checking the email address matches a set regex pattern.
     * @param email an email address string
     * @return      a boolean of whether the given email string is not previously used and of a valid format
     */
    public static boolean validateEmail(String email) throws SQLException {
        final String REGEX = "[a-zA-Z]+[@][a-zA-Z]+[.][a-zA-Z]+";
        String emailLowercase = email.toLowerCase();
        Connection connection = DatabaseController.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT email FROM users WHERE email = ?");
        statement.setString(1, emailLowercase);
        ResultSet rs = statement.executeQuery();
        connection.close();
        try {
            if (rs.next()){
                return false;
            }
            else {
                Pattern pattern = Pattern.compile(REGEX);
                Matcher matcher = pattern.matcher(email);
                if (matcher.matches()){
                    return true;
                }
                else {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Method to check the validity of a username string, checking whether the username is already associated with
     * an existing user.
     * @param username  a username string
     * @return          a boolean of whether the username is valid (is not already associated with an existing user)
     */
    public static boolean validateUsername(String username) throws SQLException {
        String usernameLowercase = username.toLowerCase();
        Connection connection = DatabaseController.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT username FROM users WHERE username ILIKE ?");
        statement.setString(1, usernameLowercase);
        ResultSet rs = statement.executeQuery();
        connection.close();
        try {
            if (rs.next()){
                return false;
            }
            else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
