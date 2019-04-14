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
    <jsp:attribute name="title">Food & Meals</jsp:attribute>
    <jsp:body>
        <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3 border-bottom">
            <h1 class="h2">Food &amp; Meals</h1>
        </div>

        <div class="my-4">
            <div class="d-flex justify-content-between flex-nowrap align-items-center">
                <h2 class="h5">Your meals</h2>
                <div class="btn-toolbar mb-2">
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addMealModal">
                        Add meal
                    </button>
                </div>
            </div>
            <div class="card">
                <div class="card-body py-0">
                    <c:if test="${empty user.meals}">
                        <div class="text-muted my-3">
                            <span>You haven't added any meals yet!</span>
                        </div>
                    </c:if>
                    <c:forEach items="${user.meals}" var="meal">
                        <div class="card bg-light my-4">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col">
                                        <h6 class="card-title"><c:out value="${meal.name}"/></h6>
                                    </div>
                                    <div class="col text-right">
                                        <h6 class="card-title"><fmt:formatNumber value="${MealController.calculateCalories(meal)}" maxFractionDigits="0" /> calories per serving</h6>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col">
                                        <c:forEach items="${meal.foods}" var="food">
                                            <p class="card-text text-muted"><fmt:formatNumber value="${food.value}" maxFractionDigits="0" />g of <c:out value="${food.key.name}"/></p>
                                        </c:forEach>
                                    </div>
                                    <div class="col text-right mt-auto">
                                        <a class="btn btn-info btn-sm" href="/dashboard/food-meals/edit-meal.jsp?meal=<c:out value="${meal.id}"/>" role="button">Edit meal</a>
                                        <button type="button" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#removeMealModal" onclick="removeMeal(<c:out value="${meal.id}"/>)">Remove meal</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>

        <div class="modal fade" id="addMealModal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addMealModalLabel">Add meal</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <form action="/dashboard/food-meals/add-meal" method="post">
                        <div class="modal-body">

                            <div class="form-group">
                                <label for="inputFoodName">Meal name</label>
                                <div class="form-row">
                                    <div class="input-group col mb-0">
                                        <input type="text" required class="form-control" id="inputMealName" name="name">
                                    </div>
                                </div>
                            </div>

                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" id="checkMealPublic" name="public" checked>
                                <label class="form-check-label" for="checkMealPublic">
                                    Allow others to use this meal
                                </label>
                                <small class="form-text text-muted">Shared meals cannot be fully deleted, but they can be dissociated with your account.</small>
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
                    <form action="/dashboard/food-meals/remove-meal" method="post">
                        <div class="modal-body p-0">
                            <div class="form-group d-none">
                                <input type="number" class="form-control" id="removeMealId" name="meal">
                            </div>
                        </div>
                        <div class="modal-footer">
                            <input class="btn btn-danger btn-block" type="submit">
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div class="my-4">
            <div class="d-flex justify-content-between flex-nowrap align-items-center">
                <h2 class="h5">Your foods</h2>
                <div class="btn-toolbar mb-2">
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addFoodModal">
                        Add food
                    </button>
                </div>
            </div>
            <div class="card">
                <div class="card-body py-0">
                    <c:if test="${empty user.foods}">
                        <div class="text-muted my-3">
                            <span>You haven't added any foods yet!</span>
                        </div>
                    </c:if>
                    <c:forEach items="${user.foods}" var="food">
                        <div class="card bg-light my-4">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col">
                                        <h6 class="card-title"><c:out value="${food.name}"/></h6>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm">
                                        <table class="table table-bordered table-sm mb-0 text-sm-left text-muted">
                                            <thead>
                                                <tr>
                                                    <th colspan="4">Per 100g</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                            <tr>
                                                <th>Calories</th>
                                                <td><c:out value="${food.calories}"/>kcal</td>
                                                <th>Protein</th>
                                                <td><c:out value="${food.protein}"/>g</td>
                                            </tr>
                                            <tr>
                                                <th>Fat</th>
                                                <td><c:out value="${food.fat}"/>g</td>
                                                <th>Carbohydrates</th>
                                                <td><c:out value="${food.carbohydrates}"/>g</td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="col-sm text-right mt-auto">
                                        <button type="button" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#removeFoodModal" onclick="removeFood(<c:out value="${food.id}"/>)">Remove food</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>

        <div class="modal fade" id="addFoodModal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addFoodModalLabel">Add food</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <form action="/dashboard/food-meals/add-food" method="post">
                        <div class="modal-body">

                            <div class="form-group">
                                <label for="inputFoodName">Food name</label>
                                <div class="form-row">
                                    <div class="input-group col mb-0">
                                        <input type="text" required class="form-control" id="inputFoodName" name="name">
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="inputCalories">Calories</label>
                                <div class="form-row">
                                    <div class="input-group col mb-0">
                                        <input type="number" min="0" step="0.1" value="0" required class="form-control" id="inputCalories" name="calories">
                                        <div class="input-group-append">
                                            <span class="input-group-text">kcal in 100g of the food</span>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="inputProtein">Protein</label>
                                <div class="form-row">
                                    <div class="input-group col mb-0">
                                        <input type="number" min="0" step="0.1" value="0" required class="form-control" id="inputProtein" name="protein">
                                        <div class="input-group-append">
                                            <span class="input-group-text">g in 100g of the food</span>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="inputFat">Fat</label>
                                <div class="form-row">
                                    <div class="input-group col mb-0">
                                        <input type="number" min="0" step="0.1" value="0" required class="form-control" id="inputFat" name="fat">
                                        <div class="input-group-append">
                                            <span class="input-group-text">g in 100g of the food</span>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="inputCarbohydrates">Carbohydrates</label>
                                <div class="form-row">
                                    <div class="input-group col mb-0">
                                        <input type="number" min="0" step="0.1" value="0" required class="form-control" id="inputCarbohydrates" name="carbohydrates">
                                        <div class="input-group-append">
                                            <span class="input-group-text">g in 100g of the food</span>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group mb-0">
                                <div class="form-row">
                                    <div class="col">
                                        <label for="inputServingSize">Standard serving size</label>
                                    </div>
                                    <div class="col">
                                        <label for="inputServingName">Serving name</label>
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="form-group col mb-0">
                                        <div class="input-group">
                                            <input type="number" min="0" step="1" value="0" required class="form-control" id="inputServingSize" name="servingSize">
                                            <div class="input-group-append">
                                                <span class="input-group-text">g</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group col mb-0">
                                        <input type="text" required class="form-control" id="inputServingName" name="servingName">
                                        <small class="form-text text-muted">i.e. slice, jar, packet etc.</small>
                                    </div>
                                </div>
                            </div>

                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" id="checkFoodPublic" name="public" checked>
                                <label class="form-check-label" for="checkFoodPublic">
                                    Allow others to use this food
                                </label>
                                <small class="form-text text-muted">Shared foods cannot be fully deleted, but they can be dissociated with your account.</small>
                            </div>
                        </div>

                        <div class="modal-footer">
                            <input class="btn btn-primary btn-block" type="submit">
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div class="modal fade" id="removeFoodModal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="removeFoodModalLabel">Remove food</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <p>Are you sure you want to remove this food?</p>
                    </div>
                    <form action="/dashboard/food-meals/remove-food" method="post">
                        <div class="modal-body p-0">
                            <div class="form-group d-none">
                                <input type="number" class="form-control" id="removeFoodId" name="food">
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
            function removeMeal(meal) {
                $("#removeMealId").val(meal);
            }

            function removeFood(food) {
                $("#removeFoodId").val(food);
            }
        </script>
    </jsp:body>
</t:dashboard>

</html>
