<%-- 병원 정보 API 데이터를 받아서 지도를 표시하고 상세 병원 정보를 보여주는 페이지 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>병원 상세정보</title>
    <link href="/css/hospital-detail-css.css" rel="stylesheet">
</head>
<body>

<div class="kakaoMap-area">
    카카오맵 지도 영역
</div>

<%--<c:forEach var="hospital" items="${hospitals}">--%>
<div class="hospital-detail-info">
    <h2 class="info-title">상세정보</h2>
    <h3 id="hospital-name">${hospital.hospitalName}</h3>
    <h3 id="hospital-addr">병원DB) 주소</h3>
    <h3 id="hospital-operate-time">병원DB) 운영시간</h3>
    <h3 id="hospital-intro">${hospital.hospitalIntro}</h3>

    <div class="doctor-info">
        <c:forEach items="${doctors}" var="doctor">
            <div class="doctor-card">
                <p>&#127976; &#128138;</p>
                <p id="doctor-no">의사 번호 : ${doctor.no}</p>
                <p id="doctor-name">의사 이름 : ${doctor.doctorName}</p>
                <p id="doctor-offDay">의사DB) 휴진일</p>
            </div>
        </c:forEach>
    </div>
    <%--    </c:forEach>--%>

    <table class="Calendar">
        <thead>
        <tr>
            <td onClick="prevCalendar();" style="cursor:pointer;">&#60;</td>
            <td colspan="5">
                <span id="calYear"></span>년
                <span id="calMonth"></span>월
            </td>
            <td onClick="nextCalendar();" style="cursor:pointer;">&#62;</td>
        </tr>
        <tr>
            <td>일</td>
            <td>월</td>
            <td>화</td>
            <td>수</td>
            <td>목</td>
            <td>금</td>
            <td>토</td>
        </tr>
        </thead>
        <tbody>
        </tbody>
    </table>

    <div class="book-time-table">
        병원DB) 예약시간 테이블 표
    </div>

    <button id="booking-btn">예약하기</button>

</div>
<script src="/script/hospital-detail.js"></script>
</body>
</html>
