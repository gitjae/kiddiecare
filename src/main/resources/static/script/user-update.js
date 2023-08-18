var getpw= RegExp(/^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,20}$/); // 문자, 숫자, 특수문자 최소 1개씩 포함시켜야함. [ 최소 8자리이상 입력 ]
var getMail = RegExp(/^[a-z0-9\.\-_]+@([a-z0-9\-]+\.)+[a-z]{2,6}$/); // 이메일 형식만 입력가능
var getPhone = RegExp(/^[0-9]{10,11}$/);
var phoneChk = false;

$(function (){
    $('#phone').on('change', function (){
        phoneChk = false;
    })
})

function updateUser(data){
    const id = $('#id').val();

    // 수정 요청
    $.ajax({
        method:'PUT',
        url:`/api/v1/users/user/${id}/update`,
        data:JSON.stringify(data),
        contentType:'application/json; charset=utf-8'
    }).done(res => {
        if(res.update === 'success'){
            // location.href = '/mypage'
            alert('수정을 완료했습니다.');
        } else {
            alert('수정에 실패했습니다.');
        }
        return res;
    })
}

function passwordUpdate(){
    // const password = $('#password');
    const passwordNew = $('#password-new').val();
    const passwordChk = $('#passwordChk').val();

    if(!getpw.test(passwordNew)){
        $("#password-new").val("");
        alert("비밀번호");
        return false;
    }

    if(passwordNew != passwordChk){
        $("#passwordChk").val("");
        alert("비밀번호확인");
        return false;
    }

    var data = {
        password:passwordNew
    }

    updateUser(data);
}

function phoneUpdate(){
    const phone = $('#phone').val();

    if(!getPhone.test(phone)){
        $('#phone').val("");
        alert("전화번호");
        return false;
    }

    if(!phoneChk){
        alert("전화번호 인증");
        return false;
    }

    var data = {
        phone:phone
    }
    var res = updateUser(data);

    if(res.update === 'success'){
        $('#phone').prop("disabled",false);
        $('#send').prop("disabled",false);
        $('#code').prop("disabled",true);
        $('#verify').prop("disabled",true);
        phoneChk = false;
    }
}

function emailUpdate(){
    const email = $('#email').val();

    if (!getMail.test(email)){
        $('#email').val("");
        alert("이메일");
        return false;
    }

    var data = {
        email:email
    }
    updateUser(data)
}

function addrUpdate(){
    const postcode = $('#addr1').val();
    const addr = $('#addr2').val();
    const addr_detail = $('#addr3').val();

    if(postcode == ""){
        alert("우편번호를 입력해주세요");
        return false;
    }

    if(addr == ""){
        alert("주소를 입력해주세요");
        return false;
    }

    var data = {
        postcode:postcode,
        addr:addr,
        addr_detail:addr_detail
    }
    updateUser(data);
}


function sendCode(){
    const phone = $('#phone').val();

    if(!getPhone.test(phone)){
        $('#phone').val("");
        alert("전화번호");
        return false;
    }

    $.ajax({
        method:'POST',
        url:'/api/v1/users/sendcode/join',
        data:{number:phone}
    }).done(res => {
        console.log(res)
        if(res.send == 'fail'){
            if(res.dupl == 'true'){
                alert("이미 등록된 전화번호 입니다.");
            } else {
                alert("인증번호 발송에 실패했습니다.");
                // console.log(res.code);
                // $('#verify').prop("disabled", false);
            }
        } else {
            alert("인증번호가 발송되었습니다.");
            console.log(res.code);
        }
    })
}

function verify(){
    const code = $('#code').val();
    $.ajax({
        method:'GET',
        url:'/api/v1/users/verify',
        data:{code:code}
    }).done(res => {
        console.log(res)
        if(res.verify === 'success'){
            $('#phone').prop("disabled",true);
            $('#send').prop("disabled",true);
            $('#code').prop("disabled",true);
            $('#verify').prop("disabled",true);
            phoneChk = true;
        } else {
            alert("인증에 실패했습니다.")
        }
    })
}