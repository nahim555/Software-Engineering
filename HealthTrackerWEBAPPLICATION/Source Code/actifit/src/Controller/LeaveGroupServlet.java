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

@WebServlet(name = "LeaveGroupServlet")
public class LeaveGroupServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            User user = UserController.fetchUser(request.getSession(false).getAttribute("username").toString());
            Group group = GroupController.findGroupById(Integer.parseInt(request.getParameter("group")));

            if (group.getUsers().contains(user)) {
                group.removeUser(user);
                GroupController.saveGroup(group);
            }

            response.sendRedirect("/dashboard/groups.jsp");
        } catch (SQLException e) {
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
