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

<t:dashboard>
    <jsp:attribute name="title">My groups</jsp:attribute>
    <jsp:body>
        <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3 border-bottom">
            <h1 class="h2">My groups</h1>
        </div>

        <div class="btn-toolbar mb-2">
            <button type="button" class="btn btn-primary ml-auto" data-toggle="modal" data-target="#createGroupModal">
                Create group
            </button>
        </div>

        <div class="card">
            <div class="card-body py-0">
                <c:if test="${empty GroupController.getGroups(user)}">
                    <div class="text-muted my-3">
                        <span>You're not a member of any groups!</span>
                    </div>
                </c:if>
                <c:forEach items="${GroupController.getGroups(user)}" var="group">
                    <div class="card bg-light my-4">
                        <a href="/dashboard/group.jsp?group=<c:out value="${group.id}"/>">
                            <div class="card-body">
                                <h6 class="card-title text-dark"><c:out value="${group.name}"/></h6>
                                <p class="card-text text-muted"><c:out value="${group.getUsers().size()}"/> member${group.getUsers().size() > 1 ? 's' : ''}</p>
                            </div>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>

        <div class="modal fade" id="createGroupModal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="createGroupModalLabel">Create group</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <form action="/dashboard/group/create" method="post">
                        <div class="modal-body">
                            <div class="form-group">
                                <label for="inputGroupName">Group name</label>
                                <input type="text" required class="form-control" id="inputGroupName" name="name">
                            </div>
                        </div>
                        <div class="modal-footer">
                            <input class="btn btn-primary btn-block" type="submit">
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
