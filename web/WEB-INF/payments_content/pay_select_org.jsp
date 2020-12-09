<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale"/>

<fmt:message key="pay.select_org" var="locale_pay_select_org"/>
<fmt:message key="admin.input_org_name" var="locale_input_org_name"/>
<fmt:message key="search" var="locale_search"/>
<fmt:message key="select" var="locale_select"/>
<fmt:message key="search_results" var="locale_search_results"/>
<fmt:message key="is_missing" var="locale_is_missing"/>


<form id="searchOrg" action="Controller" method="post">
    <input type="hidden" name="command" value="go_to_pay_select_org_command">
    <input type="hidden" name="accountFromID" value="${accountFromID}">
</form>

<form id="selectOrg" action="Controller" method="post">
    <%--    Ващета другая команда--%>
    <input type="hidden" name="command" value="go_to_payment_confirm_command">
    <input type="hidden" name="accountFromID" value="${accountFromID}">
</form>

<div class="container mt-3">
    <h1>${locale_pay_select_org.toUpperCase()}</h1>
    <div class="progress">
        <div class="progress-bar bg-info" style="width: 33%" role="progressbar"
             aria-valuenow="1" aria-valuemin="0" aria-valuemax="3"></div>
        <div class="progress-bar bg-info progress-bar-striped progress-bar-animated" style="width: 33%"
             role="progressbar"
             aria-valuenow="1" aria-valuemin="0" aria-valuemax="3"></div>
    </div>

        <div class="card mb-2">
            <div class="card-body d-flex">
                <input form="searchOrg" style="width: 90%" type="text" class="form-control" name="searchName" placeholder="${locale_input_org_name}" value="${searchName}" required>
                <input form="searchOrg" type="submit" class="btn ml-auto btn-success" value="${locale_search}">
            </div>
        </div>

    <c:if test="${searchName != null}">
        <c:if test="${organizations.size() == 0}">
            <p style="font-size: xx-large; text-align: center" class="mb-0 mt-2">${locale_search_results} "${searchName}"
                ${locale_is_missing}</p>
        </c:if>
    </c:if>

        <c:forEach items="${organizations}" var="organization">
            <div class="card">
                <div class="card-body center_box" style="padding: 10px">
                    <div>
                        <p class="mx-3 my-0">${organization.name}</p>
                    </div>
                    <button form="selectOrg" name="orgToID" type="submit" class="btn btn-info ml-auto mr-2" value="${organization.id}">${locale_select}</button>
                </div>
            </div>
        </c:forEach>
    </div>
</div>