<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <div class="login_form col-md-6 text_block mt-5 pt-5 pb-5 pl-5 pr-5">

            <p class="login_label">ВОЙДИТЕ</p>

            <form action="Controller" method="post">
                <input type="hidden" name="command" value="sign_in_command"/>
                <div class="form-group">
                    <label for="loginInput">Логин:</label>
                    <input type="text" class="form-control" name="login" id="loginInput" aria-describedby="emailHelp" required pattern="${attribute_regexp_login}" placeholder="От 6 до 16 букв и '_'">
                </div>
                <div class="form-group">
                    <label for="passwordInput">Пароль:</label>
                    <input type="password" class="form-control" name="password" id="passwordInput" required pattern="${attribute_regexp_password}"  placeholder="От 6 до 18 символов">
                </div>

                <div class="form-group mt-5">
                    <button type="submit" class="btn btn-success">ВОЙТИ</button>
                </div>

                <div class="form-group">
                    <button form="signUpForm" type="submit" class="btn btn-warning">РЕГИСТРАЦИЯ</button>
                </div>

            </form>

            <c:if test="${message != null}">
                <div class="mt-5">
                    <p class="message_label">${message}</p>
                </div>
            </c:if>

        </div>
    </div>
</div>

<jsp:include page="WEB-INF/footer.jsp"/>
</body>

