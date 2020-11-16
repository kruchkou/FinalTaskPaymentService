<%--
  Created by IntelliJ IDEA.
  User: kruchkou
  Date: 01.11.2020
  Time: 21:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add card</title>
</head>
<body>

<div class="col-md-6 mt-3 mx-auto">
    <div class="card">
        <div class="card-header">
            <h1 class="text-center">ПРИВЯЗАТЬ КАРТУ</h1>
        </div>
        <div class="card-body display_block">
            <form action="Controller" method="post">
                <input type="hidden" name="accountID" value="${accountID}">
                <input type="hidden" name="command" value="add_card_command">
                <div class="form-group">
                    <label for="number">Номер карты:</label>
                    <input type="number" class="form-control" name="number" id="number" required pattern="${attribute_regexp_card_number}" placeholder="В формате 1234567891234567">
                </div>
                <div class="form-group">
                    <label for="ownerName">Имя владельца:</label>
                    <input type="text" class="form-control" name="ownerName" id="ownerName" required pattern="${attribute_regexp_ownername}" placeholder="От 3 до 45 букв">
                </div>
                <div class="form-group">
                    <label for="expDate">Дата истечения:</label>
                    <input type="date" class="form-control" name="expDate" id="expDate">
                </div>
                <div class="form-group">
                    <label for="cvv">cvv:</label>
                    <input type="number" class="form-control" name="cvv" id="cvv" required pattern="${attribute_regexp_cvv}" placeholder="от 2-х до 3-х цифр">
                </div>
                <div class="form-group mt-5">
                    <button type="submit" class="btn btn-success">Добавить карту</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
