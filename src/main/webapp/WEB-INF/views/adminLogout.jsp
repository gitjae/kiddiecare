<%--
  Created by IntelliJ IDEA.
  User: 채희재
  Date: 2023-08-20
  Time: 오후 6:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:choose>
    <c:when test="${empty log}">
        <span>잘못된 접근입니다.</span>
    </c:when>
    <c:otherwise>
        <span>로그아웃 되었습니다.</span>
    </c:otherwise>
</c:choose>
</body>
</html>
