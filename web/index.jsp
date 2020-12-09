<%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Welcome page!</title>
    <link href="css/core.css" rel="stylesheet" type="text/css">

</head>
<body>
<jsp:include page="WEB-INF/header.jsp"/>

<div class="container bg-dark py-2 mt-5 label_window">
    <h3 class="mb-0 ml-3" style="color: white">О СИСТЕМЕ</h3>
</div>

<div class="container payment_window mb-5 pt-3 pb-3">

    <div class="mx-3 justify-content-md-center">


        <h3 style="text-align: center">Система платежей QUICKPAY создана для упрощения рутинных процедур оплаты и
            переводов между пользователями</h3>

        <div class="card mt-3">
            <div class="card-header">
               Моментальные платежи
            </div>
            <div class="card-body">
                Система QUICKPAY позволяет вам совершать моментальные переводы. Больше не нужно ождать, пока средства дойдут до получателя.
            </div>
        </div>

        <div class="card mt-3">
            <div class="card-header">
                Легкое пополнение
            </div>
            <div class="card-body">
                Пополнить счёт в системе можно с помощью карты любого банка. Комиссия на пополнение так же отсуствует.
            </div>
        </div>

        <div class="card mt-3">
            <div class="card-header">
                Отсутсвие комиссий
            </div>
            <div class="card-body">
                Отсутсвие любых видов комиссий. Сервис абсолютно бесплатный для пользователей.
            </div>
        </div>

        <form class="center" action="Controller" method="post">
            <input type="hidden" name="command" value="go_to_sign_in_command"/>
            <button class="mt-4 index_login_button btn btn-success" type="submit">ВХОД</button>
        </form>
    </div>

</div>

<jsp:include page="WEB-INF/footer.jsp"/>
</body>
</html>