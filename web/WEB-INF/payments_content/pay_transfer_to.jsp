<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale"/>


<fmt:message key="admin_panel" var="locale_admin_panel"/>
<fmt:message key="pay.select_account" var="locale_select_account"/>
<fmt:message key="account" var="locale_account"/>
<fmt:message key="select" var="locale_select"/>
<fmt:message key="search_results" var="locale_search_results"/>
<fmt:message key="search" var="locale_search"/>
<fmt:message key="is_missing" var="locale_is_missing"/>

<fmt:message key="admin.input_account_num" var="locale_input_account_num"/>
<fmt:message key="owner" var="locale_owner"/>


<form id="searchAccount" action="Controller" method="post">
    <input type="hidden" name="accountFromID" value="${accountFromID}">
    <input type="hidden" name="command" value="go_to_pay_transfer_to_command">
</form>

<form id="selectAccount" action="Controller" method="post">
    <input type="hidden" name="accountFromID" value="${accountFromID}">
    <input type="hidden" name="command" value="go_to_pay_transfer_confirm_command">
</form>

<div class="container mt-3">
    <h1>${locale_select_account.toUpperCase()}</h1>
    <div class="progress">
        <div class="progress-bar bg-info" style="width: 33%" role="progressbar"
             aria-valuenow="1" aria-valuemin="0" aria-valuemax="3"></div>
        <div class="progress-bar bg-info progress-bar-striped progress-bar-animated" style="width: 33%"
             role="progressbar"
             aria-valuenow="1" aria-valuemin="0" aria-valuemax="3"></div>
    </div>

    <div class="card mb-2">
        <div class="card-body d-flex">
            <input form="searchAccount" style="width: 90%" type="number" class="form-control" name="searchID" placeholder="${locale_input_account_num}" value="${searchID}" required>
            <input form="searchAccount" type="submit" class="btn ml-auto btn-success" value="${locale_search}">
        </div>
    </div>

    <c:if test="${searchID != null}">
        <c:if test="${accountInfoList.size() == 0}">
            <p style="font-size: xx-large; text-align: center" class="mb-0 mt-2">${locale_search_results} "${searchID}"
                ${locale_is_missing}</p>
        </c:if>
    </c:if>

    <c:forEach items="${accountInfoList}" var="accountInfo">
        <div class="card">
            <div class="card-body center_box" style="padding: 10px">
                <div>
                    <p class="mx-3 my-0">${locale_account} №: ${accountInfo.id}</p>
                </div>
                <div>
                    <a class="mx-3 my-0">${locale_owner}: ${accountInfo.userSurname} ${accountInfo.userName} ${accountInfo.userPatronymic}</a>
                </div>
                <button form="selectAccount" name="accountToID" type="submit" class="btn btn-info ml-auto mr-2" value="${accountInfo.id}">${locale_select}</button>
            </div>
        </div>
    </c:forEach>
</div>
