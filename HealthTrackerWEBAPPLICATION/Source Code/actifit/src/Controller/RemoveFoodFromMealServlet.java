package Controller;

import Model.Food;
import Model.Meal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "RemoveFoodFromMealServlet")
public class RemoveFoodFromMealServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Model.User user = UserController.fetchUser(request.getSession(false).getAttribute("username").toString());

            Meal meal = MealController.findMealById(Integer.parseInt(request.getParameter("meal")));
            Food food = FoodController.findFoodById(Integer.parseInt(request.getParameter("food")));

            if (user.getMeals().contains(meal)) {
                for (Food foodCheck : meal.getFoods().keySet()) {
                    if (food.getId() == foodCheck.getId()) {
                        meal.getFoods().remove(foodCheck);
                    }
                }
                MealController.saveMeal(meal);
            } else {
                response.sendRedirect("/dashboard/food-meals.jsp");
            }

            response.sendRedirect("/dashboard/food-meals/edit-meal.jsp?meal=" + meal.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
