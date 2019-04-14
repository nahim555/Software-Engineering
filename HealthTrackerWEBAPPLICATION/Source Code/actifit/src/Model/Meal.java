package Model;

import java.util.HashMap;
import java.util.Map;

public class Meal {

    private String name;
    private int id;
    private Map<Food, Integer> foods;
    private boolean shared;

    /**
     * Default constructor of a meal object.
     *
     * @param name the name of the meal
     * @param id the unique id of the meal
     * @param shared if the meal is public or not
     */
    public Meal(String name, int id, boolean shared) {
        this.name = name;
        this.id = id;
        this.foods = new HashMap<>();
        this.shared = shared;
    }

    /**
     * Constructor of a meal object without an ID.
     *
     * @param name the name of the meal
     * @param shared if the meal is public or not
     */
    public Meal(String name, boolean shared) {
        this.name = name;
        this.id = 0;
        this.foods = new HashMap<>();
        this.shared = shared;
    }

    /**
     * Overriding equals to compare two Meal objects.
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

        // Check if o is an instance of Meal
        if (!(o instanceof Meal)) {
            return false;
        }

        // Typecast o to Meal so that we can compare data members
        Meal c = (Meal) o;

        // Compare the name and id (this is good enough for what it is needed for)
        return c.name.equals(this.name) && Integer.compare(c.id, this.id) == 0;
    }

    /**
     * Method to get the name of a meal.
     *
     * @return the name of the meal
     */
    public String getName() {
        return name;
    }

    /**
     * Method to set the name of a meal.
     *
     * @param name the name of the meal
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method to get the id of a meal.
     *
     * @return the id of the meal
     */
    public int getId() {
        return id;
    }

    /**
     * Method to get the list of foods with their quantities in a meal.
     *
     * @return the list of foods with their quantities in grams in the meal
     */
    public Map<Food, Integer> getFoods() {
        return foods;
    }

    /**
     * Method to add a food to meal.
     *
     * @param food the food type to add
     * @param weight the number of grams of the food that goes into the meal
     */
    public void addFood(Food food, int weight) {
        foods.put(food, weight);
    }

    /**
     * Method to remove a food from a meal.
     *
     * @param food the food to remove from the meal
     */
    public void removeFood(Food food) {
        foods.remove(food);
    }

    /**
     * Method to check the visibility of a meal.
     *
     * @return if the meal has public visibility
     */
    public boolean isPublic() {
        return shared;
    }

    /**
     * Method to set the visibility of a meal.
     *
     * @param shared if the meal has public visibility
     */
    public void setPublic(boolean shared) {
        this.shared = shared;
    }
}
