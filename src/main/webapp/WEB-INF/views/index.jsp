<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>우리동네소아과</title>
    <link href="/css/common.css" rel="stylesheet">
</head>
<c:import url="header.jsp"></c:import>
<body>
<div class="container">
    <section>
        <h1>우리동네소아과 Index</h1>

        <button id="one-hospitalPage">병원상세페이지(단일)</button>
        <script>
            document.getElementById('one-hospitalPage').addEventListener('click',e=>{
                window.location = '/appointment/hospitalDetail?hospitalName=가톨릭대학교인천성모병원';
                // window.location = 'api/appointment/hospitalDetail?hospitalName=가톨릭대학교인천성모병원';
            });
        </script>

        <button id="two-hospitalPage">병원상세페이지(여러개/개발 전)</button>

        <button id="admin-appo">어드민 예약 페이지</button>
        <script>
            document.getElementById('admin-appo').addEventListener('click',e=>{
                window.location = '/admin/appointment';
            });
        </script>
    </section>
</div>
</body>
<c:import url="footer.jsp"></c:import>
</html>
