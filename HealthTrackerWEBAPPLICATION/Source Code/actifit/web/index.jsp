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
    <link rel="icon" href="">

    <title>Actifit. Enhance life.</title>

    <!-- Bootstrap core CSS -->
    <link href="/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="/css/pricing.css" rel="stylesheet">

    <link rel="stylesheet" href="/css/index.css">
</head>

<body>

<div class="d-flex flex-column flex-sm-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom box-shadow">
    <a class="navbar-brand mr-auto" href="index.jsp">
        <img src="/images/brand/wordmark-dark.svg" height="25px">
    </a>
    <a class="btn btn-outline-primary" href="sign-in.jsp">Sign in</a>
</div>

<div class="col d-flex justify-content-center">
    <div class="container">
        <div class="row justify-content-sm-center">
            <div class="col-lg-6 col-md-7 col-sm-8 card">
                <div class="card-body">
                    <div class="pricing-header px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">
                        <h1 class="display-4">Free account</h1>
                        <p class="lead">Sign up for your free account and begin tracking you health. Set yourself goals, record your exercise and track your meals; all within a simple dashboard system.</p>
                        <a class="btn btn-lg btn-block btn-outline-primary" href="sign-up.jsp">Sign up now</a>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>


<div class="card" style="width: 30vw;">

</div>


<script src="/js/jquery-3.2.1.slim.min.js"></script>
<script src="/js/popper.min.js"></script>
<script src="/js/bootstrap.min.js"></script>

</body>
</html>