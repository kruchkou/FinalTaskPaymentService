<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="css/login.css" rel="stylesheet" type="text/css">
    <title>QuickPAY</title>
</head>
<body>
<jsp:include page="header.jsp"/>


<div class="container bg-dark py-2 mt-5 label_window">
    <h3 class="mb-0 ml-3" style="color: white">ПАНЕЛЬ АДМИНИСТРАТОРА</h3>
</div>

<div class="container payment_window mb-5 pt-3 pb-5">

    <div class="row justify-content-md-center">
        <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
            <div class="btn-group mr-5" role="group" aria-label="First group">
                <a type="button" class="btn btn-secondary" href="Controller?command=go_to_admin_accounts_command">Поиск счета</a>
            </div>
            <div class="btn-group mr-5" role="group" aria-label="Second group">
                <a type="button" class="btn btn-secondary" href="Controller?command=go_to_admin_users_command">Поиск пользователя</a>
            </div>
            <div class="btn-group mr-5" role="group" aria-label="Third group">
                <a type="button" class="btn btn-secondary" href="Controller?command=go_to_admin_orgs_command">Поиск организации</a>
            </div>
        </div>
    </div>
    <jsp:include page="${admin_content}"/>

</div>

</body>
</html>
