$('#div-verify').hide();

function findId(){
    const name = $('#find-id-name').val();
    const phone = $('#find-id-phone').val();
}

function sendCode(){
    const id = $('#find-password-id').val();
    const name = $('#find-password-name').val();
    const phone = $('#find-password-phone').val();

    $.ajax({
        method:'POST'
    })
}

function findPassword(){
    const id = $('#find-password-id').val();
    const name = $('#find-password-name').val();
    const phone = $('#find-password-phone').val();
}