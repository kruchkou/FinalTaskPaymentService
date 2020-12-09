<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale"/>

<fmt:message key="payments.payment_history" var="locale_payment_history"/>
<fmt:message key="payments.incoming_payments" var="locale_payment_incoming_payments"/>
<fmt:message key="payments.outcoming_payments" var="locale_payment_outcoming_payments"/>
<fmt:message key="payments.no_payments" var="locale_payment_no_payments"/>
<fmt:message key="payments.payment" var="locale_payment"/>
<fmt:message key="account" var="locale_account"/>
<fmt:message key="payments.account_from" var="locale_payments_accounts_from"/>
<fmt:message key="payments.account_to" var="locale_payments_accounts_to"/>
<fmt:message key="date" var="locale_date"/>
<fmt:message key="payments.amount" var="locale_payment_amount"/>
<fmt:message key="payments.comment" var="locale_payment_comment"/>


<div class="container mt-3">
    <div style="flex-direction: column;">
        <h1>${locale_payment_history.toUpperCase()}</h1>
        <h5 class="fullfill">${payment_view_scope}</h5>
    </div>
    <div class="card-group">
        <div class="card">
            <div class="card">
                <div class="card-header">
                    ${locale_payment_incoming_payments}:
                </div>
                <c:choose>
                    <c:when test="${inPayments.size() == 0}">
                        <div class="card-body display_block text-center">
                            <div>
                                <p class="card-text m-0">${locale_payment_no_payments}</p>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${inPayments}" var="payment">
                            <div class="card-body display_block">
                                <div>
                                    <p class="card-text m-0">${locale_payment}: <c:out value="${payment.getId()}"/></p>
                                </div>
                                <div>
                                    <p class="card-text m-0">${locale_account}: <c:out value="${payment.getAccountFrom()}"/></p>
                                </div>
                                <div>
                                    <p class="card-text m-0">${locale_payments_accounts_to}: <c:out
                                            value="${payment.getAccountTo()}"/></p>
                                </div>
                                <div>
                                    <p class="card-text m-0">${locale_date}: <c:out
                                            value="${payment.getDatetime()}"/></p>
                                </div>
                                <div>
                                    <p class="card-text m-0">${locale_payment_amount}: <c:out
                                            value="${payment.getAmount()}"/> BYN</p>
                                </div>
                                <div>
                                    <p class="card-text m-0">${locale_payment_comment}: <c:out
                                            value="${payment.getComment()}"/></p>
                                </div>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <div class="card">
            <div class="card">
                <div class="card-header">
                    ${locale_payment_outcoming_payments}:
                </div>
                <c:choose>
                    <c:when test="${inPayments.size() == 0}">
                        <div class="card-body display_block text-center">
                            <div>
                                <p class="card-text m-0">${locale_payment_no_payments}</p>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${outPayments}" var="payment">
                            <div class="card-body display_block">
                                <div>
                                    <p class="card-text m-0">${locale_payment_no_payments}: <c:out value="${payment.getId()}"/></p>
                                </div>
                                <div>
                                    <p class="card-text m-0">${locale_payments_accounts_from}: <c:out value="${payment.getAccountFrom()}"/></p>
                                </div>
                                <div>
                                    <p class="card-text m-0">${locale_payments_accounts_to}: <c:out value="${payment.getAccountTo()}"/></p>
                                </div>
                                <div>
                                    <p class="card-text m-0">${locale_date}: <c:out value="${payment.getDatetime()}"/></p>
                                </div>
                                <div>
                                    <p class="card-text m-0">${locale_payment_amount}: <c:out value="${payment.getAmount()} BYN"/></p>
                                </div>
                                <div>
                                    <p class="card-text m-0">${locale_payment_comment}: <c:out value="${payment.getComment()}"/></p>
                                </div>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

</div>