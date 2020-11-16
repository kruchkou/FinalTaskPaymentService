<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
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
    <link rel="stylesheet" href="css/login.css">
    <title>Header</title>
</head>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top">
    <a class="navbar-brand" href="Controller?command=go_to_accounts_command" style="font-size: 3ex">QUICKPAY</a>

    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav ml-auto">

            <c:set var="user" value="${sessionScope.user}"/>
            <c:if test="${user.name == null}">
                <li class="nav-item">
                    <button form="headerForm" class="btn cl-white" type="submit" name="command"
                            value="go_to_sign_in_command">Войти
                    </button>
                </li>
            </c:if>

            <c:if test="${user.name != null}">

                <li class="nav-item">
                    <button form="headerForm" class="btn cl-white mr-5" type="submit" name="command"
                            value="go_to_accounts_command">Панель пользователя
                    </button>
                </li>

                <c:if test="${user.status.id == 2}">

                    <li class="nav-item">
                        <button form="headerForm" class="btn cl-white mr-5" type="submit" name="command"
                                value="go_to_admin_accounts_command">Панель администратора
                        </button>
                    </li>

                </c:if>


                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle cl-white" href="#" id="navbarDropdown" role="button"
                       data-toggle="dropdown"
                       aria-haspopup="true" aria-expanded="false">
                            ${user.name}
                    </a>

                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">

                        <button form="headerForm" class="btn dropdown-item" type="submit" name="command"
                                value="go_to_personal_page_command">Мой профиль
                        </button>
                        <div class="dropdown-divider"></div>
                        <button form="headerForm" class="btn btn-outline-danger dropdown-item" type="submit"
                                name="command" value="log_out_command">Выйти
                        </button>

                    </div>
                </li>
            </c:if>

        </ul>
        <ul class="navbar-nav ml-auto">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle cl-white" href="#" id="navLang" role="button"
                   data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false">
                    RU
                </a>

                <div class="dropdown-menu" aria-labelledby="navLang">

                    <button form="headerForm" class="btn dropdown-item" type="submit" name="command"
                            value="go_to_personal_page_command">RU
                    </button>
                    <div class="dropdown-divider"></div>
                    <button form="headerForm" class="btn btn-outline-danger dropdown-item" type="submit"
                            name="command" value="log_out_command">EN
                    </button>

                </div>
            </li>
            <li class="nav-item ml-auto mr-auto">
                <a class="nav-link" href="index.jsp">О сервисе</a>
            </li>
        </ul>
    </div>
</nav>

<form id="headerForm" action="Controller" method="post"></form>