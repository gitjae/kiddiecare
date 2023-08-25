var getName = RegExp(/^[가-힣a-zA-Z]{2,8}$/); // 한글, 영어 대소문자 [ 2~8자리 까지 입력가능 ]
var getPhone = RegExp(/^[0-9]{10,11}$/);
var getpw= RegExp(/^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,20}$/); // 문자, 숫자, 특수문자 최소 1개씩 포함시켜야함. [ 최소 8자리이상 입력 ]
var phoneChk = false;

$(function (){

    $('#div-verify').hide();
})

function findId(){
    const name = $('#find-id-name').val();
    const phone = $('#find-id-phone').val();

    $.ajax({
        method: 'GET',
        url:'/api/v1/users/findid',
        data:{
            name: name,
            phone: phone
        }
    }).done(res => {
        if(res.find === 'success'){
            $('#response-id').text(res.user.id);
        } else {
            alert('일치하는 정보가 없습니다.')
        }
    })
}

function sendCode(){
    const id = $('#find-password-id').val();
    const name = $('#find-password-name').val();
    const phone = $('#find-password-phone').val();

    if(!getPhone.test(phone)){
        $('#phone').val("");
        alert("전화번호를 입력해주세요 ex) 01012345678");
        return false;
    }

    var data = {
        id:id,
        name:name,
        phone:phone
    }

    $.ajax({
        method:'POST',
        url:'/api/v1/users/sendcode/find',
        data:JSON.stringify(data),
        contentType:'application/json; charset=utf-8'
    }).done(res => {
        if(res.send === 'success'){
            alert('인증번호가 발송되었습니다.')
            $('#div-verify').show();
        } else {
            alert('인증번호 발송에 실패했습니다.')
        }
    })
}

function verify(){
    const code = $('#verify-code').val();
    $.ajax({
        method:'GET',
        url:'/api/v1/users/verify',
        data:{code:code}
    }).done(res => {
        if(res.verify === 'success'){
            $('#find-password-id').prop("disabled",true);
            $('#find-password-name').prop("disabled",true);
            $('#find-password-phone').prop("disabled",true);
            $('#send').prop("disabled",true);
            $('#verify-code').prop("disabled",true);
            $('#verify-btn').prop("disabled",true);
            phoneChk = true;
            $('.new-password-a').show();
        } else {
            alert("인증에 실패했습니다.")
        }
    })
}

function findPassword(){
    const id = $('#find-password-id').val();
    const password = $('#password-new').val();
    const passwordChk = $('#password-new-chk').val();

    if(password != passwordChk){
        alert('비밀번호확인이 일치하지 않습니다.');
        return false;
    }

    if(!getpw.test(password)){
        alert('문자, 숫자 특수문자를 포함하여 8자리 이상 입력해야 합니다.');
        return false;
    }

    if(!phoneChk){
        alert('전화번호를 인증받아야 합니다.')
        return false;
    }

    var data = {
        id:id,
        password:password
    }

    $.ajax({
        method:'POST',
        url:`/api/v1/users/findpw`,
        data:JSON.stringify(data),
        contentType:'application/json; charset=utf-8'
    }).done(res => {
        if(res.find == 'success'){
            alert('비밀번호 변경에 성공했습니다.')
            location.href = '/login'
        } else {
            alert('비밀번호 변경에 실패했습니다.')
        }
    })
}