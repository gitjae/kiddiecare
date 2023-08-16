<%-- 병원 정보 API 데이터를 받아서 지도를 표시하고 상세 병원 정보를 보여주는 페이지 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>병원 상세정보</title>
    <link href="/css/common.css" rel="stylesheet">
    <link href="/css/hospital-detail-css.css" rel="stylesheet">
    <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=89098ae2758f1c766fe8ab93b869f264"></script>
    <script src="/script/hospital-detail-kakaomap.js"></script>
</head>
<c:import url="header.jsp"></c:import>
<body>
<div class="container">
    <section>
        <div class="kakaoMap-area">
            <div id="map" style="width:100%;height:100%;">

            </div>
        </div>

        <div class="hospital-detail-info">
            <h2 class="info-title">상세정보</h2>
            <h3 id="hospital-name"></h3>
            <p id="hospital-intro"></p><br>
            <p id="hospital-addr">api - 주소주소주소</p>
            <p id="hospital-tell"></p>
            <p id="hospital-operate-time">api - 오늘 기준 운영 시간</p>
            <div id="hospital-time-table">
                <div id="time-table>
                    <ul id="time-table-ul>
                        <li id="time-table-title">
                            <div id="title-weekday">요일</div>
                            <div id="title-workhour">진료 시간</div>
                        </li>
                        <li id="time-table-mon">
                            <div id="weekday-mon">월요일</div>
                            <div id="workhour-mon">데이터 없음</div>
                        </li>
                        <li id="time-table-tue">
                            <div id="weekday-tue">화요일</div>
                            <div id="workhour-tue">데이터 없음</div>
                        </li>
                        <li id="time-table-wed">
                            <div id="weekday-wed">수요일</div>
                            <div id="workhour-wed">데이터 없음</div>
                        </li>
                        <li id="time-table-thu">
                            <div id="weekday-thu">목요일</div>
                            <div id="workhour-thu">데이터 없음</div>
                        </li>
                        <li id="time-table-fri">
                            <div id="weekday-fri">금요일</div>
                            <div id="workhour-fri">데이터 없음</div>
                        </li>
                        <li id="time-table-sat">
                            <div id="weekday-sat">토요일</div>
                            <div id="workhour-sat">데이터 없음</div>
                        </li>
                        <li id="time-table-sun">
                            <div id="weekday-sun">일요일</div>
                            <div id="workhour-sun">데이터 없음</div>
                        </li>
                    </ul>
                </div>
            </div>
            <p id="hospital-park">주차 정보 : 데이터 없음</p>
            <div id="hospital-subjects">
                <p id="subject-list">진료과목 : </p>
            </div>

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