<%--
  Created by IntelliJ IDEA.
  User: kruchkou
  Date: 29.09.2020
  Time: 3:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Упс!</title>
</head>
<body>
<jsp:include page="WEB-INF/header.jsp" />
<div class="container bg-dark py-2 mt-5 label_window">
    <h3 class="mb-0 ml-3" style="color: white">Ошибка!</h3>
</div>

<div class="container payment_window mb-5 pt-3 pb-5">
    <div class="container mt-5">
        <h1 class="ml-5">Упс!</h1>
        <h2 class="mt-5">Извините, у нас технические неполадки</h2>
        <p>${exception.getMessage()}</p>
        <p>${exception.printStackTrace()}</p>
    </div>
</div>

<jsp:include page="WEB-INF/footer.jsp"/>
</body>
</html>
