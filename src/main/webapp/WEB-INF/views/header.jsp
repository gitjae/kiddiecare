<%--
  Created by IntelliJ IDEA.
  User: regul
  Date: 2023-08-08
  Time: 오후 3:28
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="/css/header.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
    <script src="/script/logout-api.js"></script>
</head>
<body>
    <header>
        <p>log : ${log}</p>
        <a href="/login">로그인</a>
        <a href="/mypage">마이페이지</a>
        <a href="#" onclick="logout()">로그아웃</a>
    </header>
</body>
</html>
