package Controller;

import Model.User;

import java.time.LocalDate;
import java.time.Period;
import com.sendgrid.*;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class Controller {

    /**
     * Method to convert a duration in seconds into the output format hh:mm:ss.
     *
     * @param secondsInput  duration as a number of seconds
     * @return              String formatted into hh:mm:ss
     */
    public static String durationFormat(int secondsInput){
        int secondsLeft = secondsInput;
        int hours = secondsLeft / 3600;
        secondsLeft = secondsLeft % 3600;
        int minutes = secondsLeft / 60;
        secondsLeft = secondsLeft % 60;

        String output = String.format("%02d:%02d:%02d", hours, minutes, secondsLeft);
        return output;
    }

    /**
     * Method to calculate a person's BMI (Body Mass Index) value.
     *
     * @param height    height in centimeters
     * @param weight    weight in kilograms
     * @return          BMI value from the given values in kg/m^2
     */
    public static double calculateBMI(double height, double weight){
        double bmi = weight / Math.pow((height/100), 2);
        return bmi;
    }

    /**
     * Method to calculate a person's BMR (Basal Metabolic Rate) value using the Mifflin St Jeor equation.
     *
     * @param height    height in centimeters
     * @param weight    weight in kgs
     * @param gender    male/female/unspecified
     * @param dob       date of birth
     * @return          BMR value in units kcal/day
     */
    public static double calculateBMR(double height, double weight, User.Gender gender, LocalDate dob){

        final int MALE_CONSTANT = 5;
        final int FEMALE_CONSTANT = -161;

        int age = Period.between(dob, LocalDate.now()).getYears();

        double bmr;
        if (gender.compareTo(User.Gender.M) == 0){
            bmr = (10 * weight) + (6.25 * height) - (5 * age) + MALE_CONSTANT;
        }
        else if (gender.compareTo(User.Gender.F) == 0){
            bmr = (10 * weight) + (6.25 * height) - (5 * age) + FEMALE_CONSTANT;
        }
        else {
            double bmr1 = (10 * weight) + (6.25 * height) - (5 * age) + MALE_CONSTANT;
            double bmr2 = (10 * weight) + (6.25 * height) - (5 * age) + FEMALE_CONSTANT;
            bmr = (bmr1 + bmr2) / 2;
        }
        
        return bmr;
    }

    /**
     * Method to send an email.
     *
     * @param fromAccount the email address to send from (the @domain is appended automatically)
     * @param toAddress the full email address to send the email to
     * @param subject the subject of the email
     * @param body the main content of the email
     * @return the status code (202 is successfully sent)
     */
    public static int sendEmail(String fromAccount, String toAddress, String subject, String body) {
        Email from = new Email(fromAccount + "@actifit.site");
        Email to = new Email(toAddress);
        Content content = new Content("text/plain", body);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid("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");
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            return response.getStatusCode();
        } catch (IOException e) {
            return 600;
        }
    }

    /**
     * Method to convert between metric and imperial units.
     *
     * @param value         double value to be converted
     * @param fromUnit      unit to be converted from (lb, kg, cm, m, km, miles, feet)
     * @param toUnit        unit to be converted to (lb, kg, cm, m, km, miles, feet)
     * @return              converted value
     */
    public static double unitConvert(double value, String fromUnit, String toUnit){
        double result = 0;
        double convertedValue = 0;
        if (fromUnit.compareTo("lb") == 0){
            convertedValue = value * 0.453592;
        } else if (fromUnit.compareTo("kg") == 0){
            convertedValue = value;
        } else if (fromUnit.compareTo("cm") == 0){
            convertedValue = value / 100;
        } else if (fromUnit.compareTo("m") == 0){
            convertedValue = value;
        } else if (fromUnit.compareTo("km") == 0){
            convertedValue = value * 1000;
        } else if (fromUnit.compareTo("miles") == 0){
            convertedValue = value * 1609.3400007802;
        } else if (fromUnit.compareTo("feet") == 0){
            convertedValue = value * 0.3048;
        } else {
            System.out.println("INVALID UNITS.");
        }

        if (toUnit.compareTo("lb") == 0){
            result = convertedValue * 2.20462;
        } else if (toUnit.compareTo("kg") == 0){
            result = convertedValue;
        } else if (toUnit.compareTo("cm") == 0){
            result = convertedValue * 100;
        } else if (toUnit.compareTo("m") == 0){
            result = convertedValue;
        } else if (toUnit.compareTo("km") == 0){
            result = convertedValue / 1000;
        } else if (toUnit.compareTo("miles") == 0){
            result = convertedValue * 0.000621371;
        } else if (toUnit.compareTo("feet") == 0){
            result = convertedValue * 3.28084;
        } else {
            System.out.println("INVALID UNITS.");
        }

        return result;
    }

    /**
     * Method to calculate speed in KM/H.
     *
     * @param distanceInM distance in metres
     * @param timeInSec time in seconds
     * @return speed in KM/H
     */
    public static double calculateSpeed(double distanceInM, double timeInSec) {
        return (distanceInM / 1000) / (timeInSec / 3600);
    }

    /**
     * Method to subtract a list from another list.
     *
     * @param minuend the list to start with
     * @param subtrahend the list to take away
     * @return a list containing those in the minuend but not in the subtrahend
     */
    public static <T> List<T> subtract(List<T> minuend, List<T> subtrahend) {
        minuend.removeAll(subtrahend);
        return minuend;
    }

    /**
     * Method to add a list to another list.
     *
     * @param addend1 the list to start with
     * @param addend1 the list to add
     * @return a list containing the items in both the lists (minus duplicates)
     */
    public static <T> List<T> add(List<T> addend1, List<T> addend2) {
        addend1 = subtract(addend1, addend2);
        addend1.addAll(addend2);
        return addend1;
    }

    /**
     * Method to calculate a percentage (capped between 0 and 100 inclusive).
     *
     * @param a the smaller number
     * @param b the larger number
     * @return the percentage of the two numbers divided
     */
    public static int calculatePercentage(int a, int b) {
        double percentage = ((double) a / (double) b) * 100;
        if (percentage > 100) {
            percentage = 100;
        } else if (percentage < 0) {
            percentage = 0;
        }
        return (int) percentage;
    }

    /**
     * Method to generate a random alphanumeric string.
     *
     * @return a random alphanumeric string of length 8
     */
    public static String generateRandomString() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}

