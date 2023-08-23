 var socket  = null;
$(document).ready(function(){
    // 웹소켓 연결
    let sock = new SockJS('/echo-ws');
    socket = sock;

    // 웹소켓 연결
    sock.onopen = function () {
        console.log('웹소켓 연결 성공');
    }

    sock.onclose = function () {
        console.log('웹소켓 연결 종료');
    }

// 데이터를 전달 받았을 때
    sock.onmessage = function (e) {
        console.log('데이터 수신: ' + e.data);
        // toast 생성
    };
    // 데이터를 전달 받았을때
    sock.onmessage = onMessage; // toast 생성

    // toast생성 및 추가
    function onMessage(evt){
        var data = evt.data;
        // toast
        let toast = "<div class='toast' role='alert' aria-live='assertive' aria-atomic='true'>";
        toast += "<div class='toast-header'><i class='fas fa-bell mr-2'></i><strong class='mr-auto'>알림</strong>";
        toast += "<small class='text-muted'>just now</small><button type='button' class='ml-2 mb-1 close' data-dismiss='toast' aria-label='Close'>";
        toast += "<span aria-hidden='true'>&times;</span></button>";
        toast += "</div> <div class='toast-body'>" + data + "</div></div>";
        $("#msgStack").append(toast);   // msgStack div에 생성한 toast 추가
    }

    $('#notifySendBtn').click(function(e){
        let modal = $('#message').has(e.target); // 메세지
        let type = '70'; // 타입 스트링
        let target = $('#targetUserId').val(); // 보낼id
        let content = $('#message').val(); // 보낼내용
        let url = '/websocket'; // url
        socket.send("관리자,"+target+","+content+","+url); // 내용
        $('#message').val('');// textarea 초기화
        // 전송한 정보를 db에 저장
        // $.ajax({
        //     type: 'POST',
        //     url: '/websocket/member',
        //     data: {
        //         target: target,
        //         content: content,
        //         type: type,
        //         url: url
        //     },
        //     success: function(response){    // db전송 성공시 실시간 알림 전송
        //         // 소켓에 전달되는 메시지
        //         // 위에 기술한 EchoHandler에서 ,(comma)를 이용하여 분리시킨다.
        //         if(response === ""){
        //             console.log("dd")
        //         }else{
        //             console.log("ddd")
        //         }
        //         socket.send("관리자,"+target+","+content+","+url);
        //     },
        //     error: function(xhr, status, error) {
        //         console.log(error);
        //         alert("인증번호를 다시 확인해주세요.");
        //     }
        // });
    });
});



