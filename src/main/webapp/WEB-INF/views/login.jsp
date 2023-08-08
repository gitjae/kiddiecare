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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
    <script src="/script/login-api.js"></script>
</head>
<body>
<div id="root">
    <section id="main-section">
        <div id="form">
            <div>log : ${log}</div>
            <div id="div-id">
                <label for="id">아이디</label>
                <input type="text" id="id" name="id">
            </div>
            <div id="div-pw">
                <label for="password">비밀번호</label>
                <input type="password" id="password" name="password">
            </div>
            <div id="div-btn">
                <button id="submit" onclick="login()">로그인</button>
            </div>
        </div>
    </section>
</div>
</body>
</html>
