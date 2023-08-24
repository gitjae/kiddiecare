// 로그아웃
function logout() {
    $.ajax({
        method: "POST",
        url: '/api/v1/logout',
    }).done(function() {
        // 로그아웃 후 로그인/회원가입 버튼 및 알림 아이콘 갱신, 페이지 이동
        location.href = '/';
    });
}

// 알림창 토글 버튼
var box = document.getElementById('box');
var down = false;

function toggleNotifi() {
    if (down) {
        $('.notifi-box').hide();
        $('#count, #alarm').text('').hide();
        down = false;
    } else {
        $('.notifi-box').show();
        down = true;
    }
}

// 웹소켓 연결 및 데이터 수신
var socket = null;
$(document).ready(function() {
    if ($('.admin-index').length > 0) {
        $('.notifi-box').css('right', '110px');


    }
    var sessionValue = $('#sessionValue').val();

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

    // session 존재 시 db에서 불러오기
    if(sessionValue !== undefined){
        $.ajax({
            type: 'POST',
            url: '/alarm/check',
            data : {
                id : sessionValue,

            }
        }).done (function (response) {
            // 받아올 알람이 있을 때만
            if(Array.isArray(response.content) && response.content.length > 0) {
                response.content.forEach((detail) => {
                    let alarm = "<div class='text'><p>" + detail.alarmText + "</p></div>";
                    $(".notifi-area").append(alarm);
                })
            }
        }).fail(function (error){
            console.log("알람에러: "+error);
        })

    }

    if (sessionValue !== undefined && sessionValue !== "") {
        console.log(sessionValue);
        let sock = new SockJS('/echo-ws');
        socket = sock;

        sock.onopen = function() {
            console.log('웹소켓 연결 성공');
        }

        sock.onclose = function() {
            console.log('웹소켓 연결 종료');
        }

        sock.onmessage = onMessage;

        // 메세지가 왔을 때
        function onMessage(evt) {
            countAdd();
            alarmAdd();
            var data = evt.data;
            getToast(data);
            let alarm = "<div class='text'><p>" + data + "</p></div>";
            $(".notifi-area").prepend(alarm);
        }

        function countAdd() {
            let cnt = Number($('#count').text() === "" ? 0 : $('#count').text());
            if (cnt === 0) { // 알림이 없을 때 새 알림 도착하면 display 속성변경
                $('#count').css('display', 'inline-block').show();
            }
            $('#count').text(cnt + 1);
        }

        function alarmAdd() {
            let cnt = Number($('#alarm').text() === "" ? 0 : $('#alarm').text());
            if (cnt === 0) { // 알림이 없을 때 새 알림 도착하면 display 속성변경
                $('#alarm').css('display', 'inline-block').show();
            }
            $('#alarm').text(cnt + 1);
        }
    }

    // 토스트
    const toast = document.querySelector(".toast"),
        closeIcon = document.querySelector(".close"),
        progress = document.querySelector(".progress");

    let timer1, timer2;

    function getToast(data) {
        $('.text-1').append(data);
        toast.classList.add("active");
        progress.classList.add("active");
        timer1 = setTimeout(() => {
            $('.text-1').text("");
            toast.classList.remove("active");
        }, 5000);
        timer2 = setTimeout(() => {
            $('.text-1').text("");
            progress.classList.remove("active");
        }, 5300);
    }

    closeIcon.addEventListener("click", () => {
        toast.classList.remove("active");
        setTimeout(() => progress.classList.remove("active"), 300);
        clearTimeout(timer1);
        clearTimeout(timer2);
    });
});
