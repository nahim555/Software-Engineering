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
    <jsp:attribute name="title">Settings</jsp:attribute>
    <jsp:body>

        <c:if test="${param.alert == 'detailSuccess'}">
            <div class="container">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <strong>Updated successful.</strong> Your details has been updated.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
        </c:if>

        <c:if test="${param.alert == 'detailFail'}">
            <div class="container">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <strong>Update failed.</strong> Your details were not updated.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
        </c:if>

        <c:if test="${param.alert == 'credentials'}">
            <div class="container">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <strong>Password change failed.</strong> Your current password was incorrect.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
        </c:if>

        <c:if test="${param.alert == 'pchanged'}">
            <div class="container">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <strong>Password changed.</strong> Your password has been updated.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
        </c:if>


        <h2 class="h5">Update profile details</h2>
        <div class="card">
            <div class="card-body">
                <form action="/dashboard/settings/update" method="post">
                    <div class="form-group">
                        <label for="staticUsername">Username</label>
                        <input class="form-control" type="text" id="staticUsername" value="<c:out value="${user.username}"/>" readonly>
                    </div>
                    <div class="form-group">
                        <label for="exampleInputEmail1">Email address</label>
                        <input name="email" type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" value="<c:out value="${user.email}"/>">
                        <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
                    </div>
                    <div class="form-group">
                        <label for="firstName">First name</label>
                        <input name="firstname" type="text" class="form-control" id="firstName" aria-describedby="emailHelp" value="<c:out value="${user.firstName}"/>">
                    </div>
                    <div class="form-group">
                        <label for="lastName">Last name</label>
                        <input name="lastname" type="text" class="form-control" id="lastName" aria-describedby="emailHelp" value="<c:out value="${user.lastName}"/>">
                    </div>
                    <div class="form-group">
                        <label for="inputGender">Gender</label>
                        <select name="gender" class="form-control" id="inputGender" required>
                            <option value="M" <c:if test = "${user.gender == 'M'}">selected</c:if>>Male</option>
                            <option value="F" <c:if test = "${user.gender == 'F'}">selected</c:if>>Female</option>
                            <option value="U" <c:if test = "${user.gender == 'U'}">selected</c:if>>I'd rather not say</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="height">Height</label>
                        <input name="height" type="text" class="form-control" id="height" aria-describedby="emailHelp" value="<c:out value="${user.height}"/>">
                    </div>
                    <button type="submit" class="btn btn-primary">Submit</button>
                </form>
            </div>
        </div>

        <div class="mt-4 my-4">
            <h2 class="h5">Change password</h2>
            <div class="card">
                <div class="card-body">
                    <form action="/dashboard/settings/update-password" method="post">
                        <div class="form-group">
                            <label for="changePasswordCurrent">Current password</label>
                            <input name="currentPassword" type="password" class="form-control" id="changePasswordCurrent" required placeholder="Current password">
                        </div>
                        <div class="form-group">
                            <label for="changePasswordNew">New password</label>
                            <input name="newPassword" type="password" class="form-control" id="changePasswordNew" pattern=".{3,}" required  oninvalid="this.setCustomValidity('Your password must be at least 4 characters long.')" placeholder="New password">
                        </div>
                        <button type="submit" class="btn btn-primary">Submit</button>
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
