<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale"/>

<fmt:message key="account.accounts" var="locale_accounts"/>
<fmt:message key="account.create_new" var="locale_create_new"/>
<fmt:message key="account.active_account" var="locale_active_account"/>
<fmt:message key="account.blocked_account" var="locale_blocked_account"/>
<fmt:message key="account.balance" var="locale_balance"/>
<fmt:message key="control" var="locale_control"/>

<div class="container mt-3">

    <div class="d-flex account-header">
            <h1>${locale_accounts.toUpperCase()}</h1>
        <a class="btn btn-warning ml-auto my-auto" href="Controller?command=add_account_command">${locale_create_new}</a>
    </div>
    <form id="accountForm" action="Controller" method="post">
        <input type="hidden" name="command" value="go_to_account_command"/>
    </form>

    <c:forEach items="${accounts}" var="account">

        <div class="card">
            <div class="card-header">

                <c:if test="${account.status.id == 1}">
                    ${locale_active_account}
                </c:if>
                <c:if test="${account.status.id == 2}">
                    ${locale_blocked_account}
                </c:if>
                №: ${account.getId()}
            </div>
            <div class="card-body center_box">
                <div>
                    <p style="margin: 0">${locale_balance}: <c:out value="${account.getBalance()}"/> BYN</p>
                </div>
                <button form="accountForm" name="accountID" type="submit" class="btn btn-info ml-auto mr-2"
                        value="${account.getId()}">${locale_control}
                </button>
            </div>
        </div>
    </c:forEach>
</div>