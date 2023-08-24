let selectedUser;
let ykihoValue = document.getElementById('ykiho').value;

// 날짜 선택 시 로그인한 병원의 timeslot불러옴
document.getElementById('confirm-date').addEventListener('change', function (event) {

    // 의사 선택 확인
    if (!$('.select-option .option').hasClass('selected')) {
        alert('의사를 선택해주세요.');
        $('#confirm-date').val('');
        return;
    }

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
            // 타임 바 표시
            $('#time-bar-area').show();

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
            $('#time-bar-area').hide();
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

    // 이전에 선택된 요소의 selected 클래스 제거
    const prevSelected = timeList.querySelector('.selected-time');
    prevSelected && prevSelected.classList.remove('selected-time');


    if (event.target && event.target.nodeName === "LI") {
        // 선택 요소에 selected 클래스 추가 (선택 표시)
        event.target.classList.add('selected-time');

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
                $('.detail-area').show();
                $('#appo-table').show();
                $('#no-appo').hide();

                // 날짜 오름차순 정렬
                list.sort((a, b) => new Date(b.createdTime) - new Date(a.createdTime));

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
                    // let createdTime = new Date(detail.createdTime);
                    console.log(detail.createdTime);
                    tr.innerHTML = `
                        <td>${detail.appoNo}</td>
<!--                        <td>createdTime</td>-->
                        <td>
                            <select id="status">
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
                    // 변경 전 status 담아두기
                    let lastStatus = statusDropdown.value;

                    // 예약 취소상태일 때 선택 불가(disabled) 처리.
                    if(statusDropdown.value === "2") {
                        statusDropdown.setAttribute('disabled','');
                    }
                    
                    // 예약 취소상태로 변경됐을 때 선택 불가(disabled) 처리.
                    statusDropdown.addEventListener('change', (event) => {


                        let result = confirm(`* 예약상태 변경  
상태를 수정하시겠습니까?`);

                        if(result){
                            const selectedValue = event.target.value;
                            const hospitalName = document.getElementById('hospital_name').textContent;
                            let selectedAppoNo = tr.dataset.appoNo;
                            console.log(selectedAppoNo);

                            $.ajax({
                                url: '/api/v1/admin/appo/modifyAdminAppo/appoStatusChange',
                                method: 'PUT',
                                data:{
                                    appoNo: selectedAppoNo,
                                    status: selectedValue,
                                    hospitalName: hospitalName
                                }
                            }).done(function (result) {
                                // alarmDto 받음
                                let alarm = result.alarm;
                                // userid 받음
                                let id = result.sId;
                                alert('상태변경 성공');

                                // socket으로 메세지 전송
                                let type = '70';
                                let target = id;
                                let content = alarm.alarmText;
                                // mypage로 이동
                                let url = '/mypage';

                                // 병원명 이후 내용만 가져오기
                                content = content.split("]")[1];
                                socket.send(hospitalName+","+target+","+content+","+url);


                                console.log(`Selected value:  ${selectedValue}`);

                            }).fail(function (error) {
                                console.log(error);
                                alert('상태변경 실패');
                                // 변경 실패했을 때 기존 상태로 변경
                                statusDropdown.value = lastStatus;
                            })
                            
                        // 취소눌렀을 때 기존 상태로 내용 변경
                        } else {
                            statusDropdown.value = lastStatus;
                        }

                        // 취소 상태일 때 버튼 비활성화
                        if(statusDropdown.value === "2") {
                            statusDropdown.setAttribute('disabled','');
                        }
                    });


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

                            let genderStr = '';

                            // 수정필요
                            if (detail.childrenGender === 1) {
                                genderStr = '남';
                            } else if (detail.childrenGender === 0) {
                                genderStr = '여';
                            }

                            let content = document.getElementById('text-area');
                            // 전역변수로 저장
                            selectedUser = detail;
                            content.innerHTML = `
                                <h3>[ 어린이 정보 ]</h3>
                                <p><b>이름: </b> <span>${detail.childrenName}</span></p>
                                <p><b>생년월일: </b> <span>${detail.childrenBirth}</span></p>
                                <p><b>성별: </b> <span>${genderStr}</span></p>
                                <p><b>참고사항: </b> <span>${detail.childrenInfo}</span></p>
                                <hr>
                                <h3>[ 보호자 정보 ]</h3>
                                <p><b>이름: </b> <span>${detail.usersName}</span></p>
                                <p><b>생년월일: </b <span>${detail.usersBirth}</span></p>
                                <p><b>주소: </b> <span>${detail.usersAddr}</span></p>
                                <button id="change-info" onclick="changeDate()">예약정보 변경하기</button>
                            `;

                            // 예약취소 상태일 때
                            if (statusDropdown.value === "2") {
                                
                                const changeInfoButtons = document.querySelectorAll('#change-info');
                                changeInfoButtons.forEach((changeInfoBtn) => {
                                    if (changeInfoBtn) {
                                        changeInfoBtn.setAttribute('disabled', '');
                                    }
                                });
                            }


                        }).fail(function (error) {
                            console.log(error);
                        })
                    });

                    // tr 요소를 table body에 추가
                    tableBody.appendChild(tr);
                });
            } else {
                console.log("예약 없음");
                $('#appo-table').hide();
                $('#no-appo').show();

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
                tableBody.innerHTML = '';
                dateStatusText.innerText = "선택한 날짜에 예약을 생성하지 않았습니다.";
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
            // alert(result.update);
            alert("예약이 변경되었습니다.");
        }).fail(function (error) {
            alert(error);
        })

        // 취소버튼
    } else if(result === false) {
        alert("변경을 취소하셨습니다.");
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

// const statusOp = document.querySelector("#status");

// 예약취소 상태 확인용
$(document).ready(function() {
    if ($("#status").val() === "2") {
        console.log("2임");
    }

    $('#status').on('change', function() {
        const selectedOption = $(this).val();
        console.log(`선택된 옵션: ${selectedOption}`);
    });
});


/* 전체 예약자 불러오기 */

