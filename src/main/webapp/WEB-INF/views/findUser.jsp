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
            </div>
            <div id="find-password">
                <label for="find-password-id">아이디</label>
                <input id="find-password-id">
                <label for="find-password-name">이름</label>
                <input id="find-password-name">
                <label for="find-password-phone">전화번호</label>
                <input id="find-password-phone">
                <button onclick="sendCode()">인증번호 발송</button>
                <div id="div-verify">
                    <label for="verify-code">인증번호</label>
                    <input id="verify-code">
                    <button onclick="verify()">인증하기</button>
                </div>
            </div>
        </section>
    </div>
</body>
</html>
