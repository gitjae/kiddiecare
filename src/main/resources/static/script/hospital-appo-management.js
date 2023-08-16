$(function () {

})

let selectedUser;

// 날짜 선택 시 로그인한 병원의 timeslot불러옴
document.getElementById('confirm-date').addEventListener('change', function (event) {
    const selectedDate = new Date(event.target.value);
    const formattedDate = selectedDate.toISOString().slice(0, 10);
    const ykiho = document.getElementById('ykiho').value;
    const selectedDoctor = document.getElementById('selectedDoctor').textContent;
    const timeList = document.getElementById('time-list');
    const dateStatusText = document.getElementById('date-status');
    const tableBody = document.getElementById('table-body');

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
            tableBody.innerHTML = '';
            dateStatusText.innerText = "선택한 날짜에 예약을 생성하지 않았습니다.";
        }

    }).fail(function (error) {
        console.log(error);
    });
});


// 시간 클릭 시 디테일 정보 표시
document.getElementById('time-list').addEventListener("click", (event) => {
    const tableBody = document.getElementById('table-body');
    const timeList = document.getElementById('time-list');
    // 기존 선택 삭제
    for (const child of timeList.children) {
        child.classList.remove('selected');
    }

    // 선택 요소에 selected 클래스 추가
    event.target.classList.add('selected');

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
            if (Array.isArray(list) && list.length > 0) {
                list.forEach((detail) => {
                    console.log(detail);
                    const tr = document.createElement('tr');

                    tr.innerHTML = `
                        <td>${detail.appoNo}</td>
                        <td>${detail.appoStatus}</td>
                        <td>${detail.guardianName}</td>
                        <td>${detail.patientName}</td>
                        <td>${detail.symptom}</td>
                        <td>${detail.note}</td>
                        <td>${detail.slotNo}</td>
                    `;

                    tr.dataset.appoNo = detail.appoNo;

                    // getAppoUserDetail
                    tr.addEventListener("click", () => {
                        let selectedAppoNo = tr.dataset.appoNo;
                        console.log(selectedAppoNo);

                        $.ajax({
                            url: '/api/v1/admin/appo/getAppoUserDetail',
                            method: 'GET',
                            data: {hospAppoNo: selectedAppoNo}
                        }).done (function (detail) {
                            showModal();
                            console.log(detail.childrenName);

                            let content = document.getElementById('text-area');
                            // 전역변수로 저장
                            selectedUser = detail;
                            content.innerHTML = `
                                <h3>[ 어린이 정보 ]</h3>
                                <b>어린이 이름: </b> <p>${detail.childrenName}</p>
                                <b>어린이 생년월일: </b> <p>${detail.childrenBirth}</p>
                                <b>어린이 성별: </b> <p>${detail.childrenGender}</p>
                                <b>어린이 참고사항: </b> <p>${detail.childrenInfo}</p>
                                <hr>
                                <h3>[ 보호자 정보 ]</h3>
                                <b>보호자명: </b><p>${detail.usersName}</p>
                                <b>보호자 생년월일: </b><p>${detail.usersBirth}</p>
                                <b>보호자 주소: </b><p>${detail.usersAddr}</p>
                                <button id="change-info" onclick="changeDate()">예약정보 변경하기</button>
                            `;
                        }).fail(function (error) {
                            console.log(error);
                        })
                    });

                    // tr 요소를 table body에 추가
                    tableBody.appendChild(tr);
                });
            } else {
                console.log("예약 없음");
            }
        }).fail(function (error) {
            console.log(error);
        });
    }
});




// 모달 창을 표시하는 함수
function showModal() {
    let modal = document.getElementById("my-modal");
    modal.style.display = "block";
}

// 모달 창을 닫는 함수
function closeModal() {
    let modal = document.getElementById("my-modal");
    modal.style.display = "none";
}


// 유저 상세정보에서 -> 정보 변경하기
function changeDate() {
    let content = document.getElementById('text-area');
    content.innerHTML = '';

    // 받아왔던 객체 전달받기
    // console.log(selectedUser);

    content.innerHTML = `
        <input type="date" id="modify-date">
    `;
}



