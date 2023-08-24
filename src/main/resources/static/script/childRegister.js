var getName = RegExp(/^[가-힣a-zA-Z]{2,8}$/); // 한글, 영어 대소문자 [ 2~8자리 까지 입력가능 ]
var getBirth = RegExp(/^(19[0-9][0-9]|20\d{2})(0[0-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$/); // 19991010 형식만 입력가능

function register(){
    const name = $('#name').val();
    const birth = $('#birth').val();
    const gender = $('input[name=gender]:checked').val() == 'm' ? 1 : 0;
    const info = $('#info').val();

    if(!getName.test(name)){
        $('#name').val("");
        alert("한글, 영어 2~8자리 입력만 가능합니다.");
        return false;
    }

    if(!getBirth.test(birth)){
        $('#birth').val("");
        alert("생년월일을 YYYYMMDD에 맞춰 입력해주세요.")
        return false;
    }

    var data = {
        name:name,
        birth:birth,
        gender:gender,
        info:info
    }

    $.ajax({
        method:'POST',
        url:`api/v1/children/child`,
        data:JSON.stringify(data),
        contentType:'application/json; charset=utf-8'
    }).done(res => {
        if(res.register === 'success'){
            location.href = "/mypage"
        } else {
            alert("등록 실패")
        }
    })
}