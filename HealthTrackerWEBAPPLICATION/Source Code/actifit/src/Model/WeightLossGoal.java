package Model;

import java.time.LocalDate;

public class WeightLossGoal extends Goal {

    private double startingWeight;
    private double targetWeight;

    /**
     * Default constructor of a weight loss goal.
     *
     * @param startingDate the date the goal was started
     * @param duration the number of days given to complete the goal
     * @param startingWeight the weight the user started on
     * @param targetWeight the weight the user is aiming to be at the end of the duration
     */
    public WeightLossGoal(LocalDate startingDate, int duration, double startingWeight, double targetWeight) {
        this.startingDate = startingDate;
        this.duration = duration;
        this.startingWeight = startingWeight;
        this.targetWeight = targetWeight;
    }

    /**
     * Method to get a users starting weight.
     *
     * @return the weight that the user started the goal at
     */
    public double getStartingWeight() {
        return startingWeight;
    }

    /**
     * Method to set a users starting weight.
     *
     * @param startingWeight the weight that the user started the goal at
     */
    public void setStartingWeight(double startingWeight) {
        this.startingWeight = startingWeight;
    }

    /**
     * Method to get a users target weight.
     *
     * @return the weight the user aims to be
     */
    public double getTargetWeight() {
        return targetWeight;
    }

    /**
     * Method to set a users target weight.
     *
     * @param targetWeight the weight the user aims to be
     */
    public void setTargetWeight(double targetWeight) {
        this.targetWeight = targetWeight;
    }
}