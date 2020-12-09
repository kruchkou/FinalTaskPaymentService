<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale"/>

<fmt:message key="user_panel" var="user_panel"/>
<fmt:message key="payment.my_accounts" var="my_accounts"/>
<fmt:message key="payment.my_cards" var="my_cards"/>
<fmt:message key="payment.my_payments" var="my_payments"/>
<fmt:message key="payment.pay" var="pay"/>
<fmt:message key="payment.transfer" var="transfer"/>
<body>
<%--<%@include file="header.jsp"%>--%>
<jsp:include page="header.jsp"/>


<html>
<head>
    <link href="css/login.css" rel="stylesheet" type="text/css">
    <title>QUICKPAY</title>
</head>
<div class="container bg-dark py-2 mt-5 label_window">
    <h3 class="mb-0 ml-3" style="color: white">${user_panel.toUpperCase()}</h3>
</div>

<div class="container payment_window mb-5 pt-3 pb-5">

    <div class="row justify-content-md-center">
        <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
            <div class="btn-group mr-5" role="group" aria-label="First group">
                <a type="button" class="btn btn-secondary"
                   href="Controller?command=go_to_accounts_command">${my_accounts}</a>
                <a type="button" class="btn btn-secondary" href="Controller?command=go_to_cards_command">${my_cards}</a>
                <a type="button" class="btn btn-secondary"
                   href="Controller?command=go_to_user_history_command">${my_payments}</a>
            </div>
            <div class="btn-group" role="group" aria-label="Second group">
                <a type="button" class="btn btn-secondary"
                   href="Controller?command=go_to_pay_select_account_command&next_command=go_to_pay_select_org_command">${pay}</a>
                <a type="button" class="btn btn-secondary"
                   href="Controller?command=go_to_pay_select_account_command&next_command=go_to_pay_transfer_to_command">${transfer}</a>
            </div>
        </div>
    </div>
    <jsp:include page="${payments_content}"/>
</div>

<jsp:include page="footer.jsp"/>

</body>
</html>
