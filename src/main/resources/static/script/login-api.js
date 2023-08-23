function login(){
    const id = $('#id').val();
    const password = $('#password').val();

    var data = {
        id:id,
        password:password
    }

    $.ajax({
        method:"POST",
        url:'/api/v1/login',
        data:JSON.stringify(data),
        contentType:'application/json; charset=utf-8'
    }).done(res => {
        console.log(res)
        if(res.login == 'success'){
            location.href = '/';
        } else {
            alert('일치하는 정보가 없습니다.');
        }
    })
}

function gotofind(){
    location.href = '/find/user';
}

function kakaoLogin(){
    location.href = `https://kauth.kakao.com/oauth/authorize?client_id=${keys.kakaoRestApi}&redirect_uri=http://localhost:8080/login/kakao/callback&response_type=code`
}

$(document).ready(function() {
    // 일반회원 로그인 버튼 클릭 이벤트
    $('#user-login-btn').click(function() {
        $('#form').show();
        $('.login-form').hide();
    });

    // 병원회원 로그인 버튼 클릭 이벤트
    $('#admin-login-btn').click(function() {
        $('.login-form').show();
        $('#form').hide();
    });

    $('#id').keyup(function (e){
        if(e.keyCode == 13){
            $('#password').focus();
        }
    })

    $('#password').keyup(function (e){
        if(e.keyCode == 13){
            login();
        }
    })


});
