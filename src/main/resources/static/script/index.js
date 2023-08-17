window.onload = function() {
    document.getElementById('one-hospitalPage').addEventListener('click', e => {
        window.location = '/hospital/Search';
    });

    document.getElementById('admin-appo').addEventListener('click', e => {
        window.location = '/admin/appointment';
    });

    document.getElementById('user-myPage').addEventListener('click', e => {
        window.location = '/mypage';
    });
}

function search(){
    const keyword = $('#search-input').val();

    location.href = `/hospital/Search?keyword=${keyword}`;
}