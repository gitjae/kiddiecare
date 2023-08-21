<%-- 아임포트 api 연동 카카오페이 결제 테스트 --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>결제</title>
    <script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
    <%-- import.payment.js --%>
    <script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
    <script type="text/javascript" src="/script/pay_modal.js"></script>
</head>
<body>
    <div>
        <h2>IAMPORT 결제 데모</h2>
        <li>
            <button id="iamportPayment" type="button">결제테스트</button>
        </li>
    </div>
</body>
</html>
