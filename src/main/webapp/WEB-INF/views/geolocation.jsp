<%--
  Created by IntelliJ IDEA.
  User: regul
  Date: 2023-08-09
  Time: 오후 5:52
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
    <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey="></script>
    <script src="/script/geolocation.js"></script>
</head>
<body>
<div class="container">
    <section>
        <div id="map" style="width:500px;height:400px;">
            <button style="position: absolute; z-index: 2;" onclick="getUserLocation()">내 주변 병원 찾기</button>
        </div>
        <div id="hospital-list">

        </div>
    </section>
</div>
</body>
</html>
