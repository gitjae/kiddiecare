var data;
var url;
var keyword;
var currentPage = 1;

document.addEventListener("DOMContentLoaded", function (){
    $('#loading').show();
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
        data['radius'] = '2000';
        url = '/search/hospList/addr';
    }

    ajaxRequest(data, url);

    $('#search-input').keyup(function (e){
        if(e.keyCode == 13){
            search();
        }
    })
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
    $('#loading').show();
    $.ajax({
        method:'GET',
        url:url,
        data:data,
        timeout:10000
    }).done(res => {
        let len = Object.keys(res.data).length;
        if(len>0){
            setMarkers(res);
            currentPage = data['pageNo'];
        } else {
            alert('더 이상 불러올 데이터가 없습니다.');
            $('#next').prop("disabled", true);
        }
        $('#loading').hide();
    }).fail(function (){
        alert('데이터를 불러오지 못했습니다.\n다시 시도해주세요')
        //$('.kakaoMap-area').hide();
        //$('#hospital-list').hide();
        $('#loading').hide();

    })
}


function search(){
    const keyword = $('#search-input').val();

    location.href = `/hospital/Search?keyword=${keyword}`;
}