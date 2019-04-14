package Controller;

import Model.Food;
import Model.Meal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet(name = "AddFoodServlet")
public class AddFoodServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Model.User user = UserController.fetchUser(request.getSession(false).getAttribute("username").toString());

            String name = request.getParameter("name");
            double calories = Double.parseDouble(request.getParameter("calories"));
            double protein = Double.parseDouble(request.getParameter("protein"));
            double fat = Double.parseDouble(request.getParameter("fat"));
            double carbohydrates = Double.parseDouble(request.getParameter("carbohydrates"));
            int servingSize = Integer.parseInt(request.getParameter("servingSize"));
            String servingName = request.getParameter("servingName");
            boolean shared = request.getParameter("public") != null;

            user.addFood(new Food(name, calories, protein, fat, carbohydrates, servingSize, servingName, shared));

            UserController.persistFoods(user);

            response.sendRedirect("/dashboard/food-meals.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
