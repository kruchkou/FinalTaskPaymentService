<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale"/>


<fmt:message key="account.control" var="locale_account_control"/>
<fmt:message key="account" var="locale_account"/>
<fmt:message key="data" var="locale_data"/>
<fmt:message key="status" var="locale_status"/>
<fmt:message key="account.status_active" var="locale_status_active"/>
<fmt:message key="account.status_blocked" var="locale_status_blocked"/>
<fmt:message key="account.balance" var="locale_balance"/>
<fmt:message key="account.creation_date" var="locale_creation_date"/>
<fmt:message key="card.add" var="locale_add_card"/>
<fmt:message key="control" var="locale_control"/>
<fmt:message key="operations_history" var="locale_operations_history"/>
<fmt:message key="account.top_up" var="locale_account_top_up"/>
<fmt:message key="account.block" var="locale_account_block"/>
<fmt:message key="account.delete" var="locale_account_delete"/>


<div class="container mt-3">
        <h1>${locale_account_control.toUpperCase()}</h1>
    <div class="card-group">
        <div class="card">
            <div class="card-header">
                ${locale_data}:
            </div>
            <div class="card-body display_block">
                <div>
                    <p class="card-text m-0">${locale_status}:
                        <c:if test="${account.status.id == 1}">
                            ${locale_status_active}
                        </c:if>
                        <c:if test="${account.status.id == 2}">
                            ${locale_status_blocked}
                        </c:if>
                    </p>
                </div>
                <div>
                    <p class="card-text m-0">${locale_account} №: <c:out value="${account.getId()}"/></p>
                </div>
                <div>
                    <p class="card-text m-0">${locale_balance}: <c:out value="${account.getBalance()}"/> BYN</p>
                </div>
                <div>
                    <p class="card-text m-0">${locale_creation_date}: <c:out
                            value="${account.getCreationDate()}"/></p>
                </div>
            </div>
        </div>
        <div class="card">
            <div class="card-header">
                ${locale_control}:
            </div>
            <div class="card-body center_box btn-group-vertical">
                <form class="fullfill mb-0" id="accountControl" action="Controller" method="post">
                    <input type="hidden" name="accountID" value="${account.getId()}"/>
                    <div>
                        <button name="command" type="submit" class="btn btn-primary control-button fullfill"
                                value="go_to_account_history_command">${locale_operations_history}
                        </button>
                    </div>

                    <c:if test="${account.status.id == 1}">
                        <div>
                            <button name="command" type="submit" class="btn btn-info control-button fullfill"
                                    value="go_to_add_card_command">${locale_add_card}
                            </button>
                        </div>
                        <div>
                            <button name="command" type="submit" class="btn btn-success control-button fullfill"
                                    value="go_to_pay_select_card_command">${locale_account_top_up}
                            </button>
                        </div>
                        <div>
                            <button name="command" type="submit" class="btn btn-warning control-button fullfill"
                                    value="block_account_command">${locale_account_block}
                            </button>
                        </div>
                    </c:if>
                    <div>
                        <button name="command" type="submit" class="btn btn-danger control-button fullfill"
                                value="delete_account_command">${locale_account_delete}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<c:if test="${cards.size()>0}">
    <jsp:include page="user_cards.jsp"/>
</c:if>