<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form id="searchOrg" action="Controller" method="post">
    <input type="hidden" name="command" value="go_to_pay_select_org_command">
    <input type="hidden" name="accountFromID" value="${accountFromID}">
</form>

<form id="selectOrg" action="Controller" method="post">
    <%--    Ващета другая команда--%>
    <input type="hidden" name="command" value="go_to_payment_confirm_command">
    <input type="hidden" name="accountFromID" value="${accountFromID}">
</form>

<div class="container mt-3">
    <h1>ВЫБРАТЬ ОРГАНИЗАЦИЮ</h1>
    <div class="progress">
        <div class="progress-bar bg-info" style="width: 33%" role="progressbar"
             aria-valuenow="1" aria-valuemin="0" aria-valuemax="3"></div>
        <div class="progress-bar bg-info progress-bar-striped progress-bar-animated" style="width: 33%"
             role="progressbar"
             aria-valuenow="1" aria-valuemin="0" aria-valuemax="3"></div>
    </div>

        <div class="card my-2">
            <div class="card-body d-flex">
                <input form="searchOrg" style="width: 90%" type="text" class="form-control" name="searchName" placeholder="Введите название организации" value="${searchName}">
                <input form="searchOrg" type="submit" class="btn ml-auto btn-success" value="ПОИСК">
            </div>
        </div>

    <c:if test="${searchName != null}">
        <c:if test="${organizations.size() == 0}">
            <p style="font-size: xx-large; text-align: center" class="mb-0 mt-2">Результаты поиска "${searchName}"
                отсутсвуют</p>
        </c:if>
    </c:if>

        <c:forEach items="${organizations}" var="organization">
            <div class="card">
                <div class="card-body center_box" style="padding: 10px">
                    <div>
                        <p class="mx-3 my-0">${organization.name}</p>
                    </div>
                    <button form="selectOrg" name="orgToID" type="submit" class="btn btn-info ml-auto mr-2" value="${organization.id}">Выбрать</button>
                </div>
            </div>
        </c:forEach>
    </div>
</div>