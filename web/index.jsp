<%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Welcome page!</title>
    <link href="css/style.css" rel="stylesheet" type="text/css">

</head>
<body>
<jsp:include page="WEB-INF/header.jsp" />

<div class="main" style="margin-top: 20px">
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