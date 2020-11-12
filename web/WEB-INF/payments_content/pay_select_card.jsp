<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>Pay from card</title>
</head>
<form id="cardForm" action="Controller" method="post">
    <input type="hidden" name="accountID" value=${accountID}>
    <input type="hidden" name="command" value="go_to_top_up_account_command"/>
</form>
<div class="container mt-3">
    <h1>ВЫБРАТЬ КАРТУ</h1>
    <div class="progress">
        <div class="progress-bar bg-info progress-bar-striped progress-bar-animated" style="width: 50%" role="progressbar" aria-valuenow="1" aria-valuemin="0" aria-valuemax="2"></div>
    </div>

    <c:forEach items="${cards}" var="card">
        <div class="card">
            <div class="card-header">
                Активная карта:
            </div>
            <div class="card-body center_box">
                <div class="d-flex">
                    <p class="mb-0 mr-3">Номер: <c:out value="${card.getNumber()}"/></p>
                    <p class="mb-0 mr-3">Владелец: ${card.ownerName}</p>
                </div>
                <button form="cardForm" name="cardID" type="submit" class="btn btn-info ml-auto mr-2"
                        value="${card.getId()}">Выбрать
                </button>
            </div>
        </div>
    </c:forEach>
<%--    if pay.status (в статусбаре та же переменка) = 2--%>


</div>