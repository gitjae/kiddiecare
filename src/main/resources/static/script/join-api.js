var getid= RegExp(/^[a-z0-9]{4,20}$/); // 영어 소문자, 숫자 [ 4~20자리 까지 입력가능 ]
var getName = RegExp(/^[가-힣a-zA-Z]{2,8}$/); // 한글, 영어 대소문자 [ 2~8자리 까지 입력가능 ]
var getpw= RegExp(/^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,20}$/); // 문자, 숫자, 특수문자 최소 1개씩 포함시켜야함. [ 최소 8자리이상 입력 ]
var getMail = RegExp(/^[a-z0-9\.\-_]+@([a-z0-9\-]+\.)+[a-z]{2,6}$/); // 이메일 형식만 입력가능
var getBirth = RegExp(/^(19[0-9][0-9]|20\d{2})(0[0-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$/); // 19991010 형식만 입력가능
var getPhone = RegExp(/^[0-9]{10,11}$/);
var duplChk = false;
var phoneChk = false;

$(document).ready(function (){
    $('#id').on("change",function (){
        duplChk = false;
    })
})



function join(){
    const id = $('#id').val();
    const password = $('#password').val();
    const pwChk = $('#passwordChk').val();
    const name = $('#name').val();
    const birth = $('#birth').val();
    const gender = $('input[name=gender]:checked').val() == 'm' ? true : false;
    const phone = $('#phone').val();
    const email = $('#email').val();
    const postcode = $('#addr1').val();
    const addr = $('#addr2').val();
    const addr_detail = $('#addr3').val();

    if(!duplChk){
        alert("아이디 중복체크");
        return false;
    }

    if(!getid.test(id)){
        $("#id").val("");
        alert("아이디");
        return false;
    }

    if(!getpw.test(password)){
        $("#password").val("");
        alert("비밀번호");
        return false;
    }

    if(password != pwChk){
        $("#passwordChk").val("");
        alert("비밀번호확인");
        return false;
    }

    if(!getName.test(name)){
        $('#name').val("");
        alert("이름");
        return false;
    }

    if (!getMail.test(email)){
        $('#email').val("");
        alert("이메일");
        return false;
    }

    if(!getBirth.test(birth)){
        $('#birth').val("");
        alert("생년월일")
        return false;
    }

    if(!getPhone.test(phone)){
        $('#phone').val("");
        alert("전화번호");
        return false;
    }

    if(!phoneChk){
        alert("전화번호 인증");
        return false;
    }

    if(postcode == ""){
        alert("우편번호를 입력해주세요");
        return false;
    }

    if(addr == ""){
        alert("주소를 입력해주세요");
        return false;
    }

    var data= {
        id:id,
        password:password,
        name:name,
        birth:birth,
        gender:gender,
        phone:phone,
        email:email,
        postcode:postcode,
        addr:addr,
        addr_detail:addr_detail
    }

    $.ajax({
        method:"POST",
        url:"api/v1/users/join",
        data:JSON.stringify(data),
        contentType:'application/json; charset=utf-8'
    }).done(res => {
        alert(res.join);
        if(res.join === "success"){
            location.href = "/login";
        }
    })
}



function idDuplChk(){
    const id = $('#id').val();

    if(!getid.test(id)){
        $("#id").val("");
        alert("아이디");
        return false;
    }

    $.ajax({
        method: "GET",
        url:`api/v1/users/user/${id}`
    }).done(user => {
        if(user.id === id){
            $('#id').val("");
            duplChk = false;
            alert("중복된 아이디");
        } else {
            duplChk = true;
            alert("사용가능한 아이디");
        }
    })
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
        url:'api/v1/users/sendcode/join',
        data:{number:phone}
    }).done(res => {
        if(res.send === 'success'){
            if(res.dupl === 'true'){
                alert("이미 등록된 전화번호 입니다.");
            } else {
                console.log(res.code)
                $('#verify').prop("disabled", false);
            }
        }
    })
}

function verify(){
    const code = $('#code').val();
    $.ajax({
        method:'GET',
        url:'api/v1/users/verify',
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