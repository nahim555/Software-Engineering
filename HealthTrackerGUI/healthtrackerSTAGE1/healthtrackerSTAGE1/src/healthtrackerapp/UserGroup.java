/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package healthtrackerapp;

/**
 *
 * @author azm16nru
 */
public class UserGroup {
    
    private String userName;
    private String groupName;
    private String userOne;
    private String userTwo;
    private String groupGoal;
    
    public UserGroup(String userName, String groupName, String userOne, String userTwo, String groupGoal){
        
        this.userName = userName;
        this.groupName = groupName;
        this.userOne = userOne;
        this.userTwo = userTwo;
        this.groupGoal = groupGoal;
        
    }
    
    public String getName() {
        return groupName;
    }
    
    public String getOne(){
        return userOne;
    }
    
    public String getTwo(){
        return userTwo;
    }
    
    
    public String getGoal(){
        return groupGoal;
    }
    

    public void setName(String groupName) {
        this.groupName = groupName;
    }
    
    public void setOne(String userOne) {
        this.userOne = userOne;
    }
    
    public void setTwo(String userTwo) {
        this.userTwo = userTwo;
    }
       
    public void setGoal(String groupGoal) {
        this.groupGoal = groupGoal;
    }
    
    
    
}
