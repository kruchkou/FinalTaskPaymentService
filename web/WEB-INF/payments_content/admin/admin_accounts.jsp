<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form id="searchAccount" action="Controller" method="post">
    <input type="hidden" name="command" value="go_to_admin_accounts_command">
</form>

<form id="selectAccount" action="Controller" method="post">
    <input type="hidden" name="command" value="go_to_admin_account_command">
</form>

<div class="container mt-3">
    <h1>ВЫБРАТЬ СЧЁТ</h1>

    <div class="card my-2">
        <div class="card-body d-flex">
            <input form="searchAccount" style="width: 90%" type="number" class="form-control" name="searchID" placeholder="Введите № счёта" value="${searchID}">
            <input form="searchAccount" type="submit" class="btn ml-auto btn-success" value="ПОИСК">
        </div>
    </div>

    <c:if test="${searchID != null}">
        <c:if test="${accountInfoList.size() == 0}">
            <p style="font-size: xx-large; text-align: center" class="mb-0 mt-2">Результаты поиска "${searchID}"
                отсутсвуют</p>
        </c:if>
    </c:if>

    <c:forEach items="${accountInfoList}" var="accountInfo">
        <div class="card">
            <div class="card-body center_box" style="padding: 10px">
                <div>
                    <p class="mx-3 my-0">№ счёта: ${accountInfo.id}</p>
                </div>
                <div>
                    <a class="mx-3 my-0">Владелец: ${accountInfo.userSurname} ${accountInfo.userName} ${accountInfo.userPatronymic}</a>
                </div>
                <button form="selectAccount" name="accountID" type="submit" class="btn btn-info ml-auto mr-2" value="${accountInfo.id}">Выбрать</button>
            </div>
        </div>
    </c:forEach>
</div>
