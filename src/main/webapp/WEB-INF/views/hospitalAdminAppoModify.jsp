<%--
  Created by IntelliJ IDEA.
  User: dldbs
  Date: 2023-08-16
  Time: 오후 5:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>병원 예약 인원수 관리</title>
    <link href="/css/common.css" rel="stylesheet">
    <link href="/css/hospital-appo-create.css" rel="stylesheet">
    <link href="/css/admin-appo-modify.css" rel="stylesheet">
</head>
<c:import url="header.jsp"></c:import>
<body>
    <div class="container">
        <section>
            <c:import url="menu-bar.jsp"></c:import>
            <div class="select-area">
                <h1>[ 예약 관리 ]</h1>

                <h1>의사 선택</h1>
                <p id="selectedDoctor"></p>
                <div class="select-option">

                </div>

                <h1>날짜 설정</h1>
                <p id="date-status"></p>
                <input type="date" id="confirm-date">

                <div id="time-bar-area">
                    <ul id="time-list">
                        <%--                    <li>9:00~10:00</li>--%>
                    </ul>
                </div>

                <div class="detail-area">
                    <h1>디테일</h1>
                    <p id="detail-status"></p>
                    <table>
                        <thead id="thead">
                        <tr>
                            <th>No.</th>
                            <th>의사명</th>
                            <th>요일</th>
                            <th>날짜</th>
                            <th>시간</th>
                            <th>최대 예약자 수</th>
                            <th>예약자 수</th>
                            <th>예외설정 수</th>
                            <th>예약가능 수</th>
                        </tr>
                        </thead>
                        <tbody id="table-body">
                        </tbody>

                        <button onclick="pageMinus()"> < </button>
                        <p id="page">1</p>
                        <button onclick="pagePlus()"> > </button>
                    </table>
                </div>
            </div>

        </section>
    </div>
<script src="/script/hospital-admin-appo-modify.js"></script>
</body>
</html>
