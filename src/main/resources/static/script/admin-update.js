let regExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;
let pwdChk = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[$@$!%*#?&])/;
let pwd_space = /[ ]/;
let nameChk = /^[a-zA-Z가-힣]/;
var emailVerification = false;

$(function () {
    getAdminName()
    emailVerification = false;
})

/* InfoForm 열기 */
function getAdminInfoForm(){
    const infoTextArea = $('#admin-info-text-area');
    const infoForm = $('.admin-info-form');

    infoTextArea.css("display", "none");
    $('#adminName').val($('#admin-name').text());
    $('#hospitalName').val($('#hos-name').text());
    infoForm.css('display', 'flex');
}

/* InfoForm 닫기 */
function closeInfoForm() {
    const infoTextArea = $('#admin-info-text-area');
    const infoForm = $('.admin-info-form');
    infoTextArea.show();
    infoForm.css("display", "none");
}

/* adminPwForm */
function getAdminPwForm(){
    const pwTextArea = $('#pw-text-area');
    const pwForm = $('.pw-form');

    pwTextArea.css("display", "none");
    pwForm.css('display', 'flex');
}

function closePwUpdateForm(){
    const pwTextArea = $('#pw-text-area');
    const pwForm = $('.pw-form');

    pwTextArea.css("display", "flex");
    pwForm.css('display', 'none');
}

/* adminPwForm */
function getAdminEmailUpdateForm(){
    const emailTextArea = $('#admin-email-text-area');
    const emailForm = $('.email-form-area');

    $('.email-auth-code-area').prop('disabled', true);
    emailTextArea.css('display', 'none');
    emailForm.css('display', 'flex');
}

function closeEmailUpdateForm(){
    const emailTextArea = $('#admin-email-text-area');
    const emailForm = $('.email-form-area');

    $('.email-auth-code-area').prop('disabled', false);
    $('.email-get-auth-code').prop('disabled', false);
    $('input[name="adminEmail"]').val('')
    $('input[name="code"]').val('')
    emailTextArea.show();
    emailForm.css('display', 'none');
}

function getAdminName(){
    $.ajax({
        url: '/admin/info/data',
        type: 'GET',
        success: function(data) {
            if(data.response === "success"){
                $('#admin-name').text(data.adminName);
                $('#admin-info-Email').val(data.adminEmail);
            } else if(data.response === "fail cause session does not exist."){
                alert("로그인 해주세요.")
            }
        },
        error: function(xhr, error) {
            alert(" 서버에 에러가 발생하였습니다.")
        }
    });
}

function adminInfoUpdateForm(){
    var adminName = $('input[name="adminName"]').val();

    if(adminName !== null && nameChk.test(adminName)){
        $.ajax({
            url: '/admin/info/update/adminName',
            type: 'PUT',
            data: {adminName:adminName},
            success: function(data) {
                if(data.response === "success"){
                    alert("수정되었습니다.")
                    $('#admin-name').text(adminName);
                    closeInfoForm()
                }else if(data.response === "fail cause pw is not matches"){
                    alert("비밀번호가 맞지 않습니다.")
                }else if(data.response === "fail cause updateItem not value"){
                    alert("올바르지 않은 요청입니다.")
                }else if(data.response === "fail cause DB error"){
                    alert("서버 오류로 수정이 되지 않았습니다.")
                }else if(data.response === "fail cause session does not exist."){
                    alert("로그인 해주세요.")
                }
            },
            error: function(xhr, error) {
                alert("에러가 발생하였습니다.")
            }
        });
    }
}

function adminPwUpdateForm(){
    let adminPw = $('input[name="adminPw"]').val();
    let adminUpdatePw = $('input[name="adminUpdatePw"]').val();
    let adminUpdatePwAgain = $('input[name="adminUpdatePwAgain"]').val();
    let check = true;

    $('input[name="adminPw"]').removeClass("error");
    $('input[name="adminUpdatePw"]').removeClass("error");
    $('input[name="adminUpdatePwAgain"]').removeClass("error");

    if (adminPw === "") {
        check = false;
        $('input[name="adminPw"]').addClass("error");
        alert("비밀번호를 확인해 주세요.")
    } else if (4 > adminPw.length || adminPw.length > 10 || !pwdChk.test(adminPw) || pwd_space.test(adminPw)) {
        check = false;
        $('input[name="adminPw"]').addClass("error");
    }

    if (adminUpdatePw === "") {
        check = false;
        $('input[name="adminUpdatePw"]').addClass("error");
    } else if (4 > adminUpdatePw.length || adminUpdatePw.length > 10 || !pwdChk.test(adminUpdatePw) || pwd_space.test(adminUpdatePw)) {
        check = false;
        $('input[name="adminUpdatePW"]').addClass("error");
    }

    if (adminUpdatePwAgain === "") {
        check = false;
        $('input[name="adminupdatepwagain"]').addClass("error");
    } else if (4 > adminUpdatePwAgain.length || adminUpdatePwAgain.length > 10 || !pwdChk.test(adminUpdatePwAgain) || pwd_space.test(adminUpdatePwAgain)) {
        check = false;
        $('input[name="AdminupdatePWAgain"]').addClass("error");
    }

    if(adminUpdatePw !== adminUpdatePwAgain){
        check=false;
    }

    if(check){
        $.ajax({
            url: '/admin/info/update/adminPw',
            type: 'PUT',
            data: {
                adminPw:adminPw,
                updateAdminPw:adminUpdatePw
            },
            success: function(data) {
                if(data.response === "success"){
                    alert("수정되었습니다.")
                    $('input[name="adminPw"]').val('');
                    $('input[name="adminUpdatePw"]').val('');
                    $('input[name="adminUpdatePwAgain"]').val('');
                    closePwUpdateForm()
                }else if(data.response === "fail cause pw is not matches"){
                    alert("비밀번호가 맞지 않습니다.")
                }else if(data.response === "fail cause updateItem not value"){
                    alert("올바르지 않은 요청입니다.")
                }else if(data.response === "fail cause DB error"){
                    alert("서버 오류로 수정이 되지 않았습니다.")
                }else if(data.response === "fail cause session does not exist."){
                    alert("로그인 해주세요.")
                }
            },
            error: function(xhr, error) {
                alert(" 5 에러가 발생하였습니다.")
            }
        });
    }
}

function sendVerificationEmail(){
    var adminEmail = $('input[name="adminEmail"]').val();
    if(adminEmail !== "" && regExp.test(adminEmail)){
        $.ajax({
            url: '/email/create',
            type: 'POST',
            data: {
                adminEmail:adminEmail
            },
            success: function(data) {
                if(data.response === "success"){
                    $('.email-auth-code-area').prop('disabled', false);
                    $('.email-get-auth-code').prop('disabled', true);
                    alert("인증번호를 확인해주세요.")
                }else if(data.response === "fail cause target is null"){
                    alert("이메일을 확인해주세요.")
                }else if(data.response === "fail cause email sender"){
                    alert("서버에서 이메일을 보내지 못했습니다.")
                }
            },
            error: function() {
                alert(" 에러가 발생하였습니다.")
            }
        });
    }else{
        alert("이메일을 입력해주세요.")
    }
}

function validateVerificationCode(){
    var adminEmail = $('input[name="adminEmail"]').val();
    var code = $('input[name="code"]').val();
    if(code !== ""){
        $.ajax({
            url: '/email/validate',
            type: 'POST',
            data: {
                adminEmail:adminEmail,
                code:code
            },
            success: function(data) {
                if(data.response === "success"){
                    alert("인증되었습니다.")
                    emailVerification = true;
                }else if(data.response === "fail cause not matched"){
                    alert("인증번호가 맞지 않습니다.")
                }else if(data.response === "fail cause timeout"){
                    alert("인증번호 유효기간이 지났습니다.")
                }
            },
            error: function() {
                alert("에러가 발생하였습니다.")
            }
        });
    }else{
        alert("인증번호를 입력해주세요.")
    }
}

function adminEmailUpdateForm(){
    var adminEmail = $('input[name="adminEmail"]').val();
    if(emailVerification){
        $.ajax({
            url: '/admin/info/update/adminEmail',
            type: 'PUT',
            data: {
                adminEmail:adminEmail
            },
            success: function(data) {
                if(data.response === "success"){
                    alert("수정되었습니다.")
                    closeEmailUpdateForm()
                }else if(data.response === "fail cause pw is not matches"){
                    alert("비밀번호가 맞지 않습니다.")
                }else if(data.response === "fail cause updateItem not value"){
                    alert("올바르지 않은 요청입니다.")
                }else if(data.response === "fail cause DB error"){
                    alert("서버 오류로 수정이 되지 않았습니다.")
                }else if(data.response === "fail cause session does not exist."){
                    alert("로그인 해주세요.")
                }
            },
            error: function() {
                alert("에러가 발생하였습니다.")
            }
        });
    }else{
        alert("이메일 인증을 다시 해야 합니다.")
    }
}

function deleteAdmin(){
    var result = confirm("정말로 떠나시는건가요?");
    if (result) {
        $.ajax({
            url: '/admin/info/leave',
            type: 'DELETE',
            success: function (data) {
                if (data.response === "success") {
                    alert("탈퇴되었습니다.")
                    location.href = "/admin/index";
                } else if (data.response === "fail cause cannot delete.") {
                    alert("서버 오류로 수정이 되지 않았습니다.")
                } else if (data.response === "fail cause session does not exist.") {
                    alert("로그인 해주세요.")
                }
            },
            error: function () {
                alert("에러가 발생하였습니다.")
            }
        });
    }
}