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
            let button = ``;
            let statusClass = "";  // 예약 상태에 따른 색상

            switch (appo.appoStatus) {
                case 1:
                    status = "예약완료";
                    statusClass = "status-1";
                    button = `<button onclick="appoModify(this)">수정</button>
                    <button onclick="cancelAppo(this)">취소</button>`
                    break;
                case 2:
                    status = "예약취소";
                    statusClass = "status-2";
                    break;
                case 3:
                    status = "예약보류";
                    statusClass = "status-3";
                    button = `<button onclick="appoModify(this)">수정</button>
                    <button onclick="cancelAppo(this)">취소</button>`
                    break;
                case 4:
                    status = "이용완료";
                    statusClass = "status-4";
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
                    <span class="appo-status ${statusClass}">${status}</span>
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
                    ${button}
                </div>
            </div>`);
        })
    })
}

function appoModify(btn){
    const no = $(btn).closest('.div-appo').find('.appo-no').text();

    location.href = `/appointment/update?no=${no}`;
}

function cancelAppo(btn){
    if(confirm('정말로 취소하시겠습니까?')){
        const appoNo = $(btn).closest('.div-appo').find('.appo-no').text();

        $.ajax({
            method: 'PUT',
            url:'/api/v1/appo/cancel',
            data:{appoNo:appoNo}
        }).done(res => {
            if(res.cancel == 'success'){
                getAppo(appoPage);
            } else {
                alert('예약을 취소하지 못했습니다.')
            }
        })
    }
}

function deleteAppo(btn){
    if(confirm('정말로 삭제하시겠습니까?')){
        const appoNo = $(btn).closest('.div-appo').find('.appo-no').text();

        $.ajax({
            method: 'DELETE',
            url:'/api/v1/appo/delete',
            data:{appoNo:appoNo}
        }).done(res => {
            if(res.delete == 'success'){
                getAppo(1);
            } else {
                alert('예약을 삭제하지 못했습니다.')
            }
        })
    }
}