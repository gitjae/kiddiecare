/* InfoForm 열기 */
function getAdminInfoForm(){
    const infoTextArea = $('#admin-info-text-area');
    const infoForm = $('.admin-info-form');

    infoTextArea.css("display", "none");
    $('#adminName').val($('#hos-name').text());
    $('#hospitalName').val($('#admin-name').text());
    infoForm.css('display', 'flex');
}

/* InfoForm 닫기 */
function closeInfoForm() {
    const infoTextArea = $('#admin-info-text-area');
    const infoForm = $('.admin-info-form');
    infoTextArea.css('display', 'flex');
    infoForm.css("display", "none");
}

/* adminPwForm */
function getAdminPwForm(){
    const pwTextArea = $('#pw-text-area');
    const pwForm = $('.pw-form');

    pwTextArea.on('click', () => {
        // pw-text-area 영역 감추기
        pwTextArea.hide();
        // pw-form 영역 보이기
        pwForm.css('display', 'flex');
    });
}

/* adminPwForm */
function getAdminEmailUpdateForm(){
    const emailTextArea = $('#admin-email-text-area');
    const emailForm = $('.email-form-area');

    emailTextArea.on('click',() => {
        emailTextArea.hide();
        emailForm.css('display', 'flex');
    });
}

function adminInfoUpdateForm(){
    var adminName = $('input[name="adminName"]').val();

    $.ajax({
        url: '/admin/info/update/adminName',
        type: 'PUT',
        data: {adminName:adminName},
        success: function(data) {
            if(data.response === "success"){
                alert("수정되었습니다.")
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
            console.log(xhr);
            console.log(error);
            alert(" 5 에러가 발생하였습니다.")
        }
    });
}

function adminPwUpdateForm(){
    var adminPw = $('input[name="adminPw"]').val();
    var adminUpdatePw = $('input[name="adminUpdatePw"]').val();
    var adminUpdatePwAgain = $('input[name="adminUpdatePwAgain"]').val();
    $.ajax({
        url: '/admin/info/update/adminPw',
        type: 'PUT',
        data: {
            adminPw:adminPw,
            adminUpdatePw:adminUpdatePw
        },
        success: function(data) {
            if(data.response === "success"){
                alert("수정되었습니다.")
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
            console.log(xhr);
            console.log(error);
            alert(" 5 에러가 발생하였습니다.")
        }
    });
}

function sendVerificationEmail(){
    var adminEmail = $('input[name="adminEmail"]').val();
    $.ajax({
        url: '/email/create',
        type: 'POST',
        data: {
            adminEmail:adminEmail
        },
        success: function(data) {
            if(data.response === "VERIFICATION_SENT"){
                alert("수정되었습니다.")
            }else if(data.response === "Target is null"){
                alert("비밀번호가 맞지 않습니다.")
            }else{
                alert("그냥 오류")
            }
        },
        error: function(xhr, error) {
            console.log(xhr);
            console.log(error);
            alert(" 5 에러가 발생하였습니다.")
        }
    });
}

function validateVerificationCode(){
    var adminEmail = $('input[name="adminEmail"]').val();
    var code = $('input[name="code"]').val();
    $.ajax({
        url: '/email/validate',
        type: 'POST',
        data: {
            adminEmail:adminEmail,
            code:code
        },
        success: function(data) {
            if(data.response === "VERIFICATION_SUCCEEDED"){
                alert("수정되었습니다.")
            }else if(data.response === "VERIFICATION_FAILED"){
                alert("비밀번호가 맞지 않습니다.")
            }else{
                alert("그냥 오류")
            }
        },
        error: function(xhr, error) {
            console.log(xhr);
            console.log(error);
            alert(" 5 에러가 발생하였습니다.")
        }
    });
}

function adminEmailUpdateForm(){
    var adminEmail = $('input[name="adminEmail"]').val();
    $.ajax({
        url: '/admin/info/update/adminEmail',
        type: 'PUT',
        data: {
            adminEmail:adminEmail
        },
        success: function(data) {
            if(data.response === "success"){
                alert("수정되었습니다.")
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
            console.log(xhr);
            console.log(error);
            alert(" 5 에러가 발생하였습니다.")
        }
    });
}

function deleteAdmin(){
    $.ajax({
        url: '/admin/info/leave',
        type: 'Delete',
        data: {
            adminPw:adminPw
        },
        success: function(data) {
            if(data.response === "success"){
                alert("수정되었습니다.")
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
            console.log(xhr);
            console.log(error);
            alert(" 5 에러가 발생하였습니다.")
        }
    });
}