import Controller.*;
import Controller.UserController;
import Model.Meal;
import Model.User;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Test {

    public static void main(String[] args) throws Exception {

        User user = UserController.fetchUser("JSmith");

        int bmr = UserController.findBMR(user);
        int consumed = MealController.getUsersConsumedCalories(user, LocalDate.now());
        int burned = ActivityController.getCaloriesBurnedForDate(user, LocalDate.now());

        System.out.println("Total allowed: " + bmr);
        System.out.println("Change today: " + (consumed - burned));
        System.out.println("Left to consume: " + (bmr + burned - consumed));

        System.out.println(Controller.calculatePercentage((bmr + burned - consumed), bmr));

    }
}
