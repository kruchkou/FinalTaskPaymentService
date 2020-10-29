<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="css/login.css" rel="stylesheet" type="text/css">
    <title>QuickPAY</title>
</head>
<body>
<jsp:include page="header.jsp"/>

<div class="container payment_window">

    <div class="row justify-content-md-center">
        <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
            <div class="btn-group mr-5" role="group" aria-label="First group">
                <button type="button" class="btn btn-secondary">Мои счета</button>
                <button type="button" class="btn btn-secondary">Мои карты</button>
                <button type="button" class="btn btn-secondary">Мои платежи</button>
            </div>
            <div class="btn-group" role="group" aria-label="Second group">
                <button type="button" class="btn btn-secondary">Оплатить</button>
                <button type="button" class="btn btn-secondary">Перевести</button>
            </div>
        </div>
    </div>

    <div class="content">
        <c:forEach var="element" items="${accounts}">
            <p>${element}</p>
        </c:forEach>
    </div>

</div>

</body>
</html>
