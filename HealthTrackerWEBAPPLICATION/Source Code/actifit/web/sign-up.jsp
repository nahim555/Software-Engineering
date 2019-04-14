<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>

<%
    Object user = request.getSession().getAttribute("username");
    if (null != user) {
        response.sendRedirect("/dashboard/index.jsp");
    }
%>

<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>ActiFit - Sign up</title>

        <!-- Bootstrap core CSS -->
        <link href="css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom styles for this template -->
        <link href="css/login.css" rel="stylesheet">
    </head>

    <body>

        <c:if test="${param.alert == 'username'}">
            <div class="container">
                <div class="alert alert-warning alert-dismissible fade show" role="alert">
                    <strong>Registration failed.</strong> The username is not available.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
        </c:if>

        <div class="container">
            <form class="form-signup" action="/authentication/sign-up" method="post">
                <div class="text-center">
                    <img class="mb-4" src="/images/brand/wordmark-dark.svg" alt="ActiFit" height="60">
                    <h1 class="h3 mb-3 font-weight-normal">User registration</h1>
                </div>

                <div class="row">
                    <div class="col-sm">
                        <div class="form-group">
                            <label for="inputFirstName">First name</label>
                            <input name="firstname" type="text" class="form-control" id="inputFirstName"
                                   pattern="[^\s]+" title="Please remove spaces" placeholder="Enter first name"
                                   value="<c:out value="${sessionScope.firstname}"/>" required>
                        </div>
                    </div>
                    <div class="col-sm">
                        <div class="form-group">
                            <label for="inputLastName">Last name</label>
                            <input name="lastname" type="text" class="form-control" id="inputLastName"
                                   pattern="[^\s]+" title="Please remove spaces" placeholder="Enter last name"
                                   value="<c:out value="${sessionScope.lastname}"/>" required>
                        </div>
                    </div>
                    <div class="col-sm">
                        <div class="form-group">
                            <label for="inputGender">Gender</label>
                            <select name="gender" class="form-control" id="inputGender" required>
                                <option value="M" <c:if test = "${sessionScope.gender == 'M'}">selected</c:if>>Male</option>
                                <option value="F" <c:if test = "${sessionScope.gender == 'F'}">selected</c:if>>Female</option>
                                <option value="U" <c:if test = "${sessionScope.gender == 'U'}">selected</c:if>>I'd rather not say</option>
                            </select>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-sm">
                        <div class="form-group">
                            <label for="inputUsername">Username</label>
                            <input name="username" type="text" class="form-control" id="inputUsername"
                                   pattern="[^\s]+" title="Please remove spaces" placeholder="Enter username" required>
                        </div>
                    </div>
                    <div class="col-sm">
                        <div class="form-group">
                            <label for="inputPassword">Password</label>
                            <input name="password" type="password" class="form-control" id="inputPassword"
                                   placeholder="Enter password" pattern=".{3,}" required  oninvalid="this.setCustomValidity('Your password must be at least 4 characters long.')">
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-sm">
                        <div class="form-group">
                            <label for="inputEmail">Email address</label>
                            <input name="email" type="email" class="form-control" id="inputEmail"
                                   placeholder="Enter email address"
                                   value="<c:out value="${sessionScope.email}"/>" required>
                        </div>
                    </div>
                    <div class="col-sm">
                        <div class="form-group">
                            <label for="inputDateOfBirth">Date of birth</label>
                            <input name="dateofbirth" type="date" class="form-control" id="inputDateOfBirth"
                                   value="<c:out value="${sessionScope.dateofbirth}"/>" required>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-sm">
                        <div class="form-group">
                            <label for="inputHeight">Height (cm)</label>
                            <input name="height" type="number" step="1" class="form-control" id="inputHeight"
                                   placeholder="Enter current height"
                                   value="<c:out value="${sessionScope.height}"/>" required>
                        </div>
                    </div>
                    <div class="col-sm">
                        <div class="form-group">
                            <label for="inputWeight">Weight (kg)</label>
                            <input name="weight" type="number" step="0.1" class="form-control" id="inputWeight"
                                   placeholder="Enter current weight"
                                   value="<c:out value="${sessionScope.weight}"/>" required>
                        </div>
                    </div>
                </div>

                <input class="mt-3 btn btn-lg btn-primary btn-block" type="submit">
                <p class="mt-5 mb-3 text-muted text-center">ActiFit, 2018. CMP-5012B, Group 21.</p>

            </form>
        </div>

        <script src="/js/jquery-3.2.1.slim.min.js"></script>
        <script src="/js/popper.min.js"></script>
        <script src="/js/bootstrap.min.js"></script>

    </body>
</html>
