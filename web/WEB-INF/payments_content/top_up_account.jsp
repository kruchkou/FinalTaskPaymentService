<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/tld/custom_tags.tld" prefix="mytag" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>Pay from card</title>
</head>
<form id="transferForm" action="Controller" method="post">
    <input type="hidden" name="command" value="top_up_account_command">
    <input type="hidden" name="accountID" value="${account.id}">
    <input type="hidden" name="cardID" value="${card.id}">
</form>
<div class="container mt-3">
    <h1>ПОДТВЕРДИТЬ ПОПОЛНЕНИЕ</h1>
    <div class="progress">
        <div class="progress-bar bg-info" style="width: 50%"
             role="progressbar" aria-valuenow="1" aria-valuemin="0" aria-valuemax="2"></div>
        <div class="progress-bar bg-info progress-bar-striped progress-bar-animated" style="width: 50%"
             role="progressbar" aria-valuenow="1" aria-valuemin="0" aria-valuemax="2"></div>
    </div>

    <div class="card">
        <div class="card-header">
            Пополнить с карты:
        </div>
        <div class="card-body center_box">
            <div class="d-flex">
                <p class="mb-0 mr-3">Номер карты: <mytag:cardNumberTag cardNumber="${card.number}"/></p>
                <p class="mb-0 mr-3">Владелец: ${card.ownerName}</p>
            </div>
        </div>
    </div>
    <div class="card mt-2">
        <div class="card-header">
            На счет:
        </div>
        <div class="card-body center_box">
            <div class="d-flex">
                    <p class="mb-0 mr-3">Счет №: ${account.id}</p>
                <p class="mb-0 mr-3">
                    Баланс: ${account.balance} BYN</p>
            </div>
        </div>
    </div>

    <div class="card mt-2">
        <div class="card-body d-flex">
            <label class="my-auto" for="amountInput">Введите сумму, BYN:</label>
            <input form="transferForm" style="width: 30%" type="number" class="mx-3 form-control" name="amount" id="amountInput">
        </div>
    </div>

    <div class="card mt-2">
        <div class="card-body d-flex">
            <label class="my-auto" for="passwordInput">Введите пароль:</label>
            <input form="transferForm" style="width: 50%" type="password" class="mx-3 form-control" name="password" id="passwordInput">
            <input form="transferForm" style="width: 30%" type="submit" class="btn ml-auto btn-success" value="ПОПОЛНИТЬ">
        </div>
    </div>

    <c:if test="${message != null}">
        <p style="font-size: xx-large; text-align: center" class="mb-0 mt-2">${message}</p>
    </c:if>

</div>