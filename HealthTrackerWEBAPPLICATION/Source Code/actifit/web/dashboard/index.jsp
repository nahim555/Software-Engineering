<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>

<%@ page import="Model.*" %>
<%@ page import="Controller.*" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.*" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.util.stream.Collectors" %>

<t:dashboard>
    <jsp:attribute name="title">Dashboard</jsp:attribute>
    <jsp:body>
        <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3 border-bottom">
            <h1 class="h2">Hello <c:out value="${user.firstName}"/>!</h1>
        </div>

        <div class="d-flex justify-content-between flex-nowrap align-items-center">
            <h2 class="h5">Your weight</h2>
            <div class="btn-toolbar mb-2">
                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#weightModal">
                    Log weight
                </button>
                <div class="modal fade" id="weightModal" tabindex="-1" role="dialog">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="weightModalLabel">Log weight</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <form action="/dashboard/weight/log" method="post">
                                <div class="modal-body">
                                    <label for="inputWeight">Weight (kg)</label>
                                    <input name="weight" type="number" step="0.1" min="0" class="form-control"
                                           id="inputWeight" placeholder="Enter today's weight" required>
                                </div>
                                <div class="modal-footer">
                                    <input class="btn btn-primary btn-block" type="submit">
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <canvas class="mt-3 mb-4" id="weightChart" width="900" height="280"></canvas>

        <h2 class="h5">Personal bests</h2>

        <div class="row">
            <div class="col-sm">
                <h3 class="h6">Distance</h3>
                <div class="card mb-4">
                    <div class="card-body py-0">
                        <c:forEach items="${ActivityController.getAllDistancePersonalBests(user)}" var="best">
                            <div class="card bg-light my-4">
                                <div class="card-body">
                                    <h6 class="card-title"><c:out value="${best.key.name}"/></h6>
                                    <p class="card-text text-muted"><c:out value="${Controller.unitConvert(best.value, 'm', 'km')}"/>km</p>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <div class="col-sm">
                <h3 class="h6">Speed (average)</h3>
                <div class="card mb-4">
                    <div class="card-body py-0">
                        <c:forEach items="${ActivityController.getAllSpeedPersonalBests(user)}" var="best">
                            <div class="card bg-light my-4">
                                <div class="card-body">
                                    <h6 class="card-title"><c:out value="${best.key.name}"/></h6>
                                    <p class="card-text text-muted"><fmt:formatNumber value="${best.value}" maxFractionDigits="1" />km/h</p>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>

        <h2 class="h5">Calories consumed</h2>

        <canvas class="mt-3 mb-4" id="caloriesConsumedChart" width="900" height="280"></canvas>

        <h2 class="h5">Calories burned</h2>

        <canvas class="mt-3 mb-4" id="caloriesBurnedChart" width="900" height="280"></canvas>

        <script src="/js/jquery-3.2.1.slim.min.js"></script>
        <script src="/js/popper.min.js"></script>
        <script src="/js/bootstrap.min.js"></script>
        <script src="/js/feather.min.js"></script>
        <script>feather.replace()</script>

        <script src="/js/moment.min.js"></script>
        <script src="/js/chart.min.js"></script>
        <script>
            var ctx = document.getElementById("weightChart");
            var weightChart = new Chart(ctx, {
                type: 'line',
                data: {
                    labels: [<c:forEach items="${user.getWeights().keySet()}" var="date">
                                new Date(<c:out value="${date.getYear()}"/>, <c:out value="${date.getMonthValue() - 1}"/>, <c:out value="${date.getDayOfMonth()}"/>),
                            </c:forEach>],
                    datasets: [{
                        data: <c:out value="${Arrays.toString(user.getWeights().values().toArray())}"/>,
                        lineTension: 0,
                        backgroundColor: 'transparent',
                        borderColor: '#007bff',
                        borderWidth: 4,
                        pointBackgroundColor: '#007bff'
                    }]
                },
                options: {
                    scales: {
                        xAxes: [{
                            type: 'time',
                            time: {
                                unit: 'day',
                                unitStepSize: 1,
                                displayFormats: { 'day': 'DD MMM' }
                            },
                            display: true,
                            scaleLabel: { display: false }
                        }],
                        yAxes: [{
                            ticks: { beginAtZero: false },
                            scaleLabel: {
                                display: true,
                                labelString: 'Weight (kg)'
                            }
                        }]
                    },
                    legend: { display: false },
                    spanGaps: true
                }
            });
        </script>

        <script>
            var ctx = document.getElementById("caloriesConsumedChart");
            var caloriesConsumedChart = new Chart(ctx, {
                type: 'line',
                data: {
                    labels: [<c:forEach items="${MealController.getUsersConsumedCalories(user, 14).keySet()}" var="date">
                        new Date(<c:out value="${date.getYear()}"/>, <c:out value="${date.getMonthValue() - 1}"/>, <c:out value="${date.getDayOfMonth()}"/>),
                        </c:forEach>],
                    datasets: [{
                        data: <c:out value="${Arrays.toString(MealController.getUsersConsumedCalories(user, 14).values().toArray())}"/>,
                        lineTension: 0,
                        backgroundColor: 'transparent',
                        borderColor: '#007bff',
                        borderWidth: 4,
                        pointBackgroundColor: '#007bff'
                    }]
                },
                options: {
                    scales: {
                        xAxes: [{
                            type: 'time',
                            time: {
                                unit: 'day',
                                unitStepSize: 1,
                                displayFormats: { 'day': 'DD MMM' }
                            },
                            display: true,
                            scaleLabel: { display: false }
                        }],
                        yAxes: [{
                            ticks: { beginAtZero: false },
                            scaleLabel: {
                                display: true,
                                labelString: 'Calories (kcal)'
                            }
                        }]
                    },
                    legend: { display: false },
                    spanGaps: true
                }
            });
        </script>

        <script>
            var ctx = document.getElementById("caloriesBurnedChart");
            var caloriesBurnedChart = new Chart(ctx, {
                type: 'line',
                data: {
                    labels: [<c:forEach items="${ActivityController.getUsersBurnedCalories(user, 14).keySet()}" var="date">
                        new Date(<c:out value="${date.getYear()}"/>, <c:out value="${date.getMonthValue() - 1}"/>, <c:out value="${date.getDayOfMonth()}"/>),
                        </c:forEach>],
                    datasets: [{
                        data: <c:out value="${Arrays.toString(ActivityController.getUsersBurnedCalories(user, 14).values().toArray())}"/>,
                        lineTension: 0,
                        backgroundColor: 'transparent',
                        borderColor: '#007bff',
                        borderWidth: 4,
                        pointBackgroundColor: '#007bff'
                    }]
                },
                options: {
                    scales: {
                        xAxes: [{
                            type: 'time',
                            time: {
                                unit: 'day',
                                unitStepSize: 1,
                                displayFormats: { 'day': 'DD MMM' }
                            },
                            display: true,
                            scaleLabel: { display: false }
                        }],
                        yAxes: [{
                            ticks: { beginAtZero: false },
                            scaleLabel: {
                                display: true,
                                labelString: 'Calories (kcal)'
                            }
                        }]
                    },
                    legend: { display: false },
                    spanGaps: true
                }
            });
        </script>
    </jsp:body>
</t:dashboard>

</html>
