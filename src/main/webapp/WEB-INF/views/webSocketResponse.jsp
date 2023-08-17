<%--
  Created by IntelliJ IDEA.
  User: 채희재
  Date: 2023-08-17
  Time: 오전 1:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>JavaScript 코드 삽입 예시</title>
    <!-- jQuery 라이브러리 추가 -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<!-- 모달 -->
<div class="modal">
    <div class="modal-content">
        <div class="modal-body">
            <label>대상:</label>
            <input type="text" />
            <label>내용:</label>
            <textarea></textarea>
        </div>
        <button id="notifySendBtn">전송</button>
    </div>
</div>

<script>
    $(document).ready(function() {
        $('#notifySendBtn').click(function(e){
            let modal = $('.modal-content').has(e.target);
            let type = '70';
            let target = modal.find('.modal-body input').val();
            let content = modal.find('.modal-body textarea').val();
            let url = '${contextPath}/member/notify.do';

            // 전송한 정보를 db에 저장
            $.ajax({
                type: 'post',
                url: '${contextPath}/member/saveNotify.do',
                dataType: 'text',
                data: {
                    target: target,
                    content: content,
                    type: type,
                    url: url
                },
                success: function(){    // db전송 성공시 실시간 알림 전송
                    // 소켓에 전달되는 메시지
                    // 위에 기술한 EchoHandler에서 ,(comma)를 이용하여 분리시킨다.
                    socket.send("관리자,"+target+","+content+","+url);
                }
            });

            modal.find('.modal-body textarea').val('');	// textarea 초기화
        });
    });
</script>
</body>
</html>



