package Controller;

import Model.Group;
import Model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "CreateGroupServlet")
public class CreateGroupServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            User user = UserController.fetchUser(request.getSession(false).getAttribute("username").toString());
            String name = request.getParameter("name");

            Group group = GroupController.newGroup(name);
            group.addUser(user);
            GroupController.saveGroup(group);

            response.sendRedirect("/dashboard/groups.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
