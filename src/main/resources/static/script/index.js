window.onload = function() {
    document.getElementById('one-hospitalPage').addEventListener('click', e => {
        window.location = '/hospital/Search';
    });

    document.getElementById('admin-appo').addEventListener('click', e => {
        // window.location = '/adminIndex';
        window.location = '/admin/index';
    });

    document.getElementById('user-myPage').addEventListener('click', e => {
        window.location = '/mypage';
    });

    document.getElementById('like-hospitalPage').addEventListener('click', function() {
        window.location.href = '/mypage';
        document.getElementById('nav-favor').click();
    });
}

function search(){
    const keyword = $('#search-input').val();

    location.href = `/hospital/Search?keyword=${keyword}`;
}