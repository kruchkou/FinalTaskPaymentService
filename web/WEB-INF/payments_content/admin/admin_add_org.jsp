<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create org</title>
</head>
<div class="container mt-3">
    <h1>СОЗДАТЬ ОРГАНИЗАЦИЮ</h1>
    <div class="progress">
        <div class="progress-bar bg-info progress-bar-striped progress-bar-animated" style="width: 50%"
             role="progressbar" aria-valuenow="1" aria-valuemin="0" aria-valuemax="3"></div>
    </div>

    <form id="payForm" action="Controller" method="post">
        <input type="hidden" name="command" value="go_to_add_org_confirm_command">

        <div class="card">
            <div class="card-body d-flex">
                <label class="my-auto" for="accountToInput">Введите название организации</label>
                <input form="payForm" style="width: 80%" type="text" class="mx-3 form-control" name="name" id="accountToInput">
            </div>
        </div>

        <div class="card mt-2">
            <div class="card-body d-flex">
                <label class="my-auto" for="amountInput">Введите номер счёта для поступления средств</label>
                <input form="payForm" style="width: 80%" type="number" class="mx-3 form-control" name="accountID" id="amountInput">
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
