<%--
  Created by IntelliJ IDEA.
  User: kruchkou
  Date: 22.09.2020
  Time: 1:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <title>QuickPay: Login page</title>
    <link href="css/login.css" rel="stylesheet" type="text/css">
</head>
<body>
<jsp:include page="WEB-INF/header.jsp"/>

<form id="signUpForm" action="Controller" method="post">
    <input type="hidden" name="command" value="go_to_sign_up_command"/>
</form>

<div class="container">
    <div class="row justify-content-md-center">
        <div class="col-md-6">

            <div class="login_form">
                <p class="login_label">ВОЙДИТЕ</p>

                <form action="Controller" method="post">
                    <input type="hidden" name="command" value="sign_in_command"/>
                    <div class="form-group">
                        <label for="loginInput">Логин:</label>
                        <input type="text" class="form-control" name="login" id="loginInput" aria-describedby="emailHelp">
                    </div>
                    <div class="form-group">
                        <label for="passwordInput">Пароль:</label>
                        <input type="password" class="form-control" name="password" id="passwordInput">
                    </div>
                    <div class="form-group">
                        <button type="submit" class="btn btn-success">ВОЙТИ</button>
                    </div>

                    <div class="form-group">
                        <button form="signUpForm" type="submit" class="btn btn-warning">РЕГИСТРАЦИЯ</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>


</body>

