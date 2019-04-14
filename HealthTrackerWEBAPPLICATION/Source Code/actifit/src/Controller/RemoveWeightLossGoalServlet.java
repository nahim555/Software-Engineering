package Controller;

import Model.Goal;
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

@WebServlet(name = "RemoveWeightLossGoalServlet")
public class RemoveWeightLossGoalServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Model.User user = UserController.fetchUser(request.getSession(false).getAttribute("username").toString());

            int duration = Integer.parseInt(request.getParameter("duration"));
            double start = Double.parseDouble(request.getParameter("start"));
            double target = Double.parseDouble(request.getParameter("target"));

            for (Goal goal : user.getGoals()) {
                if (goal instanceof WeightLossGoal
                        && goal.getDuration() == duration
                        && ((WeightLossGoal) goal).getStartingWeight() == start
                        && ((WeightLossGoal) goal).getTargetWeight() == target) {
                    user.removeGoal(goal);
                    break;
                }
            }

            UserController.persistGoals(user);

            response.sendRedirect("/dashboard/goals.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
