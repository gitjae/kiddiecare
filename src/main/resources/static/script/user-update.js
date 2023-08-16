var getid= RegExp(/^[a-z0-9]{4,20}$/); // 영어 소문자, 숫자 [ 4~20자리 까지 입력가능 ]
var getName = RegExp(/^[가-힣a-zA-Z]{2,8}$/); // 한글, 영어 대소문자 [ 2~8자리 까지 입력가능 ]
var getpw= RegExp(/^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,20}$/); // 문자, 숫자, 특수문자 최소 1개씩 포함시켜야함. [ 최소 8자리이상 입력 ]
var getMail = RegExp(/^[a-z0-9\.\-_]+@([a-z0-9\-]+\.)+[a-z]{2,6}$/); // 이메일 형식만 입력가능
var getBirth = RegExp(/^(19[0-9][0-9]|20\d{2})(0[0-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$/); // 19991010 형식만 입력가능
var getPhone = RegExp(/^[0-9]{10,11}$/);

function update(){
    const id = $('#id').val();
    const password = $('#password').val();
    const passwordChk = $('#passwordChk').val();
    const passwordNew = $('#password-new').val();
    const phone = $('#phone').val();
    const email = $('#email').val();
    const postcode = $('#addr1').val();
    const addr = $('#addr2').val();
    const addr_detail = $('#addr3').val();

    // 유효성검사
    if(!getpw.test(passwordNew)){
        $("#password").val("");
        alert("비밀번호");
        return false;
    }

    if(passwordNew != pwChk){
        $("#passwordChk").val("");
        alert("비밀번호확인");
        return false;
    }

    // 수정 요청
    const data = {
        password:passwordNew,
        phone:phone,
        email:email,
        postcode:postcode,
        addr:addr,
        addr_detail:addr_detail
    }
    $.ajax({
        method:'PUT',
        url:`/api/v1/users/user/${id}/update`,
        data:JSON.stringify(data)
    }).done(res => {
        if(res.update === 'success'){
            location.href = '/mypage'
        } else {
            alert('수정에 실패했습니다.')
        }
    })
}