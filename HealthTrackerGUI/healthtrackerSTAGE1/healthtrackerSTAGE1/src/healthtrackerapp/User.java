/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package healthtrackerapp;



/**
 *
 * @author ilyas
 */
public class User {
    
    
    
    //Database fields
    private final String userName;
    private String firstName;
    private String lastName;
    private final String password;
    private String DOB; 
    private double height; //meters
    private double mass; //kilograms
    private final String email;
    private double BMI;
    
    
    
    public User(String userName,String firstName, String lastName, String password,
            String DOB, double height, double weight,String email, double BMI){
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.DOB = DOB;
        this.height = height;
        this.mass = weight;
        this.email = email;
        this.BMI = BMI;
        
        
    }
      
    public String getID(){
        return userName;
    }
    
    public String getName(){
        String name = ""+firstName+" "+lastName;
        return name;
    }
    
    public String getDOB(){
        return DOB;
    }
    
    
    
    protected String password(){
        return password;
    }
    
    
    public double getHeight(){
        return height;
    }
    
    public void setHeight(double height){
        this.height = height;
    }
    
    
    public double getBMI(){
        
        return BMI;
    }
    
    public double calculateBMI(){
        BMI = mass/(height*height);
        return BMI;
    }
    public double getMass(){
        return mass;
    }
    public void setMass(double weight){
        this.mass = weight;
    }
    
    
}
