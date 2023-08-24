<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>마이페이지</title>
    <link href="/css/common.css" rel="stylesheet">
    <link rel="stylesheet" href="/css/mypage.css">
    <script src="/script/mypage-user.js"></script>
    <script src="/script/mypage-children.js"></script>
    <script src="/script/mypage-appo.js"></script>
    <script src="/script/mypage-likeHospital.js"></script>
</head>
<c:import url="header.jsp"/>
<body>
<div class="container">
    <section>
        <div class="mypage-title">
            <p>마이페이지</p>
        </div>
        <div class="content-wrapper">
            <aside id="sidebar">
                <div id="bar-nav">
                    <ul id="ul-nav">
                        <li>
                            <div id="nav-user" name="div-user" onclick="sectionChange(this)">회원정보</div>
                        </li>
                        <li>
                            <div id="nav-children" name="div-children" onclick="sectionChange(this), getChildren(1)">
                                자녀관리
                            </div>
                        </li>
                        <li>
                            <div id="nav-appo" name="div-appo" onclick="sectionChange(this), getAppo(1)">예약내역</div>
                        </li>
<%--                        <li>--%>
<%--                            <div id="nav-review" name="div-review" onclick="sectionChange(this)">후기관리</div>--%>
<%--                        </li>--%>
                        <li>
                            <div id="nav-favor" name="div-favor" onclick="sectionChange(this)">찜한병원</div>
                        </li>
                    </ul>
                </div>
            </aside>
            <div id="main-section">
                <div class="main-div" id="div-user">
                    <div id="user">
                        <div class="info-item">
                            <span class="info-title">이름</span>
                            <span>${user.name}</span>
                        </div>
                        <div class="info-item">
                            <span class="info-title">생년월일</span>
                            <span>${user.birth}</span>
                        </div>
                        <div class="info-item">
                            <span class="info-title">성별</span>
                            <span>
                                <c:choose>
                                    <c:when test="${user.gender}">남성</c:when>
                                    <c:otherwise>여성</c:otherwise>
                                </c:choose>
                            </span>
                        </div>
                        <div class="info-item">
                            <span class="info-title">전화번호</span>
                            <span>${user.phone}</span>
                        </div>
                        <div class="info-item">
                            <span class="info-title">이메일</span>
                            <span>${user.email}</span>
                        </div>
                        <div class="info-item">
                            <span class="info-title">주소</span>
                            <span>${user.addr}</span>
                        </div>
                        <div class="info-item">
                            <span class="info-title">상세주소</span>
                            <span>${user.addr_detail}</span>
                        </div>
                    </div>
                    <button id="user-update" onclick="gotoUpdate()">수정</button>
                    <button id="user-quit" onclick="gotoQuit()">탈퇴</button>
                </div>

                <div class="main-div" id="div-children">
                    <div id="children">

                    </div>
                    <a href="childRegister" style="text-decoration: underline">자녀 등록을 원하시나요?</a>
                    <button onclick="prevChildren()" style="background: none; color: #007BFF; font-size: larger; font-weight: 700;">&#9665;</button>
                    <button onclick="nextChildren()" style="background: none; color: #007BFF; font-size: larger; font-weight: 700;">&#9655;</button>
                </div>

                <div class="main-div" id="div-appo">
                    <div id="appointments">

                    </div>
                    <button onclick="prevAppo()" style="background: none; color: #007BFF; font-size: larger; font-weight: 700;">&#9665;</button>
                    <button onclick="nextAppo()" style="background: none; color: #007BFF; font-size: larger; font-weight: 700;">&#9655;</button>
                </div>

<%--                <div class="main-div" id="div-review">--%>
<%--                    <p>후기</p>--%>
<%--                </div>--%>

                <div class="main-div" id="div-favor">
                </div>
            </div>
        </div>
    </section>
</div>
</body>
<c:import url="footer.jsp"></c:import>
</html>
