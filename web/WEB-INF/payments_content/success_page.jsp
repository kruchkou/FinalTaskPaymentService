<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale"/>

<fmt:message key="operation_success" var="locale_operation_success"/>
<fmt:message key="go_to_main_menu" var="locale_go_to_main_menu"/>


<form id="successForm" action="Controller" method="post">
    <input type="hidden" name="command" value="go_to_accounts_command">
</form>

<div class="container mt-3">
    <h1>${locale_operation_success.toUpperCase()}</h1>
    <div class="progress">
        <div class="progress-bar bg-success w-100" role="progressbar"
             aria-valuenow="3" aria-valuemin="0" aria-valuemax="3"></div>
    </div>

    <div class="mt-5 d-flex">
            <button form="successForm" style="height: 3em" type="submit" class="w-50 btn mx-auto btn-warning">${locale_go_to_main_menu.toUpperCase()}</button>
    </div>
</div>
