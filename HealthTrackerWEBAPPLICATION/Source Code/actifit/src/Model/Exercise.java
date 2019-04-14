package Model;

public class Exercise {

    private String name;
    private int id;
    private double multiplier;
    private double adjustment;
    private boolean measurable;

    /**
     * Constructor of an Exercise object.
     *
     * @param name the name of the exercise
     * @param multiplier the amount to mutliply the intensity (speed) by to get the MET value
     * @param adjustment the amount to add to the previous value to the final MET value
     */
    public Exercise(String name, int id, double multiplier, double adjustment, boolean measurable) {
        this.name = name;
        this.id = id;
        this.multiplier = multiplier;
        this.adjustment = adjustment;
        this.measurable = measurable;
    }

    /**
     * Overriding equals to compare two Exercise objects.
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

        // Check if o is an instance of Exercise
        if (!(o instanceof Exercise)) {
            return false;
        }

        // Typecast o to Exercise so that we can compare data members
        Exercise c = (Exercise) o;

        // Compare the id (this is good enough for what it is needed for)
        return c.id == this.id;
    }

    /**
     * Method to get a name of an exercise.
     *
     * @return the name of the exercise
     */
    public String getName() {
        return name;
    }

    /**
     * Method to set a name of an exercise.
     *
     * @param name name of the exercise
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method to get the id of an exercise.
     *
     * @return the id of the exercise
     */
    public int getId() {
        return id;
    }

    /**
     * Method to set the id of an exercise.
     *
     * @param id the id of the exercise
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Method to get the multiplier value of an exercise.
     *
     * @return the multiplier
     */
    public double getMultiplier() {
        return multiplier;
    }

    /**
     * Method to set the multiplier value of an exercise.
     *
     * @param multiplier the new multiplier value
     */
    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    /**
     * Method to get the adjustment value of an exercise.
     *
     * @return the adjustment value of an exercise
     */
    public double getAdjustment() {
        return adjustment;
    }

    /**
     * Method to set the adjustment value of an exercise.
     *
     * @param adjustment the new adjustment value
     */
    public void setAdjustment(double adjustment) {
        this.adjustment = adjustment;
    }

    /**
     * Method to see if a exercise is measurable (it has a distance).
     *
     * @return if a exercise is measurable (if it has a distance)
     */
    public boolean isMeasurable() {
        return measurable;
    }

    /**
     * Method to set if a exercise is measurable (if it has a distance).
     *
     * @param measurable if a exercise is measurable (if it has a distance)
     */
    public void setMeasurable(boolean measurable) {
        this.measurable = measurable;
    }
}
