<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
        <link rel="icon" href="../../../../favicon.ico">

        <title>ActiFit - Sign in</title>

        <!-- Bootstrap core CSS -->
        <link href="/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom styles for this template -->
        <link href="/css/login.css" rel="stylesheet">
    </head>

    <body>

        <c:if test="${param.alert == 'credentials'}">
            <div class="container">
                <div class="alert alert-warning alert-dismissible fade show" role="alert">
                    <strong>Sign in failed.</strong> The username or password is incorrect.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
        </c:if>

        <c:if test="${param.alert == 'registered'}">
            <div class="container">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <strong>Registration successful.</strong>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
        </c:if>

        <c:if test="${param.alert == 'reset'}">
            <div class="container">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <strong>Your password has been reset.</strong>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </div>
        </c:if>

        <div class="container text-center">
            <form class="form-signin" action="/authentication/sign-in" method="post">
                <img class="mb-4" src="/images/brand/wordmark-dark.svg" alt="ActiFit" height="60">
                <h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
                <label for="inputUsername" class="sr-only">Username</label>
                <input name="username" type="username" id="inputUsername" class="form-control" placeholder="Username" required autofocus>
                <label for="inputPassword" class="sr-only">Password</label>
                <input name="password" type="password" id="inputPassword" class="form-control" placeholder="Password" required>
                <input class="btn btn-lg btn-primary btn-block mb-2" type="submit">
                <a href="/forgot-password.jsp"><small>I've forgotten my password</small></a>
                <p class="mt-5 mb-3 text-muted">ActiFit, 2018. CMP-5012B, Group 21.</p>
            </form>
        </div>

        <script src="/js/jquery-3.2.1.slim.min.js"></script>
        <script src="/js/popper.min.js"></script>
        <script src="/js/bootstrap.min.js"></script>

    </body>
</html>