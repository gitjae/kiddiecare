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
    var sessionValue = $('#sessionValue').val();

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

        function onMessage(evt) {
            countAdd();
            alarmAdd();
            var data = evt.data;
            getToast(data);
            let alarm = "<div class='text'><p>" + data + "</p></div>";
            $(".notifi-area").append(alarm);
        }

        function countAdd() {
            let cnt = Number($('#count').text() === "" ? 0 : $('#count').text());
            $('#count').text(cnt + 1);
        }

        function alarmAdd() {
            let cnt = Number($('#alarm').text() === "" ? 0 : $('#alarm').text());
            $('#alarm').text(cnt + 1).show();
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
