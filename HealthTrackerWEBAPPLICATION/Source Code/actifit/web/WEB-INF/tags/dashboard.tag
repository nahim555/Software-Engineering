<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@ attribute name="title" fragment="true" %>

<%@ tag import="Model.*" %>
<%@ tag import="Controller.*" %>
<%@ tag import="java.util.List" %>

<c:set var = "user" scope = "session" value = "${UserController.fetchUser(sessionScope.username.toString())}"/>

<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="icon" href="../../../../favicon.ico">
        <title>ActiFit - <jsp:invoke fragment="title"/></title>
        <link href="/css/bootstrap.min.css" rel="stylesheet">
        <link href="/css/dashboard.css" rel="stylesheet">
    </head>

    <body>
        <nav class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0">
            <a class="navbar-brand col-sm-3 col-md-2 mr-0" href="#">
                <img src="/images/brand/wordmark-light.svg" height="25px">
            </a>

            <ul class="navbar-nav px-3 list-inline d-inline">
                <li class="nav-item text-nowrap list-inline-item">
                    <a class="nav-link" href="/dashboard/settings.jsp">Settings</a>
                </li>
                <li class="nav-item text-nowrap list-inline-item">
                    <a class="nav-link" href="/authentication/sign-out">Sign out</a>
                </li>
            </ul>
        </nav>
        <div class="container-fluid">
            <div class="row">
                <nav class="col-md-2 d-none d-md-block bg-light sidebar">
                    <div class="sidebar-sticky">
                        <ul class="nav flex-column">
                            <li class="nav-item">
                                <a class="nav-link" href="/dashboard/index.jsp"><span data-feather="home"></span>Dashboard</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/dashboard/groups.jsp"><span data-feather="users"></span>My groups</a>
                                <c:forEach items="${GroupController.getGroups(user)}" var="group">
                                    <a class="ml-5 mb-1 text-muted d-block" href="/dashboard/group.jsp?group=<c:out value="${group.id}"/>"><c:out value="${group.name}"/></a>
                                </c:forEach>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/dashboard/diary.jsp"><span data-feather="book-open"></span>Diary</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/dashboard/food-meals.jsp"><span data-feather="shopping-cart"></span>Food & Meals</a>
                            </li>
                            <li class="nav=item">
                                <a class="nav-link" href="/dashboard/goals.jsp"><span data-feather="target"></span>Goals</a>
                            </li>
                        </ul>
                    </div>
                </nav>

                <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
                    <jsp:doBody/>
                </main>
            </div>
        </div>
    </body>
</html>