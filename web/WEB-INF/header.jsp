<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" crossorigin="anonymous">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap-theme.min.css" integrity="sha384-6pzBo3FDv/PJ8r2KRkGHifhEocL+1X2rVCTTkUfGk7/0pbek5mMa1upzvWbrUbOZ" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js" integrity="sha384-aJ21OjlMXNL5UyIl/XNwTMqvzeRMZH2w8c5cRVpzpU8Y5bApTppSuUkhZXN0VxHd" crossorigin="anonymous"></script>
</head>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a href="#" class="navbar-brand" style="font-size: 5ex">QUICKPAY</a>
        </div>
        <div>
            <ul class="nav navbar-nav">
                <li><a href="#">Главная</a></li>
                <li><a href="index.jsp">О системе</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <c:set var="current_user_name" value="${sessionScope.user.name}"/>
                <li style="margin-right: 20px">
                <c:if test="${current_user_name == null}">
                    <c:set var="command" value="go_to_sign_in_command" scope="request"/>
                    <a href="#">Вход</a></li>
                </c:if>
                <c:if test="${current_user_name != null}">
                    <c:set var="command" value="go_to_personal_page_command" scope="request"/>
                    <a href="#">${current_user_name}</a></li>
                </c:if>

            </ul>
        </div>
    </div>
    <form method="POST" action="/Controller">
        <input type="hidden" name="command" value="go_to_sign_in_command">
        <A HREF="javascript:document.submitForm.submit()">Click Me</A>
    </form>
</nav>
