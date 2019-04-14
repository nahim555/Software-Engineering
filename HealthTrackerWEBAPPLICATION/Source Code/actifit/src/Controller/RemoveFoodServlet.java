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
import java.time.LocalDate;

@WebServlet(name = "RemoveFoodServlet")
public class RemoveFoodServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Model.User user = UserController.fetchUser(request.getSession(false).getAttribute("username").toString());
            Food food = FoodController.findFoodById(Integer.parseInt(request.getParameter("food")));

            if (food.isPublic()) {
                FoodController.dissociateFood(user, food);
            } else {
                user.removeFood(food);
                UserController.persistFoods(user);
            }

            response.sendRedirect("/dashboard/food-meals.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
