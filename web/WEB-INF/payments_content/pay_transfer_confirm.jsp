<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>Pay from card</title>
</head>
<form id="transferForm" action="Controller" method="post">
    <input type="hidden" name="command" value="pay_command">
    <input type="hidden" name="accountFromID" value="${account.id}">
    <input type="hidden" name="accountToID" value="${accountInfo.id}">
    <input type="hidden" name="amount" value="${amount}">
    <input type="hidden" name="comment" value="${comment}">
</form>
<div class="container mt-3">
    <h1>ПОДТВЕРДИТЬ ПЛАТЕЖ</h1>
    <div class="progress">
        <div class="progress-bar bg-info" style="width: 66%"
             role="progressbar" aria-valuenow="1" aria-valuemin="0" aria-valuemax="3"></div>
        <div class="progress-bar bg-info progress-bar-striped progress-bar-animated" style="width: 34%"
             role="progressbar" aria-valuenow="1" aria-valuemin="0" aria-valuemax="3"></div>
    </div>

    <div class="card">
        <div class="card-header">
            Оплатить со счёта:
        </div>
        <div class="card-body center_box">
            <div class="d-flex">
                <p class="mb-0 mr-3">Счет №: ${account.id}</p>
                <p class="mb-0 mr-3">Баланс: ${account.balance}</p>
            </div>
        </div>
    </div>
    <div class="card mt-2">
        <div class="card-header">
            На счет:
        </div>
        <div class="card-body center_box">
            <div class="d-flex">
                <p class="mb-0 mr-3">Счет №: ${accountInfo.id}</p>
                <p class="mb-0 mr-3">
                    Получатель: ${accountInfo.userSurname} ${accountInfo.userName} ${accountInfo.userPatronymic}</p>
            </div>
        </div>
    </div>

    <div class="card mt-2">
        <div class="card-header">
            Детали платежа:
        </div>
        <div class="card-body center_box">
            <div class="d-flex">
                <p class="mb-0 mr-3">Сумма: ${amount} BYN</p>
                <c:if test="${comment != null}">
                    <p class="mb-0 mr-3">Комментарий: ${comment}</p>
                </c:if>
            </div>
        </div>
    </div>

    <div class="card mt-2">
        <div class="card-body d-flex">
            <label class="my-auto" for="passwordInput">Введите пароль:</label>
            <input form="transferForm" style="width: 50%" type="password" class="mx-3 form-control" name="password" id="passwordInput">
            <input form="transferForm" style="width: 30%" type="submit" class="btn ml-auto btn-success" value="ЗАПЛАТИТЬ">
        </div>
    </div>

    <c:if test="${message != null}">
        <p class="mb-0 mt-2">${message}</p>
    </c:if>
    <%--    if pay.status (в статусбаре та же переменка) = 2--%>


</div>