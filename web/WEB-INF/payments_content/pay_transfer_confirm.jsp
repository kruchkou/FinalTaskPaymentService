<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale"/>

<fmt:message key="account" var="locale_account"/>
<fmt:message key="account.balance" var="locale_account_balance"/>
<fmt:message key="pay.up_to" var="locale_pay_up_to"/>
<fmt:message key="pay.optional" var="locale_pay_optional"/>
<fmt:message key="pay.receiver" var="locale_pay_receiver"/>
<fmt:message key="pay.input_amount" var="locale_pay_input_amount"/>
<fmt:message key="pay.input_comment" var="locale_pay_input_comment"/>
<fmt:message key="pay.input_password" var="locale_pay_input_password"/>
<fmt:message key="pay.submit_transfer" var="locale_submit_transfer"/>
<fmt:message key="payment.pay" var="locale_payment_pay"/>
<fmt:message key="payments.account_to" var="locale_account_to"/>
<fmt:message key="payments.account_from" var="locale_payments_account_from"/>


<form id="transferForm" action="Controller" method="post">
    <input type="hidden" name="command" value="pay_command">
    <input type="hidden" name="accountFromID" value="${account.id}">
    <input type="hidden" name="accountToID" value="${accountInfo.id}">
</form>

<div class="container mt-3">
    <h1>${locale_submit_transfer.toUpperCase()}</h1>
    <div class="progress">
        <div class="progress-bar bg-info" style="width: 66%"
             role="progressbar" aria-valuenow="1" aria-valuemin="0" aria-valuemax="3"></div>
        <div class="progress-bar bg-info progress-bar-striped progress-bar-animated" style="width: 34%"
             role="progressbar" aria-valuenow="1" aria-valuemin="0" aria-valuemax="3"></div>
    </div>

    <div class="card">
        <div class="card-header">
            ${locale_payments_account_from}:
        </div>
        <div class="card-body center_box">
            <div class="d-flex">
                <p class="mb-0 mr-3">${locale_account} №: ${account.id}</p>
                <p class="mb-0 mr-3">
                    ${locale_account_balance}: ${account.balance} BYN</p>
            </div>
        </div>
    </div>
    <div class="card mt-2">
        <div class="card-header">
            ${locale_account_to}:
        </div>
        <div class="card-body center_box">
            <div class="d-flex">
                <p class="mb-0 mr-3">${locale_account} №: ${accountInfo.id}</p>
                <p class="mb-0 mr-3">
                    ${locale_pay_receiver}: ${accountInfo.userSurname} ${accountInfo.userName} ${accountInfo.userPatronymic}</p>
            </div>
        </div>
    </div>

    <div class="card mt-2">
        <div class="card-body d-flex">
            <label class="my-auto" for="amountInput">${locale_pay_input_amount}, BYN:</label>
            <input form="transferForm" style="width: 30%" type="number" class="mx-3 form-control" name="amount" id="amountInput" required  min="0.01" max="${account.balance}" step="0.01" placeholder="${locale_pay_up_to} ${account.balance} BYN">
        </div>
    </div>

    <div class="card mt-2">
        <div class="card-body d-flex">
            <label class="my-auto" for="commentInput">${locale_pay_input_comment}:</label>
            <input form="transferForm" style="width: 90%" type="text" class="mx-3 form-control" id="commentInput" name="comment" placeholder="${locale_pay_optional}">
        </div>
    </div>

    <div class="card mt-2">
        <div class="card-body d-flex">
            <label class="my-auto" for="passwordInput">${locale_pay_input_password}:</label>
            <input form="transferForm" style="width: 50%" type="password" class="mx-3 form-control" name="password" id="passwordInput" required>
            <input form="transferForm" style="width: 30%" type="submit" class="btn ml-auto btn-success" value="${locale_payment_pay.toUpperCase()}">
        </div>
    </div>

    <c:if test="${message != null}">
        <p style="font-size: xx-large; text-align: center" class="mb-0 mt-2">${message}</p>
    </c:if>
</div>