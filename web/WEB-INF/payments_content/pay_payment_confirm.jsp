<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>Pay to organization</title>
</head>
<form id="payForm" action="Controller" method="post">
    <input type="hidden" name="command" value="pay_command">
    <input type="hidden" name="accountFromID" value="${account.id}">
    <input type="hidden" name="accountToID" value="${organization.account}">
</form>
<div class="container mt-3">
    <h1>ПОДТВЕРДИТЬ ОПЛАТУ</h1>
    <div class="progress">
        <div class="progress-bar bg-info" style="width: 66%"
             role="progressbar" aria-valuenow="1" aria-valuemin="0" aria-valuemax="2"></div>
        <div class="progress-bar bg-info progress-bar-striped progress-bar-animated" style="width: 34%"
             role="progressbar" aria-valuenow="1" aria-valuemin="0" aria-valuemax="2"></div>
    </div>

    <div class="card">
        <div class="card-header">
            Cо счёта:
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
        <div class="card-header">
            На счёт организации:
        </div>
        <div class="card-body center_box">
            <div class="d-flex">
                <p class="mb-0 mr-3">${organization.name}</p>
            </div>
        </div>
    </div>

    <div class="card mt-2">
        <div class="card-body d-flex">
            <label class="my-auto" for="amountInput">Введите сумму, BYN:</label>
            <input form="payForm" style="width: 30%" type="number" class="mx-3 form-control" name="amount" id="amountInput">
        </div>
    </div>

    <div class="card mt-2">
        <div class="card-body d-flex">
            <label class="my-auto" for="commentInput">Введите комментарий:</label>
            <input form="payForm" style="width: 90%" type="text" class="mx-3 form-control" id="commentInput" name="comment" placeholder="опционально">
        </div>
    </div>

    <div class="card mt-2">
        <div class="card-body d-flex">
            <label class="my-auto" for="passwordInput">Введите пароль:</label>
            <input form="payForm" style="width: 50%" type="password" class="mx-3 form-control" name="password" id="passwordInput">
            <input form="payForm" style="width: 30%" type="submit" class="btn ml-auto btn-success" value="ОПЛАТИТЬ">
        </div>
    </div>

    <c:if test="${message != null}">
        <p style="font-size: xx-large; text-align: center" class="mb-0 mt-2">${message}</p>
    </c:if>

</div>