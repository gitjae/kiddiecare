function logout(){
    $.ajax({
        method:"POST",
        url:'/api/v1/logout',
    }).done(function (){
        // 로그아웃 후 로그아웃 상태의 네비게이션 업데이트
        $('.bell').hide();
        $('.searchHos').hide();
        $('.logout-a').hide();
        $('.login-a').show();
        $('.join-a').show();
        location.href = '/';
    })
}