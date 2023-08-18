

function sectionChange(nav){
    const name = $(nav).attr("name");
    $('.main-div').css("display", "none");
    $(`#${name}`).css("display", "block");
}

function gotoQuit(){
    location.href = '/quitUser';
}

function gotoUpdate(){
    location.href = '/user/update';
}

