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
            $('#children').append(`<p>이름 : ${child.name}</p>
                    <p>성별 : ${child.gender}</p>
                    <p>생년월일 : ${child.birth}</p>
                    <p>참고사항 : ${child.info}</p>`);
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
