<%--
  Created by IntelliJ IDEA.
  User: dldbs
  Date: 2023-08-08
  Time: 오후 12:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
    <title>병원 예약하기 생성</title>
</head>
<body>
    <div>
        <form method="post" onsubmit="return false">
            <input type="text" id="hospital_name" name="hospital_name" placeholder="병원명">
            <input type="text" id="hospital_announcement" name="hospital_announcement" placeholder="병원 공지사항">
            <input type="text" id="count" name="count" placeholder="현재 예약자 수">
            <input type="text" id="max" name="max" placeholder="예약 가능한 최대 인원수">

            <button onclick="appo_create()">예약 생성</button>
        </form>
    </div>

    <script src="/script/js/hospital_appo.js"></script>
</body>
</html>
