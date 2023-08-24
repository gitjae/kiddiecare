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

<body>
    <div id="select-area-modify">

        <h1>예약 관리</h1>

        <h3>날짜 설정</h3>
        <p id="date-status"></p>

        <div id="detail-area">
            <input type="date" id="lookup-date">

            <div id="no-detail" style="display: none;">해당 날짜에 예약을 생성하지 않았습니다.</div>
            <div class="detail-change-area">
                <table>
                    <h3>상세 스케줄</h3>
                    <button id="reset" onclick="reset()">DB내용 다시 불러오기</button>
                    <thead id="thead">
                    <tr>
<%--                        <th>No.</th>--%>
                        <th>의사명</th>
                        <th>요일</th>
                        <th>날짜</th>
                        <th>시간</th>
                        <th>총 예약 허용 인원</th>
                        <th>예약 예외 인원</th>
                        <th>현재 예약 인원</th>
                        <th>예약 가능 인원</th>
                        <th>수정하기</th>
                    </tr>
                    </thead>
                    <tbody id="appo-mo-table">
                    </tbody>

                </table>

            </div>

        </div>
    </div>


</body>
</html>
