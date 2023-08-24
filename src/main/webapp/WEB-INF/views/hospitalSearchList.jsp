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
    <title>병원목록</title>
    <link rel="shortcut icon" href="/image/favicon.ico">
    <link href="/css/common.css" rel="stylesheet">
    <link href="/css/hospital-search-css.css" rel="stylesheet">
    <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=${appkey}"></script>
    <script src="/script/hospital-search-list.js"></script>
    <script src="/script/hospital-search-kakaomap.js"></script>
</head>
<c:import url="header.jsp"></c:import>
<body>
    <section>
        <div id="loading" style="display: none">
            <div class="loading-content">
                <div class="loadingio-spinner-spinner-822xu4kqp5u"><div class="ldio-zhpd3csebyc">
                    <div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div>
                </div>
                </div>
                <p>데이터를 불러오는 중입니다 잠시만 기다려주세요..</p>
            </div>
        </div>

        <div id="hospital-list">
            <div class="flex-area">
                <p class="list-title">병원 목록</p>
                <div class="index-search">
                    <input type="text" id="search-input" class="search-input" placeholder="검색어를 입력해주세요." name="searchText" maxlength="100">
                    <button type="submit" class="search-btn" onclick="search()">
                        <img src="https://s3.ap-northeast-2.amazonaws.com/cdn.wecode.co.kr/icon/search.png" onclick="">
                    </button>
                </div>
                <div id="hospital-list-btn">
                    <button id="prev" onclick="prev()" style="background: none; color: #007BFF; font-size: larger; font-weight: 700;">&#9665;</button>
                    <button id="next" onclick="next()" style="background: none; color: #007BFF; font-size: larger; font-weight: 700;">&#9655;</button>
                </div>
            </div>
            <ul id="hospital-list-ul">

            </ul>
        </div>


        <div class="kakaoMap-area" style="width:100%;height: 700px;">
            <div id="map" style="width:100%;height:100%; margin-top: 50px;">
                <button style="position: absolute; z-index: 2; margin: 20px;" onclick="getUserLocation()">내 주변 병원 찾기</button>
            </div>
        </div>
    </section>
</body>
<c:import url="footer.jsp"></c:import>
</html>
