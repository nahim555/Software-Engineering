/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package healthtrackerapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ilyas
 */
public class UserController {
    
    //connection to database
    Connection myconn = null;
    Statement mystat = null;
    ResultSet myres = null;
    PreparedStatement update;
    private final String userName;
    private String firstName;
    private String lastName;
    private String password;
    private String DOB; 
    private double height; //meters
    private double mass; //kilograms
    private String email;
    private double BMI;
    
    User userInfo;
    DatabaseController conn;
    
    /**
     * Constructor to initialise variables
     * @param userName 
     */
    public UserController(String userName){
        this.userName = userName;
        this.firstName = null;
        this.lastName = null;
        this.password = null;
        this.DOB = null;
        this.height = 0;
        this.mass = 0;
        this.email = null;
        this.BMI = 0;
        conn = new DatabaseController();
        
        try {
            selectionall();
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        userInfo = new User(userName, firstName, lastName, password, DOB, height, mass
                                 , email, BMI);
        
        
    }
    
    /**
     * Method to select all from USERS 
     * @throws SQLException 
     */
    public void selectionall() throws SQLException{
        myconn = conn.getConnect();
        mystat = myconn.createStatement();
        mystat.executeQuery("select * from APP.USERS where username = '"+userName+"'");
        ResultSet myres = mystat.getResultSet();
      
        while(myres.next()){
            firstName = myres.getString("firstName");
            lastName = myres.getString("lastName");
            password = myres.getString("password");
            DOB = myres.getString("DOB");
            height = myres.getInt("height");
            mass = myres.getInt("mass");
            email = myres.getString("email");
            BMI = myres.getInt("BMI");
            
        }
        myres.close();
        mystat.close();
       
    }
    
    public String getUserName(){ 
        return userInfo.getName();
    }
    
    public String getUserDOB(){
        return userInfo.getDOB();
    }
    
    public double getUserMass(){
        return userInfo.getMass();
    }
    
    public double getUserBMI(){
        return userInfo.getBMI();
    }
    
    public void setUserHeight(double height){
        this.height = height;
        userInfo.setHeight(this.height);
        
    }
    
    public void setUserMass(double mass){
        this.mass = mass;
        userInfo.setMass(this.mass);
        
    }
    
    /**
     * Method to update user information in database
     */
    public void updateUserInfo(){
        userInfo.calculateBMI();
        try {
            myconn = DriverManager.getConnection("jdbc:derby://localhost:1527/APP", "APP", "APP");
            String query = "UPDATE APP.USERS SET height = ? , mass = ? , BMI = ? WHERE username = ?";
            update = myconn.prepareStatement(query);
            
            
            update.setDouble(1,userInfo.getHeight());
            update.setDouble(2,userInfo.getMass());
            update.setDouble(3,userInfo.getBMI());
            update.setString(4,userName);
            int row = update.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(GoalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
}
