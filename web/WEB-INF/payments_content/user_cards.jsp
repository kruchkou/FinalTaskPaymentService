<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/tld/custom_tags.tld" prefix="mytag" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale"/>

<fmt:message key="control" var="locale_control"/>
<fmt:message key="account" var="locale_account"/>

<fmt:message key="card.cards" var="locale_cards"/>
<fmt:message key="card.active_card" var="locale_active_card"/>
<fmt:message key="num" var="locale_num"/>

<div class="container mt-3">
    <h1>${locale_cards.toUpperCase()}</h1>
    <form id="cardForm" action="Controller" method="post">
        <input type="hidden" name="command" value="go_to_card_command"/>
    </form>

    <c:forEach items="${cards}" var="card">

        <div class="card">
            <div class="card-header">
                ${locale_active_card}
            </div>
            <div class="card-body center_box">
                <div>
                    <p style="margin: 0">${locale_num}: <mytag:cardNumberTag cardNumber="${card.number}"/></p>
                    <p style="margin: 0">${locale_account} №: <c:out value="${card.getAccount()}"/></p>
                </div>
                <button form="cardForm" name="cardID" type="submit" class="btn btn-info ml-auto mr-2" value="${card.getId()}">${locale_control}</button>
            </div>
        </div>
    </c:forEach>
</div>