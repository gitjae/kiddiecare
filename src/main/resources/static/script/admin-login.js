function checkValue(htmlForm) {
    const ID = htmlForm.adminId.value;
    const PW = htmlForm.adminPw.value;

    let check = true;

    console.log(ID);
    console.log(PW);

    if (ID === "") {
        $('#error-id').show();
        $('#user_email').focus();
        check = false;

    } else if (PW === "") {
        $('#error-password').show();
        $('#user_password').focus();//포커스 이동시켜서 다시 입력하라고
        check = false;

    }

    if (check === true) {
        $.ajax({
            method: "POST",
            url: "/admin/login/check",
            data: JSON.stringify({adminId:ID, adminPw: PW }),
            contentType:'application/json; charset=utf-8',
            success: function(data) {
                console.log(data);
                if (data.adminLogin === "success") {
                    location.href = "/admin/index";
                } else {
                    alert('아이디와 비밀번호가 일치하지 않습니다.');
                }
            }
        });
    }

}