<%--
  Created by IntelliJ IDEA.
  User: regul
  Date: 2023-08-08
  Time: 오후 2:27
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
    <script src="/script/join-api.js"></script>
</head>
<body>
    <div id="root">
        <section id="main-section">
            <div id="form">
                <div id="div-id">
                    <label for="id">아이디</label>
                    <input type="text" id="id" name="id">
                    <input type="button" id="duplChk" value="중복확인" onclick="dupl()">
                </div>
                <div id="div-pw">
                    <label for="password">비밀번호</label>
                    <input type="password" id="password" name="password">
                </div>
                <div id="div-pwChk">
                    <label for="passwordChk">비밀번호확인</label>
                    <input type="password" id="passwordChk" name="passwordChk">
                </div>
                <div id="div-name">
                    <label for="name">이름</label>
                    <input type="text" id="name" name="name">
                </div>
                <div id="div-birth">
                    <label for="birth">생년월일</label>
                    <input type="text" id="birth" name="birth">
                </div>
                <div id="div-gender">
                    <label>성별</label>

                    <label for="gender-m">남</label>
                    <input type="radio" id="gender-m" name="gender" value="m">

                    <label for="gender-f">여</label>
                    <input type="radio" id="gender-f" name="gender" value="f">
                </div>
                <div id="div-phone">
                    <label for="phone">전화번호</label>
                    <input type="text" id="phone" name="phone">
                </div>
                <div id="div-email">
                    <label for="email">이메일</label>
                    <input type="email" id="email" name="email">
                </div>
                <button id="submit" onclick="join()">가입하기</button>
            </div>
        </section>
    </div>
</body>
</html>
