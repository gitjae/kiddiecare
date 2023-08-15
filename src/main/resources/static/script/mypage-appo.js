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
                case 0:
                    status = "예약완료";
                    break;
                case 1:
                    status = "예약취소";
                    break;
                case 2:
                    status = "예약보류";
                    break;
                case 3:
                    status = "이용완료";
                    break;
                default:
                    status = "";
            }

            $('#appointments').append(`
            <div class="div-appo">
                <p class="appo-no">${appo.no}</p>
                <p class="appo-hospital">${appo.hospital}</p>
                <p class="appo-date">${appo.date}</>
                <p class="appo-time">${appo.time}</p>
                <p class="appo-doctor">${appo.doctor}</p>
                <p class="appo-status">${status}</p>
                <p class="appo-name">${appo.name}</p>
                <p class="appo-symptom">${appo.symptom}</p>
                <p class="appo-note">${appo.note}</p>
                <div class="appo-btn">
                    <button>수정</button>
                    <button>취소</button>
                </div>
            </div>`);
        })
    })
}