function register(){
    const name = $('#name').val();
    const birth = $('#birth').val();
    const gender = $('input[name=gender]:checked').val() == 'm' ? 1 : 0;
    const info = $('#info').val();

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