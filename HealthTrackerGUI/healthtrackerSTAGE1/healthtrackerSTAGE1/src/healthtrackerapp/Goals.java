/*
 * Model Class
 */
package healthtrackerapp;

/**
 *
 * @author ilyas
 */
public class Goals {
    //instance variable
    private String userName; //userName
    private String name; //Goaln title
    private String description; //Detailed description of the goal
    private double duration; //How many days it will take to complete
    private boolean isComplete; //Have the goal been reached
    
    //Constructor 
    public Goals(String userName, String name, String description, double duration, boolean isComplete){
        this.userName = userName;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.isComplete = isComplete;
    }
    
   
    //Accessor method
    public String getName(){
        return name;
    }
    
    public String getDescription(){
        return description;
    }
    
    public double getDuration(){
        return duration;
    }
    
    public boolean getIsComplete(){
        return isComplete;
    }
    
    //Mutator methods
    public void setName(String name){
        this.name = name;
    }
    
    public void setDescription(String description){
        this.description = description;
    }
    
    public void setDuration(double duration){
        this.duration = duration;
    }
    
    public void setIsComplete(boolean isComplete){
        this.isComplete = isComplete;
    }
    
}


    
    

    
    