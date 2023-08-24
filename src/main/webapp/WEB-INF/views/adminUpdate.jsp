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
    <link rel="shortcut icon" href="/image/favicon.ico">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
    <link href="/css/admin-update.css" rel="stylesheet">
    <script src="/script/admin-update.js"></script>
    <title>Update</title>
</head>
<body>
<div class="admin-update-container">
    <div class="admin-info">
        <div id="admin-info-text-area">
            <h2 id="admin-name">이름을 조회하지 못했습니다.</h2>
            <p id="hos-name">병원을 조회하지 못했습니다.</p>
            <div id="admin-info-btn-area">
                <button class="admin-info-btn" onclick="getAdminInfoForm()">수정</button>
            </div>
        </div>
        <form class="admin-info-form">
            <span> 관리자 개인정보 수정 </span>
            <input id="adminName" name="adminName" placeholder="">
            <input id="hospitalName" name="hospitalName" placeholder="" readonly>
            <div class="info-btn">
                <input type="button" class="admin-info-update-btn" value="수정" onclick="adminInfoUpdateForm()">
                <input type="button" class="admin-red-btn" value="취소" onclick="closeInfoForm()">
            </div>
        </form>
    </div>
    <div class="admin-info-update">
        <div class="admin-info-pw-area">
            <div id="pw-text-area">
                <span>비밀번호</span>
                <button onclick="getAdminPwForm()">비밀번호 변경</button>
            </div>
            <form class="pw-form">
                <span> 관리자 비밀번호 수정 </span>
                <input type="password" id="admin-pw" name="adminPw" placeholder="현재 비밀번호">
                <input type="password" id="new-admin-pw" name="adminUpdatePw" placeholder="새 비밀번호">
                <input type="password" id="new-admin-pw-again" name="adminUpdatePwAgain" placeholder="새 비밀번호 확인">
                <div class="info-btn">
                    <input type="button" value="비밀번호 변경 저장" class="admin-blue-btn" onclick="adminPwUpdateForm()">
                    <input type="button" value="취소" class="admin-red-btn" onclick="closePwUpdateForm()">
                </div>
            </form>
        </div>
        <div class="admin-info-email-area">
            <div class="email-form" id="admin-email-text-area">
                <span>이메일</span>
                <input id="admin-info-Email" readonly/>
                <button onclick="getAdminEmailUpdateForm()">이메일 변경</button>
            </div>
            <form class="email-form-area">
                <div class="email-form-text">
                    <span> 관리자 이메일 수정 </span>
                    <span id="import-email">* 이메일 변경시 이메일 인증을 다시해야합니다.*</span>
                </div>
                <div class="email-form email-flex">
                    <input class="email-get-auth-code" name="adminEmail" placeholder="이메일을 입력해주세요.">
                    <input class="email-get-auth-code" type="button" value="인증 보내기" onclick="sendVerificationEmail()">
                </div>
                <div class="email-form email-flex">
                    <input class="email-auth-code-area" name="code" placeholder="인증 코드를 입력해주세요.">
                    <input class="email-auth-code-area" type="button" value="인증 받기" onclick="validateVerificationCode()">
                </div>
                <div class="info-btn">
                    <input type="button" value="변경" class="admin-blue-btn" onclick="adminEmailUpdateForm()">
                    <input type="button" value="취소" class="admin-red-btn" onclick="closeEmailUpdateForm()">
                </div>
            </form>
        </div>
        <div class="admin-leave-area">
            <span>회원 탈퇴</span>
            <button class="admin-red-btn" onclick="deleteAdmin()">회원 탈퇴</button>
        </div>
    </div>
</div>
</body>
</html>
