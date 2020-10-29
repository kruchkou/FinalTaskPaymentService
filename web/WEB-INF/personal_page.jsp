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

    <div class="row justify-content-md-center mt-5 pt-5 pb-5 pl-3 pr-3 text_block">
        <div class="col-md-4">
            <img src="img/chest.jpg" width="200" height="300" alt="фотография профиля">
        </div>

        <div class="col-md-6">
            Добро пожаловать,
            <jsp:getProperty name="user" property="role"/>
            <br/><br/>
            Ваши данные: <br/>
            Логин:
            <jsp:getProperty name="user" property="login"/>
            <br/>
            Имя:
            <jsp:getProperty name="user" property="name"/>
            <br/>
            Фамилия:
            <jsp:getProperty name="user" property="surname"/>
            <br/>
            Отчество:
            <jsp:getProperty name="user" property="patronymic"/>
            <br/>
            Дата рождения:
            <jsp:getProperty name="user" property="birthDate"/>
            <br/>
            Номер телефона:
            <jsp:getProperty name="user" property="phoneNumber"/>

            <form action="Controller" method="post">
                <input type="hidden" name="command" value="go_to_personal_edit_command"/>
                <div class="form-group">
                    <button type="submit" class="btn btn-light edit_button">Редактировать</button>
                </div>
            </form>
        </div>
    </div>

</div>

</body>
</html>
