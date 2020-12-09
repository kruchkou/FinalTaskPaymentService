<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale"/>

<fmt:message key="pay.select_account" var="locale_pay_select_account"/>
<fmt:message key="account" var="locale_account"/>
<fmt:message key="account.active_account" var="locale_active_account"/>
<fmt:message key="account.balance" var="locale_account_balace"/>
<fmt:message key="select" var="locale_select"/>


<form id="cardForm" action="Controller" method="post">
    <input type="hidden" name="command" value="${next_command}"/>
</form>
<div class="container mt-3">
    <h1>${locale_pay_select_account.toUpperCase()}</h1>
    <div class="progress">
        <div class="progress-bar bg-info progress-bar-striped progress-bar-animated" style="width: 33%" role="progressbar" aria-valuenow="1" aria-valuemin="0" aria-valuemax="3"></div>
    </div>

    <c:forEach items="${accounts}" var="account">
        <div class="card">
            <div class="card-header">
                ${locale_active_account}
            </div>
            <div class="card-body center_box">
                <div class="d-flex">
                    <p class="mb-0 mr-3">${locale_account} №: ${account.id}</p>
                        <%--                    Когда сделаешь бины нормальные сделай баланс остаточный на счету, привязанному к карте--%>
                    <p class="mb-0 mr-3">${locale_account_balace}: ${account.balance} BYN</p>
                </div>
                <button form="cardForm" name="accountFromID" type="submit" class="btn btn-info ml-auto mr-2"
                        value="${account.id}">${locale_select}
                </button>
            </div>
        </div>
    </c:forEach>
    <%--    if pay.status (в статусбаре та же переменка) = 2--%>


</div>