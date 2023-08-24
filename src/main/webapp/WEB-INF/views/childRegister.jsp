<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>자녀등록</title>
    <link rel="shortcut icon" href="/image/favicon.ico">
    <link href="/css/childRegister.css" rel="stylesheet">
    <script src="/script/childRegister.js"></script>
</head>
<c:import url="header.jsp"/>
<body>
<div class="container">
    <section id="main-section">
        <div id="form">
        <div class="addChild-div">
            <p class="addChild-title">자녀등록</p>
            <p class="addChild-sub-title">미리 자녀 정보를 등록하면 예약이 간단해져요!</p>
            <div id="div-name">
                <label for="name">이름</label>
                <input type="text" id="name" name="name">
            </div>
            <div id="div-birth">
                <label for="birth">생년월일</label>
                <input type="text" id="birth" name="birth" placeholder="생년월일 8자리를 입력해주세요. (YYYYMMDD)">
            </div>
            <div id="div-gender">
                <label id="label-gender">성별</label>

                <input type="radio" id="gender-m" name="gender" value="m" checked>
                <label for="gender-m">남</label>

                <input type="radio" id="gender-f" name="gender" value="f">
                <label for="gender-f">여</label>
            </div>
            <div id="div-info">
                <label for="info">참고사항</label>
                <input type="text" id="info" name="info">
            </div>

            <button id="submit" onclick="register()">등록하기</button>
        </div>
        </div>
    </section>
</div>
</body>
<c:import url="footer.jsp"></c:import>
</html>
