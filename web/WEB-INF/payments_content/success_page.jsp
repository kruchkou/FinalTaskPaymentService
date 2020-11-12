<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form id="successForm" action="Controller" method="post">
    <input type="hidden" name="command" value="go_to_accounts_command">
</form>

<div class="container mt-3">
    <h1>ОПЕРАЦИЯ ВЫПОЛНЕНА УСПЕШНО</h1>
    <div class="progress">
        <div class="progress-bar bg-success w-100" role="progressbar"
             aria-valuenow="3" aria-valuemin="0" aria-valuemax="3"></div>
    </div>

    <div class="mt-5 d-flex">
            <button form="successForm" style="height: 3em" type="submit" class="w-50 btn mx-auto btn-warning">ВЕРНУТЬСЯ В ГЛАВНОЕ МЕНЮ</button>
    </div>
</div>
