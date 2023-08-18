function quitUser(){
    confirm('정말로 탈퇴하시겠습니까?');

    const id = $('#id').val();
    const password = $('#password').val();

    $.ajax({
        method:'DELETE',
        url:`/api/v1/users/user/${id}/delete`,
    }).done(res => {
        if(res.delete == 'success'){
            location.href = '/';
        } else {
            alert('탈퇴에 실패했습니다.')
        }
    })
}