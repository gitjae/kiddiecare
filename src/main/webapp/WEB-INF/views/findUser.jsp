<%--
  Created by IntelliJ IDEA.
  User: regul
  Date: 2023-08-14
  Time: 오후 5:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
    <script src="/script/find-user.js"></script>
</head>
<body>
    <div>
        <section>
            <div id="find-id">
                <label for="find-id-name">이름</label>
                <input id="find-id-name">
                <label for="find-id-phone">전화번호</label>
                <input id="find-id-phone">
                <button id="find-id-btn" onclick="findId()">아이디 찾기</button>
                <div id="find-id-result">
                    <p id="response-id"></p>
                </div>
            </div>
            <div id="find-password">
                <label for="find-password-id">아이디</label>
                <input id="find-password-id">
                <label for="find-password-name">이름</label>
                <input id="find-password-name">
                <label for="find-password-phone">전화번호</label>
                <input id="find-password-phone">
                <button id="send" onclick="sendCode()">인증번호 발송</button>
                <div id="div-verify">
                    <label for="verify-code">인증번호</label>
                    <input id="verify-code">
                    <button id="verify-btn" onclick="verify()">인증하기</button>
                </div>
                <button id="find-password-btn" onclick="findPassword()">비밀번호 찾기</button>
                <div id="find-password-result">
                    <p id="response-password"></p>
                </div>
            </div>
        </section>
    </div>
</body>
</html>
