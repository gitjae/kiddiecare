<%--
  Created by IntelliJ IDEA.
  User: dldbs
  Date: 2023-08-13
  Time: 오후 11:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
    <link href="/css/common.css" rel="stylesheet">
    <link href="/css/hospital-appo-management.css" rel="stylesheet">
</head>
<c:import url="header.jsp"></c:import>
<body>
<div class="container">
    <section>
        <c:import url="menu-bar.jsp"></c:import>
        <div class="select-area">
            <h1>[ 예약자 현황 ]</h1>
            <h1>의사 선택</h1>
            <div id="selectedDoctor"></div>
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
                            <th>예약상태</th>
                            <th>보호자명</th>
                            <th>환자명</th>
                            <th>증상</th>
                            <th>참고사항</th>
                        </tr>
                    </thead>
                    <tbody id="table-body">
<%--                        <tr id="detail-tr">--%>

<%--                        </tr>--%>
                    </tbody>

                </table>
            </div>
        </div>

        <div id="my-modal" class="modal">
            <div class="modal-content">
                <span class="close" onclick="closeModal()">&times;</span>
                <div id="text-area">

                </div>
            </div>

        </div>

    </section>
</div>
    <script src="/script/hospital-appo-management.js"></script>
</body>
<c:import url="footer.jsp"></c:import>
</html>
