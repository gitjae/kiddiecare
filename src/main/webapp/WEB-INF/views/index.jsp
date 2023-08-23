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
            <div class="session-title">
                <p class="top-title">주변에 예약 가능한 병원이 어디지?</p>
                <p class="bottom-title">이제 한 눈에 주변 병원을 확인하고 실시간으로 예약해보세요!</p>
            </div>
            <div class="index-search">
                <input type="text" id="search-input" class="search-input" placeholder="병원명으로 검색할 수 있어요." name="searchText" maxlength="100">
                <button type="submit" class="search-btn" onclick="search()">
                    <img src="https://s3.ap-northeast-2.amazonaws.com/cdn.wecode.co.kr/icon/search.png" onclick="">
                </button>
            </div>
        </div>

        <div class="shortCut-area">
            <div class="find-nearby-hospital-div">
                <h2>주변 병원 찾기</h2>
                <p>위치 기반으로 내 주변에 있는 병원 검색 가능합니다.<br>병원 예약은 로그인 후에 가능해요!</p>
                <button id="one-hospitalPage">주변 병원 찾기</button>
                <img src="/image/medical-team.png">
            </div>

            <div class="shortCut-area2">
            <div class="favorite-hospital-div">
                <h2>찜한 병원</h2>
                <p>자주 가는 병원을 등록하면 검색할 필요없이 원하는 병원만 검색할 수 있어요.</p>
                <button id="like-hospitalPage">찜한 병원</button>
                <img src="/image/favorite.png">
            </div>

            <div class="admin-myPage">
                <h2>병원 어드민(사이트 도움말로 변경)</h2>
                <p>병원 관계자는 병원 정보 수정과 환자 예약관리를 할 수 있어요.</p>
                <button id="admin-appo">병원 마이페이지</button>
                <img src="/image/userGuide.png">
            </div>
            </div>

            <div class="user-myPage">
                <h2>회원 마이페이지</h2>
                <p>회원은 예약정보 수정과 자녀 관리가 가능해요.</p>
                <button id="user-myPage">회원 마이페이지</button>
                <img src="/image/edit.png">
            </div>
        </div>
        <a href="/appointment/hospitalDetail?hospitalName=가톨릭대학교인천성모병원&sgguCd=220003">가톨릭대학교인천성모병원 숏컷</a>
    </section>
</div>
</body>
<c:import url="footer.jsp"></c:import>
</html>
