<%-- 병원 정보 API 데이터를 받아서 지도를 표시하고 상세 병원 정보를 보여주는 페이지 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>병원 상세정보</title>
    <link rel="shortcut icon" href="/image/favicon.ico">
    <link href="/css/common.css" rel="stylesheet">
    <link href="/css/hospital-detail-css.css" rel="stylesheet">
    <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=${appkey}"></script>
</head>
<c:import url="header.jsp"></c:import>
<body>
<div class="container">
    <section>
        <div id="loading" style="display: none">
            <div class="loading-content">
                <div class="loadingio-spinner-spinner-822xu4kqp5u"><div class="ldio-zhpd3csebyc">
                    <div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div>
                </div>
                </div>
                <p>데이터를 불러오는 중입니다 잠시만 기다려주세요..</p>
            </div>
        </div>

        <input type="hidden" id="loggedInUser" value="${log}">

        <div class="likeHospital-area">

        </div>

        <div class="kakaoMap-area">
            <div id="map" style="width:100%;height:400px;">

            </div>
        </div>

        <div class="hospital-detail-info">
            <h2 class="info-title">상세정보</h2>
            <h3 id="hospital-name" style="color: #3E85EF"></h3>
            <p id="hospital-intro"></p><br>
            <p id="hospital-addr" style="margin-bottom: 10px;"></p>
            <p id="hospital-tell" style="margin-bottom: 10px;"></p>
<%--            <p id="hospital-operate-time">api - 오늘 기준 운영 시간</p>--%>
            <div id="hospital-time-table" style="margin-bottom: 10px;">
                <div id="time-table>
                    <ul id="time-table-ul style="display: flex">
                        <li id="time-table-title">
                            <div id="title-weekday">요일별 운영시간</div>
<%--                            <div id="title-workhour">진료 시간</div>--%>
                        </li>
                        <li id="time-table-mon" style="margin-right: 20px;">
                            <div id="weekday-mon">월요일</div>
                            <div id="workhour-mon">병원 문의</div>
                        </li>
                        <li id="time-table-tue" style="margin-right: 20px;">
                            <div id="weekday-tue">화요일</div>
                            <div id="workhour-tue">병원 문의</div>
                        </li>
                        <li id="time-table-wed"style="margin-right: 20px;">
                            <div id="weekday-wed">수요일</div>
                            <div id="workhour-wed">병원 문의</div>
                        </li>
                        <li id="time-table-thu" style="margin-right: 20px;">
                            <div id="weekday-thu">목요일</div>
                            <div id="workhour-thu">병원 문의</div>
                        </li>
                        <li id="time-table-fri" style="margin-right: 20px;">
                            <div id="weekday-fri">금요일</div>
                            <div id="workhour-fri">병원 문의</div>
                        </li>
                        <li id="time-table-sat" style="margin-right: 20px;">
                            <div id="weekday-sat">토요일</div>
                            <div id="workhour-sat">병원 문의</div>
                        </li>
                        <li id="time-table-sun" style="margin-right: 20px;">
                            <div id="weekday-sun">일요일</div>
                            <div id="workhour-sun">병원 문의</div>
                        </li>
                    </ul>
                </div>
            </div>
            <p id="hospital-park" style="margin-bottom: 10px;">주차 정보 : 병원 문의</p>
<%--            <div id="hospital-subjects" style="margin-bottom: 10px;">--%>
<%--                <p id="subject-list">진료과목 : </p>--%>
<%--            </div>--%>

            <div class="doctor-info" style="display: none">
                <div class="doctor-card">

                </div>
            </div>

            <h2 class="info-title" style="display: none">병원예약</h2>
            <p class="info-sub-title" style="display: none">예약은 <strong>날짜 선택 &#8680; 예약 시간 선택 &#8680; 담당 의사 선택</strong> 순서로 진행해주세요.</p>
            <div class="appo-table" style="display: none">
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

                </div>
                <div id="doctor-cards">

                </div>
            </div>
<%--            <div id="doctor-cards">--%>

<%--            </div>--%>

            <button id="booking-btn" data-ykiho="${hospital.ykiho}" style="display: none">예약하기</button>
        </div>
    </section>
</div>
<%--<script src="/script/hospital-detail.js"></script>--%>
<script src="/script/hospital-detail-kakaomap.js"></script>
<script src="/script/Calendar.js"></script>
<script src="/script/hospital-detail-new.js"></script>
<script src="/script/hospital-detail-onload.js"></script>
</body>
<c:import url="footer.jsp"></c:import>
</html>