package Controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ForgotPasswordServlet")
public class ForgotPasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Model.User user = UserController.fetchUser(request.getParameter("username"));
            String token = UserController.sendPasswordResetEmail(user);
            HttpSession session = request.getSession();
            session.setAttribute("userToReset", user.getUsername());
            session.setAttribute("token", token);

            response.sendRedirect("/reset-password.jsp");
        } catch (SQLException e) {
            response.sendRedirect("/forgot-password.jsp?alert=user");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
