<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="/css/common.css" rel="stylesheet">
    <link href="/css/header.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
    <script src="/script/logout-api.js"></script>
</head>
<body>
    <header id="header">
        <div class="logoWrap">
            <a href="/">
                <div class="logoImg">
                    <img src="/image/logo_hospital_512.png"><span class="logoTitle">우리동네소아과</span>
                </div>
            </a>
        </div>

        <div class="topNav">
            <p>log : ${log}</p>
            <a href="/login">로그인</a>
            <a href="/mypage">마이페이지</a>
            <a href="#" onclick="logout()">로그아웃</a>
        </div>
    </header>
</body>
</html>
