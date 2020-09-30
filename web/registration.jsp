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
    <link href="css/login.css" rel="stylesheet" type="text/css">
</head>
<body>
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

</body>
</html>
