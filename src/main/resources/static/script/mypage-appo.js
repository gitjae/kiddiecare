var appoPage = 1;

function prevAppo(){
    let page = appoPage - 1;
    if(page < 1) {page = 1}
    getAppo(page);
}

function nextAppo(){
    getAppo(appoPage + 1);
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
        $('#appointments').empty();
        res.appointments.forEach(appo => {

            let status = "";
            switch (appo.appoStatus) {
                case 1:
                    status = "예약완료";
                    break;
                case 2:
                    status = "예약취소";
                    break;
                case 3:
                    status = "예약보류";
                    break;
                case 4:
                    status = "이용완료";
                    break;
                default:
                    status = "";
            }

            $('#appointments').append(`
            <div class="div-appo">
                <div class="info-item">
                    <span class="info-title">예약번호</span>
                    <span class="appo-no">${appo.no}</span>
                </div>
                <div class="info-item">
                    <span class="info-title">예약병원</span>
                    <span class="appo-hospital">${appo.hospital}</span>
                </div>
                <div class="info-item">
                    <span class="info-title">예약날짜</span>
                    <span class="appo-date">${appo.date}</>
                </div>
                <div class="info-item">
                    <span class="info-title">예약시간</span>
                    <span class="appo-time">${appo.time}</span>
                </div>
                <div class="info-item">
                    <span class="info-title">담당의사</span>
                    <span class="appo-doctor">${appo.doctor}</span>
                </div>
                <div class="info-item">
                    <span class="info-title">예약상태</span>
                    <span class="appo-status">${status}</span>
                </div>
                <div class="info-item">
                    <span class="info-title">자녀이름</span>
                    <span class="appo-name">${appo.name}</span>
                </div>
                <div class="info-item">
                    <span class="info-title">증상</span>
                    <span class="appo-symptom">${appo.symptom}</span>
                </div>
                <div class="info-item">
                    <span class="info-title">참고사항</span>
                    <span class="appo-note">${appo.note}</span>
                </div>
                <div class="appo-btn">
                    <button onclick="gotoUpdate(this)">수정</button>
                    <button>취소</button>
                </div>
            </div>`);
        })
    })
}

function gotoUpdate(btn){
    const no = $(btn).closest('.div-appo').find('.appo-no').text();

    location.href = `/appointment/update?no=${no}`;
}