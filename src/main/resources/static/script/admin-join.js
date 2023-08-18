/* 이메일 + 병원명 + ID 중복값 체크 */
let chkAdminEmail = false;
let chkAdminHospName = false;
let chkAdminIdValue = false;

/* 정규식 검사 */
let regExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;
let pwdChk = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[$@$!%*#?&])/;
let pwd_space = /[ ]/;
let nameChk = /^[a-zA-Z가-힣]/;
let idChk = /^[a-z]+[a-z0-9]{5,19}$/;

/* 모달창 으로 병원, 주소 값 넣기*/
$(document).on('click', '.hosp-data', function() {
    const selectedHosp = $(this).find('span:first-child').text();
    const selectedAddress = $(this).find('span:last-child').text().substring(3);
    $('#hosp-name').val(selectedHosp);
    $('#hosp-address').val(selectedAddress);
    if($('#hosp-name').val() !== "" && $('#hosp-address').val() !== ""){
        $('#hosp-name-null').hide();
        $('#hosp-name').parent().css('border-color', 'lightgrey');
    }
    closeModal(); // 모달을 닫는 함수 호출
})

/* 아이디 정규식 검사*/
$('#admin-id').on('keyup', e => {
    let id = $('#admin-id').val();
    if(id !== ""){
        $('#admin-id-null').hide();
    }
    if (!idChk.test(id)) {
        $("#admin-id-err").show().html('아이디는 영문자로 시작하는 영문자 또는 숫자 6~20자 입니다.<br>').css('color', 'red');
    } else {
        $("#admin-id-err").empty().hide();
    }
});

/* 비밀번호 정규식 검사 */
$('#admin-pw').on('keyup', function() {
    let pw = $(this).val();
    if(pw !== ""){
      $('#chk-admin-pw').hide();
      $('#not-pw-format').hide();
    }
    if($('#admin-id').val() === $(this).val()){
        $('#chkNotice1').show().html('비밀번호와 아이디는 같게 설정할 수 없습니다.<br>').css('color', 'red');
    }
    // 비밀번호 길이 검사
    if ($(this).val().length < 4 || $(this).val().length > 10 || !pwdChk.test($(this).val())) {
        $('#chkNotice1').show().html('비밀번호는 영문, 숫자와 특수문자 조합 4-10자 이내로 입력해주세요.<br>').css('color', 'red');
    }else{
        $('#chkNotice1').empty().hide()
    }

    // 비밀번호의 공백 검사
    if (pwd_space.test($(this).val())) {
        $('#chkNotice1').html('비밀번호는 공백을 포함할 수 없습니다.<br>').css('color', 'red');
    }
});

// 비밀번호 확인 일치 검사
$('#admin-pw-ch').on('keyup', function() {
    if($(this).val() !== ""){
        $('#chk-admin-pw-again').hide();
    }
    if ($('#admin-pw').val() !== $(this).val()) {
        $('#chkNotice2').show().html('비밀번호가 일치하지 않습니다.<br>').css('color', 'red');
    } else {
        $('#chkNotice2').html('비밀번호가 일치합니다. 사용 가능합니다.<br>').css('color', 'navy');
    }
    // 비밀번호 확인란의 공백 검사
    if (pwd_space.test($(this).val())) {
        $('#chkNotice2').html('비밀번호는 공백을 포함할 수 없습니다.<br>').css('color', 'red');
    }
});

/* 이메일 정규식 검사 */
$('#admin-email').on('keyup', function() {
    if($(this).val() !== ""){
        $('#admin-email-null').hide();
    }
    if (!regExp.test($(this).val())) {
        $('#not-email-format').show().css('color', 'red');
    }else{
        $('#not-email-format').hide();
    }
});

/* 이름 정규식 검사*/
$('#admin-name').on('keyup', function() {
    if($(this).val() !== ""){
        $('#admin-name-null').hide();
    }
    if (!nameChk.test($(this).val())) {
        $('#chkNotice3').show().html('이름은 영문자 한글로 작성 가능합니다.<br>').css('color', 'red');
    }else if(pwd_space.test($(this).val())){
        $('#chkNotice3').show().html('이름은 공백을 포함할 수 없습니다.<br>').css('color', 'red');
    } else{
        $('#chkNotice3').empty().hide();
    }
});

/* 이름 정규식 검사*/
$('#doctor-name').on('keyup', function() {
    if($(this).val() !== ""){
        $('#doctor-name-null').hide();
    }
    if (!nameChk.test($(this).val())) {
        $('#chkNotice4').show().html('이름은 영문자 한글로 작성 가능합니다.<br>').css('color', 'red');
    }else if(pwd_space.test($(this).val())){
        $('#chkNotice4').show().html('이름은 공백을 포함할 수 없습니다.<br>').css('color', 'red');
    } else{
        $('#chkNotice4').empty().hide();
    }
});

/* 전체 동의 선택/해제 */
const agreeChkAll = document.querySelector('input[name=agree_all]');
agreeChkAll.addEventListener('change', (e) => {
    let agreeChk = document.querySelectorAll('input[name=agree]');
    for(let i = 0; i < agreeChk.length; i++){
        agreeChk[i].checked = e.target.checked;
    }
});

// 모달 창을 표시하는 함수
function showModal() {
    var modal = document.getElementById("myModal");
    modal.style.display = "block";
}

// 모달 창을 닫는 함수
function closeModal() {
    var modal = document.getElementById("myModal");
    modal.style.display = "none";
}

// 모달 창을 열기 위한 버튼 클릭 시 호출
var modalBtns = document.querySelectorAll(".korea_area");

modalBtns.forEach(function(btn) {
    btn.onclick = function() {
        showModal();
    }
});

// 모달 창 우측 상단의 닫기 버튼 클릭 시 closeModal 함수 호출
var closeBtn = document.getElementsByClassName("close")[0];
closeBtn.onclick = function () {
    closeModal();
}

// 모달 창의 배경을 클릭 시 모달 창 닫기
var modal = document.getElementById("myModal");
window.onclick = function (event) {
    if (event.target == modal) {
        closeModal();
    }
}

/* ID 중복값 체크 */
function chkAdminId(){
    let id = $('#admin-id').val();
    if(id !== "") {
        $("#admin-id-null").hide();
        $.ajax({
            type: "POST",
            url: "/admin/info/id/check",
            data: {adminId: id},
            success: function (response) {
                if (response.response === "Not a duplicate value.") {
                    alert("사용 가능한 아이디 입니다.");
                    chkAdminIdValue = true;
                } else {
                    console.log(response.response === "duplicate value");
                    alert("사용 불가능한 아이디 입니다.");
                    chkAdminIdValue = false;
                }
            },
            error: function (xhr, status, error) {
                console.log(error);
                alert("아이디 중복확인이 실패하였습니다. 잠시 후 다시 시도해주세요.");
            }
        });
    }else{
        $("#admin-id-null").show().css('color','red');
    }
}

/* 병원명 검사 */
function searchHospName() {
    let hospName = $('#hosp-search').val();
    $('.hosp-list').empty();
    $(".spinner-border").show();
    console.log("hospName"+hospName);
    $.ajax({
        type: "GET",
        url: "/search/hospList/check",
        data: { keyword: hospName },
        success: function(response) {
            if (response.result === "success") {
                chkAdminHospName = true;
                $(".spinner-border").hide();
                if (response.data !== null){
                    response.data.forEach(item => {
                        const hospData = item;
                        let html = `
                        <div class="hosp-data">
                            <a href="#">
                                <span>${hospData.yadmNm}</span><br>
                                <span>주소:${hospData.addr}</span>
                            </a>
                        </div> `;
                        $('.hosp-list').append(html); //병원 리스트
                    });
                }else{
                    $('#hosp-list-area').html('검색 결과를 찾을 수 없습니다. 다시 시도해주세요.').css('color', 'red');
                }
            } else {
                $('#hosp-list-area').html('서버의 오류가 발생하였습니다.').css('color', 'red');
            }
        },
        error: function(xhr, status, error) {
            console.log(error);
        }
    });
}

/* 이메일 인증번호 전송 */
function sendAuthToken() {
    let email = $('#admin-email').val();
    if (!regExp.test(email)) {
        alert("이메일 형식이 맞지 않습니다.");
    } else {
        $('#admin-email').prop('disabled', true);
        $.ajax({
            type: "POST",
            url: "/email/create",
            data: { adminEmail : email },
            success: function(response) {
                if (response.result === "VERIFICATION_SENT") {
                    alert("인증번호를 확인해주세요.");
                    // 인증코드 입력란 활성화 등 필요한 코드 작성
                    console.log(response.verification_duration);
                    console.log(response.verification_code);
                } else {
                    console.log(response.result === "FAIL");
                    alert("인증번호 전송에 실패하였습니다. 잠시 후 다시 시도해주세요.");
                }
            },
            error: function(xhr, status, error) {
                console.log(error);
                alert("인증번호 전송에 실패하였습니다. 잠시 후 다시 시도해주세요.");
            }
        });
    }
}

/* 이메일 인증번호 검증 */
function checkAuthToken() {
    let authCode = $('#auth-token').val();
    $.ajax({
        type: "POST",
        url: "/email/validate",
        data: { verificationCode: authCode },
        success: function(response) {
            if (response.result === "VERIFICATION_SUCCEEDED") {
                alert("인증이 완료되었습니다.");
                chkAdminEmail = true;
                // 인증이 완료되었을 때 필요한 코드 작성
            } else if (response.result === "EXPIRED") {
                alert("인증번호가 만료되었습니다. 다시 인증번호를 발급받아주세요.");
                // 인증번호 입력란 초기화 등 필요한 코드 작성
                $('#auth-token').val().empty();
                chkAdminEmail = false;
            } else {
                alert("인증번호를 다시 확인해주세요.");
                // 인증번호 입력란 초기화 등 필요한 코드 작성
                $('#auth-token').val().empty();
            }
        },
        error: function(xhr, status, error) {
            console.log(error);
            alert("인증번호를 다시 확인해주세요.");
        }
    });
}

/* 회원가입 양식 유효성 검사 */
function checkValue(htmlForm) {
    let check = true;

    /* 병원명 유효성 검사 */
    let hospName = $('#hosp-name').val();
    if (hospName === "") {
        $('#hosp-name-null').show().css('color', 'red');
        $('#hosp-name').parent().css('border-color', 'red').focus();
        check = false;
    }

    /* 아이디 유효성 검사 */
    let adminId = $('#admin-id').val();
    if (adminId === "") {
        $('#admin-id-null').show().css('color', 'red');
        $('#admin-id').parent().css('border-color', 'red').focus();
        check = false;
    }


    /* 비밀번호 유효성 검사 */
    let password = $('#admin-pw').val();
    if (password === "") {
        $('#chk-admin-pw').show().css('color', 'red');
        check = false;
    }
    if (password.length < 4 || password.length > 10 || !pwdChk.test(password) || pwd_space.test(password)) {
        $('#not-pw-format').show().css('color', 'red');
        check = false;
    }

    /* 비밀번호 확인 유효성 검사 */
    let password_ch = $('#admin-pw-ch').val();
    if (password_ch === "") {
        $('#chk-admin-pw-again').show().css('color','red');
        check = false;
    }

    if (password !== password_ch) {
        $('#chk-admin-pw-same').show().css('color','red');
        check = false;
    }


    /* 이메일 유효성 검사 */
    let email = $('#admin-email').val();
    if (email === "") {
        $('#admin-email-null').show().css('color', 'red');
        $('#admin-email').parent().css('border-color', 'red').focus();
        check = false;
    }
    if (!regExp.test(email)) {
        check = false;
    }

    /* 이름 유효성 검사 */
    let name = $('#admin-name').val();
    if (name === "") {
        $('#admin-name-null').show().css('color','red');
        check = false;
    }
    if(nameChk.test(name)){
        $('#chk-admin-name').show().css('color','red');
        check = false;
    }


    /* 의료기관 첨부파일 유효성 검사 */
    let attachFile = $('#admin-attach').val();
    if (attachFile === "") {
        $('#admin-file-null').show().css('color','red');
        check = false;
    }


    /* 원장 의사 이름 유효성 검사 */
    let doctorName = $('#doctor-name').val();
    if(doctorName === ""){
        $("#doctor-name-null").show().css('color','red');
        check = false;
    }

    /* 이용약관/개인정보 처리방침 동의 확인 */
    // if (!$('#agree1').prop('checked') || !$('#agree2').prop('checked') || $('#agree3').prop('checked')) {
    //     alert("이용약관과 개인정보 처리방침에 동의해주세요.");
    //     check = false;
    // }

    // if(!chkAdminEmail){
    //     alert("이메일 인증을 해주세요.");
    //     $('#chk-email').show().css('color','red');
    //     check = false;
    // }

    if(!chkAdminHospName){
        alert("병원 정보를 입력 해주세요.");
        check = false;
    }

    if(!chkAdminIdValue){
        alert("아이디 중복확인을 해주세요.")
        check = false;
    }

    /* 서버 검증 결과 확인 */
    if (check) {
        console.log("dd 됨")
        htmlForm.submit();
    }
}