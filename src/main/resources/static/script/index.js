window.onload = function () {
    document.getElementById('find-nearby-hospital-div').addEventListener('click', e => {
        window.location = '/hospital/Search';
    });

    document.getElementById('user-myPage').addEventListener('click', function () {
        window.location.href = '/mypage';
        document.getElementById('nav-favor').click();
    });

    document.getElementById('favorite-hospital-div').addEventListener('click', function () {
        window.location.href = '/mypage?favor=true';
        document.getElementById('nav-favor').click();
    });

    $('#search-input').keyup(function (e){
        if(e.keyCode == 13){
            search();
        }
    })

}

function search() {
    const keyword = $('#search-input').val();

    location.href = `/hospital/Search?keyword=${keyword}`;
}