<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>My accounts</title>
</head>
<div class="container mt-3">
    <h1>КАРТЫ</h1>
    <form id="cardForm" action="Controller" method="post">
        <input type="hidden" name="command" value="go_to_card_command"/>
    </form>

    <c:forEach items="${cards}" var="card">

        <div class="card">
            <div class="card-header">
                Активная карта:
            </div>
            <div class="card-body center_box">
                <div>
                    <p style="margin: 0">Номер: <c:out value="${card.getNumber()}"/></p>
                    <p style="margin: 0">Счет №: <c:out value="${card.getAccount()}"/></p>
                </div>
                <button form="cardForm" name="cardID" type="submit" class="btn btn-info ml-auto mr-2" value="${card.getId()}">Управление</button>
            </div>
        </div>
    </c:forEach>
</div>