var xPos = 127.0291236;
var yPos = 37.5001387;
var radius = 500;
var map;
var bounds;
var marker;
var infowindow;



function makeMap(){
    var container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
    var options = { //지도를 생성할 때 필요한 기본 옵션
        center: new kakao.maps.LatLng(yPos, xPos), //지도의 중심좌표.
        level: 3 //지도의 레벨(확대, 축소 정도)
    };

    map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴
}

function setBounds() {
    // LatLngBounds 객체에 추가된 좌표들을 기준으로 지도의 범위를 재설정합니다
    // 이때 지도의 중심좌표와 레벨이 변경될 수 있습니다
    map.setBounds(bounds);
}

function makeOverListener(map, marker, infowindow) {
    return function() {
        infowindow.open(map, marker);
    };
}

function makeClickListener(map, marker, infowindow) {
    return function() {
        // 인포윈도우를 엽니다.
        infowindow.open(map, marker);

        // 인포윈도우의 content를 DOMParser를 사용해 HTML 요소로 변환합니다.
        const parser = new DOMParser();
        const contentHTML = parser.parseFromString(infowindow.getContent(), 'text/html');

        // 변환된 HTML에서 원하는 요소 찾기
        const hospitalNameElement = contentHTML.querySelector('.hospital-name');
        const addrElement = contentHTML.querySelector('.hospital-addr');
        const hospitalName = hospitalNameElement.innerText;
        const sgguCd = addrElement.getAttribute("sgguCd");

        location.href = `/appointment/hospitalDetail?hospitalName=${hospitalName}&sgguCd=${sgguCd}`
    };
}

// 인포윈도우를 닫는 클로저를 만드는 함수입니다
function makeOutListener(infowindow) {
    return function() {
        infowindow.close();
    };
}

function getUserLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(setPosition, displayError);
    } else {
        alert("Geolocation is not supported by this browser.");
    }
}

function setPosition(position) {
    xPos = position.coords.longitude;
    yPos = position.coords.latitude;
    console.log("x:"+xPos+"y:"+yPos);
    data = {
        xPos:xPos,
        yPos:yPos,
        radius:radius,
        pageNo:currentPage
    }
    url = '/search/hospList';

    ajaxRequest(data, url);

    var moveLatLon = new kakao.maps.LatLng(yPos, xPos);
    map.setCenter(moveLatLon);
}

function displayError(error) {
    switch (error.code) {
        case error.PERMISSION_DENIED:
            alert("User denied the request for Geolocation.");
            break;
        case error.POSITION_UNAVAILABLE:
            alert("Location information is unavailable.");
            break;
        case error.TIMEOUT:
            alert("The request to get user location timed out.")
            break;
        case error.UNKNOWN_ERROR:
            alert("An unknown error occurred.");
            break;
    }
}

function setMarkers(res){
    if(res.result === 'success'){
        makeMap();

        var positions = [];
        $('#hospital-list-ul').empty();
        //res.data.list.forEach(hosp => {
        for(const key in res.data){
            const hosp = res.data[key];
            const hospInfo = `<a href="/appointment/hospitalDetail?hospitalName=${hosp.hospitalName}&sgguCd=${hosp.sgguCd}">
                                <div class="hospital-name" ykiho="${hosp.ykiho}">${hosp.hospitalName}</div>
                                <div class="hospital-addr" sidoCd="${hosp.sidoCd}" sgguCd="${hosp.sgguCd}">${hosp.addr}</div>
                                <div class="hospital-tel">${hosp.telno == null ? "" : hosp.telno}</div>
                                <div class="hospital-weekday">${hosp.weekday == null ? "" : hosp.weekday}</div>
                                <div class="hospital-holiday">${hosp.noTrmtHoli == null ? "" : "공휴일"}</div>
                                <div class="hospital-sunday">${hosp.noTrmtSun == null ? "" : "일요일"}</div>
                                <div class="blank">&nbsp;</div>
                            </a>`;

            var position = {
                content : hospInfo,
                latlng : new kakao.maps.LatLng(hosp.yPos, hosp.xPos)
            }
            positions.push(position);

            $('#hospital-list-ul').append(`<li class="hospital-li">`+hospInfo+`</li>`);
        }//)

        bounds = new kakao.maps.LatLngBounds();

        for (var i = 0; i < positions.length; i ++) {
            // 마커를 생성합니다
            marker = new kakao.maps.Marker({
                map: map, // 마커를 표시할 지도
                position: positions[i].latlng // 마커의 위치
            });
            //console.log(marker);

            bounds.extend(positions[i].latlng);

            // 마커에 표시할 인포윈도우를 생성합니다
            infowindow = new kakao.maps.InfoWindow({
                content: positions[i].content // 인포윈도우에 표시할 내용
            });

            // 마커에 mouseover 이벤트와 mouseout 이벤트를 등록합니다
            // 이벤트 리스너로는 클로저를 만들어 등록합니다
            // for문에서 클로저를 만들어 주지 않으면 마지막 마커에만 이벤트가 등록됩니다
            kakao.maps.event.addListener(marker, 'mouseover', makeOverListener(map, marker, infowindow));
            kakao.maps.event.addListener(marker, 'click', makeClickListener(map, marker, infowindow));
            kakao.maps.event.addListener(marker, 'mouseout', makeOutListener(infowindow));
        }

        setBounds(bounds);
    } else {
        alert(res.result);
    }
}