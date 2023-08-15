<%-- 병원 정보 API 데이터를 받아서 지도를 표시하고 상세 병원 정보를 보여주는 페이지 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>병원 상세정보</title>
    <link href="/css/common.css" rel="stylesheet">
    <link href="/css/hospital-detail-css.css" rel="stylesheet">
</head>
<c:import url="header.jsp"></c:import>
<body>
<div class="container">
    <section>
        <div class="kakaoMap-area">
            카카오맵 지도 영역
        </div>

        <div class="hospital-detail-info">
            <h2 class="info-title">상세정보</h2>
            <h3 id="hospital-name"></h3>
            <h3 id="hospital-addr"></h3>
            <h3 id="hospital-operate-time"></h3>
            <h3 id="hospital-intro"></h3>

            <div class="doctor-info">
                <div class="doctor-card">

                </div>
            </div>

            <h2 class="info-title">병원예약</h2>
            <div class="appo-table">
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

                <div class="time-slots-table">
                    <c:forEach items="${timeSlotsLimits}" var="timeSlotsLimit">
                        <div class="time-slot-card">
                            <div class="time-slot-content">
                                    ${timeSlotsLimit.time}<br>(${timeSlotsLimit.count}/${timeSlotsLimit.enable})
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>

            <button id="booking-btn" data-ykiho="${hospital.ykiho}">예약하기</button>
        </div>
    </section>
</div>
<script src="/script/hospital-detail.js"></script>
</body>
<c:import url="footer.jsp"></c:import>
</html>