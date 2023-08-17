<%--
  Created by IntelliJ IDEA.
  User: 채희재
  Date: 2023-08-17
  Time: 오전 1:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<!-- sockJS -->
<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
<script>
    $(document).ready(function () {
        // 웹소켓 연결
        socket = new SockJS("<c:url value=\"/echo-ws\"/>");

        // 데이터를 전달받았을 때 toast 생성
        socket.onmessage = function (evt) {
            var data = evt.data;
            // toast
            let toast = "<div class='toast' role='alert' aria-live='assertive' aria-atomic='true'>";
            toast += "<div class='toast-header'><i class='fas fa-bell mr-2'></i><strong class='mr-auto'>알림</strong>";
            toast += "<small class='text-muted'>just now</small><button type='button' class='ml-2 mb-1 close' data-dismiss='toast' aria-label='Close'>";
            toast += "<span aria-hidden='true'>&times;</span></button>";
            toast += "</div> <div class='toast-body'>" + data + "</div></div>";
            $("#msgStack").append(toast);   // msgStack div에 생성한 toast 추가
            $(".toast").toast({"animation": true, "autohide": false});
            $('.toast').toast('show');
        };
    });
</script>
<body>
<div id="msgStack"></div>
</body>
</html>

