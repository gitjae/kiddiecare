

function sectionChange(nav){
    const name = $(nav).attr("name");
    $('.main-div').css("display", "none");
    $(`#${name}`).css("display", "block");
}

function gotoQuit(){
    location.href = '/user/quit';
}

function gotoUpdate(){
    location.href = '/user/update';
}

function getFavorFromUrl() {
    const currentUrl = new URL(window.location.href);
    const favor = currentUrl.searchParams.get('favor');
    return favor;
}