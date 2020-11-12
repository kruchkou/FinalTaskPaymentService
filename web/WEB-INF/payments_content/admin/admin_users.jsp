<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form id="searchUser" action="Controller" method="post">
    <input type="hidden" name="command" value="go_to_admin_users_command">
</form>

<form id="selectUser" action="Controller" method="post">
    <input type="hidden" name="command" value="go_to_admin_user_command">
</form>

<div class="container mt-3">
    <h1>ВЫБРАТЬ ПОЛЬЗОВАТЕЛЯ</h1>

    <div class="card my-2">
        <div class="card-body d-flex">
            <input form="searchUser" style="width: 90%" type="text" class="form-control" name="searchName" placeholder="Введите ФИО пользователя" value="${searchName}">
            <input form="searchUser" type="submit" class="btn ml-auto btn-success" value="ПОИСК">
        </div>
    </div>

    <c:if test="${searchName != null}">
        <c:if test="${userList.size() == 0}">
            <p style="font-size: xx-large; text-align: center" class="mb-0 mt-2">Результаты поиска "${searchName}"
                отсутсвуют</p>
        </c:if>
    </c:if>

    <c:forEach items="${userList}" var="user">
        <div class="card">
            <div class="card-body center_box" style="padding: 10px">
                <div>
                    <p class="mx-3 my-0">${user.name} ${user.surname} ${user.patronymic}</p>
                </div>
                <div>
                    <p class="mx-3 my-0">Дата рождения: ${user.birthDate}</p>
                </div>
                <div>
                    <p class="mx-3 my-0">Номер телефона: ${user.phoneNumber}</p>
                </div>
                <button form="selectUser" name="userID" type="submit" class="btn btn-info ml-auto mr-2" value="${user.id}">Выбрать</button>
            </div>
        </div>
    </c:forEach>
</div>
</div>