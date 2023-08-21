<%-- 아임포트 api 연동 카카오페이 결제 테스트 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>결제</title>
    <link href="/css/common.css" rel="stylesheet">
    <link href="/css/hospital-detail-css.css" rel="stylesheet">
    <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=${appkey}"></script>
    <script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
    <%-- import.payment.js --%>
    <script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
    <script type="text/javascript" src="/script/pay_modal.js"></script>
</head>
<c:import url="header.jsp"></c:import>
<body>
<div class="container">
    <section>
        <input type="hidden" id="loggedInUser" value="${log}">

        <h2>IAMPORT 결제 데모</h2>
        <li>
            <button id="iamportPayment" type="button">결제테스트</button>
        </li>
    </section>
</div>
</body>
<c:import url="footer.jsp"></c:import>
</html>
