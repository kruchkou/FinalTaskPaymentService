<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form id="searchOrg" action="Controller" method="post">
    <input type="hidden" name="command" value="go_to_admin_orgs_command">
</form>

<form id="selectOrg" action="Controller" method="post">
    <input type="hidden" name="command" value="go_to_admin_org_command">
</form>

<div class="container mt-3">
    <div class="d-flex">
        <h1>ВЫБОР ОРГАНИЗАЦИИ</h1>
        <a class="btn btn-warning ml-auto my-auto" href="Controller?command=go_to_add_org_command">Создать организацию</a>
    </div>
    <div class="card my-2">
        <div class="card-body d-flex">
            <input form="searchOrg" style="width: 90%" type="text" class="form-control" name="searchName"
                   placeholder="Введите название организации" value="${searchName}">
            <input form="searchOrg" type="submit" class="btn ml-auto btn-success" value="ПОИСК">
        </div>
    </div>

    <c:if test="${searchName != null}">
        <c:if test="${orgList.size() == 0}">
            <p style="font-size: xx-large; text-align: center" class="mb-0 mt-2">Результаты поиска "${searchName}"
                отсутсвуют</p>
        </c:if>
    </c:if>

    <c:forEach items="${orgList}" var="organization">
        <div class="card">
            <div class="card-body center_box" style="padding: 10px">
                <div>
                    <p class="mx-3 my-0">${organization.name}</p>
                </div>
                <c:if test="${organization.account != null}">
                    <div>
                        <p class="mx-3 my-0">Счёт №: ${organization.account}</p>
                    </div>
                </c:if>
                <button form="selectOrg" name="orgID" type="submit" class="btn btn-info ml-auto mr-2"
                        value="${organization.id}">Выбрать
                </button>
            </div>
        </div>
    </c:forEach>
</div>