<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="css/login.css" rel="stylesheet" type="text/css">
    <title>QuickPAY</title>
</head>
<body>
<jsp:include page="header.jsp"/>

<div class="container payment_window mt-5 mb-5 pt-3 pb-5">

    <div class="row justify-content-md-center">
        <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
            <div class="btn-group mr-5" role="group" aria-label="First group">
                <a type="button" class="btn btn-secondary" href="Controller?command=go_to_accounts_command">Мои счета</a>
                <a type="button" class="btn btn-secondary" href="Controller?command=go_to_cards_command">Мои карты</a>
                <button type="button" class="btn btn-secondary">Мои платежи</button>
            </div>
            <div class="btn-group" role="group" aria-label="Second group">
                <button type="button" class="btn btn-secondary">Оплатить</button>
                <button type="button" class="btn btn-secondary">Перевести</button>
            </div>
        </div>
    </div>

    <jsp:include page="${payments_content}"/>

<%--    <div class="content">--%>
<%--        <c:forEach var="element" items="${accounts}">--%>
<%--            <p>${element}</p>--%>
<%--        </c:forEach>--%>
<%--    </div>--%>

</div>

</body>
</html>
