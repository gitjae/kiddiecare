$(function () {

})

let timeNo;

// 날짜 선택 시 로그인한 병원의 timeslot불러옴
document.getElementById('confirm-date').addEventListener('change', function (event) {
    const selectedDate = new Date(event.target.value);
    const formattedDate = selectedDate.toISOString().slice(0, 10);
    const ykiho = document.getElementById('ykiho').value;
    const selectedDoctor = document.getElementById('selectedDoctor').textContent;
    const timeList = document.getElementById('time-list');
    const dateStatusText = document.getElementById('date-status');

    $.ajax({
        url: '/getTimeSlots',
        method: 'GET',
        data: {
            ykiho: ykiho,
            date: formattedDate,
            doctorNo: selectedDoctor
        },
    }).done(function (list) {
        // 기존 요소 삭제
        timeList.innerHTML = "";
        dateStatusText.innerText ="";

        if(Array.isArray(list) && list.length > 0) {
            list.forEach((detail) => {
                let hour = detail.time;
                const no = detail.no;
                let li = document.createElement('li');

                // time-list 안에 li로 value=시간, text=표시될시간 (ex)9:00~10:00 시간 담기
                li.setAttribute('id', no);
                li.setAttribute('value', hour);
                hour = parseInt(hour.split(':')[0]);
                li.innerText = `${hour}:00 ~ ${hour + 1}:00`;
                timeList.appendChild(li);
            });
        } else {
            dateStatusText.innerText = "선택한 날짜에 예약을 생성하지 않았습니다.";
        }

    }).fail(function (error) {
        console.log(error);
    });
});


// 시간 클릭 시 디테일 정보 표시
document.getElementById('time-list').addEventListener("click", (event) => {
    const tableBody = document.getElementById('table-body');

    if (event.target && event.target.nodeName === "LI") {
        // 선택한 timeNo
        const timeNo = parseInt(event.target.id);

        // 클릭할 때마다 기존 행을 모두 삭제
        tableBody.innerHTML = '';

        // timeNo를 가지고 ajax -> appointment table 전송
        $.ajax({
            url: '/api/v1/admin/appo/getAppoDetails',
            method: 'GET',
            data: {timeSlotNo: timeNo},
        }).done(function (list) {
            // 예약이 있을 때만 반복문 실행
            if(Array.isArray(list) && list.length > 0) {
                list.forEach((detail) => {
                    // 보호자명, 환자명 동시에 받아오기
                    Promise.all([
                        $.ajax({ // 보호자명
                            url: '/api/v1/users/getUser',
                            method: 'GET',
                            data: {no: detail.guardian}
                        }),
                        $.ajax({ // 환자명
                            url: `/api/v1/children/child/${detail.patientId}`,
                            method: 'GET'
                        })
                    ]).then(function (results) {
                        const guardianName = results[0].name;
                        const patientName = results[1].child.name;

                        // 새로운 행 생성
                        const tr = document.createElement('tr');

                        tr.innerHTML = `
                            <td>${detail.no}</td>
                            <td>${detail.appoStatus}</td>
    <!--                        <td><input type="text" value="detail.appoStatus"></td>-->
                            <td>${guardianName}</td>
                            <td>${patientName}</td>
                            <td>${detail.symptom}</td>
                            <td>${detail.note}</td>
                            <td>${detail.timeSlotNo}</td>
                        `;

                        // tr 요소를 table body에 추가
                        tableBody.appendChild(tr);
                    }).catch(function (error) {
                        console.log(error);
                    });
                });
            } else {
                console.log("예약 없음");
            }
        }).fail(function (error){
            console.log(error);
        })
    }
});


// 모달 창을 표시하는 함수
function showModal() {
    let modal = document.getElementById("modal");
    modal.style.display = "block";
}
