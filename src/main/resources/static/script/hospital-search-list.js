var data;
var url;
var keyword;
var currentPage = 1;

document.addEventListener("DOMContentLoaded", function (){
    const urlParams = new URLSearchParams(window.location.search);
    keyword = urlParams.get('keyword');
    // xpos = urlParams.get('xpos');
    // ypos = urlParams.get('ypos');
    // radius = urlParams.get('radius');
    // pageNo = urlParams.get('pageNo');

    data = {};
    url = '';

    data['pageNo'] = currentPage;
    if (keyword) {
        data['keyword'] = keyword;
        url = '/search/hospList';
    } else {
        data['radius'] = '500';
        url = '/search/hospList/addr'
    }

    ajaxRequest(data, url);
})

function prev(){
    let target = currentPage - 1;
    if(target < 1){
        target = 1;
        return;
    }
    data['pageNo'] = target;
    console.log(data);

    ajaxRequest(data, url);

    $('#next').prop("disabled", false);
}
function next(){
    let target = currentPage + 1;
    data['pageNo'] = target;
    console.log(data)

    ajaxRequest(data, url);
}

function ajaxRequest(data, url){
    $.ajax({
        method:'GET',
        url:url,
        data:data
    }).done(res => {
        console.log(res);
        let len = Object.keys(res.data).length;
        console.log(len);
        if(len>0){
            setMarkers(res);
            currentPage = data['pageNo'];
        } else {
            alert('더 이상 불러올 데이터가 없습니다.');
            $('#next').prop("disabled", true);
        }
    })
}


function search(){
    const keyword = $('#search-input').val();

    location.href = `/hospital/Search?keyword=${keyword}`;
}