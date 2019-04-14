package Model;

import java.time.LocalDate;

public class Activity {

    private Exercise exercise;
    private LocalDate date;
    private int duration;
    private int intensity;

    /**
     * Constructor for an activity object.
     *
     * @param exercise the exercise that the user did
     * @param date when the user did the activity
     * @param duration how long the activity lasted in seconds
     * @param intensity how intense the exercise was (or the distance in m for some activities)
     */
    public Activity(Exercise exercise, LocalDate date, int duration, int intensity) {
        this.exercise = exercise;
        this.date = date;
        this.duration = duration;
        this.intensity = intensity;
    }

    /**
     * Overriding equals to compare two Activity objects.
     *
     * @param o the object to compare to
     * @return if the objects are equal
     */
    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        // Check if o is an instance of Activity
        if (!(o instanceof Activity)) {
            return false;
        }

        // Typecast o to Activity so that we can compare data members
        Activity c = (Activity) o;

        // Compare all the fields of the activity
        return c.intensity == this.intensity &&
                c.duration == this.duration &&
                c.date.equals(this.date) &&
                c.exercise.getId() == this.exercise.getId();
    }

    /**
     * Method to get the exercise of an activity.
     *
     * @return the exercise of the activity
     */
    public Exercise getExercise() {
        return exercise;
    }

    /**
     * Method to set the exercise of an activity.
     *
     * @param exercise the new exercise
     */
    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    /**
     * Method to get the date that an activity was done.
     *
     * @return the date of the activity was done
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Method to set the date that an activity was done.
     *
     * @param date the new date of the activity
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Method to get the duration of an activity.
     *
     * @return the duration of the activity in seconds
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Method to set the duration of an activity.
     *
     * @param duration the new duration of an activity in seconds
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Method to get the intensity (or distance in m) of an activity.
     *
     * @return the intensity (or distance in m) of an activity
     */
    public int getIntensity() {
        return intensity;
    }

    /**
     * Method to set the intensity (or distance in m) of an activity.
     *
     * @param intensity the new intensity (or distance in m) of an activity
     */
    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }
}
