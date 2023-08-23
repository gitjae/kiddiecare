<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="/css/common.css" rel="stylesheet">
    <link href="/css/header.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/stompjs@2.3.3/lib/stomp.min.js"></script>
    <script src="/script/logout-api.js"></script>
</head>
<body>
<header id="header">
    <div class="logoWrap">
        <a href="/">
            <div class="logoImg">
                <img src="/image/logo_hospital_512.png"><span class="logoTitle">우리동네소아과</span>
            </div>
        </a>
    </div>

    <div class="topNav">
        <c:choose>
            <c:when test="${empty log}">
                <a class="login-a" href="/login">로그인</a>
                <a class="join-a" href="/join">회원가입</a>
            </c:when>
            <c:otherwise>
                <c:set var="sessionValue" value="${sessionScope.log}" />
                <input type="hidden" id="sessionValue" value="${sessionValue}" />
                <div class="icon">
                    <img src="/image/bell.png" class="bell" onclick="toggleNotifi()">
                    <span id="alarm"></span>
                    <div class="notifi-box" id="box">
                        <h2>알림<span id="count"></span></h2>
                        <div class="notifi-area"></div>
                    </div>
                </div>
                <c:if test="${!empty Ykiho}">
                    <a class="admin-index" href="/admin/index">병원관리</a>
                </c:if>
                <a class="searchHos" href="/hospital/Search">동네병원</a>
                <a class="mypage-a" href="/mypage">마이페이지</a>
                <a class="logout-a" href="" onclick="logout()">로그아웃</a>
            </c:otherwise>
        </c:choose>
    </div>

    <div class="toast">
        <div class="toast-content">
            <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="#3E85EF" class="bi bi-check-circle-fill" viewBox="0 0 16 16">
                <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-3.97-3.03a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z"/>
            </svg>
            <div class="message">
                <span class="text-1"></span>
                <span class="text-2">예약이 완료되었습니다.</span>
            </div>
        </div>
        <span class="close">&times;</span>
        <div class="progress"></div>
    </div>
</header>
</body>
</html>
