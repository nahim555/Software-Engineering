package Controller;

import java.io.*;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        // Get the parameters
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Stuff for outputting
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            if (AuthenticationController.checkCredentials(username, password)) {
                HttpSession session = request.getSession();
                session.setAttribute("username", username); // Set the session variable for username
                response.sendRedirect("/dashboard/index.jsp"); // Redirect to the dashboard home
            } else {
                response.sendRedirect("/sign-in.jsp?alert=credentials"); // Go back to sign up page, with alert
            }
        } catch (SQLException e) {
            out.println("<h1>" + "Error" + "</h1>"); // TODO redirect to error page instead
        }



    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }
}
