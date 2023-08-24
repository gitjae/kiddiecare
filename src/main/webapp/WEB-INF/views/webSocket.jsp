<%--
  Created by IntelliJ IDEA.
  User: 채희재
  Date: 2023-08-17
  Time: 오전 11:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>WebSocket Example</title>
    <link rel="shortcut icon" href="/image/favicon.ico">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/stompjs@2.3.3/lib/stomp.min.js"></script>
    <script src="/script/websocket.js"></script>
</head>
<body>

<h1>WebSocket 연습</h1>

<p>안녕하세요, ${userId}님!</p>
<p>보낼 사용자 ID: <input type="text" id="targetUserId"></p>
<div id="msgStack"></div>

<form id='messageForm'>
    <label for="message">메시지 입력:</label><br/>
    <input type='text' id='message' name='message' required /><br/>

    <button type='button' id="notifySendBtn">보내기</button><br/>
</form>
</body>
</html>
