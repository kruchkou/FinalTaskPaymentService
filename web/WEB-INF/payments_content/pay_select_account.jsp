<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>Pay from account</title>
</head>
<form id="cardForm" action="Controller" method="post">
    <input type="hidden" name="command" value="${next_command}"/>
</form>
<div class="container mt-3">
    <h1>ВЫБРАТЬ СЧЁТ</h1>
    <div class="progress">
        <div class="progress-bar bg-info progress-bar-striped progress-bar-animated" style="width: 33%" role="progressbar" aria-valuenow="1" aria-valuemin="0" aria-valuemax="3"></div>
    </div>

    <c:forEach items="${accounts}" var="account">
        <div class="card">
            <div class="card-header">
                Активный счёт:
            </div>
            <div class="card-body center_box">
                <div class="d-flex">
                    <p class="mb-0 mr-3">Счет №: ${account.id}</p>
                        <%--                    Когда сделаешь бины нормальные сделай баланс остаточный на счету, привязанному к карте--%>
                    <p class="mb-0 mr-3">Баланс: ${account.balance} BYN</p>
                </div>
                <button form="cardForm" name="accountFromID" type="submit" class="btn btn-info ml-auto mr-2"
                        value="${account.id}">Выбрать
                </button>
            </div>
        </div>
    </c:forEach>
    <%--    if pay.status (в статусбаре та же переменка) = 2--%>


</div>