var appoPage = 1;
var childrenPage = 1;

function sectionChange(nav){
    const name = $(nav).attr("name");
    $('.main-div').css("display", "none");
    $(`#${name}`).css("display", "block");
}



function prevChildren(){
    let page = childrenPage - 1;
    if(page < 1) {page = 1}
    getChildren(page);
}

function nextChildren(){
    getChildren(childrenPage + 1);
}

function prevAppo(){
    let page = appoPage - 1;
    if(page < 1) {page = 1}
    getAppo(page);
}

function nextAppo(){
    getAppo(appoPage + 1);
}


function getChildren(page){
    $.ajax({
        method: 'GET',
        url:`/api/v1/children/list/${page}`
    }).done(res => {
        if(res.children.length == 0){
            return;
        }
        childrenPage = page;
        $('#children').empty();
        res.children.forEach(child => {
            $('#children').append(`
                    <div class="div-child">
                        <p class="child-name" child-no="${child.id}">${child.name}</p>
                        <p class="child-gender">${child.gender % 2 == 0 ? "여아" : "남아"}</p>
                        <p class="child-birth">${child.birth}</p>
                        <p class="child-info">${child.info}</p>
                        <div class="child-btn">
                            <button onclick="updateForm(this)">정보 수정</button>
                            <button onclick="delChildInfo(this)">자녀 정보 지우기</button>
                        </div>
                    </div>
            `);
        })
    })
}

function getAppo(page){
    var appoPage = 1;
    $.ajax({
        method: 'GET',
        url:`/api/v1/appo/list/${page}`
    }).done(res => {
        if(res.appointments.length == 0){
            return;
        }
        appoPage = page
        $('#appo').empty();
        res.appointments.forEach(appo => {
            $('#appo').append(`<div class="appo">
                        <p>예약번호 : ${appo.no}</p>
                        <p>병원이름 : ${appo.hospital}</p>
                        <p>예약일정 : ${appo.date} ${appo.time}</p>
                        <p>담당의 : ${appo.doctor}</p>
                        <p>예약상태 : ${appo.appoStatus}</p>
                        <p>환자이름 : ${appo.name}</p>
                        <p>증상 : ${appo.symptom}</p>
                        <p>참고사항 : ${appo.note}</p>
                    </div>`);
        })
    })
}


function delChildInfo(btn){
    const no = $(btn).closest('.div-child').find('.child-name').attr('child-no');
    if(confirm('정말로 이 자녀정보를 지우시겠습니까?')){
        $.ajax({
            method:'DELETE',
            url:`api/v1/children/child/${no}`
        }).done(res => {
            if(res.delete === 'success'){
                getChildren(1);
            } else {
                alert('자녀 정보를 지우지 못했습니다.')
            }
        })
    }
}

function updateForm(btn){
    const div = $(btn).closest('.div-child');
    const no = $(div).find('.child-name').attr('child-no');
    const name = $(div).find('.child-name').text();
    const gender = $(div).find('.child-gender').text() == '남아' ? true : false;
    const birth = $(div).find('.child-birth').text();
    const info = $(div).find('.child-info').text();

    $(div).empty();
    $(div).append(`
        <div id="form" class="form">
            <div id="div-name">
                <label for="name">이름</label>
                <input type="text" id="name" class="name" name="name" child-no="${no}" value="${name}">
            </div>
            <div id="div-birth">
                <label for="birth">생년월일</label>
                <input type="text" id="birth" name="birth" value="${birth}">
            </div>
            <div id="div-gender">
                <label id="label-gender">성별</label>
                <label for="gender-m">남</label>
                <input type="radio" id="gender-m" name="gender" value="m" ${gender ? 'checked' : ''}>
                <label for="gender-f">여</label>
                <input type="radio" id="gender-f" name="gender" value="f" ${!gender ? 'checked' : ''}>
            </div>
            <div id="div-info">
                <label for="info">참고사항</label>
                <input type="text" id="info" name="info" value="${info}">
            </div>
            <button id="submit" onclick="update(this)">수정하기</button>
            <button onclick="updateCancel(this)">취소하기</button>
        </div>
    `)
}

function updateCancel(btn){
    const div = $(btn).closest('.div-child');
    const no = $(div).find('.name').attr('child-no');
    $.ajax({
        method:'GET',
        url:`api/v1/children/child/${no}`
    }).done(res => {
        $(div).empty();
    })
}