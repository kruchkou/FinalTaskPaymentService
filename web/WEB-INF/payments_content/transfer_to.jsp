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
    <div class="card">
            <div class="card-body display_block col-md-6 mt-2 mx-auto">

                <form action="Controller" method="post">
                    <input type="hidden" name="accountFromID" value="${accountFromID}">
                    <input type="hidden" name="command" value="go_to_pay_transfer_confirm_command">
                    <div class="form-group">
                        <label for="accountToID">Счет №:</label>
                        <input type="number" class="form-control" name="accountToID" id="accountToID"
                               placeholder="Введите № cчета">
                    </div>
                    <div class="form-group">
                        <label for="amount">Сумма, BYN</label>
                        <input type="number" class="form-control" name="amount" id="amount"
                               placeholder="Введите сумму перевода">
                    </div>
                    <div class="form-group">
                        <label for="comment">Комментарий</label>
                        <input type="text" class="form-control" name="comment" id="comment"
                               placeholder="Не обязателен">
                    </div>
                    <div class="form-group mt-4">
                        <button type="submit" class="btn btn-success">Продолжить</button>
                    </div>
                </form>
                <c:if test="${message != null}">
                    <div class="mt-2">
                        <p class="message_label">${message}</p>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
<%--</div>--%>