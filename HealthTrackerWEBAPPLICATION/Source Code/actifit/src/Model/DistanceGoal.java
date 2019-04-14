package Model;

import java.time.LocalDate;

public class DistanceGoal extends Goal {

    private Exercise exercise;
    private int previousBestDistance;
    private int targetDistance;

    /**
     * Default constructor of a distance goal.
     *
     * @param startingDate the date the goal was started
     * @param duration the number of days given to complete the goal
     * @param exercise the exercise that the goal is for
     * @param previousBestDistance the greatest distance the user had acheived previous to this goal
     * @param targetDistance the distance the user aims to achieve
     */
    public DistanceGoal(LocalDate startingDate, int duration, Exercise exercise, int previousBestDistance, int targetDistance) {
        this.startingDate = startingDate;
        this.duration = duration;
        this.exercise = exercise;
        this.previousBestDistance = previousBestDistance;
        this.targetDistance = targetDistance;
    }

    /**
     * Method to get the exercise that a distance goal is for.
     *
     * @return the exercise that the goal is for
     */
    public Exercise getExercise() {
        return exercise;
    }

    /**
     * Method to set the exercise that a distance goal is for.
     *
     * @param exercise the exercise that the goal is for
     */
    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    /**
     * Method to get a user's previous best distance.
     *
     * @return the user's previous best distance
     */
    public int getPreviousBestDistance(){
        return previousBestDistance;
    }

    /**
     * Method to set a user's previous best distance.
     *
     * @param previousBestDistance the user's previous best distance
     */
    public void setPreviousBestDistance(int previousBestDistance) {
        this.previousBestDistance = previousBestDistance;
    }

    /**
     * Method to get a users targets distance.
     *
     * @return the user's target distance
     */
    public int getTargetDistance(){
        return targetDistance;
    }

    /**
     * Method to set a users target distance.
     *
     * @param targetDistance the user's target distance
     */
    public void setTargetDistance(int targetDistance) {
        this.targetDistance = targetDistance;
    }
}
