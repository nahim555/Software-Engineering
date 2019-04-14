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
 * @author azm16nru
 */
public class MealController {
    
    //database controller
    Connection myconn = null;
    Statement mystat = null;
    ResultSet myres = null;
    PreparedStatement statement;
    
    private String userName;
    private String mealName;
    private double totalCalories;
    private String mealTime;
    
    Meal meal;
    
    /**
     * Constructor to connect to database
     * @param userName 
     */
    public MealController(String userName){
        this.userName = userName;
        try {
            selectionall();
        } catch (SQLException ex) {
            Logger.getLogger(GoalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        meal = new Meal(mealName, totalCalories, mealTime);
    }
    
    /**
     * Constructor to initialise variables
     * @param userName
     * @param mealName
     * @param totalCalories
     * @param mealTime 
     */
    public MealController(String userName, String mealName, double totalCalories, String mealTime){
        this.userName = userName;
        this.mealName = mealName;
        this.totalCalories = totalCalories;
        this.mealTime = mealTime;
    }
    
    /**
     * Method to insert Meal to database
     */
    public void insertMeal(){
        try {
            Meal meal = new Meal(mealName, totalCalories, mealTime);
            myconn = DriverManager.getConnection("jdbc:derby://localhost:1527/APP", "APP", "APP");
            String query = "INSERT INTO APP.MEAL (userName, mealName, totalCalories, mealTime) VALUES(?,?,?,?)";
            
            
            statement = myconn.prepareStatement(query);
           
            statement.setString(1,userName);
            statement.setString(2,meal.getMealName());
            statement.setDouble(3,meal.getTotalCalories());
            statement.setString(4,meal.getMealTime());
           
            
            
            
            int row = statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(GoalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Method to select all MEAL from database
     * @throws SQLException 
     */
    public void selectionall() throws SQLException{
        myconn = DriverManager.getConnection("jdbc:derby://localhost:1527/APP", "APP", "APP");
        mystat = myconn.createStatement();
        mystat.executeQuery("select * from APP.MEAL where username = '"+userName+"'");
        ResultSet myres = mystat.getResultSet();
        
        while(myres.next()){
            
            mealName = myres.getString("mealName");
            totalCalories = Double.parseDouble(myres.getString("totalCalories"));
            mealTime = myres.getString("mealTime");
       
        }
        myres.close();
        mystat.close();
    
    }
    
    /**
     * Method to delete Meal from database
     */
    public void deleteMeal(){
        try {
            Meal meal = new Meal(mealName, totalCalories, mealTime);
            myconn = DriverManager.getConnection("jdbc:derby://localhost:1527/APP", "APP", "APP");
            String query = "DELETE FROM APP.MEAL WHERE userName = ?";
            
            
            statement = myconn.prepareStatement(query);
           
            statement.setString(1,userName);
            
         
            
            int row = statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(GoalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getMealName(){
        return meal.getMealName();
    }
    
    public double getTotalCalories(){
        return meal.getTotalCalories();
    }
    
    public String getMealTime(){
        return meal.getMealTime();
    }
}
