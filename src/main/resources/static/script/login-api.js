function login(){
    const id = $('#id').val();
    const password = $('#password').val();

    var data = {
        id:id,
        password:password
    }

    $.ajax({
        method:"POST",
        url:'/api/v1/login',
        data:JSON.stringify(data),
        contentType:'application/json; charset=utf-8'
    }).done(res => {
        console.log(res)
        if(res.login == 'success'){
            location.href = '/';
        }
    })
}