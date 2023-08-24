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
    <link rel="shortcut icon" href="/image/favicon.ico">
    <link href="/css/common.css" rel="stylesheet">
    <link href="/css/hospital-appo-management.css" rel="stylesheet">
</head>
<body>
        <div class="select-area">
            <h1>예약자 현황</h1>

            <h3>날짜 설정</h3>
            <p id="date-status"></p>
            <input type="date" id="confirm-date">

            <div id="no-appo" style="display: none;">해당 시간에 예약이 없습니다.</div>
            <div id="time-bar-area" >
                <ul id="time-list">

                </ul>
            </div>


            <div class="detail-area">
                <table id="appo-table">
                    <h3>예약자 상세정보</h3>
                    <thead id="thead">
                        <tr>
                            <th>No.</th>
<%--                            <th>예약생성일</th>--%>
                            <th>예약상태</th>
                            <th>보호자명</th>
                            <th>환자명</th>
                            <th>증상</th>
                            <th>참고사항</th>
                        </tr>
                    </thead>
                    <tbody id="table-body">
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


</body>
</html>
