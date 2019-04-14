package Model;

import java.time.LocalDate;

public class TimeGoal extends Goal {

    private Exercise exercise;
    private int distance;
    private int previousBestTime;
    private int targetTime;

    /**
     * Default constructor of a time goal.
     *
     * @param startingDate the date the goal was started
     * @param duration the number of days given to complete the goal
     * @param exercise the exercise that the goal is for
     * @param distance the distance the goal is for
     * @param previousBestTime the previous fastest time the user has completed the distance in
     * @param targetTime the time that the user aims do the distance in
     */
    public TimeGoal(LocalDate startingDate, int duration, Exercise exercise, int distance, int previousBestTime, int targetTime) {
        this.startingDate = startingDate;
        this.duration = duration;
        this.exercise = exercise;
        this.distance = distance;
        this.previousBestTime = previousBestTime;
        this.targetTime = targetTime;
    }

    /**
     * Method to get the exercise that a time goal is for.
     *
     * @return the exercise that the goal is for
     */
    public Exercise getExercise() {
        return exercise;
    }

    /**
     * Method to set the exercise that a time goal is for.
     *
     * @param exercise the exercise that the goal is for
     */
    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    /**
     * Method to get the distance that a time goal is for.
     *
     * @return the distance of the time goal
     */
    public int getDistance() {
        return distance;
    }

    /**
     * Method to set the distance that a time goal is for.
     *
     * @param distance the distance that the time goal is for
     */
    public void setDistance(int distance) {
        this.distance = distance;
    }

    /**
     * Method to get the best time the user has completed the distance before creating the goal.
     *
     * @return the best time the user had completed the distance before the goal
     */
    public int getPreviousBestTime() {
        return previousBestTime;
    }

    /**
     * Method to set the best time the user has completed the distance before creating the goal.
     *
     * @param previousBestTime the best time the user had completed the distance before the goal
     */
    public void setPreviousBestTime(int previousBestTime) {
        this.previousBestTime = previousBestTime;
    }

    /**
     * Method to get the time that the user aims to complete the distance in.
     *
     * @return the time the user aims to complete the distance in
     */
    public int getTargetTime() {
        return targetTime;
    }

    /**
     * Method to set the time that the user aims to complete the distance in.
     *
     * @param targetTime the time the user aims to complete the distance in
     */
    public void setTargetTime(int targetTime) {
        this.targetTime = targetTime;
    }
}
