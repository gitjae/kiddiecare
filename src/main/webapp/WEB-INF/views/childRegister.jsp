<%--
  Created by IntelliJ IDEA.
  User: regul
  Date: 2023-08-11
  Time: 오후 4:53
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="/script/childRegister.js"></script>
</head>
<c:import url="header.jsp"/>
<body>
<div id="root">
    <section id="main-section">
        <div id="form">
            <div id="div-name">
                <label for="name">이름</label>
                <input type="text" id="name" name="name">
            </div>
            <div id="div-birth">
                <label for="birth">생년월일</label>
                <input type="text" id="birth" name="birth">
            </div>
            <div id="div-gender">
                <label id="label-gender">성별</label>

                <label for="gender-m">남</label>
                <input type="radio" id="gender-m" name="gender" value="m" checked>

                <label for="gender-f">여</label>
                <input type="radio" id="gender-f" name="gender" value="f">
            </div>
            <div id="div-info">
                <label for="info">참고사항</label>
                <input type="text" id="info" name="info">
            </div>

            <button id="submit" onclick="register()">등록하기</button>
        </div>
    </section>
</div>
</body>
</html>
