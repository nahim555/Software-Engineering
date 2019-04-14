<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>

<%@ page import="Model.*" %>
<%@ page import="Controller.*" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.*" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.sql.Date" %>

<c:set var = "group" value = "${GroupController.findGroupById(param.group)}"/>

<c:if test="${!group.getUsers().contains(user)}">
    <c:redirect url = "/dashboard/groups.jsp"/>
</c:if>

<t:dashboard>
    <jsp:attribute name="title"><c:out value="${group.name}"/></jsp:attribute>
    <jsp:body>
        <c:if test="${param.alert == 'username'}">
            <div class="alert alert-warning alert-dismissible fade show" role="alert">
                <strong>User not found.</strong> We were unable to add this user to the group.
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:if>
        <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3 border-bottom">
            <h1 class="h2">View group</h1>
        </div>

        <div class="d-flex justify-content-between flex-nowrap align-items-center">
            <h2 class="h5"><c:out value="${group.name}"/></h2>
            <div class="btn-toolbar mb-2">
                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addUserModal">
                    Add user
                </button>
                <button type="button" class="btn btn-danger ml-2" data-toggle="modal" data-target="#leaveGroupModal">
                    Leave group
                </button>
            </div>
        </div>

        <h2 class="h6">Members</h2>

        <div class="card">
            <div class="card-body py-0">
                <c:forEach items="${group.users}" var="person">
                    <div class="card bg-light my-4">
                        <div class="card-body">
                            <h6 class="card-title"><c:out value="${person.username}"/></h6>
                            <p class="card-text text-muted"><c:out value="${person.fullName}"/>, <c:out value="${UserController.getAge(person)}"/></p>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

        <h2 class="h6 mt-4">Activities today</h2>

        <div class="card">
            <div class="card-body py-0">
                <c:if test="${empty GroupController.getTodaysActivities(group)}">
                    <div class="card bg-light my-4">
                        <div class="card-body py-2">
                            <p class="card-text">There has been no activity in this group today!</p>
                        </div>
                    </div>
                </c:if>
                <c:forEach items="${GroupController.getTodaysActivities(group)}" var="activity">
                    <div class="card bg-light my-4">
                        <div class="card-body py-2">
                            <p class="card-text"><strong><c:out value="${activity.key.fullName}"/></strong>: <c:out value="${Controller.durationFormat(activity.value.duration)}"/>
                                of <c:out value="${activity.value.exercise.name}"/></p>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

        <div class="modal fade" id="addUserModal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="userModalLabel">Add user</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <form action="/dashboard/group/add-user" method="post">
                        <div class="modal-body">
                            <div class="form-group">
                                <label for="inputUser">Username</label>
                                <input type="text" required class="form-control" id="inputUser" name="user">
                            </div>

                            <div class="form-group d-none">
                                <input type="number" class="form-control" value="<c:out value="${group.id}"/>" name="group">
                            </div>

                        </div>
                        <div class="modal-footer">
                            <input class="btn btn-primary btn-block" type="submit">
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div class="modal fade" id="leaveGroupModal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="removeFoodModalLabel">Leave group</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <p>Are you sure you want to leave this group?</p>
                    </div>
                    <form action="/dashboard/group/leave" method="post">
                        <div class="modal-body p-0">
                            <div class="form-group d-none">
                                <input type="number" class="form-control" name="group" value="<c:out value="${group.id}"/>">
                            </div>
                        </div>
                        <div class="modal-footer">
                            <input class="btn btn-danger btn-block" type="submit">
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <script src="/js/jquery-3.2.1.slim.min.js"></script>
        <script src="/js/popper.min.js"></script>
        <script src="/js/bootstrap.min.js"></script>
        <script src="/js/feather.min.js"></script>
        <script>feather.replace()</script>

    </jsp:body>
</t:dashboard>

</html>
