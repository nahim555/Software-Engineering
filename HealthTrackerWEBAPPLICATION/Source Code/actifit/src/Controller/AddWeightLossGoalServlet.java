package Controller;

import Model.WeightLossGoal;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

@WebServlet(name = "AddWeightLossGoalServlet")

public class AddWeightLossGoalServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Model.User user = UserController.fetchUser(request.getSession(false).getAttribute("username").toString());

            double goalWeight = Double.parseDouble(request.getParameter("goalWeight"));
            LocalDate expiry = LocalDate.parse(request.getParameter("expiry"));

            int duration = Period.between(LocalDate.now(), expiry).getDays();

            user.addGoal(new WeightLossGoal(LocalDate.now(), duration, user.getMostRecentWeight(), goalWeight));

            UserController.persistGoals(user);

            response.sendRedirect("/dashboard/goals.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }

}


