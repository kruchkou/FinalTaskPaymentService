<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>Add org command</title>
</head>
<form id="transferForm" action="Controller" method="post">
    <input type="hidden" name="command" value="add_org_command">
    <input type="hidden" name="name" value="${name}">
    <input type="hidden" name="accountID" value="${accountInfo.id}">
</form>
<div class="container mt-3">
    <h1>ПОДТВЕРДИТЬ СОЗДАНИЕ ОРГАНИЗАЦИИ</h1>
    <div class="progress">
        <div class="progress-bar bg-info" style="width: 50%"
             role="progressbar" aria-valuenow="1" aria-valuemin="0" aria-valuemax="3"></div>
        <div class="progress-bar bg-info progress-bar-striped progress-bar-animated" style="width: 50%"
             role="progressbar" aria-valuenow="1" aria-valuemin="0" aria-valuemax="3"></div>
    </div>

    <div class="card">
        <div class="card-header">
            Название:
        </div>
        <div class="card-body center_box">
            <div class="d-flex">
                <p class="mb-0 mr-3">${name}</p>
            </div>
        </div>
    </div>
    <div class="card mt-2">
        <div class="card-header">
            Привязать к счёту
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
        <div class="card-body d-flex">
            <input form="transferForm" style="width: 30%" type="submit" class="btn ml-auto btn-success" value="СОЗДАТЬ">
        </div>
    </div>


</div>