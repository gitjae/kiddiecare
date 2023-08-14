<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>우리동네소아과</title>
    <link href="/css/common.css" rel="stylesheet">
    <link href="/css/index.css" rel="stylesheet">
    <script>
        window.onload = function() {
            document.getElementById('one-hospitalPage').addEventListener('click', e => {
                window.location = '/appointment/hospitalDetail?hospitalName=가톨릭대학교인천성모병원'; // 기능 테스트를 위한 예시(수정 필요)
            });

            document.getElementById('admin-appo').addEventListener('click', e => {
                window.location = '/admin/appointment';
            });
        }
    </script>
</head>
<c:import url="header.jsp"></c:import>
<body>
<div class="container">
    <section>
        <div class="index-session">
            <div class="session-title">
                <p>병원이 닫고 나서도, 급하게 방문할 때도</p>
                <p>병원의 진료 정보를 실시간으로 확인해 보세요!</p>
            </div>
            <div class="index-search">
                <input type="text" class="search-input" placeholder="병원명 또는 지역으로 검색할 수 있어요." name="searchText" maxlength="100">
                <button type="submit" class="search-btn">검색</button>
            </div>
        </div>

        <div class="shortCut-area">
            <button id="one-hospitalPage">병원상세페이지(단일)</button>
            <button id="two-hospitalPage">병원상세페이지(여러개/개발 전)</button>
            <button id="admin-appo">어드민 예약 페이지</button>
        </div>
    </section>
</div>
</body>
<c:import url="footer.jsp"></c:import>
</html>
