<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>예약완료</title>
    <link rel="shortcut icon" href="/image/favicon.ico">
    <link href="/css/common.css" rel="stylesheet">
    <style>
        .completed-div{
            padding: 50px;
            display: flex;
            flex-direction: column;
            margin: 0 auto;
            margin-top: 50px;
            border: 1px solid #e0e0e0;
            border-radius: 5px;
            margin-bottom: 20px;
            width: 600px;
        }

        .completed-div img{
            width: 200px;
            height: 200px;
            margin: 0 auto;
            margin-bottom: 50px;
        }

        .completed-title{
            font-size: xx-large;
            text-align: center;
            margin-bottom: 20px;
        }

        .completed-sub-title{
            text-align: center;
            font-size: large;
        }

        .move-index{
            text-decoration-line: underline;
            color: #3E85EF;
            text-decoration-color: #3E85EF;
            text-align: center;
            margin-top: 50px;
        }
    </style>
</head>
<c:import url="header.jsp"></c:import>
<body>
<div class="container">
    <section>
        <div class="completed-div">
            <img src="/image/booking-completed.png">
            <p class="completed-title">예약이 완료되었습니다!</p>
            <p class="completed-sub-title">예약 내용 확인 및 수정/취소는 마이페이지에서 가능해요.</p>
            <a role="link"  href="/" class="move-index">홈으로 돌아가기</a>
        </div>
    </section>
</div>
</body>
<c:import url="footer.jsp"></c:import>
</html>
