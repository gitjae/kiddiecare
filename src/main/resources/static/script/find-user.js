var getName = RegExp(/^[가-힣a-zA-Z]{2,8}$/); // 한글, 영어 대소문자 [ 2~8자리 까지 입력가능 ]
var getPhone = RegExp(/^[0-9]{10,11}$/);

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
        alert("전화번호");
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
            console.log(res.code);
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
        console.log(res)
        if(res.verify === 'success'){
            $('#find-password-id').prop("disabled",true);
            $('#find-password-name').prop("disabled",true);
            $('#find-password-phone').prop("disabled",true);
            $('#send').prop("disabled",true);
            $('#verify-code').prop("disabled",true);
            $('#verify-btn').prop("disabled",true);
            phoneChk = true;
        } else {
            alert("인증에 실패했습니다.")
        }
    })
}

function findPassword(){
    const id = $('#find-password-id').val();
    const name = $('#find-password-name').val();
    const phone = $('#find-password-phone').val();

    if(!phoneChk){
        alert('전화번호인증')
        return false
    }

    var data = {
        id:id,
        name:name,
        phone:phone
    }

    $.ajax({
        method:'POST',
        url:`/api/v1/users/findpw`,
        data:JSON.stringify(data),
        contentType:'application/json; charset=utf-8'
    }).done(res => {
        console.log(res);
        if(res.find == 'success'){
            $('#response-password').text(res.user.password);
        } else {
            alert('일치하는 정보가 없습니다.')
        }
    })
}