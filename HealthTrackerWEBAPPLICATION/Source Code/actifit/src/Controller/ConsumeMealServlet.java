package Controller;

import Model.Meal;
import org.mockito.cglib.core.Local;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

@WebServlet(name = "ConsumeMealServlet")
public class ConsumeMealServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Model.User user = UserController.fetchUser(request.getSession(false).getAttribute("username").toString());

            Meal meal = MealController.findMealById(Integer.parseInt(request.getParameter("meal")));
            int time = Integer.parseInt(request.getParameter("time"));
            double quantity = Double.parseDouble(request.getParameter("quantity"));
            LocalDate date = LocalDate.parse(request.getParameter("date"));

            user.addConsumedMeal(date, time, meal, quantity);

            UserController.persistConsumedMeals(user);

            response.sendRedirect("/dashboard/diary.jsp?history=" + Period.between(date, LocalDate.now()).getDays());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
