package Controller;

import Model.Activity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "AddActivityServlet")
public class AddActivityServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Model.User user = UserController.fetchUser(request.getSession(false).getAttribute("username").toString());
            Model.Exercise exercise = ExerciseController.findExerciseById(Integer.parseInt(request.getParameter("exercise")));
            int intensity;
            if (exercise.isMeasurable()) {
                intensity = Integer.parseInt(request.getParameter("distance"));
            } else {
                intensity = Integer.parseInt(request.getParameter("intensity"));
            }

            int duration = 0;
            if (request.getParameter("seconds").length() > 0) {
                duration += Integer.parseInt(request.getParameter("seconds"));
            } else {
                duration += 0;
            }
            if (request.getParameter("minutes").length() > 0) {
                duration += Integer.parseInt(request.getParameter("minutes")) * 60;
            } else {
                duration += 0;
            }
            if (request.getParameter("hours").length() > 0) {
                duration += Integer.parseInt(request.getParameter("hours")) * 3600;
            } else {
                duration += 0;
            }
            
            LocalDate date = LocalDate.parse(request.getParameter("date"));

            user.addActivity(new Activity(exercise, date, duration, intensity));

            UserController.persistActivities(user);

            response.sendRedirect("/dashboard/diary.jsp?history=" + Period.between(date, LocalDate.now()).getDays());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
