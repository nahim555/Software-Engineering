/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package healthtrackerapp;

import java.sql.Time;

/**
 *
 * @author ilyas
 */
public class Exercise {
    private String userName;
    private String type;
    private double calories;
    private String intensity;
    private double duration;
    
    /**
     * Constructor to initialise a Exercise object
     * @param userName returns userName of user
     * @param type returns type of exercise of user
     * @param calories returns calories of user
     * @param intensity returns intensity of user
     * @param duration returns duration of user
     */
    public Exercise(String userName, String type, double calories, String intensity, double duration){
        this.userName = userName;
        this.type = type;
        this.calories = calories;
        this.intensity = intensity;
        this.duration = duration;
    }
    
    
    /**
     * Method to return type of exercise
     * @return type of exercise of this user
     */
    public String getType(){
        return type;
    }
    
    /**
     * Method to return calories for user
     * @return calories for this user
     */
    public double getCalories(){
        return calories;
    }
    
    /**
     * Method to return intensity of exercise
     * @return intensity of user
     */
    public String getIntensity(){
        return intensity;
    }
    
    /**
     * Method to return duration of exercise
     * @return duration of exercise for this user
     */
    public double getDuration(){
        return duration;
    }
    
    /**
     * Method to set type of exercise
     * @param type set for the exercise
     */
    public void setType(String type){
        this.type = type;
    }
    
    /**
     * Method to set intensity of this exercise
     * @param intensity set for the exercise
     */
    public void setIntensity(String intensity){
        this.intensity = intensity;
    }
    
    /**
     * Method to set calories for this exercise
     * @param calories set calories for this exercise
     */
    public void setCalories(double calories){
        this.calories = calories;
    }
    
    /**
     * Method to set the duration for this exercise
     * @param duration to set duration for the exercise
     */
    public void setDuration(double duration){
        this.duration = duration;
    }

    
}
