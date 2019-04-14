package Controller;

import Controller.ExerciseController;
import Controller.UserController;
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

@WebServlet(name = "RemoveActivityServlet")
public class RemoveActivityServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Model.User user = UserController.fetchUser(request.getSession(false).getAttribute("username").toString());
            Model.Exercise exercise = ExerciseController.findExerciseById(Integer.parseInt(request.getParameter("exercise")));
            LocalDate date = LocalDate.parse(request.getParameter("date"));
            int duration = Integer.parseInt(request.getParameter("duration"));
            int intensity = Integer.parseInt(request.getParameter("intensity"));

            user.removeActivity(new Activity(exercise, date, duration, intensity));

            UserController.persistActivities(user);

            response.sendRedirect("/dashboard/diary.jsp?history=" + Period.between(date, LocalDate.now()).getDays());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
