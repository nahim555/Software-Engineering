<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>

<%@ page import="Model.*" %>
<%@ page import="Controller.*" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.*" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.sql.Date" %>

<c:set var = "history" value = "${param.history}"/>
<c:set var = "date" value = "${LocalDate.now().minusDays(history)}"/>
<c:set var = "isToday" value = "${date.equals(LocalDate.now())}"/>

<t:dashboard>
    <jsp:attribute name="title">Diary</jsp:attribute>
    <jsp:body>
        <div class="pb-2 mb-3 border-bottom">
            <div class="row">
                <div class="col-auto">
                    <h1 class="h2">Diary</h1>
                </div>
                <div class="col text-right pt-2">
                    <p class="h6 d-inline mr-3"><c:out value="${date.format(DateTimeFormatter.ofPattern('E, d MMM yyyy'))}"/>${isToday ? ' (today)' : ''}</p>
                    <a class="btn btn-primary d-inline" href="/dashboard/diary.jsp?history=<c:out value="${history + 1}"/>" role="button"><span data-feather="arrow-left"></span></a>
                    <a class="btn btn-primary d-inline ${isToday ? 'disabled' : ''}" href="/dashboard/diary.jsp?history=<c:out value="${history - 1}"/>" role="button"><span data-feather="arrow-right"></span></a>
                </div>
            </div>
        </div>


        <div class="row">
            <div class="col-sm pr-1">
                <div class="progress" style="height: 38px;">
                    <div class="progress-bar bg-success" role="progressbar" style="width:
                        <c:out value="${Controller.calculatePercentage((UserController.getCalorieGoal(user) +
                    ActivityController.getCaloriesBurnedForDate(user, date) -
                    MealController.getUsersConsumedCalories(user, date)),
                    UserController.getCalorieGoal(user))}"/>%;">
                        <c:out value="${UserController.getCalorieGoal(user) +
                        ActivityController.getCaloriesBurnedForDate(user, date) -
                        MealController.getUsersConsumedCalories(user, date)}"/> calories remaining
                    </div>
                </div>
            </div>
            <div class="col-auto pl-1">
                <button type="button" class="btn btn-info" data-toggle="collapse" data-target="#macronutrients">View macronutrients</button>
            </div>
            <div class="col-auto pr-1">
                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#mealModal">Add meal</button>
            </div>
            <div class="col-auto pl-1">
                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#activityModal">Add activity</button>
            </div>
        </div>

        <div class="collapse" id="macronutrients">
            <div class="row mt-3">
                <div class="col-2 text-right">
                    <p><strong>Protein:</strong></p>
                </div>
                <div class="col">
                    <div class="progress" style="height: 26px;">
                        <div class="progress-bar bg-info" role="progressbar" style="width:
                            <c:out value="${Controller.calculatePercentage(MealController.getUsersConsumedProtein(user, date), 50)}"/>%;">
                            <c:out value="${Controller.calculatePercentage(MealController.getUsersConsumedProtein(user, date), 50)}"/>% of daily recommended intake
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-2 text-right">
                    <p><strong>Fat:</strong></p>
                </div>
                <div class="col">
                    <div class="progress" style="height: 26px;">
                        <div class="progress-bar bg-info" role="progressbar" style="width:
                            <c:out value="${Controller.calculatePercentage(MealController.getUsersConsumedFat(user, date), 70)}"/>%;">
                            <c:out value="${Controller.calculatePercentage(MealController.getUsersConsumedFat(user, date), 70)}"/>% of daily recommended intake
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-2 text-right">
                    <p><strong>Carbohydrates:</strong></p>
                </div>
                <div class="col">
                    <div class="progress" style="height: 26px;">
                        <div class="progress-bar bg-info" role="progressbar" style="width:
                            <c:out value="${Controller.calculatePercentage(MealController.getUsersConsumedCarbohydrates(user, date), 260)}"/>%;">
                            <c:out value="${Controller.calculatePercentage(MealController.getUsersConsumedCarbohydrates(user, date), 260)}"/>% of daily recommended intake
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="mt-4">
            <h2 class="h5">Breakfast</h2>
            <div class="card">
                <div class="card-body py-0">
                    <c:if test="${empty MealController.getUsersEatenMeals(user, date, 1)}">
                        <div class="text-muted my-3">
                            <span>Nothing added yet!</span>
                        </div>
                    </c:if>
                    <c:forEach items="${MealController.getUsersEatenMeals(user, date, 1)}" var="meal">
                        <div class="card bg-light my-4">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col">
                                        <h6 class="card-title"><c:out value="${meal.key.name}"/></h6>
                                    </div>
                                    <div class="col text-right">
                                        <h6 class="card-title">consumed <fmt:formatNumber value="${MealController.calculateCalories(meal.key) * meal.value}" maxFractionDigits="0" /> calories</h6>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col">
                                        <c:forEach items="${meal.key.foods}" var="food">
                                            <p class="card-text text-muted"><fmt:formatNumber value="${food.value * meal.value}" maxFractionDigits="0" />g of <c:out value="${food.key.name}"/></p>
                                        </c:forEach>
                                    </div>
                                    <div class="col text-right mt-auto">
                                        <button type="button" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#removeMealModal" onclick="removeMeal(<c:out value="${meal.key.id}"/>, 1, '<c:out value="${Date.valueOf(date)}"/>')">Remove meal</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>

        <div class="mt-4">
            <h2 class="h5">Lunch</h2>
            <div class="card">
                <div class="card-body py-0">
                    <c:if test="${empty MealController.getUsersEatenMeals(user, date, 2)}">
                        <div class="text-muted my-3">
                            <span>Nothing added yet!</span>
                        </div>
                    </c:if>
                    <c:forEach items="${MealController.getUsersEatenMeals(user, date, 2)}" var="meal">
                        <div class="card bg-light my-4">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col">
                                        <h6 class="card-title"><c:out value="${meal.key.name}"/></h6>
                                    </div>
                                    <div class="col text-right">
                                        <h6 class="card-title">consumed <fmt:formatNumber value="${MealController.calculateCalories(meal.key) * meal.value}" maxFractionDigits="0" /> calories</h6>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col">
                                        <c:forEach items="${meal.key.foods}" var="food">
                                            <p class="card-text text-muted"><fmt:formatNumber value="${food.value * meal.value}" maxFractionDigits="0" />g of <c:out value="${food.key.name}"/></p>
                                        </c:forEach>
                                    </div>
                                    <div class="col text-right mt-auto">
                                        <button type="button" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#removeMealModal" onclick="removeMeal(<c:out value="${meal.key.id}"/>, 2, '<c:out value="${Date.valueOf(date)}"/>')">Remove meal</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>

        <div class="mt-4">
            <h2 class="h5">Dinner</h2>
            <div class="card">
                <div class="card-body py-0">
                    <c:if test="${empty MealController.getUsersEatenMeals(user, date, 3)}">
                        <div class="text-muted my-3">
                            <span>Nothing added yet!</span>
                        </div>
                    </c:if>
                    <c:forEach items="${MealController.getUsersEatenMeals(user, date, 3)}" var="meal">
                        <div class="card bg-light my-4">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col">
                                        <h6 class="card-title"><c:out value="${meal.key.name}"/></h6>
                                    </div>
                                    <div class="col text-right">
                                        <h6 class="card-title">consumed <fmt:formatNumber value="${MealController.calculateCalories(meal.key) * meal.value}" maxFractionDigits="0" /> calories</h6>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col">
                                        <c:forEach items="${meal.key.foods}" var="food">
                                            <p class="card-text text-muted"><fmt:formatNumber value="${food.value * meal.value}" maxFractionDigits="0" />g of <c:out value="${food.key.name}"/></p>
                                        </c:forEach>
                                    </div>
                                    <div class="col text-right mt-auto">
                                        <button type="button" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#removeMealModal" onclick="removeMeal(<c:out value="${meal.key.id}"/>, 3, '<c:out value="${Date.valueOf(date)}"/>')">Remove meal</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>

        <div class="mt-4">
            <h2 class="h5">Snacks</h2>
            <div class="card">
                <div class="card-body py-0">
                    <c:if test="${empty MealController.getUsersEatenMeals(user, date, 4)}">
                        <div class="text-muted my-3">
                            <span>Nothing added yet!</span>
                        </div>
                    </c:if>
                    <c:forEach items="${MealController.getUsersEatenMeals(user, date, 4)}" var="meal">
                        <div class="card bg-light my-4">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col">
                                        <h6 class="card-title"><c:out value="${meal.key.name}"/></h6>
                                    </div>
                                    <div class="col text-right">
                                        <h6 class="card-title">consumed <fmt:formatNumber value="${MealController.calculateCalories(meal.key) * meal.value}" maxFractionDigits="0" /> calories</h6>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col">
                                        <c:forEach items="${meal.key.foods}" var="food">
                                            <p class="card-text text-muted"><fmt:formatNumber value="${food.value * meal.value}" maxFractionDigits="0" />g of <c:out value="${food.key.name}"/></p>
                                        </c:forEach>
                                    </div>
                                    <div class="col text-right mt-auto">
                                        <button type="button" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#removeMealModal" onclick="removeMeal(<c:out value="${meal.key.id}"/>, 4, '<c:out value="${Date.valueOf(date)}"/>')">Remove meal</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>

        <div class="my-4">
            <h2 class="h5">Exercise</h2>
            <div class="card">
                <div class="card-body py-0">
                    <c:if test="${empty ActivityController.getActivitiesForDate(user, date)}">
                        <div class="text-muted my-3">
                            <span>Nothing added yet!</span>
                        </div>
                    </c:if>
                    <c:forEach items="${ActivityController.getActivitiesForDate(user, date)}" var="activity">
                        <div class="card bg-light my-4">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col">
                                        <h6 class="card-title"><c:out value="${activity.exercise.name}"/></h6>
                                    </div>
                                    <div class="col text-right">
                                        <h6 class="card-title">burned <c:out value="${ActivityController.getCaloriesBurned(user, activity)}"/> calories</h6>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col">
                                        <c:choose>
                                            <c:when test="${activity.getExercise().isMeasurable()}">
                                                <p class="card-text text-muted"><c:out value="${Controller.unitConvert(activity.intensity, 'm', 'km')}"/>km in <c:out value="${Controller.durationFormat(activity.duration)}"/></p>
                                            </c:when>
                                            <c:otherwise>
                                                <c:choose>
                                                    <c:when test="${activity.getIntensity() == 3}">
                                                        <p class="card-text text-muted">High intensity for <c:out value="${Controller.durationFormat(activity.duration)}"/></p>
                                                    </c:when>
                                                    <c:when test="${activity.getIntensity() == 2}">
                                                        <p class="card-text text-muted">Moderate intensity for <c:out value="${Controller.durationFormat(activity.duration)}"/></p>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <p class="card-text text-muted">Low intensity for <c:out value="${Controller.durationFormat(activity.duration)}"/></p>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div class="col text-right mt-auto">
                                        <button type="button" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#removeActivityModal" onclick="removeActivity(<c:out value="${activity.exercise.id}"/>, '<c:out value="${Date.valueOf(date)}"/>', <c:out value="${activity.duration}"/>, <c:out value="${activity.intensity}"/>)">Remove activity</button>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>

        <div class="modal fade" id="mealModal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="mealModalLabel">Add meal</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <form action="/dashboard/diary/consume-meal" method="post">
                        <div class="modal-body">

                            <div class="form-group">
                                <label for="selectMeal">Meal</label>
                                <select class="form-control" id="selectMeal" name="meal" onchange="changeMeal();" required>
                                    <option selected disabled hidden value="">Select meal</option>
                                    <optgroup label="Your meals">
                                        <c:forEach items="${user.meals}" var="meal">
                                            <option value="<c:out value="${meal.id}"/>"><c:out value="${meal.name}"/></option>
                                        </c:forEach>
                                    </optgroup>
                                    <optgroup label="Other meals">
                                        <c:forEach items="${Controller.subtract(MealController.getPublicMeals(), user.getMeals()) }" var="meal">
                                            <option value="<c:out value="${meal.id}"/>"><c:out value="${meal.name}"/></option>
                                        </c:forEach>
                                    </optgroup>
                                </select>
                            </div>

                            <c:forEach items="${Controller.add(user.getMeals(), MealController.getPublicMeals())}" var="meal">
                                <div class="card bg-light my-4 meal" id="meal-<c:out value="${meal.id}"/>">
                                    <div class="card-body p-3">
                                        <div class="row">
                                            <div class="col">
                                                <h6 class="card-title"><c:out value="${meal.name}"/></h6>
                                            </div>
                                            <div class="col-auto text-right">
                                                <h6 class="card-title"><fmt:formatNumber value="${MealController.calculateCalories(meal)}" maxFractionDigits="0" /> cal</h6>
                                            </div>
                                        </div>

                                        <c:forEach items="${meal.foods}" var="food">
                                            <p class="card-text text-muted"><fmt:formatNumber value="${food.value}" maxFractionDigits="0" />g of <c:out value="${food.key.name}"/></p>
                                        </c:forEach>

                                    </div>
                                </div>
                            </c:forEach>

                            <div class="form-group">
                                <label for="selectTime">Time</label>
                                <select class="form-control" id="selectTime" name="time">
                                    <option value="1">Breakfast</option>
                                    <option value="2">Lunch</option>
                                    <option value="3">Dinner</option>
                                    <option value="4">Snack</option>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="selectQuantity">Portion size</label>
                                <select class="form-control" id="selectQuantity" name="quantity">
                                    <option value="0.5">Half portion</option>
                                    <option value="0.75">Three-quarter portion</option>
                                    <option value="1" selected>Full portion</option>
                                    <option value="1.5">One-and-a-half portion</option>
                                    <option value="2">Double portion</option>
                                </select>
                            </div>

                            <div class="form-group d-none">
                                <input type="date" class="form-control" value="${Date.valueOf(date)}" name="date">
                            </div>
                        </div>
                        <div class="modal-footer">
                            <input class="btn btn-primary btn-block" type="submit">
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div class="modal fade" id="removeMealModal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="removeMealModalLabel">Remove meal</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <p>Are you sure you want to remove this meal?</p>
                    </div>
                    <form action="/dashboard/diary/remove-consumed-meal" method="post">
                        <div class="modal-body p-0">
                            <div class="form-group d-none">
                                <input type="number" class="form-control" id="removeMealId" name="meal">
                            </div>
                            <div class="form-group d-none">
                                <input type="number" class="form-control" id="removeMealTime" name="time">
                            </div>
                            <div class="form-group d-none">
                                <input type="date" class="form-control" id="removeMealDate" name="date">
                            </div>
                        </div>
                        <div class="modal-footer">
                            <input class="btn btn-danger btn-block" type="submit">
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div class="modal fade" id="activityModal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="activityModalLabel">Add activity</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <form action="/dashboard/diary/add-activity" method="post">
                        <div class="modal-body">
                            <div class="form-group">
                                <label for="selectExercise">Exercise</label>
                                <select class="form-control" id="selectExercise" name="exercise" onchange="changeInputs();">
                                    <c:forEach items="${ExerciseController.getExercises()}" var="exercise">
                                        <option ${exercise.isMeasurable() ? 'class="measurable"' : ''} value="<c:out value="${exercise.id}"/>"><c:out value="${exercise.name}"/></option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div id="intensityGroup" class="form-group">
                                <label for="selectIntensity">Intensity</label>
                                <select class="form-control" id="selectIntensity" name="intensity">
                                    <option value="1">Low</option>
                                    <option value="2">Moderate</option>
                                    <option value="3">High</option>
                                </select>
                            </div>
                            <div id="distanceGroup" class="form-group">
                                <label for="inputDistance">Distance</label>
                                <div class="form-row">
                                    <div class="input-group col mb-0">
                                        <input type="number" min="1" step="1" value="0" required class="form-control" id="inputDistance" name="distance">
                                        <div class="input-group-append">
                                            <span class="input-group-text">metres</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="inputDuration">Duration</label>
                                <div class="form-row">
                                    <div class="input-group col mb-0">
                                        <input type="number" min="0" max="23" step="1" class="form-control" id="inputDuration" name="hours" value="00">
                                        <div class="input-group-append">
                                            <span class="input-group-text">h</span>
                                        </div>
                                    </div>
                                    <span class="h4 mb-0" style="margin-top:3px;">&colon;</span>
                                    <div class="input-group col mb-0">
                                        <input type="number" min="0" max="59" step="1" required class="form-control" name="minutes" value="00">
                                        <div class="input-group-append">
                                            <span class="input-group-text">m</span>
                                        </div>
                                    </div>
                                    <span class="h4 mb-0" style="margin-top:3px;">&colon;</span>
                                    <div class="input-group col mb-0">
                                        <input type="number" min="0" max="59" step="1" required class="form-control" name="seconds" value="00">
                                        <div class="input-group-append">
                                            <span class="input-group-text">s</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group d-none">
                                <input type="date" class="form-control" value="${Date.valueOf(date)}" name="date">
                            </div>
                        </div>
                        <div class="modal-footer">
                            <input class="btn btn-primary btn-block" type="submit">
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div class="modal fade" id="removeActivityModal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="removeActivityModalLabel">Remove activity</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <p>Are you sure you want to remove this activity?</p>
                    </div>
                    <form action="/dashboard/diary/remove-activity" method="post">
                        <div class="modal-body p-0">
                            <div class="form-group d-none">
                                <input type="number" class="form-control" id="removeActivityExercise" name="exercise">
                            </div>
                            <div class="form-group d-none">
                                <input type="date" class="form-control" id="removeActivityDate" name="date">
                            </div>
                            <div class="form-group d-none">
                                <input type="number" class="form-control" id="removeActivityDuration" name="duration">
                            </div>
                            <div class="form-group d-none">
                                <input type="number" class="form-control" id="removeActivityIntensity" name="intensity">
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

        <script>
            function changeMeal() {
                $(".meal").hide();
                $("#meal-" + $("#selectMeal").find(":selected").attr("value")).show();
            }

            function changeInputs() {
                if ($("#selectExercise").find(":selected").hasClass("measurable")) {
                    $("#intensityGroup").hide();
                    $("#distanceGroup").show();
                } else {
                    $("#intensityGroup").show();
                    $("#distanceGroup").hide();
                }
            }

            function removeMeal(meal, time, date) {
                $("#removeMealId").val(meal);
                $("#removeMealTime").val(time);
                $("#removeMealDate").val(date);
            }

            function removeActivity(exercise, date, duration, intensity) {
                $("#removeActivityExercise").val(exercise);
                $("#removeActivityDate").val(date);
                $("#removeActivityDuration").val(duration);
                $("#removeActivityIntensity").val(intensity);
            }

            $( document ).ready(function() {
                changeMeal();
                changeInputs();
            });
        </script>
    </jsp:body>
</t:dashboard>

</html>
