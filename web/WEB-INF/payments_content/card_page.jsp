<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>QUICKPAY: CARD</title>
</head>
<div class="container mt-3">
    <h1>КАРТА: УПРАВЛЕНИЕ</h1>

    <div class="card-group">
        <div class="card">
            <div class="card-header">
                Данные:
            </div>
            <div class="card-body display_block">
                    <p class="card-text m-0">Номер: <c:out value="${card.getNumber()}"/></p>
                    <p class="card-text m-0">Владелец: <c:out value="${card.getOwnerName()}"/></p>
                    <p class="card-text m-0">Счет №: <c:out value="${card.getAccount()}"/></p>
            </div>
        </div>
        <div class="card">
            <div class="card-header">
                Управление:
            </div>
            <div class="card-body center_box btn-group-vertical">
                <form class="fullfill mb-0" id="accountControl" action="Controller" method="post">
                    <input type="hidden" name="accountID" value="${card.getId()}"/>
                    <div>
                        <button name="command" type="submit" class="btn btn-primary control-button fullfill"
                                value="go_to_card_history_command">История операций
                        </button>
                    </div>
                    <div>
                        <button name="command" type="submit" class="btn btn-danger control-button fullfill"
                                value="delete_card_command">Удалить карту
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>