<%--
  Created by IntelliJ IDEA.
  User: regul
  Date: 2023-08-07
  Time: 오후 7:32
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>목록</title>
</head>
<body>
    <h1>Date Source : ${apiResponse.source}</h1>
    <p>${apiResponse.data}</p>
    <c:forEach items="${response.body.items}" var="item">
        <p>병원명 : ${item.yadmNm}</p>
        <p>양호기호 : ${item.ykiho}</p>
        <p>${item.XPos}</p>
        <p>${item.YPos}</p>
    </c:forEach>
</body>
</html>
