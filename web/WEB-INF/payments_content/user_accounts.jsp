<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>My accounts</title>
</head>
<div class="container mt-3">

    <div class="d-flex account-header">
            <h1>СЧЕТА</h1>
        <a class="btn btn-warning ml-auto my-auto" href="Controller?command=add_account_command">Создать счет</a>
    </div>
    <form id="accountForm" action="Controller" method="post">
        <input type="hidden" name="command" value="go_to_account_command"/>
    </form>

    <c:forEach items="${accounts}" var="account">

        <div class="card">
            <div class="card-header">
                    <%--                уточнить про хешмапу (я думаю тут нужны бины)--%>
                <c:if test="${account.getStatus().keySet().toArray()[0] == 1}">
                    Активный счет
                </c:if>
                <c:if test="${account.getStatus().keySet().toArray()[0] == 2}">
                    Заблокированный счет
                </c:if>
                №: ${account.getId()}
            </div>
            <div class="card-body center_box">
                <div>
                    <p style="margin: 0">Баланс: <c:out value="${account.getBalance()}"/> BYN</p>
                </div>
                <button form="accountForm" name="accountID" type="submit" class="btn btn-info ml-auto mr-2"
                        value="${account.getId()}">Управление
                </button>
            </div>
        </div>
    </c:forEach>
</div>