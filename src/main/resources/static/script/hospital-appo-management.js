let selectedUser;
let ykihoValue = document.getElementById('ykiho').value;

// 날짜 선택 시 로그인한 병원의 timeslot불러옴
document.getElementById('confirm-date').addEventListener('change', function (event) {
    const selectedDate = new Date(event.target.value);
    const formattedDate = selectedDate.toISOString().slice(0, 10);
    const selectedDoctor = document.getElementById('selectedDoctor').textContent;
    const timeList = document.getElementById('time-list');
    const dateStatusText = document.getElementById('date-status');
    const tableBody = document.getElementById('table-body');

    $.ajax({
        url: '/getTimeSlots',
        method: 'GET',
        data: {
            ykiho: ykihoValue,
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
    // 페이징처리
    // let nowPage = document.getElementById('page').textContent;

    // 기존 선택 삭제
    for (const child of timeList.children) {
        child.classList.remove('selected');
    }

    // 선택 요소에 selected 클래스 추가 (선택 표시)
    event.target.classList.add('selected');

    if (event.target && event.target.nodeName === "LI") {
        // 선택한 timeNo
        const timeNo = parseInt(event.target.id);

        // 클릭할 때마다 기존 행을 모두 삭제
        tableBody.innerHTML = '';

        // timeNo를 가지고 ajax -> appointment table 전송
        $.ajax({
            url: `/api/v1/admin/appo/getAppoDetails/`,
            method: 'GET',
            data: {timeSlotNo: timeNo},
        }).done(function (list) {
            // 예약이 있을 때만 반복문 실행
            if (Array.isArray(list) && list.length > 0) {
                list.forEach((detail) => {
                    const createStatusDropdown = (appoStatus) => {
                        const statusOptions = [
                            { value: 1, text: "예약완료" },
                            { value: 2, text: "예약취소" },
                            { value: 3, text: "예약보류" },
                            { value: 4, text: "이용완료" }
                        ];

                        return statusOptions.reduce((acc, option) => {
                            const selected = appoStatus === option.value ? "selected" : "";
                            return acc + `<option value="${option.value}" ${selected}>${option.text}</option>`;
                        }, "");
                    };

                    const tr = document.createElement('tr');

                    tr.innerHTML = `
                        <td>${detail.appoNo}</td>
                        <td>
                            <select>
                                ${createStatusDropdown(detail.appoStatus)}
                            </select>
                        </td>
                        <td>${detail.guardianName}</td>
                        <td>${detail.patientName}</td>
                        <td>${detail.symptom}</td>
                        <td>${detail.note}</td>
                        <td><button class="detail-button">자세히보기</button></td>
                    `;



                    tr.dataset.appoNo = detail.appoNo;

                    const statusDropdown = tr.querySelector('select');
                    statusDropdown.addEventListener('change', (event) => {

                        let result = confirm(`* 예약상태 변경  
상태를 수정하시겠습니까?`);

                        if(result){
                            const selectedValue = event.target.value;
                            console.log(`Selected value:  ${selectedValue}`);
                            let selectedAppoNo = tr.dataset.appoNo;
                            console.log(selectedAppoNo);

                            $.ajax({
                                url: '/api/v1/admin/appo/modifyAdminAppo/appoStatusChange',
                                method: 'PUT',
                                data:{
                                    appoNo: selectedAppoNo,
                                    status: selectedValue
                                }
                            }).done(function (result) {
                                console.log(result.status)
                                alert('상태변경 성공');
                            }).fail(function (error) {
                                console.log(error);
                                alert('상태변경 실패');
                            })
                        }

                    })


                    // 자세히보기 버튼 클릭 시 상세정보 표시
                    const detailButton = tr.querySelector('.detail-button');
                    detailButton.addEventListener("click", () => {
                        event.stopPropagation(); // tr의 클릭 이벤트 동작을 막음
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
    selectedUser = "";
}


// 유저 상세정보에서 -> 정보 변경하기
function changeDate() {
    let content = document.getElementById('text-area');
    content.innerHTML = '';

    // 받아왔던 객체 전달받기
    // console.log(selectedUser);

    content.innerHTML = `
        <div>
            <h1>예약정보 수정하기</h1>
            <h2>날짜 설정</h2>
            <input type="date" id="modify-date">
                <div id="mo-time-bar-area">
                    <ul id="mo-time-list">
                    </ul>
                </div>
        </div>
    `;

    document.getElementById('modify-date').addEventListener("change", (event) => {
        const selectedDate = new Date(event.target.value);
        const formattedDate = selectedDate.toISOString().slice(0, 10);
        console.log(formattedDate);
        const selectedDoctor = document.getElementById('selectedDoctor').textContent;
        const timeList = document.getElementById('mo-time-list');
        $.ajax({
            url: '/getTimeSlotsForEnable',
            method: 'GET',
            data: {
                ykiho: ykihoValue,
                date: formattedDate,
                doctorNo: selectedDoctor
            },
        }).done(function (list) {
            console.log(list);
            // 기존 요소 삭제
            timeList.innerHTML = "";
            // dateStatusText.innerText ="";

            if(Array.isArray(list) && list.length > 0) {
                list.forEach((detail) => {
                    let hour = detail.time;
                    const no = detail.no;
                    const enable = detail.enable;
                    let li = document.createElement('li');

                    // mo-time-list 안에 li로 value=시간, text=표시될시간 (ex)9:00~10:00 시간 담기
                    li.setAttribute('id', no);
                    li.setAttribute('value', hour);
                    hour = parseInt(hour.split(':')[0]);
                    li.innerText = `${hour}:00 ~ ${hour + 1}:00 (예약가능수: ${enable})`;

                    li.addEventListener("click", clickListener);
                    timeList.appendChild(li);
                });
            } else {
                // tableBody.innerHTML = '';
                // dateStatusText.innerText = "선택한 날짜에 예약을 생성하지 않았습니다.";
            }

        }).fail(function (error) {
            console.log(error);
        });
    });
}

// 최종 클릭 이벤트 함수
function clickListener(event) {
    const date = document.getElementById('modify-date').value;
    // 변경할 hour
    const hour = event.target.value;
    // time_slots_no
    const id = event.target.id;
    // int status => 1: 추가, 2: 삭제, 3: 변경
    let status;
    console.log(hour);
    console.log(date);
    console.log(id);

    let result = confirm(`
    * 선택한 예약자
    (${selectedUser.hospAppoNo}) 보호자명: ${selectedUser.usersName}, 환자명: ${selectedUser.childrenName}
    ${date}, ${event.target.value}시로 예약을 수정하시겠습니까?
    `)

    // 확인버튼 눌렀을 때
    if(result === true) {
        status = 3;
        $.ajax({
            url: `/api/v1/admin/appo/modifyAdminAppo/${status}`,
            method: 'PUT',
            data: {
                hospAppoNo: selectedUser.hospAppoNo,
                hour: hour,
                timeSlotNo: id,
            },
        }).done(function (result) {
            alert(result.update);
        }).fail(function (error) {
            alert(error);
        })

        // 취소버튼
    } else if(result === false) {
        alert("취소누름");
    }
}

// 페이징 처리
function pageMinus() {
    let page = document.getElementById('page');
    let nowPage = parseInt(page.textContent);
    // let nowPage = parseInt(document.getElementById('page').textContent);
    if(nowPage > 1) {
        nowPage -= 1;
        page.innerText = String(nowPage);
    }
}

function pagePlus() {
    let page = document.getElementById('page');
    let nowPage = parseInt(page.textContent);

    nowPage += 1;
    page.innerText = String(nowPage);
}

/* 전체 예약자 불러오기 */

