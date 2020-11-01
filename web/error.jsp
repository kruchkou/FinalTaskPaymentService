<%--
  Created by IntelliJ IDEA.
  User: kruchkou
  Date: 29.09.2020
  Time: 3:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Упс!</title>
</head>
<body>
<jsp:include page="WEB-INF/header.jsp" />
<h1>Извините, у нас технические неполадки</h1>
<p>${exception.getMessage()}</p>
<p>${exception.printStackTrace()}</p>
</body>
</html>
