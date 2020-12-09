<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/tld/custom_tags.tld" prefix="mytag" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale"/>


<fmt:message key="card.control" var="locale_card_control"/>
<fmt:message key="account" var="locale_account"/>
<fmt:message key="data" var="locale_data"/>
<fmt:message key="num" var="locale_num"/>
<fmt:message key="owner" var="locale_owner"/>
<fmt:message key="card.delete_card" var="locale_card_delete"/>
<fmt:message key="go_to_account" var="locale_go_to_account"/>


<div class="container mt-3">
        <h1>${locale_card_control.toUpperCase()}</h1>
    <div class="card-group">
        <div class="card">
            <div class="card-header">
                ${locale_data}:
            </div>
            <div class="card-body display_block">
                    <p class="card-text m-0">${locale_num}: <mytag:cardNumberTag cardNumber="${card.number}"/></p>
                    <p class="card-text m-0">${locale_owner}: <c:out value="${card.getOwnerName()}"/></p>
                    <p class="card-text m-0">${locale_account} №: <c:out value="${card.getAccount()}"/></p>
            </div>
        </div>
        <div class="card">
            <div class="card-header">
                ${locale_control}:
            </div>
            <div class="card-body center_box btn-group-vertical">
                <form class="fullfill mb-0" id="accountControl" action="Controller" method="post">
                    <input type="hidden" name="cardID" value="${card.getId()}"/>
                    <input type="hidden" name="accountID" value="${card.getAccount()}"/>
                    <div>
                        <button name="command" type="submit" class="btn btn-info control-button fullfill"
                                value="go_to_account_command">${locale_go_to_account}
                        </button>
                    </div>
                    <div>
                        <button name="command" type="submit" class="btn btn-danger control-button fullfill"
                                value="delete_card_command">${locale_card_delete}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>