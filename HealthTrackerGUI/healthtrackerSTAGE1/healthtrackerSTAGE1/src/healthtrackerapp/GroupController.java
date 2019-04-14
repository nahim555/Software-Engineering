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
public class GroupController {
    
    
    UserGroup user;
    DatabaseController conn;
    
    //database connection
    Connection myconn = null;
    Statement mystat = null;
    ResultSet myres = null;
    PreparedStatement statement;
    
    private String userName;
    private String groupName;
    private String userOne;
    private String userTwo;
    private String groupGoal;
    private boolean userCheck;
    
    /**
     * Constructor to connect to database and create a new usergroup object
     * @param userName 
     */
    public GroupController(String userName){
        this.userName = userName;
        try {
            selectionall();
        } catch (SQLException ex) {
            Logger.getLogger(GoalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        user = new UserGroup(userName, groupName, userOne, userTwo, groupGoal);
    }
    
    /**
     * Constructor to initialise controller
     * @param userName
     * @param groupName
     * @param userOne
     * @param userTwo
     * @param groupGoal 
     */
    public GroupController(String userName,String groupName, String userOne, String userTwo, String groupGoal){
        
        this.userName = userName;
        this.groupName = groupName;
        this.userOne = userOne;
        this.userTwo = userTwo;
        this.groupGoal = groupGoal;
        this.userCheck = false;
        
        
    }
    
    /**
     * Method to select all USERGROUP to database for user
     * @throws SQLException 
     */
    public void selectionall() throws SQLException{
        myconn = DriverManager.getConnection("jdbc:derby://localhost:1527/APP", "APP", "APP");
        mystat = myconn.createStatement();
        mystat.executeQuery("select * from APP.USERGROUP where username = '"+userName+"'");
        myres = mystat.getResultSet();
        
        while(myres.next()){
            
            groupName = myres.getString("groupName");
            userOne = myres.getString("userOne");
            userTwo = myres.getString("userTwo");
            groupGoal = myres.getString("groupGoal");
        }
        myres.close();
        mystat.close();
    
    }
    
    public String getGroupName(){
        return user.getName();
    }
    
    public String getUserOne(){
        return user.getOne();
    }
    
    public String getUserTwo(){
        return user.getTwo();
    }
    
    public String getGroupGoal(){
        return user.getGoal();
    }
    
    public void setGroupName(String groupName){
        this.groupName = groupName;
    }
    
    public void setUserOne(String userOne){
        this.userOne = userOne;
    }
    
    public void setUserTwo(String userTwo){
        this.userTwo = userTwo;
    }
    
    public void setGroupGoal(String groupGoal){
        this.groupGoal = groupGoal;
    }
    
    /**
     * Method to add group to database
     */
    public void addGroup(){
        try {
            myconn = DriverManager.getConnection("jdbc:derby://localhost:1527/APP", "APP", "APP");
            statement = myconn.prepareStatement("INSERT INTO USERGROUP (userName,groupName,userOne,userTwo,groupGoal) VALUES (?,?,?,?,?)");
            statement.setString(1, userName);
            statement.setString(2, groupName);
            statement.setString(3, userOne);
            statement.setString(4, userTwo);
            statement.setString(5, groupGoal);
            int row = statement.executeUpdate();
            statement.close();
            
            
        } catch (SQLException ex) {
            Logger.getLogger(GroupController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Method to updateGroup
     */
    public void updateGroup(){
        try {
            myconn = DriverManager.getConnection("jdbc:derby://localhost:1527/APP", "APP", "APP");
            statement = myconn.prepareStatement("UPDATE APP.USERGROUP SET groupName = ?, userOne = ?, userTwo = ?, groupGoal = ? WHERE userName = ?");
            statement.setString(1, groupName);
            statement.setString(2,userOne);
            statement.setString(3,userTwo);
            statement.setString(4,groupGoal);
            statement.setString(5,userName);
             int row = statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(GroupController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Method to check user using boolean
     * @param user
     * @return 
     */
    public boolean check(String user){
        userCheck = false;
        try {
            myconn = DriverManager.getConnection("jdbc:derby://localhost:1527/APP", "APP", "APP");
            statement = myconn.prepareStatement("SELECT * FROM USERS WHERE userName = ?");
            statement.setString(1, user);
        
            ResultSet r1=statement.executeQuery();
            String c;
            if(r1.next()){
                c = r1.getString("userName");
                if(c.equals(user)){
                    userCheck = true;
                    return userCheck;
                }
               
            }


        
        
        } catch (SQLException ex) {
            Logger.getLogger(GroupController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userCheck;
    }
    
}
