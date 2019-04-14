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
public class Food {
    private String foodName;
    private double carbohydrates;
    private double fat;
    private double protein;
    private double calories;
    
    public Food(String foodName, double carbohydrates, double fat,
            double protein, double calories){
        
        this.foodName = foodName;
        this.carbohydrates = carbohydrates;
        this.fat = fat;
        this.protein = protein;
        this.calories = calories;
    }
    
    public String getName(){
        return foodName;
    }
    
    public double getCarbs(){
        return carbohydrates;
    }
    
    public double getFat(){
        return fat;
    }
    
    public double getProtein(){
        return protein;
    }
    
    public double getCalories(){
        return calories;
    }
    
    public void setName(String foodName){
        this.foodName = foodName;
    }
    
    public void setCarbs(double carbohydrates){
        this.carbohydrates = carbohydrates;
    }
    
    public void setFat(double fat){
        this.fat = fat;
    }
    
    public void setProtein(double protein){
        this.protein = protein;
    }
    
    public void setCalories(double calories){
        this.calories = calories;
    }
    
}
