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
</head>
<body>
    <jsp:useBean id="user" scope="request" class="bean.UserBean"/>

    <jsp:setProperty name="user" property="role" value = "${role}"/>
    <jsp:setProperty name="user" property="login" value ="${login}"/>
    <jsp:setProperty name="user" property="name" value ="${name}"/>
    <jsp:setProperty name="user" property="surname" value ="${surname}"/>
    <jsp:setProperty name="user" property="patronymic" value ="${patronymic}"/>
    <jsp:setProperty name="user" property="birthDate" value ="${birth_date}"/>
    <jsp:setProperty name="user" property="phoneNumber" value ="${phone_number}"/>

    Добро пожаловать, <jsp:getProperty name="user" property="role"/> <br/><br/>
    Ваши данные: <br/>
    Логин: <jsp:getProperty name="user" property="login"/> <br/>
    Имя: <jsp:getProperty name="user" property="name"/> <br/>
    Фамилия: <jsp:getProperty name="user" property="surname"/> <br/>
    Отчество: <jsp:getProperty name="user" property="patronymic"/> <br/>
    Дата рождения: <jsp:getProperty name="user" property="birthDate"/> <br/>
    Номер телефона: <jsp:getProperty name="user" property="phoneNumber"/>

</body>
</html>
