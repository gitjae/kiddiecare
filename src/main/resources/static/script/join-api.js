function join(){
    const id = $('#id').val();
    const password = $('#password').val();
    const name = $('#name').val();
    const birth = $('#birth').val();
    const gender = $('input[name=gender]:checked') == 'm' ? true : false;
    const phone = $('#phone').val();
    const email = $('#email').val();

    var data = {
        id:id,
        password:password,
        name:name,
        birth:birth,
        gender:gender,
        phone:phone,
        email:email
    }

    $.ajax({
        method:"POST",
        url:"api/v1/users/join",
        data:JSON.stringify(data),
        contentType:'application/json; charset=utf-8'
    }).done(res => {
        alert(res.join);
    })
}