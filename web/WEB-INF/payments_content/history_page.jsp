<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>QUICKPAY: History</title>
</head>
<div class="container mt-3">
    <div style="flex-direction: column;">
        <h1>История платежей</h1>
        <h5 class="fullfill">${payment_view_scope}</h5>
    </div>
    <div class="card-group">
        <div class="card">
            <div class="card-header">
                Входящие транзакции:
            </div>
            <c:forEach items="${inPayments}" var="payment">
                <div class="card-body display_block">
                    <div>
                        <p class="card-text m-0">№ транзакции: <c:out value="${payment.getId()}"/></p>
                    </div>
                    <div>
                        <p class="card-text m-0">Со счёта: <c:out value="${payment.getAccountFrom()}"/></p>
                    </div>
                    <div>
                        <p class="card-text m-0">На счет: <c:out
                                value="${payment.getAccountTo()}"/></p>
                    </div>
                    <div>
                        <p class="card-text m-0">Дата: <c:out
                                value="${payment.getDatetime()}"/></p>
                    </div>
                    <div>
                        <p class="card-text m-0">Сумма: <c:out
                                value="${payment.getAmount()}"/> BYN</p>
                    </div>
                    <div>
                        <p class="card-text m-0">Комментарий: <c:out
                                value="${payment.getComment()}"/></p>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div class="card">
            <div class="card-header">
                Исходящие транзакции:
            </div>
            <c:forEach items="${outPayments}" var="payment">
                <div class="card-body display_block">
                    <div>
                        <p class="card-text m-0">№ транзакции: <c:out value="${payment.getId()}"/></p>
                    </div>
                    <div>
                        <p class="card-text m-0">Со счёта: <c:out value="${payment.getAccountFrom()}"/></p>
                    </div>
                    <div>
                        <p class="card-text m-0">На счет: <c:out value="${payment.getAccountTo()}"/></p>
                    </div>
                    <div>
                        <p class="card-text m-0">Дата: <c:out value="${payment.getDatetime()}"/></p>
                    </div>
                    <div>
                        <p class="card-text m-0">Сумма: <c:out value="${payment.getAmount()} BYN"/></p>
                    </div>
                    <div>
                        <p class="card-text m-0">Комментарий: <c:out value="${payment.getComment()}"/></p>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

</div>