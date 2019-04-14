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
public class ExerciseController {
    
    // Connections to database
    Connection myconn = null;
    Statement mystat = null;
    ResultSet myres = null;
    PreparedStatement add;
    
    //variables for the exercise Model
    private int exerciseID;
    private String userName;
    private String type;
    private double calories;
    private String intensity;
    private double duration;
    
    //variable initialisation for databasecontroller 
    Exercise exer;
    DatabaseController conn;
    
    /**
     * Constructor to retrieve exercise from database
     * @param userName 
     */
    public ExerciseController(String userName){
        this.userName = userName;
        conn = new DatabaseController();
        try {
            selectionall();
        } catch (SQLException ex) {
            Logger.getLogger(GoalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        exer = new Exercise(this.userName, type, calories, intensity,duration);
        
    }
    
    /**
     * Constructor to initialise variables
     * @param userName
     * @param type
     * @param duration
     * @param intensity 
     */
    public ExerciseController(String userName, String type, double duration, String intensity){
        this.userName = userName;
        this.type = type;
        this.intensity = intensity;
        this.duration = duration;
        
        
    }
    
    /**
     * Method to select all Exercise information for the user
     * @throws SQLException 
     */
    public void selectionall() throws SQLException{
        myconn = conn.getConnect();
        mystat = myconn.createStatement();
        mystat.executeQuery("select * from APP.EXERCISE where username = '"+userName+"'");
        ResultSet myres = mystat.getResultSet();
        
        while(myres.next()){
            exerciseID = myres.getInt("exerciseID");
            type = myres.getString("exerciseType");
            calories = myres.getDouble("calories");
            intensity = myres.getString("intensity");
            duration = myres.getDouble("duration");
        }
        myres.close();
        mystat.close();
    
    }
     
    /**
     * Method to get type of exercise
     * @return type of exercise
     */
    public String getExerciseType(){
        return exer.getType();
    }
    
    /**
     * Method to get calories for the exercise
     * @return calories for the exercise
     */
    public double getExerciseCalories(){
        return exer.getCalories();
    }
    
    /**
     * Method to get intensity for the exercise
     * @return intensity of the exercise
     */
    public String getExerciseIntensity(){
        return exer.getIntensity();
    }
    
    /**
     * Method to get duration of exercise
     * @return duration
     */
    public double getExerciseTime(){
        return exer.getDuration();
    }
    
    /**
     * Method UPDATE information to the database
     */
    public void addExercise(){
        
        calories = 10;
        exer = new Exercise(this.userName, this.type, this.calories, this.intensity, this.duration);
        try {
            System.out.println(userName);
            System.out.println(exer.getType());
            System.out.println(exer.getCalories());
            System.out.println(exer.getDuration());
            System.out.println(exer.getIntensity());
            myconn = DriverManager.getConnection("jdbc:derby://localhost:1527/APP", "APP", "APP");
            String query = "UPDATE APP.EXERCISE SET exerciseType = ? , calories = ? , duration = ? , intensity = ? WHERE username = ?";
            
            
            add = myconn.prepareStatement(query);
           
            
            add.setString(1,exer.getType());
            add.setDouble(2,exer.getCalories());
            add.setDouble(3,exer.getDuration());
            add.setString(4,exer.getIntensity());
            add.setString(5,userName);
            
            int row = add.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(GoalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * Method to INSERT exercise to database
     */
    public void insertExercise(){
        calories = 10;
        exer = new Exercise(this.userName, this.type, this.calories, this.intensity, this.duration);
        try {
            System.out.println(userName);
            System.out.println(exer.getType());
            System.out.println(exer.getCalories());
            System.out.println(exer.getDuration());
            System.out.println(exer.getIntensity());
            myconn = DriverManager.getConnection("jdbc:derby://localhost:1527/APP", "APP", "APP");
            String query = "INSERT INTO APP.EXERCISE (userName, exerciseType, calories, duration, intensity) VALUES(?,?,?,?,?)";
            
            
            add = myconn.prepareStatement(query);
           
            add.setString(1,userName);
            add.setString(2,exer.getType());
            add.setDouble(3,exer.getCalories());
            add.setDouble(4,exer.getDuration());
            add.setString(5,exer.getIntensity());
            
            
            int row = add.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(GoalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /**
     * Method to calculate calories
     * @param duration for the exercise
     * @param intensity for the exercise
     * @return 
     */
    public double calculateCalories(double duration, String intensity){
        calories = 0;
        double hourlyBurnRate = 100;
        if(intensity == "high") {
            
            calories = (hourlyBurnRate*duration)*3;
                   
        }
        else if(intensity == "medium"){
            calories = (hourlyBurnRate*duration)*2;
        }
        else{
            calories = (hourlyBurnRate*duration)*1;
        }
        return calories;
    }
    
    /**
     * Method to set exercise to completed
     */
    public void exerciseCompleted(){
        try {
            myconn = conn.getConnect();
            add = myconn.prepareStatement("DELETE FROM Table WHERE username = ?");
            add.setString(1,userName);
            add.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(GoalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
