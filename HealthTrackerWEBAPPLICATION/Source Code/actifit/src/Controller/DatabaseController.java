package Controller;

import java.sql.*;

public class DatabaseController {

    static final String JDBC_DRIVER = "org.postgresql.Driver";

    static final String DB_URL = "jdbc:postgresql://ec2-54-247-101-205.eu-west-1.compute.amazonaws.com:5432/de6a94at52bs3j?sslmode=require";
    static final String USER = "etawbhyozlyxmk";
    static final String PASS = "522337ac114e28433126c8684fff98366ee92846f633648930a47c5eae64de0f";

    /**
     * Method to get the connection of the database
     * @return the connection
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(JDBC_DRIVER);
            return DriverManager.getConnection(DB_URL, USER, PASS); // Open a connection
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
