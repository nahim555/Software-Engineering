package Controller;

import Model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet(name = "RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Stuff for outputting
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Get the parameters
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String gender = request.getParameter("gender");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        LocalDate dateOfBirth = LocalDate.parse(request.getParameter("dateofbirth"));
        int height = Integer.parseInt(request.getParameter("height"));
        double weight = Double.parseDouble(request.getParameter("weight"));

        try {
            if (!AuthenticationController.validateEmail(email)) {
                response.sendRedirect("/forgot-password.jsp?alert=email");
            } else if (!AuthenticationController.validateUsername(username)) { // If username is taken

                HttpSession session = request.getSession();
                session.setAttribute("firstname", firstName);
                session.setAttribute("lastname", lastName);
                session.setAttribute("gender", gender);
                session.setAttribute("email", email);
                session.setAttribute("dateofbirth", dateOfBirth);
                session.setAttribute("height", height);
                session.setAttribute("weight", weight);

                response.sendRedirect("/sign-up.jsp?alert=username"); // Go back to sign up page with alert
            } else {
                UserController.newUser(username, email, firstName, lastName, dateOfBirth, height, weight, password, User.Gender.valueOf(gender));
                response.sendRedirect("/sign-in.jsp?alert=registered");
            }
        } catch (Exception e) {
            out.println("<h1>" + "Error" + "</h1>"); // TODO redirect to error page instead
            out.println(e.getMessage());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
