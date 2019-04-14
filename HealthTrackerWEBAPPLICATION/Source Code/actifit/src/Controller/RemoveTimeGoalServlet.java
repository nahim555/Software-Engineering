package Controller;

import Model.Goal;
import Model.TimeGoal;
import Model.WeightLossGoal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet(name = "RemoveTimeGoalServlet")
public class RemoveTimeGoalServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Model.User user = UserController.fetchUser(request.getSession(false).getAttribute("username").toString());

            int duration = Integer.parseInt(request.getParameter("duration"));
            int start = Integer.parseInt(request.getParameter("start"));
            int target = Integer.parseInt(request.getParameter("target"));

            for (Goal goal : user.getGoals()) {
                if (goal instanceof TimeGoal
                        && goal.getDuration() == duration
                        && ((TimeGoal) goal).getPreviousBestTime() == start
                        && ((TimeGoal) goal).getTargetTime() == target) {
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
