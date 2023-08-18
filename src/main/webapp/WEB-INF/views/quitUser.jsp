<%--
  Created by IntelliJ IDEA.
  User: regul
  Date: 2023-08-18
  Time: 오후 3:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="/css/common.css" rel="stylesheet">
    <link rel="stylesheet" href="/css/login.css">
    <title>회원 탈퇴</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
</head>
<body>
<div class="container">
    <section id="main-section">
        <a href="/">
            <h2 class="loginTitle">우리동네소아과</h2>
        </a>
        <div id="form">
            <%--            <div>log : ${log}</div>--%>
            <div class="wrapper">
                <div class="login-div">
                    <div id="div-id">
                        <label for="id">ID</label>
                        <input type="text" id="id" name="id" placeholder="탈퇴하려면 아이디를 입력해주세요.">
                    </div>

                    <div id="div-pw">
                        <label for="password">PASSWORD</label>
                        <input type="password" id="password" name="password" placeholder="탈퇴하려면 비밀번호를 입력해주세요.">
                    </div>

                    <div id="div-btn">
                        <button id="submit" onclick="quitUser()">탈퇴하기</button>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>
</body>
<script src="/script/quit.js"></script>
</html>
