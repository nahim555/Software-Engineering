package Controller;

import Model.Exercise;
import Model.TimeGoal;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

@WebServlet(name = "AddTimeGoalServlet")

public class AddTimeGoalServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Model.User user = UserController.fetchUser(request.getSession(false).getAttribute("username").toString());

            int distance = (int) (Double.parseDouble(request.getParameter("distance")) * 1000);
            LocalDate expiry = LocalDate.parse(request.getParameter("expiry"));
            Exercise exercise = ExerciseController.findExerciseById(Integer.parseInt(request.getParameter("exercise")));

            int duration = Period.between(LocalDate.now(), expiry).getDays();

            int targetTime = 0;
            if (request.getParameter("seconds").length() > 0) {
                targetTime += Integer.parseInt(request.getParameter("seconds"));
            } else {
                targetTime += 0;
            }
            if (request.getParameter("minutes").length() > 0) {
                targetTime += Integer.parseInt(request.getParameter("minutes")) * 60;
            } else {
                targetTime += 0;
            }
            if (request.getParameter("hours").length() > 0) {
                targetTime += Integer.parseInt(request.getParameter("hours")) * 3600;
            } else {
                targetTime += 0;
            }

            user.addGoal(new TimeGoal(LocalDate.now(), duration, exercise, distance, ActivityController.calculateFastestForDistance(user, exercise, distance), targetTime));

            UserController.persistGoals(user);

            response.sendRedirect("/dashboard/goals.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }


}