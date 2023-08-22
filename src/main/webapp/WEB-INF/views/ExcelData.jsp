<%--
  Created by IntelliJ IDEA.
  User: 채희재
  Date: 2023-08-22
  Time: 오전 11:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form method="POST" enctype="multipart/form-data" action="/data/parsing/excel">
        <label for="admin-attach">엑셀 파일</label>
        <input type="file" id="admin-attach" name="file" size="10000000"/>
        <button type="submit">전송</button>
    </form>
</body>
</html>
