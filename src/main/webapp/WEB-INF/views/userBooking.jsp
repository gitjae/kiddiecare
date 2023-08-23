<%-- 병원 상세페이지에서 예약하기 버튼을 클릭하고 예약 정보를 입력하는 페이지 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>예약정보입력</title>
    <link rel="shortcut icon" href="/image/favicon.ico">
    <link href="/css/common.css" rel="stylesheet">
    <link href="/css/user-booking-css.css" rel="stylesheet">
    <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=${appkey}"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="/script/user-booking.js"></script>
    <%-- import.payment.js --%>
    <script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
</head>
<c:import url="header.jsp"></c:import>
<body>
<input type="hidden" id="loggedInUser" value="${log}">
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
                <span><input type="text" id="symptom" placeholder="증상을 적어주세요."></span>
            </div>
            <div class="info-item">
                <span class="info-title">참고사항</span>
                <span><input type="text" id="note" value="${child.info}"><br/></span>
            </div>

            <div class="form-area">
                <h4>약관동의</h4>
                <div class="term-section">
                    <input type="checkbox" id="allTerms" > 모두 동의합니다.
                </div>

                <div class="term-section">
                    <h5 class="term-title"> * 이용약관 <button class="view-btn">(펼치기)</button></h5>
                    <div class="term-content">
                        <c:import url="userTerm.jsp"></c:import>
                    </div>
                    <input type="checkbox" class="individualTerm" id="userTerm" required> 동의합니다.
                </div>
                <div class="term-section">
                    <h5 class="term-title"> * 개인정보처리방침 <button class="view-btn">(펼치기)</button></h5>
                    <div class="term-content">
                        <c:import url="privacyTerm.jsp"></c:import>
                    </div>
                    <input type="checkbox" class="individualTerm" id="privacyTerm" required> 동의합니다.
                </div>
                <div class="term-section">
                    <h5 class="term-title"> * 위치기반서비스 이용약관 <button class="view-btn">(펼치기)</button></h5>
                    <div class="term-content">
                        <c:import url="locationTerm.jsp"></c:import>
                    </div>
                    <input type="checkbox" class="individualTerm" id="locationTerm" required> 동의합니다.
                </div>

            </div>

            <div class="pay-notice-area">
                <div class="notice-box">
                    <img class="pay-notice-img" src="/image/exclamation-mark.png">
                    <p class="pay-notice-title">결제 전 꼭 확인해주세요!</p>
                </div>
                <p class="pay-notice-text">결제하신 금액은 우리동네소아과 예약금이예요.</p>
                <p class="pay-notice-text">환자의 진료 또는 시술에 의한 최종 진료 비용은 병원에서 결제가 가능해요.</p>
                <p class="pay-notice-text">예약금은 예약 취소 시 환불이 불가해요.</p>
                <input type="submit" id="payBtn" value="결제하기">
            </div>
        </form>

        <!-- 결제 모달창 시작 -->
        <div id="paymentModal" class="modal">
            <div class="modal-content">
                <span class="close">&times;</span>
                <h2>IAMPORT 결제 데모</h2>
                <li>
                    <button id="iamportPayment" type="button">결제테스트</button>
                </li>
            </div>
        </div>
        <!-- 결제 모달창 종료 -->

    </section>
</div>
</body>
<c:import url="footer.jsp"></c:import>
</html>