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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
    <link href="/css/admin-update.css" rel="stylesheet">
    <script src="/script/admin-update.js"></script>
    <title>Update</title>
</head>
<body>
<div class="admin-update-container">
    <section class="admin-info">
        <h2>이름</h2>
        <p>병원</p>
        <div id="admin-info-btn-area">
        <button class="admin-info-btn">수정</button>
        </div>
        <form class="admin-info-form">
            <input name="adminName" placeholder="">
            <input name="hospitalName" placeholder="">
            <button class="admin-info-update-btn">수정</button>
        </form>
    </section>
    <section class="admin-info-update">
        <div class="admin-info-pw-area">
            <div id="pw-text-area">
                <span>비밀번호</span>
                <button onclick="adminPwUpdateForm()">비밀번호 변경</button>
            </div>
            <form class="pw-form">
                <input name="adminPw" placeholder="현재 비밀번호">
                <input name="adminUpdatePw" placeholder="새 비밀번호">
                <input id="admin-pw-again" name="adminUpdatePw" placeholder="새 비밀번호 확인">
                <button onclick="adminPwUpdateForm()">비밀번호 변경 저장</button>
            </form>
        </div>
        <div class="admin-info-email-area">
            <span>이메일</span>
            <input id="admin-info-Email" name="adminEmail"/>
            <button onclick="adminPwUpdateForm()">이메일 변경</button>
            <form class="email-form">
                <input name="adminEmail" placeholder="">
                <button onclick="adminEmailUpdateForm()">변경</button>
            </form>
        </div>
        <div class="admin-leave-area">
            <span>회원 탈퇴</span>
            <button id="admin-leave-btn" onclick="deleteAdmin()">회원 탈퇴</button>
        </div>
    </section>
</div>
</body>
</html>
