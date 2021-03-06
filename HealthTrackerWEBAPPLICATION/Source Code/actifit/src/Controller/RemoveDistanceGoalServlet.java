package Controller;

import Model.DistanceGoal;
import Model.Goal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet(name = "RemoveDistanceGoalServlet")
public class RemoveDistanceGoalServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Model.User user = UserController.fetchUser(request.getSession(false).getAttribute("username").toString());

            int duration = Integer.parseInt(request.getParameter("duration"));
            int start = Integer.parseInt(request.getParameter("start"));
            int target = Integer.parseInt(request.getParameter("target"));

            for (Goal goal : user.getGoals()) {
                if (goal instanceof DistanceGoal
                        && goal.getDuration() == duration
                        && ((DistanceGoal) goal).getPreviousBestDistance() == start
                        && ((DistanceGoal) goal).getTargetDistance() == target) {
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
