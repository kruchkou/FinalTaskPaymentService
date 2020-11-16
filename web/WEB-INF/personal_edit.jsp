<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link href="css/login.css" rel="stylesheet" type="text/css">
</head>
<body>
<jsp:include page="header.jsp"/>

<jsp:useBean id="user" scope="request" class="by.epamtc.PaymentService.bean.UserBean"/>
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
                <p class="login_label">РЕДАКТИРОВАНИЕ</p>

                <form action="Controller" method="post">
                    <input type="hidden" name="command" value="personal_edit_command"/>
                    <div class="form-group">
                        <label for="loginInput">Логин:</label>
                        <input type="text" class="form-control" name="login" id="loginInput" value="${user.login}" required pattern="${attribute_regexp_login}" placeholder="От 6 до 16 букв и '_'">
                    </div>
                    <div class="form-group">
                        <label for="nameInput">Имя:</label>
                        <input type="text" class="form-control" name="name" id="nameInput" value="${user.name}" required pattern="${attribute_regexp_fio}" placeholder="От 2 до 20 букв">
                    </div>
                    <div class="form-group">
                        <label for="surnameInput">Фамилия:</label>
                        <input type="text" class="form-control" name="surname" id="surnameInput" value="${user.surname}" required pattern="${attribute_regexp_fio}" placeholder="От 2 до 20 букв">
                    </div>
                    <div class="form-group">
                        <label for="patromymicInput">Отчество:</label>
                        <input type="text" class="form-control" name="patronymic" id="patromymicInput" value="${user.patronymic}" required pattern="${attribute_regexp_fio}" placeholder="От 2 до 20 букв">
                    </div>
                    <div class="form-group">
                        <label for="birthdateInput">Дата рождения:</label>
                        <input type="date" class="form-control" name="birthdate" id="birthdateInput"
                               value="${user.birthDate}">
                    </div>
                    <div class="form-group">
                        <label for="phoneInput">Номер телефона:</label>
                        <input type="tel" class="form-control" name="phone_number" id="phoneInput" value="${user.phoneNumber}" required pattern="${attribute_regexp_phone_number}" placeholder="В формате +111111111111">
                    </div>
                    <hr class="separator" style="background-color: white">
<%--                    Разделитель сделать--%>
                    <div class="form-group">
                        <label for="passwordInput">Пароль для подтверждения:</label>
                        <input type="password" class="form-control" name="password" id="passwordInput" required pattern="${attribute_regexp_password}"  placeholder="От 6 до 18 символов">
                    </div>
                    <div class="form-group pt-3">
                        <button type="submit" class="btn btn-success">СОХРАНИТЬ</button>
                    </div>
                    <%--                    кнопка отмены--%>
                </form>
                <c:if test="${message != null}">
                        <p class="message_label">${message}</p>
                </c:if>
            </div>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"/>
</body>
</html>
