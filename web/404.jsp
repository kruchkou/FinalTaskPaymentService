<%--
  Created by IntelliJ IDEA.
  User: kruchkou
  Date: 01.11.2020
  Time: 17:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>QUICKPAY: OOPS</title>
</head>
<body>
<jsp:include page="WEB-INF/header.jsp" />

<div class="container bg-dark py-2 mt-5 label_window">
    <h3 class="mb-0 ml-3" style="color: white">Ошибка!</h3>
</div>

<div class="container payment_window mb-5 pt-3 pb-5">
    <div class="container mt-5">
        <h1 class="ml-5">Упс! 404</h1>
        <h2 class="mt-5">Вы нашли QUICKPAY... Но, не нашли страницу!</h2>
    </div>
</div>

<jsp:include page="WEB-INF/footer.jsp"/>
</body>
</html>
