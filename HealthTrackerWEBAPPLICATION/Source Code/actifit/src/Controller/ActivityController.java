package Controller;

import Model.Activity;
import Model.Exercise;
import Model.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class ActivityController {

    /**
     * Method to get the user's activities on a specific day.
     *
     * @param user the user to get the activities of
     * @param date the date to get the activities on
     * @return a list of the activities completed by that user on that day
     */
    public static List<Activity> getActivitiesForDate(User user, LocalDate date) {
        List<Activity> activities = new ArrayList<>();
        for (Activity activity : user.getActivities()) {
            if (activity.getDate().equals(date)) {
                activities.add(activity);
            }
        }
        return activities;
    }

    /**
     * Method to get the calories that an activity burned.
     *
     * @param activity the activity to find the calories burned for
     * @return the number of calories burned doing an activity
     * @throws SQLException
     */
    public static int getCaloriesBurned(User user, Activity activity) throws SQLException {
        double mets;
        if (activity.getExercise().isMeasurable()) {
            mets = ExerciseController.getMETValue(activity.getExercise(),Controller.calculateSpeed(activity.getIntensity(), activity.getDuration()));
        } else {
            mets = ExerciseController.getMETValue(activity.getExercise(),activity.getIntensity());
        }
        double weight = UserController.getWeightAtDate(user, activity.getDate());
        double hours = (double) activity.getDuration() / 3600;
        return (int) (mets * weight * hours);
    }

    /**
     * Method to get the calories that the user has burned doing activities on a specific day.
     *
     * @param user the user to get the calories burned for
     * @param date the date to get the calories burned
     * @return the number of calories that were burned on that day for the given user
     * @throws SQLException
     */
    public static int getCaloriesBurnedForDate(User user, LocalDate date) throws SQLException {
        int calories = 0;

        for (Activity activity : getActivitiesForDate(user, date)) {
            calories += getCaloriesBurned(user, activity);
        }

        return calories;
    }

    /**
     * Method to find the number of calories that a user has burned for the past days.
     *
     * @param user the user to find the calories burned for
     * @param days the number of days to return
     * @return a map of the day and calories burned on that day
     * @throws SQLException
     */
    public static Map<LocalDate, Integer> getUsersBurnedCalories(User user, int days) throws SQLException {
        Map<LocalDate, Integer> calories = new TreeMap<>();

        for (int i = days; i >= 0; i--) {
            calories.put(LocalDate.now().minusDays(i), getCaloriesBurnedForDate(user, LocalDate.now().minusDays(i)));
        }

        return calories;
    }

    /**
     * Method to get the furthest distance a user has completed of a given exercise.
     *
     * @param user the user to find the personal best of
     * @param exercise the exercise to find the personal best of
     * @return the furthest a user has completed of this exercise
     */
    public static int getDistancePersonalBest(User user, Exercise exercise) {
        int pb = 0;
        if (exercise.isMeasurable()) {
            for (Activity activity : user.getActivities()) {
                if (activity.getExercise().equals(exercise)) {
                    if (activity.getIntensity() > pb) {
                        pb = activity.getIntensity();
                    }
                }
            }
        }
        return pb;
    }

    /**
     * Method to get the fastest a user has completed a given exercise.
     *
     * @param user the user to find the personal best of
     * @param exercise the exercise to find the personal best of
     * @return the fastest a user has completed this exercise
     */
    public static double getSpeedPersonalBest(User user, Exercise exercise) {
        double pb = 0;
        if (exercise.isMeasurable()) {
            for (Activity activity : user.getActivities()) {
                if (activity.getExercise().equals(exercise)) {
                    if (Controller.calculateSpeed(activity.getIntensity(), activity.getDuration()) > pb) {
                        pb = Controller.calculateSpeed(activity.getIntensity(), activity.getDuration());
                    }
                }
            }
        }
        return pb;

    }

    /**
     * Method to get all the distance personal bests of a user.
     *
     * @param user the user to get the personal bests of
     * @return a map with the exercise and personal best
     * @throws SQLException
     */
    public static Map<Exercise, Integer> getAllDistancePersonalBests(User user) throws SQLException {
        Map<Exercise, Integer> pbs = new HashMap<>();
        for (Exercise exercise : ExerciseController.getExercises()) {
            int pb = getDistancePersonalBest(user, exercise);
            if (pb != 0) {
                pbs.put(exercise, pb);
            }
        }
        return pbs;
    }

    /**
     * Method to get all the speed personal bests of a user.
     *
     * @param user the user to get the personal bests of
     * @return a map with the exercise and personal best
     * @throws SQLException
     */
    public static Map<Exercise, Double> getAllSpeedPersonalBests(User user) throws SQLException {
        Map<Exercise, Double> pbs = new HashMap<>();
        for (Exercise exercise : ExerciseController.getExercises()) {
            double pb = getSpeedPersonalBest(user, exercise);
            if (pb != 0) {
                pbs.put(exercise, pb);
            }
        }
        return pbs;
    }

    /**
     * Method to find the fastest time a user has completed a given distance in.
     *
     * @param user the user to find the fastest time for
     * @param exercise the (measurable) exercise to find the fastest time for
     * @param distance the distance to find the fastest time for
     * @return the fastest time the user has completed the distance in
     */
    public static int calculateFastestForDistance(User user, Exercise exercise, int distance) {
        int fastest = Integer.MAX_VALUE;
        for (Activity activity : user.getActivities()) {
            if (activity.getExercise().equals(exercise) && activity.getIntensity() >= distance) {
                int time = (int)((double)activity.getDuration() / (double)activity.getIntensity() * (double)distance);
                if (time < fastest) {
                    fastest = time;
                }
            }
        }
        return fastest;
    }
}
