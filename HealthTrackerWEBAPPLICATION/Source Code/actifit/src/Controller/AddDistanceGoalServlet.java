package Controller;

import Model.DistanceGoal;
import Model.Exercise;
import Model.WeightLossGoal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

@WebServlet(name = "AddDistanceGoalServlet")
public class AddDistanceGoalServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Model.User user = UserController.fetchUser(request.getSession(false).getAttribute("username").toString());

            int goalDistance = (int) (Double.parseDouble(request.getParameter("goalDistance")) * 1000);
            LocalDate expiry = LocalDate.parse(request.getParameter("expiry"));
            Exercise exercise = ExerciseController.findExerciseById(Integer.parseInt(request.getParameter("exercise")));

            int duration = Period.between(LocalDate.now(), expiry).getDays();

            user.addGoal(new DistanceGoal(LocalDate.now(), duration, exercise, ActivityController.getDistancePersonalBest(user, exercise), goalDistance));

            UserController.persistGoals(user);

            response.sendRedirect("/dashboard/goals.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
