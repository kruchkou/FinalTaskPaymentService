<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>QUICKPAY: Org</title>
</head>

<div class="container mt-3">
    <h1>ОРГАНИЗАЦИЯ: УПРАВЛЕНИЕ</h1>
    <div class="card-group">
        <div class="card">
            <div class="card-header">
                Данные:
            </div>
            <div class="card-body display_block">
                <div>
                </div>
                <div>
                    <p class="card-text m-0">${organization.name}</p>
                </div>
                <p class="card-text mt-3 m-0">Статус:
                    <c:if test="${organization.status.id == 1}">
                        активна
                    </c:if>
                    <c:if test="${organization.status.id == 2}">
                        заблокирована
                    </c:if>
                </p>
                <div>
                    <p class="card-text mt-3 m-0">Привязанный счёт: </p>
                    <c:choose>
                        <c:when test="${accountInfo != null}">
                            <p class="card-text m-0">Cчёт №: ${accountInfo.id}</p>
                            <p class="card-text m-0">Владелец: ${accountInfo.userName} ${accountInfo.userSurname} ${accountInfo.userPatronymic}</p>
                            <c:if test="${accountInfo.status.id == 1}">
                                <p class="card-text m-0">Статус: активный</p>
                            </c:if>
                            <c:if test="${accountInfo.status.id == 2}">
                                <p class="card-text m-0">Статус: заблокирован</p>
                            </c:if>
                            <c:if test="${accountInfo.status.id == 3}">
                                <p class="card-text m-0">Статус: удалён</p>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <p class="card-text m-0">не привязан</p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        <div class="card">
            <div class="card-header">
                Управление:
            </div>
            <div class="card-body center_box btn-group-vertical">
                <form class="fullfill mb-0" id="accountControl" action="Controller" method="post">
                    <input type="hidden" name="orgID" value="${organization.id}"/>
                    <input type="hidden" name="accountID" value="${accountInfo.id}"/>
                    <button name="command" type="submit" class="btn btn-primary control-button fullfill"
                            value="go_to_admin_account_command">Управление счётом
                    </button>
                    <button name="command" type="submit" class="btn btn-info control-button fullfill"
                            value="go_to_edit_org_command">Редактировать организацию
                    </button>
                    <c:if test="${organization.status.id == 1}">
                        <button name="command" type="submit" class="btn btn-warning control-button fullfill"
                                value="block_org_command">Заблокировать организацию
                        </button>
                    </c:if>
                    <c:if test="${organization.status.id == 2}">
                        <button name="command" type="submit" class="btn btn-success control-button fullfill"
                                value="unlock_org_command">Разблокировать организацию
                        </button>
                    </c:if>
                    <button name="command" type="submit" class="btn btn-danger control-button fullfill"
                            value="delete_org_command">Удалить организацию
                    </button>
                </form>
            </div>
        </div>
    </div>
    <c:if test="${message != null}">
        <div class="mt-2">
            <p class="message_label">${message}</p>
        </div>
    </c:if>
</div>