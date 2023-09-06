<%--
  Created by IntelliJ IDEA.
  User: 채희재
  Date: 2023-08-08
  Time: 오후 4:00
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link href="/css/common.css" rel="stylesheet">
    <link rel="stylesheet" href="/css/admin-login.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
    <script src="/script/admin-login.js"></script>
    <title>login</title>
</head>
<c:import url="headerIndex.jsp"></c:import>
<body>
<div class="container">
    <section>
        <div class="login-box">
            <div class="login-header">
                <header>관리자 로그인</header>
            </div>
            <div class="input-box">
                <input type="text" name="adminId" id="input-id" class="input-field" placeholder="아이디를 입력해주세요." autocomplete="off" required>
            </div>
            <div class="err-box">
                <span class="err" id="error-id"></span>
            </div>
            <div class="input-box">
                <input type="password" name="adminPw" id="input-pw" class="input-field" placeholder="비밀번호를 입력해주세요." autocomplete="off" required>
            </div>
            <div class="err-box">
                <span class="err" id="admin-pw-err"></span>
            </div>
            <div class="forgot">
                <section>
                    <a href="#">비밀번호 찾기</a>
                </section>
            </div>
            <div class="input-submit">
                <input type="button" class="submit-btn" value="로그인" onclick="checkValue()"/>
            </div>
            <div class="sign-up-link">
                <p>아직 회원이 아니신가요? <a href="/admin/join">회원 가입</a></p>
            </div>
        </div>
    </section>
</div>
</body>
</html>
