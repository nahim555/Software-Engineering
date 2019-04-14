package Model;

import java.time.LocalDate;

public abstract class Goal {

    protected LocalDate startingDate;
    protected int duration;

    /**
     * Method to get the date a goal was started.
     *
     * @return the date the goal was started
     */
    public LocalDate getStartingDate() {
        return startingDate;
    }

    /**
     * Method to set the date that a goal was started.
     *
     * @param startingDate the date the goal started
     */
    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    /**
     * Method to get the duration of a goal.
     *
     * @return the number of days the user has given to complete the goal
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Method to set the duration of a goal.
     *
     * @param duration the number of days the user has given to complete the goal
     */
    public void setDuration(int duration) {
        this.duration = duration;
    };
}
