package Controller;

import Model.User;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ControllerTest {

    @Test
    public void durationFormat() {
        int seconds = 12345;
        String convertedDuration = Controller.durationFormat(seconds);
        int result = convertedDuration.compareTo("03:25:45");
        assertEquals(result, 0);
    }

    @Test
    public void calculateBMI() {
        double height = 180;
        double weight = 80;
        double bmi = Controller.calculateBMI(height, weight);
        //actual value = 24.691358024691358
        //using the delta value of 0 to make sure they 100% match
        assertEquals(24.691358024691358, bmi, 0);
    }

    @Test
    public void calculateBMR() {
        double height = 180;
        double weight = 80;
        User.Gender gender = User.Gender.M;
        LocalDate dob = LocalDate.of(1997,1,1);
        double bmr = Controller.calculateBMR(height, weight, gender, dob);
        //calculated a value of 1825 separately with the same values
        assertEquals(1825, bmr, 0);
    }

    @Test
    public void sendEmail() {
        int status = Controller.sendEmail("no-reply", "actifit@carbtc.net", "Test subject", "This is a test message");
        assertEquals(status, 202);
    }

    @Test
    public void poundsToKilos(){
        double value = Controller.unitConvert(1, "lb", "kg");
        assertEquals(0.453592, value, 0.1);
    }

    @Test
    public void kilosToPounds(){
        double value = Controller.unitConvert(1, "kg", "lb");
        assertEquals(2.20462, value, 0.1);
    }

    @Test
    public void centimeterToMeter(){
        double value = Controller.unitConvert(1, "cm", "m");
        assertEquals(0.01, value, 0.1);
    }

    @Test
    public void meterToKilometer(){
        double value = Controller.unitConvert(1, "m", "km");
        assertEquals(0.001, value, 0.1);
    }

    @Test
    public void meterToMiles(){
        double value = Controller.unitConvert(1, "m", "miles");
        assertEquals(0.000621371, value, 0.1);
    }

    @Test
    public void meterToFeet(){
        double value = Controller.unitConvert(1, "m", "feet");
        assertEquals(3.28084, value, 0.1);
    }

    @Test
    public void kilometerToMiles(){
        double value = Controller.unitConvert(1, "km", "miles");
        assertEquals(0.621371, value, 0.1);
    }

    @Test
    public void milesToFeet(){
        double value = Controller.unitConvert(1, "miles", "feet");
        assertEquals(5279.987048, value, 0.1);
    }

    @Test
    public void calculateSpeed(){
        double speed = Controller.calculateSpeed(4500, 1500);
        assertEquals(10.8, speed, 0.1);
    }

    @Test
    public void subtract(){
        List<Integer> minuend = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        List<Integer> subtrahend = new ArrayList<>(Arrays.asList(4, 5, 6));
        assertEquals(7, Controller.subtract(minuend, subtrahend).size());
    }

    @Test
    public void add(){
        List<Integer> addend1 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        List<Integer> addend2 = new ArrayList<>(Arrays.asList(11, 12, 13, 14));
        assertEquals(14, Controller.add(addend1, addend2).size());
    }

    @Test
    public void calculatePercentage(){
        int percentage = Controller.calculatePercentage(65, 98);
        assertEquals(66, percentage);
    }

    @Test
    public void generateRandomString(){
        assertEquals(8, Controller.generateRandomString().length());
    }
}