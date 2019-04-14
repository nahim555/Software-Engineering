package Controller;

import Model.Activity;
import Model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet(name = "UpdateInfoServlet")
public class UpdateInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Model.User user = UserController.fetchUser(request.getSession(false).getAttribute("username").toString());

            String firstName = request.getParameter("firstname");
            String lastName = request.getParameter("lastname");
            User.Gender gender = User.Gender.valueOf(request.getParameter("gender"));
            String email = request.getParameter("email");
            int height = Integer.parseInt(request.getParameter("height"));

            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setGender(gender);
            user.setEmail(email);
            user.setHeight(height);

            UserController.persistUser(user);

            response.sendRedirect("/dashboard/settings.jsp?alert=detailSuccess");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
