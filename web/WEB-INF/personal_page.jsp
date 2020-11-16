<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/WEB-INF/tld/custom_tags.tld" prefix="mytag" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>

<head>
    <title>О пользователе</title>
    <link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<jsp:include page="header.jsp"/>
<jsp:useBean id="user" scope="request" class="by.epamtc.PaymentService.bean.UserBean"/>
<jsp:setProperty name="user" property="role" value="${sessionScope.user.status.name}"/>
<jsp:setProperty name="user" property="login" value="${sessionScope.user.login}"/>
<jsp:setProperty name="user" property="name" value="${sessionScope.user.name}"/>
<jsp:setProperty name="user" property="surname" value="${sessionScope.user.surname}"/>
<jsp:setProperty name="user" property="patronymic" value="${sessionScope.user.patronymic}"/>
<jsp:setProperty name="user" property="birthDate" value="${sessionScope.user.birthDate}"/>
<jsp:setProperty name="user" property="phoneNumber" value="${sessionScope.user.phoneNumber}"/>
<jsp:setProperty name="user" property="imageSrc" value="${sessionScope.user.imageSrc}"/>

<div class="container">

    <div class="row justify-content-md-center mt-5 pt-5 pb-5 pl-3 pr-3 text_block">
        <div class="col-md-4">
            <img src="<mytag:userImageTag imageURL="${sessionScope.user.imageSrc}"/>" width="200" height="300" alt="фотография профиля">
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

            <form action="Controller" method="post" enctype="multipart/form-data">
                <input type="hidden" name="command" value="upload_user_image_command" />
                <input type="file" id="image" name="file" accept=".jpg" />
                <input type="submit" value="LoadPhoto" name="Uload" />
            </form>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"/>
</body>
</html>
