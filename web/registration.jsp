<%--
  Created by IntelliJ IDEA.
  User: kruchkou
  Date: 29.09.2020
  Time: 1:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>QuickPay: registration page</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <link href="css/login.css" rel="stylesheet" type="text/css">
</head>
<body>
<jsp:include page="WEB-INF/header.jsp" />
<div class="login_form">
    <p class="login_label">QUICKPAY: РЕГИСТРАЦИЯ</p>
    <form action="SignUp" method="post">
        <input type="hidden" name="command" value="sign_up_command"/>
        <label>Логин: </label> <input type="text" name="login" value=""/> <br/>
        <label>Пароль: </label> <input type="password" name="password" value=""/> <br/>
        <label>Имя: </label> <input type="text" name="name" value=""/> <br/>
        <label>Фамилия: </label> <input type="text" name="surname" value=""/> <br/>
        <label>Отчество: </label> <input type="text" name="patronymic" value=""/> <br/>
        <label>Дата рождения: </label> <input type="date" name="birthdate" value=""/> <br/>
        <label>Номер телефона: </label> <input type="tel" name="phone_number" value=""/> <br/>
        <button class="login_button" type="submit">Зарегистрироваться</button>
    </form>

</div>


<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
</body>
</html>
