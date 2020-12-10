<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale"/>

<fmt:message key="about_service" var="locale_about_service"/>
<fmt:message key="about_desc" var="locale_about_desc"/>
<fmt:message key="instant_payments" var="locale_instant_payments"/>
<fmt:message key="instant_payments_desc" var="locale_instant_payments_desc"/>
<fmt:message key="eazy_top_up" var="locale_eazy_top_up"/>
<fmt:message key="eazy_top_up_desc" var="locale_eazy_top_up_desc"/>
<fmt:message key="no_coms" var="locale_no_coms"/>
<fmt:message key="no_coms_desc" var="locale_no_coms_desc"/>


<html>
<head>
    <title>QUICKPAY: ${locale_about_service}</title>
    <link href="css/core.css" rel="stylesheet" type="text/css">

</head>
<body>
<jsp:include page="WEB-INF/header.jsp"/>

<div class="container bg-dark py-2 mt-5 label_window">
    <h3 class="mb-0 ml-3" style="color: white">${locale_about_service.toUpperCase()}</h3>
</div>

<div class="container payment_window mb-5 pt-3 pb-3">

    <div class="mx-3 justify-content-md-center">


        <h3 style="text-align: center">${locale_about_desc}</h3>

        <div class="card mt-3">
            <div class="card-header">
                ${locale_instant_payments}
            </div>
            <div class="card-body">
                ${locale_instant_payments_desc}
            </div>
        </div>

        <div class="card mt-3">
            <div class="card-header">
                ${locale_eazy_top_up}
            </div>
            <div class="card-body">
                ${locale_eazy_top_up_desc}
            </div>
        </div>

        <div class="card mt-3">
            <div class="card-header">
                ${locale_no_coms}
            </div>
            <div class="card-body">
                ${locale_no_coms_desc}
            </div>
        </div>

        <form class="center" action="Controller" method="post">
            <input type="hidden" name="command" value="go_to_sign_in_command"/>
            <button class="mt-4 index_login_button btn btn-success" type="submit">ВХОД</button>
        </form>
    </div>

</div>

<jsp:include page="WEB-INF/footer.jsp"/>
</body>
</html>