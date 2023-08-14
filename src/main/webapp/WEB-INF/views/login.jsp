<%--
  Created by IntelliJ IDEA.
  User: regul
  Date: 2023-08-08
  Time: 오후 3:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="/css/common.css" rel="stylesheet">
    <link rel="stylesheet" href="/css/login.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
    <script src="/script/login-api.js"></script>
</head>
<body>
<div class="container">
    <section id="main-section">
        <a href="/">
            <h2 class="loginTitle">우리동네소아과</h2>
        </a>
        <div id="form">
            <div>log : ${log}</div>
            <div id="div-id">
                <label for="id">ID</label>
                <input type="text" id="id" name="id">
            </div>
            <div id="div-pw">
                <label for="password">PASSWORD</label>
                <input type="password" id="password" name="password">
            </div>
            <div id="div-btn">
                <button id="submit" onclick="login()">LOGIN</button>
            </div>
            <div id="div-kakao">
                <button onclick=""><a href="#">KAKAO LOGIN</a></button>
            </div>
            <div id="div-find">
                <a href="#">아이디/비밀번호 찾기</a>
            </div>
        </div>
    </section>
</div>
</body>
</html>
