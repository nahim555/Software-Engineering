/*
 * GoalController
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
public class GoalController {
    
    //database connection
    Connection myconn = null;
    Statement mystat = null;
    ResultSet myres = null;
    PreparedStatement add;
    
    //private instance variable
    private String userName;
    private String name;
    private String description;
    private double duration;
    private boolean isComplete;
    
    //create a goal object
    Goals goal;
    DatabaseController conn;
    
    /**
     * Constructor initialises database connection and initialise Goal object
     * @param userName 
     */
    public GoalController(String userName){
        this.userName = userName;
        
        conn = new DatabaseController(); //initalise object method
        try {
            selectionall();
        } catch (SQLException ex) {
            Logger.getLogger(GoalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        goal = new Goals(userName, name, description, duration, isComplete); //initalise object goal
        
    
    }
    
    //Constructor to initalise private variables
    public GoalController(String userName, String name, String description,
            double duration){
        
        this.userName = userName;
        this.name = name;
        this.description = description;
        this.duration = duration;
        isComplete = false;
        goal = new Goals(this.userName, this.name, this.description, this.duration, isComplete);
    }
    
    
    /**
     * Method to SELECT all GOALS from USER
     * @throws SQLException 
     */
    public void selectionall() throws SQLException{
        myconn = conn.getConnect();
        mystat = myconn.createStatement();
        mystat.executeQuery("select * from APP.GOAL where username = '"+userName+"'");
        ResultSet myres = mystat.getResultSet();
        
        while(myres.next()){
            
            name = myres.getString("goalName");
            description = myres.getString("description");
            duration = myres.getDouble("duration");
            isComplete = myres.getBoolean("isComplete");
        }
        myres.close();
        mystat.close();
    
    }
    
    /**
     * Method to return goal name from goal
     * @return name of goal
     */
    public String getGoalName(){
        return goal.getName();
    }
    
    /**
     * Method to get goal description
     * @return description of a goal
     */
    public String getGoalDescription(){
        return goal.getDescription();
    }
    
    public double getGoalDuration(){
        return goal.getDuration();
    }
    
    public boolean getGoalComplete(){
        return goal.getIsComplete();
    }
    
    /**
     * Method to UPDATE to add goal to the database
     */
    public void addGoal(){
        
        try {
            myconn = DriverManager.getConnection("jdbc:derby://localhost:1527/APP", "APP", "APP");
            String query = "UPDATE APP.GOAL SET goalname = ? , description = ? , duration = ? , isComplete = ? WHERE username = ?";
            add = myconn.prepareStatement(query);
            
            
            add.setString(1,goal.getName());
            add.setString(2,goal.getDescription());
            add.setDouble(3,goal.getDuration());
            add.setBoolean(4,goal.getIsComplete());
            add.setString(5,userName);
            int row = add.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(GoalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * Method to insert goal into database
     */
    public void insertGoal(){
        try{
            myconn = DriverManager.getConnection("jdbc:derby://localhost:1527/APP", "APP", "APP");
            String query = "INSERT INTO APP.GOAL (userName, goalName, description, duration, isComplete) VALUES (?,?,?,?,?)";
            add = myconn.prepareStatement(query);
            
            add.setString(1,userName);
            add.setString(2,goal.getName());
            add.setString(3,goal.getDescription());
            add.setDouble(4,goal.getDuration());
            add.setBoolean(5,goal.getIsComplete());
            int row = add.executeUpdate();
        } catch(SQLException ex){
            Logger.getLogger(GoalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /**
     * Method for user to set goal to complete 
     */
    public void goalCompleted(){
        try {
            myconn = conn.getConnect();
            add = myconn.prepareStatement("DELETE FROM Table WHERE name = ?");
            add.setString(1,userName);
            add.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(GoalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
