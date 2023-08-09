<%--
  Created by IntelliJ IDEA.
  User: 채희재
  Date: 2023-08-08
  Time: 오후 4:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">

    <title>login</title>
</head>
<body>
<section>
    <div class="login-area">
        <div class="icon-area">
            <a href="/">
                <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor" class="bi bi-file-earmark-code-fill" viewBox="0 0 16 16">
                    <path d="M9.293 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V4.707A1 1 0 0 0 13.707 4L10 .293A1 1 0 0 0 9.293 0zM9.5 3.5v-2l3 3h-2a1 1 0 0 1-1-1zM6.646 7.646a.5.5 0 1 1 .708.708L5.707 10l1.647 1.646a.5.5 0 0 1-.708.708l-2-2a.5.5 0 0 1 0-.708l2-2zm2.708 0 2 2a.5.5 0 0 1 0 .708l-2 2a.5.5 0 0 1-.708-.708L10.293 10 8.646 8.354a.5.5 0 1 1 .708-.708z"/>
                </svg>
                <span>Spring rest API</span>
            </a>
        </div>
        <form class="login-form" method="POST" action="/admin/login/check" enctype="multipart/form-data">
            <div class="input-setting">
                <input type="text" required placeholder="아이디를 입력해 주세요." name="adminId" class="current-id">
            </div>
            <div class="input-setting">
                <input type="password" required placeholder="비밀번호를 입력해 주세요." name="adminPw" class="current-password">
            </div>
            <div>
                <button role="button" color="skyblue" type="submit" class="submit-btn"><span>로그인</span></button>
            </div>
        </form>
        <div class="sign-up-area">
            <a role="link" color="default" href="/" class="sign-up-a">회원가입</a>
        </div>
    </div>
</section>
</body>
</html>
