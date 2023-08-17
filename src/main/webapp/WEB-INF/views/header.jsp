<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="/css/common.css" rel="stylesheet">
    <link href="/css/header.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
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
<%--            <p id="log0">log : ${log}</p>--%>
            <a class="bell" href="#"><img src="/image/bell.png" onclick=""></a>       <%-- 로그인 O --%>
            <a class="login-a" href="/login">로그인</a>                                        <%-- 로그인 X --%>
            <a class="join-a" href="/join">회원가입</a>                                       <%-- 로그인 X --%>
            <a class="searchHos" href="/hospital/Search">동네병원</a>                              <%-- 로그인 O --%>
            <a class="mypage-a" href="/mypage">마이페이지</a>                              <%-- 로그인 O --%>
            <a class="logout-a" href="#" onclick="logout()">로그아웃</a>                          <%-- 로그인 X --%>
        </div>
    </header>
</body>
<script>
    let logValue = '${log}';
    console.log("logValue : ", logValue);

    if (logValue === "null" || logValue.trim() === "") {
        // 로그아웃 상태일 때 보여야 하는 요소들 표시
        $('.login-a').show();
        $('.join-a').show();

        // 로그아웃 상태일 때 숨겨야 하는 요소들 숨기기
        $('.bell').hide();
        $('.searchHos').hide();
        $('.logout-a').hide();
        $('.mypage-a').hide();
    } else {
        // 로그인 상태일 때 보여야 하는 요소들 표시
        $('.bell').show();
        $('.searchHos').show();
        $('.logout-a').show();
        $('.mypage-a').show();

        // 로그인 상태일 때 숨겨야 하는 요소들 숨기기
        $('.login-a').hide();
        $('.join-a').hide();
    }
</script>
</html>
