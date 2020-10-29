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
        <div class="col-md-7">

            <div class="login_form text_block mt-3 pt-3 pb-3 pl-5 pr-5 mb-3">
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
