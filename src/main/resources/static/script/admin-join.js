// /* 병원 이름 검사 api로 정보 검색 */
// function chkHospName() {
//     const hospName = $('#hosp-name').val();
//     // 이 부분은 ajax로 검증해야 하므로 일부러 에러 메시지 표시하지 않습니다.
//     if (hospName === "") {
//         $('#hosp-name').parent().css('border-color', 'red');
//     } else {
//         $('#hosp-name').parent().css('border-color', 'lightgrey');
//         // 검증 및 결과 메시지 표시
//     }
// }


/* 이메일 유효성 검사 */
$('#admin-email').on('change', e => {
    let email = $('#admin-email').val();
    let regExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;
    if (email !== "") {
        if (!regExp.test(email)) {
            $('#error-email').html('올바른 이메일 형식이 아닙니다.<br>').css('color', 'red').show();
            $('#admin-email').parent().css('border-color', 'red');
        } else {
            $('#error-email').hide();
            $('#admin-email').parent().css('border-color', 'lightgrey');
        }
    }
});

/* 비밀번호 유효성 검사 */
$('#admin-pw').on('change', e => {
    let password = $('#admin-pw').val();
    let pwdChk = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[$@$!%*#?&])/;
    let pwd_space = /[ ]/;
    if (password !== "") {
        /* 비밀번호 길이 검사 */
        if (password.length < 4 || password.length > 10 || !pwdChk.test(password)) {
            $('#chk-notice1').html('비밀번호는 영문, 숫자와 특수문자 조합 4-10자 이내로 입력해주세요.<br>').css('color', 'red');
            $('#admin-pw').parent().css('border-color', 'red');
        } else if (pwd_space.test(password)) {
            $('#chk-notice1').html('비밀번호는 공백을 포함할 수 없습니다.<br>').css('color', 'red');
            $('#admin-pw').parent().css('border-color', 'red');
        } else {
            $('#chk-notice1').hide();
            $('#admin-pw').parent().css('border-color', 'lightgrey');
        }
    }
});

/* 비밀번호 확인 유효성 검사 */
$('#admin-pw-ch').on('change', e => {
    let password = $('#admin-pw').val();
    let password_ch = $('#admin-pw-ch').val();
    let pwd_space = /[ ]/;
    if (password_ch !== "") {
        if (password !== password_ch) {
            $('#chk-notice2').html('비밀번호가 일치하지 않습니다.<br><br>').css('color', 'red');
            $('#admin-pw-ch').parent().css('border-color', 'red');
        } else if (pwd_space.test(password_ch)) {
            $('#chk-notice2').html('비밀번호는 공백을 포함할 수 없습니다.<br>').css('color', 'red');
            $('#admin-pw-ch').parent().css('border-color', 'red');
        } else {
            $('#chk-notice2').html('비밀번호가 일치합니다. 사용 가능합니다.<br>').css('color', 'navy');
            $('#admin-pw-ch').parent().css('border-color', 'lightgrey');
        }
    }
});

/* 전체 동의 선택/해제 */
const agreeChkAll = $('input[name=agree_all]');
agreeChkAll.on('change', e => {
    let agreeChk = $('input[name=user_check]');
    agreeChk.prop('checked', e.target.checked);
});

$(document).on('click', '.hosp-data', function() {
    const selectedHosp = $(this).find('span:first-child').text();
    const selectedAddress = $(this).find('span:last-child').text().substring(3);
    $('#hosp-name').val(selectedHosp);
    $('#hosp-address').val(selectedAddress);
    closeModal(); // 모달을 닫는 함수 호출

})

/* 회원가입 양식 유효성 검사 */
function checkValue(htmlForm) {
    let check = true;
    let regExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;
    let pwdChk = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[$@$!%*#?&])/;
    let pwd_space = /[ ]/;

    /* 병원명 유효성 검사 */
    let hospName = $('#hosp-name').val();
    if (hospName === "") {
        $('#hosp-name-null').show();
        $('#hosp-name').parent().css('border-color', 'red');
        check = false;
    }

    /* 아이디 유효성 검사 */
    let adminId = $('#admin-id').val();
    if (adminId === "") {
        $('#admin-id-null').show();
        $('#admin-id').parent().css('border-color', 'red').focus();
        check = false;
    }

    /* 이메일 유효성 검사 */
    let email = $('#admin-email').val();
    if (email === "") {
        $('#hosp-name-isnull').show();
        $('#admin-email').parent().css('border-color', 'red').focus();
        check = false;
    }
    if (!regExp.test(email)) {
        check = false;
    }

    /* 비밀번호 유효성 검사 */
    let password = $('#admin-pw').val();
    if (password === "") {
        $('#chk-notice1').html('*필수 입력 항목입니다.').css('color', 'red');
        check = false;
    }
    if (password.length < 4 || password.length > 10 || !pwdChk.test(password) || pwd_space.test(password)) {
        check = false;
    }

    /* 비밀번호 확인 유효성 검사 */
    let password_ch = $('#admin-pw-ch').val();
    if (password_ch === "") {
        $('#chk-notice2').html('*필수 입력 항목입니다.').css('color', 'red');
        check = false;
    }
    if (password !== password_ch) {
        check = false;
    }

    /* 이름 유효성 검사 */
    let name = $('#admin-name').val();
    if (name === "") {
        $('#error-name').show();
        check = false;
    }

    /* 의료기관 첨부파일 유효성 검사 */
    let attachFile = $('#admin-attach').val();
    if (attachFile === "") {
        alert("의료기관 개설 허가증/의사 면허/사업자 등록증을 첨부해주세요.");
        check = false;
    }

    /* 이용약관/개인정보 처리방침 동의 확인 */
    if (!$('#user_check1').prop('checked') || !$('#user_check2').prop('checked')) {
        alert("이용약관과 개인정보 처리방침에 동의해주세요.");
        check = false;
    }

    /* 서버 검증 결과 확인 */
    if (check && isIdChecked && isToKenChecked) {
        htmlForm.submit();
    } else if (!isIdChecked) {
        alert("병원 확인을 해주세요.");
    } else if (!isToKenChecked) {
        alert("이메일 인증을 해주세요.");
    }
}

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

/* 병원명 검사 */
function searchHospName() {
    let hospName = $('#hosp-search').val();
    $(".spinner-border").show();
    console.log("hospName"+hospName);
    $.ajax({
        type: "GET",
        url: "/search/hospList/check",
        data: { "keyword": hospName },
        success: function(response) {
            if (response.result === "success") {
                $(".spinner-border").hide();
                $('.hosp-list-area').empty();
                if (response.data !== null){
                    response.data.forEach(item => {
                        const hospData = item;
                        let html = `
                        <div class="hosp-data">
                            <a href="#">
                                <span>${hospData.yadmNm}</span><br>
                                <span>주소:${hospData.addr}</span>
                            </a>
                        </div>
                    `;
                        $('.hosp-list-area').append(html); //병원 리스트
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
        $.ajax({
            type: "POST",
            url: "/admin/info/",
            data: { "email": email },
            success: function(response) {
                if (response.result === "success") {
                    alert("인증번호를 확인해주세요.");
                    // 인증코드 입력란 활성화 등 필요한 코드 작성
                    console.log(response.token);
                } else {
                    console.log(response.result);
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
        url: "/check-auth-token",
        data: { "authCode": authCode },
        success: function(response) {
            if (response.result === "success") {
                alert("인증이 완료되었습니다.");
                isToKenChecked = true;
                // 인증이 완료되었을 때 필요한 코드 작성
            } else if (response.result === "expired") {
                alert("인증번호가 만료되었습니다. 다시 인증번호를 발급받아주세요.");
                // 인증번호 입력란 초기화 등 필요한 코드 작성
            } else {
                alert("인증번호를 다시 확인해주세요.");
                // 인증번호 입력란 초기화 등 필요한 코드 작성
            }
        },
        error: function(xhr, status, error) {
            console.log(error);
            alert("인증번호를 다시 확인해주세요.");
        }
    });
}

