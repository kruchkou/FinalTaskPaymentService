<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale"/>

<fmt:message key="sign_up" var="locale_sign_up"/>
<fmt:message key="do_sign_up" var="locale_do_sign_up"/>
<fmt:message key="user.login" var="locale_user_login"/>
<fmt:message key="user.name" var="locale_user_name"/>
<fmt:message key="user.surname" var="locale_user_surname"/>
<fmt:message key="user.patronymic" var="locale_user_patronymic"/>
<fmt:message key="user.password" var="locale_user_password"/>
<fmt:message key="user.birthdate" var="locale_user_birthdate"/>
<fmt:message key="user.phone_number" var="locale_user_phone_number"/>

<fmt:message key="from" var="locale_from"/>
<fmt:message key="to" var="locale_to"/>
<fmt:message key="letters" var="locale_letters"/>
<fmt:message key="symbols" var="locale_symbols"/>
<fmt:message key="format" var="locale_format"/>
<fmt:message key="and" var="locale_and"/>

<html>
<head>
    <title>QUICKPAY: ${locale_sign_up}</title>
    <link href="css/core.css" rel="stylesheet" type="text/css">
</head>
<body>
<jsp:include page="WEB-INF/header.jsp"/>

<div class="container mb-5">
    <div class="row justify-content-md-center">
        <div class="col-md-7">

            <div class="login_form text_block mt-3 pt-3 pb-3 pl-5 pr-5 mb-3">
                <p class="login_label">${locale_sign_up.toUpperCase()}</p>

                <form action="SignUp" method="post">
                    <input type="hidden" name="command" value="sign"/>

                    <div class="form-group">
                        <label for="loginInput">${locale_user_login}:</label>
                        <input type="text" class="form-control" name="login" id="loginInput" required
                               pattern="${attribute_regexp_login}" placeholder="${locale_from} 6 ${locale_to} 16 ${locale_letters} ${locale_and} '_'">
                    </div>

                    <div class="form-group">
                        <label for="passwordInput">${locale_user_password}:</label>
                        <input type="password" class="form-control" name="password" id="passwordInput" required
                               pattern="${attribute_regexp_password}" placeholder="${locale_from} 6 ${locale_to} 18 ${locale_symbols}">
                    </div>

                    <div class="form-group">
                        <label for="nameInput">${locale_user_name}:</label>
                        <input type="text" class="form-control" name="name" id="nameInput" required
                               pattern="${attribute_regexp_fio}" placeholder="${locale_from} 2 ${locale_to} 20 ${locale_letters}">
                    </div>
                    <div class="form-group">
                        <label for="surnameInput">${locale_user_surname}:</label>
                        <input type="text" class="form-control" name="surname" id="surnameInput" required
                               pattern="${attribute_regexp_fio}" placeholder="${locale_from} 2 ${locale_to} 20 ${locale_letters}">
                    </div>

                    <div class="form-group">
                        <label for="patromymicInput">${locale_user_patronymic}:</label>
                        <input type="text" class="form-control" name="patronymic" id="patromymicInput" required
                               pattern="${attribute_regexp_fio}" placeholder="${locale_from} 2 ${locale_to} 20 ${locale_letters}">
                    </div>

                    <div class="form-group">
                        <label for="birthdateInput">${locale_user_birthdate}:</label>
                        <input type="date" class="form-control" name="birthdate" id="birthdateInput">
                    </div>

                    <div class="form-group">
                        <label for="phoneInput">${locale_user_phone_number}:</label>
                        <input type="tel" class="form-control" name="phone_number" id="phoneInput" required
                               pattern="${attribute_regexp_phone_number}" placeholder="${locale_format}: +111111111111">
                    </div>

                    <div class="form-group pt-3">
                        <button type="submit" class="btn btn-warning">${locale_do_sign_up.toUpperCase()}</button>
                    </div>
                </form>
                <c:if test="${message != null}">
                    <p class="message_label">${message}</p>
                </c:if>
            </div>
        </div>
    </div>
</div>

<jsp:include page="WEB-INF/footer.jsp"/>
</body>
</html>
