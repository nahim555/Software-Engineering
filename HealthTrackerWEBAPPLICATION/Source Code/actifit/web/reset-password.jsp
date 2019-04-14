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

    <title>ActiFit - Reset password</title>

    <!-- Bootstrap core CSS -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="/css/login.css" rel="stylesheet">
</head>

<body>

<div class="container text-center">
    <form class="form-signin" action="/authentication/reset-password" method="post">
        <img class="mb-4" src="/images/brand/wordmark-dark.svg" alt="ActiFit" height="60">
        <h1 class="h6 mb-3 font-weight-normal">We've sent an email to you with a single use password reset token.</h1>
        <label for="inputToken" class="sr-only">Password reset token</label>
        <input name="token" type="text" id="inputToken" class="form-control mb-2" placeholder="Password reset token" required autofocus>
        <label for="inputPassword" class="sr-only">Password</label>
        <input name="password" type="password" style="border-radius: 4px" id="inputPassword" class="form-control" placeholder="New password" pattern=".{3,}" required oninvalid="this.setCustomValidity('Your password must be at least 4 characters long.')">
        <input class="btn btn-lg btn-primary btn-block" type="submit">
        <p class="mt-5 mb-3 text-muted">ActiFit, 2018. CMP-5012B, Group 21.</p>
    </form>
</div>

<script src="/js/jquery-3.2.1.slim.min.js"></script>
<script src="/js/popper.min.js"></script>
<script src="/js/bootstrap.min.js"></script>

</body>
</html>