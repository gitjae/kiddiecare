function logout(){
    $.ajax({
        method:"POST",
        url:'/api/v1/logout',
    }).done(function (){
        location.href = '/';
    })
}