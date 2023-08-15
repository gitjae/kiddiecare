<%--
  Created by IntelliJ IDEA.
  User: dldbs
  Date: 2023-08-15
  Time: 오후 1:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

</head>
    <body>
    <div class="menu-bar-area">
        <div id="hospital-name">
            <input type="text" id="ykiho" name="ykiho" placeholder="병원코드" value="${Ykiho}" style="display: none">
            <span><span id="hospital_name"></span>병원</span>
            <span>${log}님</span>
        </div>
        <div id="menu-bar">
            <a><span>스케줄 생성</span></a>
            <a><span>예약자 현황</span></a>
            <a><span>공지사항 설정</span></a>
            <a><span>회원정보 변경</span></a>
        </div>
    </div>
    <script src="/script/menu-bar.js"></script>
</body>
</html>
