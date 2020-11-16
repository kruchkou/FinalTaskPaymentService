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
    <h3 class="mb-0 ml-3" style="color: white">ПАНЕЛЬ ПОЛЬЗОВАТЕЛЯ</h3>
</div>

<div class="container payment_window mb-5 pt-3 pb-5">

    <div class="row justify-content-md-center">
        <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
            <div class="btn-group mr-5" role="group" aria-label="First group">
                <a type="button" class="btn btn-secondary" href="Controller?command=go_to_accounts_command">Мои счета</a>
                <a type="button" class="btn btn-secondary" href="Controller?command=go_to_cards_command">Мои карты</a>
                <a type="button" class="btn btn-secondary" href="Controller?command=go_to_user_history_command">Мои платежи</a>
            </div>
            <div class="btn-group" role="group" aria-label="Second group">
                <a type="button" class="btn btn-secondary" href="Controller?command=go_to_pay_select_account_command&next_command=go_to_pay_select_org_command">Оплатить</a>
                <a type="button" class="btn btn-secondary" href="Controller?command=go_to_pay_select_account_command&next_command=go_to_pay_transfer_to_command">Перевести</a>
            </div>
        </div>
    </div>
    <jsp:include page="${payments_content}"/>
</div>

<jsp:include page="footer.jsp"/>

</body>
</html>
