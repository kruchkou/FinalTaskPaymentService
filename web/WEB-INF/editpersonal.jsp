<%--
  Created by IntelliJ IDEA.
  User: kruchkou
  Date: 29.09.2020
  Time: 3:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>

<head>
    <title>О пользователе</title>
    <link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<jsp:include page="header.jsp"/>
<jsp:useBean id="user" scope="request" class="bean.UserBean"/>
<jsp:setProperty name="user" property="role" value="${sessionScope.user.roleName}"/>
<jsp:setProperty name="user" property="login" value="${sessionScope.user.login}"/>
<jsp:setProperty name="user" property="name" value="${sessionScope.user.name}"/>
<jsp:setProperty name="user" property="surname" value="${sessionScope.user.surname}"/>
<jsp:setProperty name="user" property="patronymic" value="${sessionScope.user.patronymic}"/>
<jsp:setProperty name="user" property="birthDate" value="${sessionScope.user.birthDate}"/>
<jsp:setProperty name="user" property="phoneNumber" value="${sessionScope.user.phoneNumber}"/>

<div class="container">
    <div class="row justify-content-md-center">
        <div class="col-md-7">

            <div class="login_form text_block mt-3 pt-3 pb-3 pl-5 pr-5 mb-3">
                <p class="login_label">ЗАРЕГИСТРИРУЙТЕСЬ</p>

                <form action="Controller" method="post">
                    <input type="hidden" name="command" value="update_user_command"/>
                    <div class="form-group">
                        <label for="loginInput">Логин:</label>
                        <input type="text" class="form-control" name="login" id="loginInput">
                    </div>
                    <div class="form-group">
                        <label for="passwordInput">Пароль:</label>
                        <input type="password" class="form-control" name="password" id="passwordInput">
                    </div>
                    <div class="form-group">
                        <label for="nameInput">Имя:</label>
                        <input type="text" class="form-control" name="name" id="nameInput">
                    </div>
                    <div class="form-group">
                        <label for="surnameInput">Фамилия:</label>
                        <input type="text" class="form-control" name="surname" id="surnameInput">
                    </div>
                    <div class="form-group">
                        <label for="patromymicInput">Отчество:</label>
                        <input type="text" class="form-control" name="patronymic" id="patromymicInput">
                    </div>
                    <div class="form-group">
                        <label for="birthdateInput">Дата рождения:</label>
                        <input type="date" class="form-control" name="bithdate" id="birthdateInput">
                    </div>
                    <div class="form-group">
                        <label for="phoneInput">Номер телефона:</label>
                        <input type="tel" class="form-control" name="phone_number" id="phoneInput">
                    </div>
                    <div class="form-group pt-3">
                        <button type="submit" class="btn btn-warning">ЗАРЕГИСТРИРОВАТЬСЯ</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>


</body>
</html>
