<%--
  Created by IntelliJ IDEA.
  User: regul
  Date: 2023-08-10
  Time: 오후 4:56
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/css/mypage.css">
    <script src="/script/mypage.js"></script>
</head>
<c:import url="header.jsp"/>
<body>
    <div id="root">
        <aside id="sidebar">
            <div id="bar-header">
                <h2 id="h2-header">헤더</h2>
            </div>
            <div id="bar-nav">
                <ul id="ul-nav">
                    <li><div id="nav-user" name="div-user" onclick="sectionChange(this)">회원정보</div></li>
                    <li><div id="nav-children" name="div-children" onclick="sectionChange(this), getChildren(1)">자녀관리</div></li>
                    <li><div id="nav-appo" name="div-appo" onclick="sectionChange(this), getAppo(1)">예약내역</div></li>
                    <li><div id="nav-review" name="div-review" onclick="sectionChange(this)">후기관리</div></li>
                    <li><div id="nav-favor" name="div-favor" onclick="sectionChange(this)">찜한병원</div></li>
                </ul>
            </div>
        </aside>
        <section id="main-section">
            <div class="main-div" id="div-user">
                <p>유저</p>
                <div id="user">
                    <p>${user.name}</p>
                    <p>${user.email}</p>
                </div>
            </div>
            <div class="main-div" id="div-children">
                <div id="children">

                </div>
                <button onclick="prevChildren()">prev</button>
                <button onclick="nextChildren()">next</button>
            </div>
            <div class="main-div" id="div-appo">
                <div id="appo">

                </div>
                <button onclick="prevAppo()">prev</button>
                <button onclick="nextAppo()">next</button>
            </div>
            <div class="main-div" id="div-review">
                <p>후기</p>
            </div>
            <div class="main-div" id="div-favor">
                <p>찜</p>
            </div>
        </section>
    </div>
</body>
</html>
