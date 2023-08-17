<%--
  Created by IntelliJ IDEA.
  User: 채희재
  Date: 2023-08-10
  Time: 오후 12:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>List</title>
    <link href="/css/common.css" rel="stylesheet">
    <link href="/css/hospital-search-css.css" rel="stylesheet">
    <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=89098ae2758f1c766fe8ab93b869f264"></script>
    <script src="/script/hospital-search-list.js"></script>
    <script src="/script/hospital-search-kakaomap.js"></script>
</head>
<c:import url="header.jsp"></c:import>
<body>
    <section>
<%--        <c:when test="${empty response}">--%>
<%--            <div class="data-notfund">--%>
<%--                <p>찾는 병원이 없어요..</p>--%>
<%--            </div>--%>
<%--        </c:when>--%>
<%--        <c:otherwise>--%>
<%--            <c:forEach items="${response}" var="item">--%>
<%--                <div class="hospList">--%>
<%--                    <a>--%>
<%--                    </a>--%>
<%--                    <span>병원명 : ${item.yadmNm}</span>--%>
<%--                    <span>병원 주소: ${item.addr}</span>--%>
<%--                    <span>병원 번호: 미정</span>--%>
<%--                    <span>운영시간: 미정</span>--%>
<%--                    <span>휴진일: 미정</span>--%>
<%--                </div>--%>
<%--            </c:forEach>--%>
<%--        </c:otherwise>--%>

        <div id="hospital-list">
            <div class="flex-area">
                <p class="list-title">병원 목록</p>
                <div id="hospital-list-btn">
                    <button id="prev" onclick="prev()">&#128281;</button>
                    <button id="next" onclick="next()">&#128284;</button>
                </div>
            </div>
            <ul id="hospital-list-ul">

            </ul>
<%--            <div id="hospital-list-btn">--%>
<%--                <button id="prev" onclick="prev()">이전</button>--%>
<%--                <button id="next" onclick="next()">다음</button>--%>
<%--            </div>--%>
        </div>

        <div class="kakaoMap-area" style="width:100%;height: 700px;">  <!-- height: 400px; -->
            <div id="map" style="width:100%;height:100%; margin-top: 50px;">
                <button style="position: absolute; z-index: 2; margin: 20px;" onclick="getUserLocation()">내 주변 병원 찾기</button>
            </div>
        </div>
    </section>
</body>
<c:import url="footer.jsp"></c:import>
</html>
