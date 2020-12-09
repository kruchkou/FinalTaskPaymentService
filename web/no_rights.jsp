<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="css/core.css" rel="stylesheet" type="text/css">
    <title>QuickPAY</title>
</head>
<body>
<jsp:include page="WEB-INF/header.jsp"/>

    <div class="container bg-dark py-2 mt-5 label_window">
        <h3 class="mb-0 ml-3" style="color: white">Ошибка!</h3>
    </div>

    <div class="container payment_window mb-5 pt-3 pb-5">
        <div class="container mt-5">
            <h1 class="ml-5">НЕДОСТАТОЧНО ПРАВ</h1>
            <h2 class="mt-5">К сожалению, у вас отсутсвуют права на доступ к этой комманде.</h2>
            <p>Если произошла ошибка - обратитесь к администратору.</p>
        </div>
    </div>

<jsp:include page="WEB-INF/footer.jsp"/>
</body>
</html>
