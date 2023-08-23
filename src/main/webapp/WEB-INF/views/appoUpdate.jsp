<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>예약수정</title>
    <link rel="stylesheet" href="/css/hospital-detail-css.css">
    <link href="/css/appo-update-css.css" rel="stylesheet">
</head>
<c:import url="header.jsp"/>
<body>
<div class="container">
    <section>
        <p class="user-appo-title">예약수정</p>
        <div id="appo-info">
            <div class="div-appo">
                <div class="info-item">
                    <span class="info-title">예약번호</span>
                    <span class="appo-no">${appo.no}</span>
                </div>
                <div class="info-item">
                    <span class="info-title">예약병원</span>
                    <span id="hospital-name" class="appo-hospital" ykiho="${hosp.ykiho}">${hosp.hospitalName}</span>
                </div>
                <div class="info-item">
                    <span class="info-title">예약날짜</span>
                    <span class="appo-date">${time.date}</>
                </div>
                <div class="info-item">
                    <span class="info-title">예약시간</span>
                    <span class="appo-time">${time.time}</span>
                </div>
                <div class="info-item">
                    <span class="info-title">담당의사</span>
                    <span class="appo-doctor">${doc.doctorName}</span>
                </div>
                <div class="info-item">
                    <span class="info-title">자녀이름</span>
                    <span class="appo-name">${child.name}</span>
                </div>
                <div class="info-item">
                    <span class="info-title">증상</span>
                    <input type="text" id="appo-symptom" class="appo-symptom" value="${appo.symptom}">
                </div>
                <div class="info-item">
                    <span class="info-title">참고사항</span>
                    <input type="text" id="appo-note" class="appo-note" value="${appo.note}">
                </div>
            </div>
        </div>

        <p class="info-sub-title">변경할 날짜와 시간, 의사를 선택해주세요.</p>
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
            <div id="doctor-cards">

            </div>
        </div>
        <button id="booking-btn" data-ykiho="${hospital.ykiho}" onclick="appoUpdate()">예약하기</button>
    </section>
</div>
</body>
<script src="/script/Calendar.js"></script>
<script src="/script/appo-update.js"></script>
</html>
