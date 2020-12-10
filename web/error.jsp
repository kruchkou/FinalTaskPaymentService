<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale"/>

<fmt:message key="error_tech_come_later" var="locale_error_come_later"/>
<fmt:message key="error_tech_problems" var="locale_error_tech_problems"/>
<fmt:message key="error_head" var="locale_error_head"/>
<fmt:message key="error_error" var="locale_error_error"/>


<html>
<head>
    <title>QUICKPAY: ${locale_error_head}!</title>
</head>
<body>
<jsp:include page="WEB-INF/header.jsp" />
<div class="container bg-dark py-2 mt-5 label_window">
    <h3 class="mb-0 ml-3" style="color: white">${locale_error_error}!</h3>
</div>

<div class="container payment_window mb-5 pt-3 pb-5">
    <div class="container mt-5">
        <h1 class="ml-5">${locale_error_head}!</h1>
        <h2 class="mt-5">${locale_error_tech_problems}</h2>
        <h2 class="mt-1">${locale_error_come_later}</h2>
        <p>${exception.getMessage()}</p>
        <p>${exception.printStackTrace()}</p>
    </div>
</div>

<jsp:include page="WEB-INF/footer.jsp"/>
</body>
</html>
