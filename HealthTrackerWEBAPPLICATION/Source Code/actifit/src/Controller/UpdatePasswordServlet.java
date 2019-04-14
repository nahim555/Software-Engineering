package Controller;

import Model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "UpdatePasswordServlet")
public class UpdatePasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Model.User user = UserController.fetchUser(request.getSession(false).getAttribute("username").toString());

            //String currentPassword = request.getSession(false).getAttribute("password").toString();
            String currentPasswordInput = request.getParameter("currentPassword");
            String newPassword = request.getParameter("newPassword");
            System.out.println(newPassword);

            if (AuthenticationController.checkCredentials(user.getUsername(), currentPasswordInput) == true) {
                UserController.changePassword(user, newPassword);

                StringBuilder content = new StringBuilder();
                content.append("Hi ").append(user.getFirstName());
                content.append("\n\n");
                content.append("Your password has been changed.");
                content.append("\n\n");
                content.append("If this was not you, please reset your password using the 'Forgot my password' process");

                Controller.sendEmail("no-reply", user.getEmail(), "Password changed", content.toString());

                response.sendRedirect("/dashboard/settings.jsp?alert=pchanged");
            }
            else {
                response.sendRedirect("/dashboard/settings.jsp?alert=credentials");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
