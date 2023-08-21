<%--
  Created by IntelliJ IDEA.
  User: 집
  Date: 2023-08-13
  Time: 오후 11:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update</title>
</head>
<body>
    <section class="admin-hosp">
        <img src="">
        <span>이름:<span id="name"></span></span>
        <span>병원:<span id="hospName"></span></span>
    </section>
    <section class="admin-info-update">
        <div class="admin-info-pw-area">
            <span>비밀번호 :</span>
            <button onclick="adminPwUpdateForm()">비밀번호 변경</button>
            <form class="pw-form">
                <input name="adminPw" placeholder="현재 비밀번호">
                <input name="adminUpdatePw" placeholder="새 비밀번호">
                <input id="admin-pw-again" name="adminUpdatePw" placeholder="새 비밀번호 확인">
            </form>
        </div>
        <div class="admin-info-email-area">
            <span>이메일 :</span>
            <input id="admin-info-Email" name="adminEmail"/>
        </div>
        <div class="admin-leave-area">
            <span>회원 탈퇴</span>
            <button id="admin-leave-btn" onclick="deleteAdmin()">회원 탈퇴</button>
        </div>
    </section>
</body>
</html>
