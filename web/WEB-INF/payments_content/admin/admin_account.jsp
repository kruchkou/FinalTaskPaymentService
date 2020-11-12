<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>QUICKPAY: Account</title>
</head>
<div class="container mt-3">
    <h1>СЧЕТ: УПРАВЛЕНИЕ</h1>
    <div class="card-group">
        <div class="card">
            <div class="card-header">
                Данные:
            </div>
            <div class="card-body display_block">
                <div>
                    <p class="card-text m-0">Статус:
                        <c:if test="${account.getStatus().keySet().toArray()[0] == 1}">
                            активен
                        </c:if>
                        <c:if test="${account.getStatus().keySet().toArray()[0] == 2}">
                            заблокирован
                        </c:if>
                        <c:if test="${account.getStatus().keySet().toArray()[0] == 3}">
                            удалён
                        </c:if>
                    </p>
                </div>
                <div>
                    <p class="card-text m-0">Счет №: <c:out value="${account.getId()}"/></p>
                </div>
                <div>
                    <p class="card-text m-0">Дата создания: <c:out
                            value="${account.getCreationDate()}"/></p>
                </div>
            </div>
        </div>
        <div class="card">
            <div class="card-header">
                Управление:
            </div>
            <div class="card-body center_box btn-group-vertical">
                <form class="fullfill mb-0" id="accountControl" action="Controller" method="post">
                    <input type="hidden" name="accountID" value="${account.getId()}"/>
                    <input type="hidden" name="userID" value="${account.user}"/>
                    <button name="command" type="submit" class="btn btn-info control-button fullfill"
                            value="go_to_admin_user_command">Владелец
                    </button>
                    <c:if test="${account.getStatus().keySet().toArray()[0] == 1}">
                            <button name="command" type="submit" class="btn btn-warning control-button fullfill"
                                    value="block_account_command">Заблокировать счет
                            </button>
                    </c:if>
                    <c:if test="${account.getStatus().keySet().toArray()[0] == 2}">
                        <button name="command" type="submit" class="btn btn-success control-button fullfill"
                                value="unlock_account_command">Разблокировать счет
                        </button>
                    </c:if>
                </form>
            </div>
        </div>
    </div>
</div>