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

function execPostCode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 도로명 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var fullRoadAddr = data.roadAddress; // 도로명 주소 변수
            var extraRoadAddr = ''; // 도로명 조합형 주소 변수

            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
            if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                extraRoadAddr += data.bname;
            }
            // 건물명이 있고, 공동주택일 경우 추가한다.
            if(data.buildingName !== '' && data.apartment === 'Y'){
                extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }
            // 도로명, 지번 조합형 주소가 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
            if(extraRoadAddr !== ''){
                extraRoadAddr = ' (' + extraRoadAddr + ')';
            }
            // 도로명, 지번 주소의 유무에 따라 해당 조합형 주소를 추가한다.
            if(fullRoadAddr !== ''){
                fullRoadAddr += extraRoadAddr;
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            console.log(data.zonecode);
            console.log(fullRoadAddr);


            $("[name=addr1]").val(data.zonecode);
            $("[name=addr2]").val(fullRoadAddr);

            /* document.getElementById('signUpUserPostNo').value = data.zonecode; //5자리 새우편번호 사용
            document.getElementById('signUpUserCompanyAddress').value = fullRoadAddr;
            document.getElementById('signUpUserCompanyAddressDetail').value = data.jibunAddress; */
        }
    }).open();
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
        url:'api/v1/users/sendcode',
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