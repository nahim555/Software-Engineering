package Model;

public class Food {

    private String name;
    private int id;

    // The following fields should store the information for 100g of a food type
    private double calories;
    private double protein;
    private double fat;
    private double carbohydrates;

    private int servingSize; // The number of grams in a standard serving of this food
    private String servingName; // The name of a serving of the food (e.g. biscuit or slice)
    private boolean shared;

    /**
     * Default constructor of a food object.
     *
     * @param name the name of the food
     * @param id the unique id of the food
     * @param calories the number of calories in 100g of the food
     * @param protein the grams of protein in 100g of the food
     * @param fat the grams of fat in 100g of the food
     * @param carbohydrates the grams of carbohydrates in 100g of the food
     * @param servingSize the standard serving size of the food
     * @param servingName the name of a serving of the food
     * @param shared if the food has public visibility
     */
    public Food(String name, int id, double calories, double protein, double fat, double carbohydrates, int servingSize, String servingName, boolean shared) {
        this.name = name;
        this.id = id;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrates = carbohydrates;
        this.servingSize = servingSize;
        this.servingName = servingName;
        this.shared = shared;
    }

    /**
     * Constructor of a food object without an id.
     *
     * @param name the name of the food
     * @param calories the number of calories in 100g of the food
     * @param protein the grams of protein in 100g of the food
     * @param fat the grams of fat in 100g of the food
     * @param carbohydrates the grams of carbohydrates in 100g of the food
     * @param servingSize the standard serving size of the food
     * @param servingName the name of a serving of the food
     * @param shared if the food has public visibility
     */
    public Food(String name, double calories, double protein, double fat, double carbohydrates, int servingSize, String servingName, boolean shared) {
        this.name = name;
        this.id = 0;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrates = carbohydrates;
        this.servingSize = servingSize;
        this.servingName = servingName;
        this.shared = shared;
    }

    /**
     * Overriding equals to compare two Food objects.
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

        // Check if o is an instance of Food
        if (!(o instanceof Food)) {
            return false;
        }

        // Typecast o to Food so that we can compare data members
        Food c = (Food) o;

        // Compare the id (this is good enough for what it is needed for)
        return c.id == this.id;
    }

    /**
     * Method to get the id of a food.
     *
     * @return the id of the food
     */
    public int getId() {
        return id;
    }

    /**
     * Method to get the name of a food.
     *
     * @return the name of the food
     */
    public String getName() {
        return name;
    }

    /**
     * Method to set the name of a food.
     *
     * @param name the new name of the food
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method to get the calorie count of a food.
     *
     * @return the amount of calories per 100g of the food
     */
    public double getCalories() {
        return calories;
    }

    /**
     * Method to set the calorie count of a food.
     *
     * @param calories the amount of calories per 100g of the food
     */
    public void setCalories(double calories) {
        this.calories = calories;
    }

    /**
     * Method to get the protein amount of a food.
     *
     * @return the grams of protein per 100g of the food
     */
    public double getProtein() {
        return protein;
    }

    /**
     * Method to set the protein amount per food.
     *
     * @param protein the grams of protein per 100g of the food
     */
    public void setProtein(double protein) {
        this.protein = protein;
    }

    /**
     * Method to get the amount of fat in a food.
     *
     * @return the grams of fat per 100g of the food
     */
    public double getFat() {
        return fat;
    }

    /**
     * Method to set the fat amount in a food.
     *
     * @param fat the grams of fat per 100g of the food
     */
    public void setFat(double fat) {
        this.fat = fat;
    }

    /**
     * Method to get the amount of carbohydrates in a food.
     *
     * @return the grams of carbohydrates per 100g of the food
     */
    public double getCarbohydrates() {
        return carbohydrates;
    }

    /**
     * Method to set the amount of carbohydrates in a food.
     *
     * @param carbohydrates the grams of carbohydrates per 100g of the food
     */
    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    /**
     * Method to get the standard serving size of a food.
     *
     * @return the serving size in grams
     */
    public int getServingSize() {
        return servingSize;
    }

    /**
     * Method to set the standard serving size of a food.
     *
     * @param servingSize the serving size in grams
     */
    public void setServingSize(int servingSize) {
        this.servingSize = servingSize;
    }

    /**
     * Method to get the name of a standard serving of a food.
     *
     * @return the name of a standard serving of the food
     */
    public String getServingName() {
        return servingName;
    }

    /**
     * Method to set the name of a standard serving of a food.
     *
     * @param servingName the name of a standard serving of the food
     */
    public void setServingName(String servingName) {
        this.servingName = servingName;
    }

    /**
     * Method to check the visibility of a food.
     *
     * @return if the food has public visibility
     */
    public boolean isPublic() {
        return shared;
    }

    /**
     * Method to set the visibility of a food.
     *
     * @param shared if the food has public visibility
     */
    public void setPublic(boolean shared) {
        this.shared = shared;
    }
}
