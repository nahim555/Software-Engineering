<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>

<%@ page import="Model.*" %>
<%@ page import="Controller.*" %>

<t:dashboard>
    <jsp:attribute name="title">Goals</jsp:attribute>
    <jsp:body>
        <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3 border-bottom">
            <h1 class="h2">Goals</h1>
        </div>

        <div class="mt-4">
            <div class="d-flex justify-content-between flex-nowrap align-items-center">
                <h2 class="h5">Weight loss</h2>
                <div class="btn-toolbar mb-2">
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addWeightLossGoalModal">
                        Add weight loss goal
                    </button>
                </div>
            </div>
            <div class="card">
                <div class="card-body py-0">
                    <c:if test="${empty GoalController.getWeightLossGoals(user)}">
                        <div class="text-muted my-3">
                            <span>You haven't added any weight loss goals yet!</span>
                        </div>
                    </c:if>
                    <c:forEach items="${GoalController.getWeightLossGoals(user)}" var="goal">
                        <div class="card bg-light my-4">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col">
                                        <h6 class="card-title"><c:out value="${goal.targetWeight}"/>kg</h6>
                                    </div>
                                    <div class="col text-right">
                                        <h6 class="card-title"><c:out value="${GoalController.getDaysRemaining(goal)}"/> days remaining</h6>
                                    </div>
                                </div>
                                <div class="row mb-1">
                                    <div class="col">
                                        <p class="card-text text-muted text-left">Starting weight: <c:out value="${goal.startingWeight}"/>kg</p>
                                    </div>
                                    <div class="col">
                                        <p class="card-text text-muted text-center">Current weight: <c:out value="${user.mostRecentWeight}"/>kg</p>
                                    </div>
                                    <div class="col">
                                        <p class="card-text text-muted text-right">Goal weight: <c:out value="${goal.targetWeight}"/>kg</p>
                                    </div>
                                </div>
                                <div class="progress mb-3">
                                    <div class="progress-bar" role="progressbar" style="width: <c:out value="${GoalController.calculatePercentageComplete(user, goal)}"/>%"></div>
                                </div>
                                <c:choose>
                                    <c:when test="${GoalController.isGoalTooMuch(user, goal)}">
                                        <div class="alert alert-warning">
                                            Be careful! This goal is very ambitious.
                                        </div>
                                    </c:when>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${GoalController.calculatePercentageComplete(user, goal) == 100}">
                                        <div class="alert alert-success">
                                            Congratulations, you have achieved this goal! Why not push yourself further and set another?
                                        </div>
                                    </c:when>
                                    <c:when test="${GoalController.getDaysRemaining(goal) == 0}">
                                        <div class="alert alert-warning">
                                            Unfortunately you did not complete this goal within the given time. But don't be put off, go ahead and give yourself a new goal!
                                        </div>
                                    </c:when>
                                </c:choose>
                                <div class="row">
                                    <div class="col text-right mt-auto">
                                        <button type="button" class="btn btn-danger btn-sm" data-toggle="modal"
                                                data-target="#removeWeightLossGoalModal"
                                                onclick="removeWeightLossGoal(<c:out value="${goal.duration}"/>, <c:out value="${goal.startingWeight}"/>, <c:out value="${goal.targetWeight}"/>)">Remove goal
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>

        <div class="mt-4">
            <div class="d-flex justify-content-between flex-nowrap align-items-center">
                <h2 class="h5">Distance</h2>
                <div class="btn-toolbar mb-2">
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addDistanceGoalModal">
                        Add distance goal
                    </button>
                </div>
            </div>
            <div class="card">
                <div class="card-body py-0">
                    <c:if test="${empty GoalController.getDistanceGoals(user)}">
                        <div class="text-muted my-3">
                            <span>You haven't added any distance goals yet!</span>
                        </div>
                    </c:if>
                    <c:forEach items="${GoalController.getDistanceGoals(user)}" var="goal">
                        <div class="card bg-light my-4">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col">
                                        <h6 class="card-title"><c:out value="${goal.exercise.name}"/> for <c:out value="${Controller.unitConvert(goal.targetDistance, 'm', 'km')}"/>km</h6>
                                    </div>
                                    <div class="col text-right">
                                        <h6 class="card-title"><c:out value="${GoalController.getDaysRemaining(goal)}"/> days remaining</h6>
                                    </div>
                                </div>
                                <div class="row mb-1">
                                    <div class="col">
                                        <p class="card-text text-muted text-left">Starting distance: <c:out value="${Controller.unitConvert(goal.previousBestDistance, 'm', 'km')}"/>km</p>
                                    </div>
                                    <div class="col">
                                        <p class="card-text text-muted text-center">Current best distance: <c:out value="${Controller.unitConvert(ActivityController.getDistancePersonalBest(user, goal.exercise), 'm', 'km')}"/>km</p>
                                    </div>
                                    <div class="col">
                                        <p class="card-text text-muted text-right">Goal distance: <c:out value="${Controller.unitConvert(goal.targetDistance, 'm', 'km')}"/>km</p>
                                    </div>
                                </div>
                                <div class="progress mb-3">
                                    <div class="progress-bar" role="progressbar" style="width: <c:out value="${GoalController.calculatePercentageComplete(user, goal)}"/>%"></div>
                                </div>
                                <c:choose>
                                    <c:when test="${GoalController.calculatePercentageComplete(user, goal) == 100}">
                                        <div class="alert alert-success">
                                            Congratulations, you have achieved this goal! Why not push yourself further and set another?
                                        </div>
                                    </c:when>
                                    <c:when test="${GoalController.getDaysRemaining(goal) == 0}">
                                        <div class="alert alert-warning">
                                            Unfortunately you did not complete this goal within the given time. But don't be put off, go ahead and give yourself a new goal!
                                        </div>
                                    </c:when>
                                </c:choose>
                                <div class="row">
                                    <div class="col text-right mt-auto">
                                        <button type="button" class="btn btn-danger btn-sm" data-toggle="modal"
                                                data-target="#removeDistanceGoalModal"
                                                onclick="removeDistanceGoal(<c:out value="${goal.duration}"/>, <c:out value="${goal.previousBestDistance}"/>, <c:out value="${goal.targetDistance}"/>)">Remove goal
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>

        <div class="mt-4">
            <div class="d-flex justify-content-between flex-nowrap align-items-center">
                <h2 class="h5">Time</h2>
                <div class="btn-toolbar mb-2">
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addTimeGoalModal">
                        Add time goal
                    </button>
                </div>
            </div>
            <div class="card mb-4">
                <div class="card-body py-0">
                    <c:if test="${empty GoalController.getTimeGoals(user)}">
                        <div class="text-muted my-3">
                            <span>You haven't added any time goals yet!</span>
                        </div>
                    </c:if>
                    <c:forEach items="${GoalController.getTimeGoals(user)}" var="goal">
                        <div class="card bg-light my-4">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col">
                                        <h6 class="card-title"><c:out value="${goal.exercise.name}"/> <c:out value="${Controller.unitConvert(goal.distance, 'm', 'km')}"/>km in <c:out value="${Controller.durationFormat(goal.targetTime)}"/></h6>
                                    </div>
                                    <div class="col text-right">
                                        <h6 class="card-title"><c:out value="${GoalController.getDaysRemaining(goal)}"/> days remaining</h6>
                                    </div>
                                </div>
                                <div class="row mb-1">
                                    <div class="col">
                                        <p class="card-text text-muted text-left">Starting time: <c:out value="${Controller.durationFormat(goal.previousBestTime)}"/></p>
                                    </div>
                                    <div class="col">
                                        <p class="card-text text-muted text-center">Current best time: <c:out value="${Controller.durationFormat(ActivityController.calculateFastestForDistance(user, goal.getExercise(), goal.getDistance()))}"/></p>
                                    </div>
                                    <div class="col">
                                        <p class="card-text text-muted text-right">Goal time: <c:out value="${Controller.durationFormat(goal.targetTime)}"/></p>
                                    </div>
                                </div>
                                <div class="progress mb-3">
                                    <div class="progress-bar" role="progressbar" style="width: <c:out value="${GoalController.calculatePercentageComplete(user, goal)}"/>%"></div>
                                </div>
                                <c:choose>
                                    <c:when test="${GoalController.calculatePercentageComplete(user, goal) == 100}">
                                        <div class="alert alert-success">
                                            Congratulations, you have achieved this goal! Why not push yourself further and set another?
                                        </div>
                                    </c:when>
                                    <c:when test="${GoalController.getDaysRemaining(goal) == 0}">
                                        <div class="alert alert-warning">
                                            Unfortunately you did not complete this goal within the given time. But don't be put off, go ahead and give yourself a new goal!
                                        </div>
                                    </c:when>
                                </c:choose>
                                <div class="row">
                                    <div class="col text-right mt-auto">
                                        <button type="button" class="btn btn-danger btn-sm" data-toggle="modal"
                                                data-target="#removeTimeGoalModal"
                                                onclick="removeTimeGoal(<c:out value="${goal.duration}"/>, <c:out value="${goal.previousBestTime}"/>, <c:out value="${goal.targetTime}"/>)">Remove goal
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>

        <div class="modal fade" id="addWeightLossGoalModal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addWeightLossGoalModalLabel">Add weight loss goal</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <form action="/dashboard/goals/add-weight-loss-goal" method="post">
                        <div class="modal-body">
                            <div class="form-group">
                                <label>Goal weight</label>
                                <div class="form-row">
                                    <div class="input-group col mb-0">
                                        <input type="number" min="0.1" max="<c:out value="${user.mostRecentWeight}"/>" step="0.1" value="0" required class="form-control" name="goalWeight">
                                        <div class="input-group-append">
                                            <span class="input-group-text">kg</span>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label>Expiry date</label>
                                <div class="form-row">
                                    <div class="input-group col mb-0">
                                        <input type="date" required class="form-control" name="expiry">
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="modal-footer">
                            <input class="btn btn-primary btn-block" type="submit">
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div class="modal fade" id="removeWeightLossGoalModal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="removeWeightLossGoalModalLabel">Remove weight loss goal</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <p>Are you sure you want to remove this goal?</p>
                    </div>
                    <form action="/dashboard/goals/remove-weight-loss-goal" method="post">
                        <div class="modal-body p-0">
                            <div class="form-group d-none">
                                <input type="number" step="any" class="form-control" id="removeWeightLossGoalDuration" name="duration">
                            </div>
                            <div class="form-group d-none">
                                <input type="number" step="any" class="form-control" id="removeWeightLossGoalStart" name="start">
                            </div>
                            <div class="form-group d-none">
                                <input type="number" step="any" class="form-control" id="removeWeightLossGoalTarget" name="target">
                            </div>
                        </div>
                        <div class="modal-footer">
                            <input class="btn btn-danger btn-block" type="submit">
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div class="modal fade" id="addDistanceGoalModal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addDistanceGoalModalLabel">Add distance goal</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <form action="/dashboard/goals/add-distance-goal" method="post">
                        <div class="modal-body">

                            <div class="form-group">
                                <label>Exercise</label>
                                <select class="form-control" name="exercise">
                                    <c:forEach items="${ExerciseController.getExercises()}" var="exercise">
                                        <c:if test="${exercise.isMeasurable()}">
                                            <option value="<c:out value="${exercise.id}"/>"><c:out value="${exercise.name}"/></option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="form-group">
                                <label>Goal distance</label>
                                <div class="form-row">
                                    <div class="input-group col mb-0">
                                        <input type="number" min="0.001" step="0.001" value="0" required class="form-control" name="goalDistance">
                                        <div class="input-group-append">
                                            <span class="input-group-text">km</span>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label>Expiry date</label>
                                <div class="form-row">
                                    <div class="input-group col mb-0">
                                        <input type="date" required class="form-control" name="expiry">
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="modal-footer">
                            <input class="btn btn-primary btn-block" type="submit">
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div class="modal fade" id="removeDistanceGoalModal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="removeDistanceGoalModalLabel">Remove distance goal</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <p>Are you sure you want to remove this goal?</p>
                    </div>
                    <form action="/dashboard/goals/remove-distance-goal" method="post">
                        <div class="modal-body p-0">
                            <div class="form-group d-none">
                                <input type="number" step="any" class="form-control" id="removeDistanceGoalDuration" name="duration">
                            </div>
                            <div class="form-group d-none">
                                <input type="number" step="any" class="form-control" id="removeDistanceGoalStart" name="start">
                            </div>
                            <div class="form-group d-none">
                                <input type="number" step="any" class="form-control" id="removeDistanceGoalTarget" name="target">
                            </div>
                        </div>
                        <div class="modal-footer">
                            <input class="btn btn-danger btn-block" type="submit">
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div class="modal fade" id="addTimeGoalModal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addTimeGoalModalLabel">Add time goal</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <form action="/dashboard/goals/add-time-goal" method="post">
                        <div class="modal-body">

                            <div class="form-group">
                                <label>Exercise</label>
                                <select class="form-control" name="exercise">
                                    <c:forEach items="${ExerciseController.getExercises()}" var="exercise">
                                        <c:if test="${exercise.isMeasurable()}">
                                            <option value="<c:out value="${exercise.id}"/>"><c:out value="${exercise.name}"/></option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="form-group">
                                <label>Distance</label>
                                <div class="form-row">
                                    <div class="input-group col mb-0">
                                        <input type="number" min="0.001" step="0.001" value="0" required class="form-control" name="distance">
                                        <div class="input-group-append">
                                            <span class="input-group-text">km</span>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label>Goal time</label>
                                <div class="form-row">
                                    <div class="input-group col mb-0">
                                        <input type="number" min="0" max="23" step="1" class="form-control" name="hours" value="00">
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

                            <div class="form-group">
                                <label>Expiry date</label>
                                <div class="form-row">
                                    <div class="input-group col mb-0">
                                        <input type="date" required class="form-control" name="expiry">
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="modal-footer">
                            <input class="btn btn-primary btn-block" type="submit">
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div class="modal fade" id="removeTimeGoalModal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="removeTimeGoalModalLabel">Remove time goal</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <p>Are you sure you want to remove this goal?</p>
                    </div>
                    <form action="/dashboard/goals/remove-time-goal" method="post">
                        <div class="modal-body p-0">
                            <div class="form-group d-none">
                                <input type="number" step="any" class="form-control" id="removeTimeGoalDuration" name="duration">
                            </div>
                            <div class="form-group d-none">
                                <input type="number" step="any" class="form-control" id="removeTimeGoalStart" name="start">
                            </div>
                            <div class="form-group d-none">
                                <input type="number" step="any" class="form-control" id="removeTimeGoalTarget" name="target">
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
            function removeWeightLossGoal(duration, start, target) {
                $("#removeWeightLossGoalDuration").val(duration);
                $("#removeWeightLossGoalStart").val(start);
                $("#removeWeightLossGoalTarget").val(target);
            }

            function removeDistanceGoal(duration, start, target) {
                $("#removeDistanceGoalDuration").val(duration);
                $("#removeDistanceGoalStart").val(start);
                $("#removeDistanceGoalTarget").val(target);
            }

            function removeTimeGoal(duration, start, target) {
                $("#removeTimeGoalDuration").val(duration);
                $("#removeTimeGoalStart").val(start);
                $("#removeTimeGoalTarget").val(target);
            }
        </script>

    </jsp:body>
</t:dashboard>

</html>
