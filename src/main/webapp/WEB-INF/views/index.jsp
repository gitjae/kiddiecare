<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: regul
  Date: 2023-08-08
  Time: 오후 3:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>우리동네소아과</title>
</head>
<c:import url="header.jsp"></c:import>
<body>
    <h1>우리동네소아과 Index</h1>

    <button id="one-hospitalPage">병원상세페이지(단일)</button>
    <script>
        document.getElementById('one-hospitalPage').addEventListener('click',e=>{
            window.location = '/appointment/hospitalDetail?ykiho=JDQ4MTYyMiM1MSMkMSMkMCMkODkkMzgxMzUxIzExIyQxIyQzIyQ3OSQyNjE4MzIjNDEjJDEjJDgjJDgz';
        });
    </script>

    <button id="two-hospitalPage">병원상세페이지(여러개/개발 전)</button>

    <button id="admin-appo">어드민 예약 페이지</button>
    <script>
        document.getElementById('admin-appo').addEventListener('click',e=>{
            window.location = '/admin/appointment';
        });
    </script>

</body>
</html>
