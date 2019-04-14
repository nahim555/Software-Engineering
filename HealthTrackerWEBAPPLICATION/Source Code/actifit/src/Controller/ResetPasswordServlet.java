package Controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ResetPasswordServlet")
public class ResetPasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Model.User user = UserController.fetchUser(request.getSession(false).getAttribute("userToReset").toString());
            String token = request.getSession(false).getAttribute("token").toString();

            HttpSession session = request.getSession();
            session.removeAttribute("userToReset");
            session.removeAttribute("token");

            String tokenInput = request.getParameter("token");
            String newPassword = request.getParameter("password");

            if (token.equals(tokenInput)) {
                UserController.changePassword(user, newPassword);
                response.sendRedirect("/sign-in.jsp?alert=reset");
            } else {
                response.sendRedirect("/forgot-password.jsp?alert=token");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
