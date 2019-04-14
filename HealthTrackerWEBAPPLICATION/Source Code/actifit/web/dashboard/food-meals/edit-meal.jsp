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

<c:set var = "meal" value = "${MealController.findMealById(param.meal)}"/>

<c:if test="${!user.getMeals().contains(meal)}">
    <c:redirect url = "/dashboard/food-meals.jsp"/>
</c:if>

<t:dashboard>
    <jsp:attribute name="title">Edit meal</jsp:attribute>
    <jsp:body>
        <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3 border-bottom">
            <h1 class="h2">Edit meal</h1>
        </div>

        <div class="d-flex justify-content-between flex-nowrap align-items-center">
            <h2 class="h5"><c:out value="${meal.name}"/></h2>
            <div class="btn-toolbar mb-2">
                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addFoodModal">
                    Add food
                </button>
            </div>
        </div>

        <div class="card">
            <div class="card-body py-0">
                <c:if test="${empty meal.foods}">
                    <div class="text-muted my-3">
                        <span>You haven't added any foods to this meal yet!</span>
                    </div>
                </c:if>
                <c:forEach items="${meal.foods}" var="food">
                    <div class="card bg-light my-4">
                        <div class="card-body">
                            <div class="row">
                                <div class="col">
                                    <h6 class="card-title"><c:out value="${food.key.name}"/></h6>
                                </div>
                                <div class="col text-right">
                                    <h6 class="card-title"><fmt:formatNumber value="${food.key.calories * food.value / 100}" maxFractionDigits="0" /> calories</h6>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm">
                                    <p class="card-text text-muted"><c:out value="${food.value}"/>g</p>
                                </div>
                                <div class="col-sm text-right mt-auto">
                                    <button type="button" class="btn btn-danger btn-sm" data-toggle="modal" data-target="#removeFoodModal" onclick="removeFood(<c:out value="${food.key.id}"/>)">Remove food</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

        <div class="modal fade" id="addFoodModal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="mealModalLabel">Add food</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <form action="/dashboard/food-meals/edit-meal/add-food" method="post">
                        <div class="modal-body">

                            <div class="form-group">
                                <label for="selectFood">Food</label>
                                <select class="form-control" id="selectFood" name="food" required onchange="changeFood();">
                                    <option selected disabled hidden value="">Select food</option>
                                    <optgroup label="Your foods">
                                        <c:forEach items="${user.foods}" var="food">
                                            <option value="<c:out value="${food.id}"/>"><c:out value="${food.name}"/></option>
                                        </c:forEach>
                                    </optgroup>
                                    <optgroup label="Other foods">
                                        <c:forEach items="${Controller.subtract(FoodController.getPublicFoods(), user.getFoods()) }" var="food">
                                            <option value="<c:out value="${food.id}"/>"><c:out value="${food.name}"/></option>
                                        </c:forEach>
                                    </optgroup>
                                </select>
                            </div>

                            <c:forEach items="${Controller.add(user.getFoods(), FoodController.getPublicFoods())}" var="food">
                                <div class="card bg-light my-4 food" id="food-<c:out value="${food.id}"/>">
                                    <div class="card-body p-3">
                                        <h6 class="card-title"><c:out value="${food.name}"/></h6>
                                        <table class="table table-bordered table-sm mb-0 text-sm-left text-muted">
                                            <thead>
                                            <tr>
                                                <th colspan="4">Per 100g</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr>
                                                <th>Calories</th>
                                                <td><fmt:formatNumber value="${food.calories}" maxFractionDigits="0"/>kcal</td>
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
                                        <p class="card-text text-muted mt-2">Standard serving size: <c:out value="${food.servingSize}"/>g (<c:out value="${food.servingName}"/>)</p>
                                    </div>
                                </div>
                            </c:forEach>

                            <div class="form-group">
                                <label for="inputQuantity">Quantity</label>
                                <div class="form-row">
                                    <div class="input-group col mb-0">
                                        <input type="number" min="0" step="1" value="100" required class="form-control" id="inputQuantity" name="quantity">
                                        <div class="input-group-append">
                                            <span class="input-group-text">g</span>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group d-none">
                                <input type="number" class="form-control" value="${meal.id}" name="meal">
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
                    <form action="/dashboard/food-meals/edit-meal/remove-food" method="post">
                        <div class="modal-body p-0">
                            <div class="form-group d-none">
                                <input type="number" class="form-control" value="${meal.id}" name="meal">
                            </div>
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
            function changeFood() {
                $(".food").hide();
                $("#food-" + $("#selectFood").find(":selected").attr("value")).show();
            }

            function removeFood(food) {
                $("#removeFoodId").val(food);
            }

            $( document ).ready(function() {
                changeFood();
            });
        </script>

    </jsp:body>
</t:dashboard>

</html>
