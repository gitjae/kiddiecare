<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>우리동네소아과</title>
    <link href="/css/common.css" rel="stylesheet">
    <link href="/css/index.css" rel="stylesheet">
    <script src="/script/index.js"></script>
</head>
<c:import url="header.jsp"></c:import>
<body>
<div class="container">
    <section>
        <div class="index-session">
            <div class="img-wrapper">
                <div class="session-title">
                    <p class="top-title">주변의 예약가능한 병원을 손쉽게 찾아보세요</p>
                    <p class="bottom-title">&#x2714; 병원검색 &nbsp; &#x2714; 진료현황 &nbsp; &#x2714; 실시간 예약</p>
                </div>
                <div class="index-search">
                    <input type="text" id="search-input" class="search-input" placeholder="병원명 또는 지역으로 검색할 수 있어요."
                           name="searchText" maxlength="100">
                    <button type="submit" class="search-btn" onclick="search()">
                        <img src="/image/search-white.png" onclick="">
                    </button>
                </div>
            </div>
        </div>

        <div class="shortCut-container">
            <p class="shortCut-title">바로가기</p>
            <div class="shortCut-area">
                <div id="find-nearby-hospital-div">
                    <img src="/image/medical-team.png">
                    <p>주변 병원 찾기</p>
                </div>

                <div id="favorite-hospital-div">
                    <img src="/image/favorite.png">
                    <p>찜한 병원</p>
                </div>

    <%--            <div class="admin-myPage">--%>
    <%--                <p>병원 어드민(사이트 도움말로 변경)</p>--%>
    <%--                <img src="/image/userGuide.png">--%>
    <%--            </div>--%>

                <div id="user-myPage">
                    <img src="/image/edit.png">
                    <p>마이페이지</p>
                </div>
            </div>
        </div>

        <a href="/appointment/hospitalDetail?hospitalName=가톨릭대학교인천성모병원&sgguCd=220003">가톨릭대학교인천성모병원 숏컷</a>
    </section>
</div>
</body>
<c:import url="footer.jsp"></c:import>
</html>
