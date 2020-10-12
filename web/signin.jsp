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
<div class="login_form">
    <p class="login_label">QUICKPAY: ВХОД</p>
    <form action="Controller" method="post">
        <input type="hidden" name="command" value="sign_in_command"/>
        <label>Логин: </label> <input type="text" name="login" value=""/> <br/>
        <label>Пароль: </label> <input type="password" name="password" value=""/> <br/>
        <button class="login_button" type="submit">Войти</button>
        <input type = "button" class="login _button" onclick="location.href='signup.jsp'" value = "Регистрация"/>
    </form>

</div>

</body>
</html>
