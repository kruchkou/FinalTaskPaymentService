<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%--<head>--%>
<%--    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"--%>
<%--          integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" crossorigin="anonymous">--%>
<%--    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap-theme.min.css"--%>
<%--          integrity="sha384-6pzBo3FDv/PJ8r2KRkGHifhEocL+1X2rVCTTkUfGk7/0pbek5mMa1upzvWbrUbOZ" crossorigin="anonymous">--%>
<%--    <script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"--%>
<%--            integrity="sha384-aJ21OjlMXNL5UyIl/XNwTMqvzeRMZH2w8c5cRVpzpU8Y5bApTppSuUkhZXN0VxHd"--%>
<%--            crossorigin="anonymous"></script>--%>
<%--</head>--%>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
            integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
            integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
            crossorigin="anonymous"></script>
</head>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top">
    <a class="navbar-brand" href="#" style="font-size: 3ex">QUICKPAY</a>

    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav ml-auto">

            <c:set var="current_user_name" value="${sessionScope.user.name}"/>
            <c:if test="${current_user_name == null}">
                <li class="nav-item">
                    <button form="goToSignInForm" class="btn" style="color: white" type="submit">Войти</button>
                </li>
            </c:if>
            <c:if test="${current_user_name != null}">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                       data-toggle="dropdown"
                       aria-haspopup="true" aria-expanded="false">
                            ${current_user_name}
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <button form="goToPersonalAreaForm" class="btn dropdown-item" type="submit">Мой профиль</button>
                        <div class="dropdown-divider"></div>
                        <button form="logOutForm" class="btn btn-outline-danger dropdown-item" type="submit">Выйти
                        </button>
                    </div>
                </li>
            </c:if>

        </ul>
        <ul class="navbar-nav ml-auto">
            <li class="nav-item ml-auto mr-auto">
                <a class="nav-link" href="index.jsp">О сервисе</a>
            </li>
        </ul>
    </div>
</nav>

<form id="goToSignInForm" action="Controller" method="post">
    <input type="hidden" name="command" value="go_to_sign_in_command"/>
</form>

<form id="logOutForm" action="Controller" method="post">
    <input type="hidden" name="command" value="log_out_command"/>
</form>

<form id="goToPersonalAreaForm" action="Controller" method="post">
    <input type="hidden" name="command" value="go_to_personal_page_command"/>
</form>
