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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author azm16nru
 */
public class FoodController {
    
    //Database connection
    Connection myconn = null;
    Statement mystat = null;
    ResultSet myres = null;
    PreparedStatement statement;
    
   //variables for creating a Food object
    private String foodName;
    private double carbohydrates;
    private double fat;
    private double protein;
    private double calories;
  
    //list to store food as string
    ArrayList<String> foodList;
    
    /**
     * Constructor to initialise FoodController
     * @param foodName
     * @param carbohydrates
     * @param fat
     * @param protein
     * @param calories 
     */
    public FoodController(String foodName, double carbohydrates, double fat,
            double protein, double calories){
        
        this.foodName = foodName;
        this.carbohydrates = carbohydrates;
        this.fat = fat;
        this.protein = protein;
        this.calories = calories;
    }
    
    /**
     * Constructor to initialise arrayList to store Food
     */
    public FoodController(){
        foodList = new ArrayList<>();
    }
    
    /**
     * Method to insert a Food into the database
     */
    public void insertFood(){
       
        
        try {
            Food food = new Food(foodName, carbohydrates, fat, protein, calories);
            myconn = DriverManager.getConnection("jdbc:derby://localhost:1527/APP", "APP", "APP");
            String query = "INSERT INTO APP.FOOD (foodName, carbohydrates, fat, protein, calories) VALUES(?,?,?,?,?)";
            
            
            statement = myconn.prepareStatement(query);
           
            statement.setString(1,food.getName());
            statement.setDouble(2,food.getCarbs());
            statement.setDouble(3,food.getFat());
            statement.setDouble(4,food.getProtein());
            statement.setDouble(5,food.getCalories());
            
            
            
            int row = statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(GoalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Method to get calories for a Food
     * @param foodName for this food
     * @return 
     */
    public double getFoodCalories(String foodName){
        try {
            
            myconn = DriverManager.getConnection("jdbc:derby://localhost:1527/APP", "APP", "APP");
            String query = "SELECT calories FROM APP.FOOD WHERE foodName ='"+foodName+"'";
            
            
            mystat = myconn.createStatement();
            myres = mystat.executeQuery(query);
            while(myres.next()){
                calories = myres.getDouble("calories");
            }
            
            
           
        } catch (SQLException ex) {
            Logger.getLogger(GoalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return calories;
    }
    
}
