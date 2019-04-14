package healthtrackerapp;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author azm16nru
 */
public class Meal {
    
    private String mealName;
    private double totalCalories;
    private String mealTime;
    
    public Meal(String mealName, double totalCalories, String mealTime){
        this.mealName = mealName;
        this.totalCalories = totalCalories;
        this.mealTime = mealTime;
    }
    
    public String getMealName(){
        return mealName;
    }
    
    public double getTotalCalories(){
        return totalCalories;
    }
    
    public String getMealTime(){
        return mealTime;
    }
    
    public void setMealName(String mealName){
        this.mealName = mealName;
    }
    
    public void setTotalCalories(double totalCalories){
        this.totalCalories = totalCalories;
    }
    
    public void setMealTime(String mealTime){
        this.mealTime = mealTime;
    }
    
}
