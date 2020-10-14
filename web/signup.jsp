<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>QuickPay: registration page</title>
    <link href="css/login.css" rel="stylesheet" type="text/css">
</head>
<body>
<jsp:include page="WEB-INF/header.jsp"/>

<div class="container">
    <div class="row justify-content-md-center">
        <div class="col-md-6">

            <div class="login_form">
                <p class="login_label">ЗАРЕГИСТРИРУЙТЕСЬ</p>

                <form action="SignUp" method="post">
                    <input type="hidden" name="command" value="sign"/>
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
                    <div class="form-group">
                        <button type="submit" class="btn btn-warning">ЗАРЕГИСТРИРОВАТЬСЯ</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<%--<div class="login_form">--%>
<%--    <p class="login_label">QUICKPAY: РЕГИСТРАЦИЯ</p>--%>
<%--    <form action="SignUp" method="post">--%>
<%--        <input type="hidden" name="command" value="sign_up_command"/>--%>
<%--        <label>Логин: </label> <input type="text" name="login" value=""/> <br/>--%>
<%--        <label>Пароль: </label> <input type="password" name="password" value=""/> <br/>--%>
<%--        <label>Имя: </label> <input type="text" name="name" value=""/> <br/>--%>
<%--        <label>Фамилия: </label> <input type="text" name="surname" value=""/> <br/>--%>
<%--        <label>Отчество: </label> <input type="text" name="patronymic" value=""/> <br/>--%>
<%--        <label>Дата рождения: </label> <input type="date" name="birthdate" value=""/> <br/>--%>
<%--        <label>Номер телефона: </label> <input type="tel" name="phone_number" value=""/> <br/>--%>
<%--        <button class="login_button" type="submit">Зарегистрироваться</button>--%>
<%--    </form>--%>

<%--</div>--%>
</body>
</html>
