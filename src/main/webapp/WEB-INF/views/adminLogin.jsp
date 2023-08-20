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
    <script
            src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
    <script src="/script/admin-login.js"></script>
    <title>login</title>
</head>
<c:import url="headerIndex.jsp"></c:import>
<body>
<div class="container">
    <section>
            <div class="login-area">
                <div class="icon-area">
                    <a href="/">
<%--                        <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor" class="bi bi-file-earmark-code-fill" viewBox="0 0 16 16">--%>
<%--                            <path d="M9.293 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V4.707A1 1 0 0 0 13.707 4L10 .293A1 1 0 0 0 9.293 0zM9.5 3.5v-2l3 3h-2a1 1 0 0 1-1-1zM6.646 7.646a.5.5 0 1 1 .708.708L5.707 10l1.647 1.646a.5.5 0 0 1-.708.708l-2-2a.5.5 0 0 1 0-.708l2-2zm2.708 0 2 2a.5.5 0 0 1 0 .708l-2 2a.5.5 0 0 1-.708-.708L10.293 10 8.646 8.354a.5.5 0 1 1 .708-.708z"/>--%>
<%--                        </svg>--%>
                        <span>Admin Login</span>
                    </a>
                </div>
                <form class="login-form" method="POST">
                    <div class="input-setting">
                        <label for="input-id" id="label-id">ID</label>
                        <input type="text" required placeholder="아이디를 입력해 주세요." name="adminId" class="current-id" id="input-id">
                    </div>
                    <div class="input-setting">
                        <label for="input-pw" id="label-pw">PW</label>
                        <input type="password" required placeholder="비밀번호를 입력해 주세요." name="adminPw" class="current-password" id="input-pw">
                    </div>
                    <div id="submit-btn-area">
                        <input type="button" id="submit-btn" value="로그인" onclick="checkValue(form)">
                    </div>
                </form>
                <div class="sign-up-area">
                    <a role="link" color="default" href="/admin/join" class="sign-up-a">회원가입</a>
                </div>
            </div>
    </section>
</div>
</body>
</html>
