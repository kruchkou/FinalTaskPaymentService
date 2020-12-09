<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/tld/custom_tags.tld" prefix="mytag" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>
<fmt:setBundle basename="locale"/>

<fmt:message key="user.control" var="locale_user_control"/>
<fmt:message key="data" var="locale_data"/>
<fmt:message key="user.user" var="locale_user"/>
<fmt:message key="status" var="locale_status"/>
<fmt:message key="control" var="locale_control"/>
<fmt:message key="user.login" var="locale_user_login"/>
<fmt:message key="user.status_user" var="locale_user_status_user"/>
<fmt:message key="user.status_admin" var="locale_user_status_admin"/>
<fmt:message key="user.birthdate" var="locale_user_birthdate"/>
<fmt:message key="user.phone_number" var="locale_user_phone_number"/>
<fmt:message key="user.user_accounts" var="locale_user_accounts"/>
<fmt:message key="user.grand_admin_rights" var="locale_user_grand_admin_rights"/>
<fmt:message key="user.revoke_admin_rights" var="locale_user_revoke_admin_rights"/>
<fmt:message key="user.choose_image" var="locale_choose_image"/>
<fmt:message key="user.change_password" var="locale_change_password"/>
<fmt:message key="user.edit_data" var="locale_edit_data"/>
<fmt:message key="user.upload_image" var="locale_upload_image"/>
<fmt:message key="user.profile_photo" var="locale_profile_photo"/>


<div class="container mt-3">
    <h1>${locale_user_control.toUpperCase()}</h1>
    <div class="card-group">
        <div class="card">
            <div class="card-header">
                ${locale_data}:
            </div>
            <div class="card-body">
                <div class="d-flex justify-content-center">
                    <img class="user_image" src="<mytag:userImageTag imageURL="${sessionScope.user.imageSrc}"/>" width="200" height="200" alt="${locale_profile_photo}">
                </div>
                <div>
                    <p class="card-text m-0">${locale_user}:
                        ${user.surname} ${user.name} ${user.patronymic}
                    </p>
                </div>
                <div>
                    <p class="card-text m-0">${locale_user_login}: ${user.login} </p>
                </div>
                <div>${locale_status}:
                    <c:if test="${user.status.id == 1}">
                        ${locale_user_status_user}
                    </c:if>
                    <c:if test="${user.status.id == 2}">
                        ${locale_user_status_admin}
                    </c:if>
                    <p class="card-text m-0">${locale_user_birthdate}: ${user.birthDate}</p>
                </div>
                <div>
                    <p class="card-text m-0">${locale_user_phone_number}: ${user.phoneNumber}</p>
                </div>
            </div>
        </div>
        <div class="card">
            <div class="card-header">
                ${locale_control}:
            </div>

            <div class="card-body">

                <div class="fullfill mb-0">
                    <form action="Controller" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="command" value="upload_user_image_command"/>
                        <div class="custom-file">
                            <input type="file" class="custom-file-input" id="image" name="file" accept=".jpg" required>
                            <label class="custom-file-label" for="image">${locale_choose_image}...</label>
                        </div>
                        <div class="d-flex mt-2">
                            <input class="btn btn-success fullfill mx-auto" type="submit" value="${locale_upload_image}" name="load_photo"/>
                        </div>
                    </form>
                </div>

                <form class="fullfill mt-5 mb-0" id="accountControl" action="Controller" method="post">
                    <input type="hidden" name="userID" value="${user.getId()}"/>

                    <button name="command" type="submit" class="btn btn-primary control-button fullfill"
                            value="go_to_personal_edit_command">${locale_edit_data}
                    </button>
                        <button name="command" type="submit" class="btn btn-warning control-button fullfill"
                                value="">${locale_change_password}
                        </button>

                </form>
            </div>
        </div>
    </div>
</div>