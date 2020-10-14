<%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Welcome page!</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" crossorigin="anonymous">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap-theme.min.css" integrity="sha384-6pzBo3FDv/PJ8r2KRkGHifhEocL+1X2rVCTTkUfGk7/0pbek5mMa1upzvWbrUbOZ" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js" integrity="sha384-aJ21OjlMXNL5UyIl/XNwTMqvzeRMZH2w8c5cRVpzpU8Y5bApTppSuUkhZXN0VxHd" crossorigin="anonymous"></script>
    <link href="css/style.css" rel="stylesheet" type="text/css">

</head>
<body>
<jsp:include page="WEB-INF/header.jsp" />

<header>
    <div class="header">
        <h1 style="display: inline-block">QUICKPAY</h1>
        <h2 style="float: right">Легче, быстрее, проще!</h2>
    </div>
</header>
<div class="main">
    <div class="container">
        <h2 class="center">ПЛАТЕЖНАЯ СИСТЕМА</h2>

        <table>
            <tr>
                <td>
                    Моментальные переводы
                </td>
                <td>
                    Моментальный перевод средств на счет QuickPay с карты и обратно!
                </td>
            </tr>
            <tr>
                <td>
                    Минимальные комиссии
                </td>
                <td>
                    А на платежи внутри сервиса - нулевые!
                </td>
            </tr>
            <tr>
                <td>
                    Много счетов, много карт
                </td>
                <td>
                    Никаких ограничений! Различайте свои счета по назначению. Сберегательный, платежный или накопительный.
                    <br>Как вам захочется!
                </td>
            </tr>
        </table>

        <form class="center" action="Controller" method="post">
            <input type="hidden" name="command" value="go_to_sign_in_command"/>
            <button class="login" type="submit">ВХОД</button>
        </form>
    </div>

</div>
</body>
</html>