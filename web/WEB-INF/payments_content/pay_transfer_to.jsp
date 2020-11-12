<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Transfer to</title>
</head>
<div class="container mt-3">
    <h1>ПЕРВЕСТИ НА СЧЕТ</h1>
    <div class="progress">
        <div class="progress-bar bg-info" style="width: 33%" role="progressbar"
             aria-valuenow="1" aria-valuemin="0" aria-valuemax="3"></div>
        <div class="progress-bar bg-info progress-bar-striped progress-bar-animated" style="width: 33%"
             role="progressbar"
             aria-valuenow="1" aria-valuemin="0" aria-valuemax="3"></div>
    </div>

    <%--    <div class="col-md-6 mt-3 mx-auto">--%>

            <form id="payForm" action="Controller" method="post">
                <input type="hidden" name="accountFromID" value="${accountFromID}">
                <input type="hidden" name="command" value="go_to_pay_transfer_confirm_command">

                <div class="card">
                    <div class="card-body d-flex">
                        <label class="my-auto" for="accountToInput">Введите номер счёта</label>
                        <input form="payForm" style="width: 30%" type="number" class="mx-3 form-control" name="accountToID" id="accountToInput">
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
                        <input form="payForm" style="width: 30%" type="submit" class="btn ml-auto btn-success" value="ПРОДОЛЖИТЬ">
                    </div>
                </div>

            <c:if test="${message != null}">
                <div class="mt-2">
                    <p class="message_label">${message}</p>
                </div>
            </c:if>
        </div>
