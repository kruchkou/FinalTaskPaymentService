<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>QUICKPAY: Пользователь</title>
</head>
<div class="container mt-3">
    <h1>ПОЛЬЗОВАТЕЛЬ: УПРАВЛЕНИЕ</h1>
    <div class="card-group">
        <div class="card">
            <div class="card-header">
                Данные:
            </div>
            <div class="card-body display_block">
                <div>
                    <p class="card-text m-0">Пользователь:
                        ${user.surname} ${user.name} ${user.patronymic}
                    </p>
                </div>
                <div>Статус:
                    <c:if test="${user.status.id == 1}">
                        пользователь
                    </c:if>
                    <c:if test="${user.status.id == 2}">
                        администратор
                    </c:if>
                    <p class="card-text m-0">Дата рождения: ${user.birthDate}</p>
                </div>
                <div>
                    <p class="card-text m-0">Номер телефона: ${user.phoneNumber}</p>
                </div>
            </div>
        </div>
        <div class="card">
            <div class="card-header">
                Управление:
            </div>
            <div class="card-body center_box btn-group-vertical">
                <form class="fullfill mb-0" id="accountControl" action="Controller" method="post">
                    <input type="hidden" name="userID" value="${user.getId()}"/>

                        <button name="command" type="submit" class="btn btn-primary control-button fullfill"
                                value="go_to_admin_user_accounts_command">Счета пользователя
                        </button>
                    <c:if test="${user.status.id == 1}">
                        <button name="command" type="submit" class="btn btn-info control-button fullfill"
                                value="grand_admin_rights_command">Сделать администратором
                        </button>
                    </c:if>
                    <c:if test="${user.status.id == 2}">
                        <button name="command" type="submit" class="btn btn-info control-button fullfill"
                                value="revoke_admin_rights_command">Сделать пользователем
                        </button>
                    </c:if>
                    <button name="command" type="submit" class="btn btn-success control-button fullfill"
                            value="admin_unlock_user_accounts_command">Разблокировать все счета
                    </button>
                        <button name="command" type="submit" class="btn btn-warning control-button fullfill"
                                value="admin_lock_user_accounts_command">Заблокировать все счета
                        </button>
                </form>
            </div>
        </div>
    </div>
</div>