<%-- 병원 상세페이지에서 예약하기 버튼을 클릭하고 예약 정보를 입력하는 페이지 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>사용자 예약 페이지</title>
    <script src="/script/user-booking.js"></script>
</head>
<c:import url="header.jsp"></c:import>
<body>
<h3>진료예약</h3>

<form action="/reserve" method="post">
    <p>병원명</p>
    <input type="text" id="hospitalName" value=${hospital.hospitalName} readonly>
    <p>병원주소</p>
    <input type="text" id="hospitalAddr" value="hospitalAddr" readonly>
    <p>진료날짜</p>
    <input type="text" id="treatmentDate" value="" readonly>
    <p>진료일</p>
    <input type="text" id="treatmentDay" value="" readonly>
    <p>보호자명</p>
    <input type="text" id="guardian" value=${userName} readonly>            <%-- 수정 필요 --%>
    <p>자녀정보</p>
    <input type="text" id="children" value="children">
    <p>증상</p>
    <input type="text" id="symptom" value="symptom">
    <p>참고사항</p>
    <input type="text" id="note" value="note"><br/>

    <input type="submit" id="payBtn" value="결제하기">
</form>

</body>
</html>