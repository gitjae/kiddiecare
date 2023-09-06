function checkValue() {
    const ID = $('#input-id').val();
    const PW = $('#input-pw').val();

    let check = true;

    if (ID === "") {
        $('#error-id').html(`<span>아이디를 입력해주세요.</span>`);
        $('#error-id').show();
        $('#input-id').focus();
        check = false;

    } else if (PW === "") {
        $('#error-password').html(`<span>비밀번호를 입력해주세요.</span>`);
        $('#error-password').show();
        $('#input-pw').focus();//포커스 이동시켜서 다시 입력하라고
        check = false;

    }

    if (check === true) {
        $.ajax({
            method: "POST",
            url: "/admin/login/check",
            data: JSON.stringify({ adminId:ID, adminPw: PW }),
            contentType:'application/json; charset=utf-8',
            success: function(data) {
                if (data.adminLogin === "success") {
                    location.href = "/admin/index";
                } else {
                    alert('아이디와 비밀번호가 일치하지 않습니다.');
                }
            }
        });
    }

}