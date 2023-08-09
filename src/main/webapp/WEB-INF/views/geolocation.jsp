<%--
  Created by IntelliJ IDEA.
  User: regul
  Date: 2023-08-09
  Time: 오후 5:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
    <script src="/script/geolocation.js"></script>
</head>
<body>
    <h1>User's Location</h1>
    <button onclick="getUserLocation()">Get Location</button>
    <p id="locationInfo"></p>
</body>
</html>
