function adminLogout() {
    $.ajax({
        method: "GET",
        url: "/admin/logout/check",
    }).done(function () {
        alert("로그아웃되었습니다.");
        location.href = '/';
    }).fail(function (error) {
        alert(error);
    });
}

