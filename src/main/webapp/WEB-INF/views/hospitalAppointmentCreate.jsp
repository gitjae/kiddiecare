<%--
  Created by IntelliJ IDEA.
  User: dldbs
  Date: 2023-08-11
  Time: 오후 7:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>AppointmentCreate</title>

    <!-- jQuery UI -->
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
    <script src="/script/hospital_appo_create.js"></script>

    <link href="/css/hospital_appo_create.css" rel="stylesheet">
</head>
<c:import url="header.jsp"></c:import>
<body>
    <section>
        <div class="menu-bar-area">
            <div id="hospital-name">
                <input type="text" id="ykiho" name="ykiho" placeholder="병원코드" value="${Ykiho}" style="display: none">
                <span><span id="hospital_name"></span>병원</span>
                <span>${log}님</span>
            </div>
            <div id="menu-bar">
                <a><span>스케줄 생성</span></a>
                <a><span>예약자 현황</span></a>
                <a><span>공지사항 설정</span></a>
                <a><span>회원정보 변경</span></a>
            </div>
        </div>
        <div class="select-area">
            <h1>스케줄 생성</h1>
            <h1>의사 선택</h1>
            <div class="select-option">
                <div class="doctor-area">

                </div>
                <div class="doctor-area">

                </div>
                <div class="doctor-area">

                </div>
            </div>

            <div class="date-set-area">
                <h1>날짜 설정</h1>
                <input type="date" id="start-date">
                부터 <input type="date" id="end-date">
                <button id="set-date" onclick="setDate()">날짜 범위 설정</button>

                <div>
                    <h3>제외날짜</h3>
                    <input type="date" id="except-day">
                    <input type="button" id="except-add" onclick="exceptAdd()" value="추가">
                    <div>
                        <input type="checkbox" id="except-holidays">
                        <label for="except-holidays">공휴일제외</label>
                        <input type="checkbox" id="except-sunday">
                        <label for="except-sunday">일요일제외</label>
                    </div>
                    <div id="except-days-area">

                    </div>
                </div>
                <button id="time-set-btn" onclick="timeSetBtn()">시간 범위 설정하기</button>
            </div>

            <div class="time-set-area">
                <h1>시간 범위 설정</h1>
                <div id="set-weekday">

                </div>
            </div>

            <div class="meal-time-set-area">
                <h1>식사 시간 설정</h1>
                <div id="lunch">
                    <h3>점심시간</h3>
                    <input type="checkbox" id="no-lunch">
                    <label for="no-lunch">점심시간 없음</label>
                    <input type="number" id="l-start-hour" min="1" max="12" value="12"/>
                    <input type="number" id="l-start-minute" value="00" readonly/>
                    부터
                    <input type="number" id="l-end-hour" value="01"/>
                    <input type="number" id="l-end-minute" value="00" readonly/>
                </div>

                <div id="dinner">
                    <h3>저녁시간</h3>
                    <input type="checkbox" id="no-dinner">
                    <label for="no-dinner">저녁시간 없음</label>
                    <input type="number" id="d-start-hour" min="1" max="12" value="6"/>
                    <input type="number" id="d-start-minute" value="00" readonly/>
                    부터
                    <input type="number" id="d-end-hour" min="1" max="12" value="7"/>
                    <input type="number" id="d-end-minute" value="00" readonly/>
                </div>

                <button>예약 생성하기</button>
            </div>
        </div>

    </section>
    <script src="/script/hospital_appo_create.js"></script>
</body>
</html>
