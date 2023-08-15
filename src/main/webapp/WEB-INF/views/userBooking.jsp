<%-- 병원 상세페이지에서 예약하기 버튼을 클릭하고 예약 정보를 입력하는 페이지 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>사용자 예약 페이지</title>
    <link href="/css/common.css" rel="stylesheet">
    <link href="/css/user-booking-css.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="/script/user-booking.js"></script>
</head>
<c:import url="header.jsp"></c:import>
<body>
<div class="container">
    <section>
        <p class="user-appo-title">진료예약</p>

        <form action="/reserve" method="post">
            <div class="info-item">
                <span class="info-title">병원명</span>
                <span><input type="text" id="hospitalName" value="${hospitalName}" readonly></span>
            </div>
            <div class="info-item">
                <span class="info-title">진료날짜</span>
                <span><input type="text" id="treatmentDate" value="${treatmentDate}" readonly></span>
            </div>
            <div class="info-item">
                <span class="info-title">진료일</span>
                <span><input type="text" id="treatmentDay" value="${treatmentDay}" readonly></span>
            </div>
            <div class="info-item">
                <span class="info-title">진료시간</span>
                <span><input type="text" id="treatmentTime" value="${slotTime}" readonly></span>
                <input type="hidden" id="timeSlotNo" value="${timeSlotNo}" />
            </div>
            <div class="info-item">
                <span class="info-title">보호자명</span>
                <span><input type="text" id="guardian" value=${userName} readonly></span>
                <input type="hidden" id="parentId" value="${parentId}" />
                <input type="hidden" id="log" value="${log}" />
            </div>
            <div class="info-item">
                <span class="info-title">자녀정보</span>
                <div id="childrenContainer">

                </div>
                <input type="hidden" id="selectedChildNo" />
            </div>
            <div class="info-item">
                <span class="info-title">증상</span>
                <span><input type="text" id="symptom" placeholder="아픈곳을 적어주세요."></span>
            </div>
            <div class="info-item">
                <span class="info-title">참고사항</span>
                <span><input type="text" id="note" placeholder="병원에서 참고할 부분을 알려주세요."><br/></span>
            </div>
            <div class="form-area">
                <h4>약관동의</h4>
                <div class="term-section">
                    <h5 class="term-title"> * 이용약관 <button class="view-btn">(펼치기)</button></h5>
                    <div class="term-content">
                        <c:import url="userTerm.jsp"></c:import>
                    </div>
                    <input type="checkbox" id="userTerm" required> 동의합니다.
                </div>
                <div class="term-section">
                    <h5 class="term-title"> * 개인정보처리방침 <button class="view-btn">(펼치기)</button></h5>
                    <div class="term-content">
                        <c:import url="privacyTerm.jsp"></c:import>
                    </div>
                    <input type="checkbox" id="privacyTerm" required> 동의합니다.
                </div>
                <div class="term-section">
                    <h5 class="term-title"> * 위치기반서비스 이용약관 <button class="view-btn">(펼치기)</button></h5>
                    <div class="term-content">
                        <c:import url="locationTerm.jsp"></c:import>
                    </div>
                    <input type="checkbox" id="locationTerm" required> 동의합니다.
                </div>
            </div>

            <input type="submit" id="payBtn" value="결제하기 패스하고 예약꽂기">
        </form>
    </section>
</div>
</body>
<c:import url="footer.jsp"></c:import>
</html>