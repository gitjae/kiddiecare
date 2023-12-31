window.onload = function () {
    getHospInfoDetail();
    buildCalendar();
    getTotalInfo();
    handleLikeFeature(ykiho);
}

let ykiho = null;
let nowMonth = new Date();
let today = new Date();
today.setHours(0, 0, 0, 0);

let selectedSlotInfo = {
    doctorNo: null, date: null, time: null, weekday: null, ykiho: null, timeSlotNo: null
};

// 달력 생성 : 해당 달에 맞춰 테이블을 만들고, 날짜를 채워 넣기
function buildCalendar() {
    let firstDate = new Date(nowMonth.getFullYear(), nowMonth.getMonth(), 1);
    let lastDate = new Date(nowMonth.getFullYear(), nowMonth.getMonth() + 1, 0);

    let tbody_Calendar = document.querySelector(".Calendar > tbody");

    document.getElementById("calYear").innerText = nowMonth.getFullYear();
    document.getElementById("calMonth").innerText = leftPad(nowMonth.getMonth() + 1);

    while (tbody_Calendar.rows.length > 0) {                        // 이전 출력결과가 남아있는 경우 초기화
        tbody_Calendar.deleteRow(tbody_Calendar.rows.length - 1);
    }

    let nowRow = tbody_Calendar.insertRow();        // 첫번째 행 추가

    for (let j = 0; j < firstDate.getDay(); j++) {  // 이번달 1일의 요일만큼
        let nowColumn = nowRow.insertCell();        // 열 추가
    }

    for (let nowDay = firstDate; nowDay <= lastDate; nowDay.setDate(nowDay.getDate() + 1)) {   // day는 날짜를 저장하는 변수, 이번달 마지막날까지 증가시키며 반복

        let nowColumn = nowRow.insertCell();        // 새 열을 추가하고
        nowColumn.innerText = leftPad(nowDay.getDate());      // 추가한 열에 날짜 입력
        if (nowDay.getDay() == 0) {                 // 일요일인 경우 글자색 빨강으로
            nowColumn.style.color = "#DC143C";
        }
        if (nowDay.getDay() == 6) {                 // 토요일인 경우 글자색 파랑으로 하고
            nowColumn.style.color = "#0000CD";
            nowRow = tbody_Calendar.insertRow();    // 새로운 행 추가
        }
        if (nowDay < today) {                       // 지난날인 경우
            nowColumn.className = "pastDay";
        } else if (nowDay.getFullYear() == today.getFullYear() && nowDay.getMonth() == today.getMonth() && nowDay.getDate() == today.getDate()) { // 오늘인 경우
            nowColumn.className = "today";
            nowColumn.onclick = function () {
                choiceDate(this);
            }
        } else {                                      // 미래인 경우
            nowColumn.className = "futureDay";
            nowColumn.onclick = function () {
                choiceDate(this);
            }
        }
    }
}

// 날짜 선택, 각 날짜의 timeSlots
let selectDate = null; // 날짜 저장 (Date 타입)
let selectDay = null;   // 요일 저장 (String 타입)
let formattedDate = null;

function choiceDate(nowColumn) {
    if (document.getElementsByClassName("choiceDay")[0]) {                              // 기존에 선택한 날짜가 있으면
        document.getElementsByClassName("choiceDay")[0].classList.remove("choiceDay");  // 해당 날짜의 "choiceDay" class 제거
    }
    nowColumn.classList.add("choiceDay");
    // 클릭된 날짜 및 요일 저장
    selectDate = new Date(nowMonth.getFullYear(), nowMonth.getMonth(), parseInt(nowColumn.innerText)); // 선택된 날짜 저장
    let dayNames = ["일", "월", "화", "수", "목", "금", "토"];
    selectDay = dayNames[selectDate.getDay()]; // 선택된 요일 저장
    formattedDate = selectDate.getFullYear() + "-" + leftPad(selectDate.getMonth() + 1) + "-" + leftPad(selectDate.getDate());  // --> 선택된 날짜: Thu Aug 17 2023 00:00:00 GMT+0900 (한국 표준시) 값 변경

    document.querySelector('.time-slots-table').style.display = 'block';        // css

    let ykiho = document.getElementById("hospital-name").getAttribute("ykiho");
    document.querySelector('.time-slots-table').innerHTML = '';     // ajax 호출 전에 슬롯 정보 초기화시켜서 데이터가 있는 날 -> 없는 날 클릭하면 다시 호출되도록

    $.ajax({
        url: "/timeSlotsDateGetByYkiho", method: "GET", data: {
            ykiho: ykiho, date: selectDate
        }
    }).done(res => {
        if (res.slots && res.slots.length > 0) {  // slots 데이터가 있을 경우에만 화면 업데이트
            showTimeSlots(res.slots);
        }
    }).fail(function () {
        console.error("timeSlotsGet");
    });
}

// 타임 슬롯 화면에 나타내기
function showTimeSlots(slots) {
    const timeSlotsTable = document.querySelector('.time-slots-table');
    let timeSlotsContent = '<p class="time-slots-info"></p>';

    slots.forEach(slot => {
        // 예약 풀부킹 확인
        let isFull = slot.max === slot.count;

        timeSlotsContent += `
            <div class="time-slot-card ${isFull ? 'full-time-slot' : ''}" 
                 data-doctorNo="${slot.doctorNo}" 
                 data-date="${slot.date}" 
                 data-time="${slot.time}" 
                 data-weekday="${slot.weekday}" 
                 data-ykiho="${slot.ykiho}"
                 data-timeSlotNo="${slot.no}">
                <div class="time-slot-content">
                    ${slot.time}<br>(${slot.count}/${slot.max})
                </div>
            </div>
        `;
    });

    timeSlotsTable.innerHTML = timeSlotsContent;

    // 선택한 타임 슬롯
    let currentSelectedCard = null;


    // 각 타임 슬롯 클릭 이벤트
    document.querySelectorAll('.time-slot-card').forEach(card => {
        card.addEventListener('click', function () {
            // 이전에 선택된 카드의 배경색을 원래대로 돌려놓기
            if (currentSelectedCard) {
                currentSelectedCard.style.backgroundColor = '';
            }

            // 클릭된 카드의 배경색 변경
            this.style.backgroundColor = '#e0e0e0';

            // 현재 선택된 카드 업데이트
            currentSelectedCard = this;

            selectedSlotInfo.doctorNo = this.getAttribute('data-doctorNo');
            selectedSlotInfo.date = this.getAttribute('data-date');
            selectedSlotInfo.time = this.getAttribute('data-time');
            selectedSlotInfo.weekday = this.getAttribute('data-weekday');
            selectedSlotInfo.ykiho = this.getAttribute('data-ykiho');
            selectedSlotInfo.timeSlotNo = this.getAttribute('data-timeSlotNo');
        });
    });

}

// 이전달 버튼 클릭
function prevCalendar() {
    nowMonth = new Date(nowMonth.getFullYear(), nowMonth.getMonth() - 1, nowMonth.getDate());   // 현재 달을 1 감소
    buildCalendar();    // 달력 다시 생성
}

// 다음달 버튼 클릭
function nextCalendar() {
    nowMonth = new Date(nowMonth.getFullYear(), nowMonth.getMonth() + 1, nowMonth.getDate());   // 현재 달을 1 증가
    buildCalendar();    // 달력 다시 생성
}

// input값이 한자리 숫자인 경우 앞에 '0' 붙여주는 함수
function leftPad(value) {
    if (value < 10) {
        value = "0" + value;
        return value;
    }
    return value;
}

// ----------------------------------------------
function getHospitalNameFromUrl() {
    const currentUrl = new URL(window.location.href);
    const hospitalName = currentUrl.searchParams.get('hospitalName');
    return hospitalName;
}

function getSgguCdFromUrl() {
    const currentUrl = new URL(window.location.href);
    const sgguCd = currentUrl.searchParams.get('sgguCd');
    return sgguCd;
}

// 예약하기 버튼 액션
document.getElementById("booking-btn").onclick = function () {
    if (selectedSlotInfo.timeSlotNo == null) {
        alert("예약 시간을 선택해주세요.");
    } else {
        let hospitalName = encodeURIComponent(getHospitalNameFromUrl().trim());
        location.href = `/appointment/booking?hospitalName=${hospitalName}&treatmentDate=${formattedDate}&treatmentDay=${selectDay}&doctorNo=${selectedSlotInfo.doctorNo}&slotTime=${selectedSlotInfo.time}&slotWeekday=${selectedSlotInfo.weekday}&timeSlotNo=${selectedSlotInfo.timeSlotNo}`;
    }
}

// 정보 뿌리기
function getHospInfoDetail() {
    const hospitalName = getHospitalNameFromUrl();

    $.ajax({
        method: 'GET',
        url: `/api/appointment/hospitalDetail?hospitalName=${hospitalName}`,
    })
        .done(res => {
            if (res.dbHospitalData) {
                document.getElementById('hospital-name').textContent = res.dbHospitalData.hospitalName;
                document.getElementById('hospital-name').setAttribute('ykiho', res.dbHospitalData.ykiho);
                document.getElementById('hospital-intro').textContent = res.dbHospitalData.hospitalIntro;

                // 의사 정보 처리
                let doctorContainer = document.querySelector(".doctor-info");
                doctorContainer.innerHTML = ''; // Clear existing doctor info

                res.doctors.forEach(doctor => {
                    let doctorCard = document.createElement('div');
                    doctorCard.classList.add('doctor-card');
                    doctorCard.innerHTML = `
                    <img src="/image/doctor_icon.png">
                    <div class="doctor-text">
                    <p id="doctor-no">의사 번호 : ${doctor.no}</p>
                    <p id="doctor-name">${doctor.doctorName}</p>
<!--                    <p id="doctor-offDay">휴진일</p>-->
                    </div>`;

                    doctorContainer.appendChild(doctorCard);
                });
                // 찜 기능 수정중 -- 희수
                handleLikeFeature(res.dbHospitalData.ykiho);
            }
        })
        .fail(err => {
            console.error("Error:", err);
        });
}


// 찜 (좋아요)
let userName = null;
function handleLikeFeature(ykiho) {
    userName = document.getElementById('loggedInUser').value;
    getUserNoBySession()
        .then(userNo => {
            checkLikeStatus(userNo, ykiho);
            // 찜 기능 나머지
        })
        .catch(err => {
            console.error("Error while getting userNo:", err.responseText);
        });
}

function getUserNoBySession() {
    return new Promise((resolve, reject) => {
        $.ajax({
            method: 'GET',
            url: `/api/v1/users/getUserNoBySession`,
        })
            .done(res => {
                resolve(res);
            })
            .fail(err => {
                console.error("Error:", err.responseText);
                reject(err);
            });
    });
}

// 실제 좋아요 기능
function likeHospital(userNo, ykiho) {
    $.ajax({
        method: 'POST',
        url: `/api/like`,
        contentType: "application/json",
        data: JSON.stringify({
            userNo: userNo,
            ykiho: ykiho
        })
    })
        .done(function() {
            updateLikeButtons(true);
        })
        .fail(function(err) {
            console.error("Error while liking the hospital:", err.responseText);
        });
}

function unlikeHospital(userNo, ykiho) {
    $.ajax({
        method: 'DELETE',
        url: `/api/like/${userNo}/${ykiho}`
    })
        .done(function() {
            updateLikeButtons(false);
        })
        .fail(function(err) {
            console.error("Error while unliking the hospital:", err.responseText);
        });
}

function updateLikeButtons(isLiked) {
    const noLikes = document.querySelectorAll('.noLike');
    const yesLikes = document.querySelectorAll('.yesLike');

    noLikes.forEach(noLike => {
        noLike.style.display = isLiked ? "none" : "block";
    });

    yesLikes.forEach(yesLike => {
        yesLike.style.display = isLiked ? "block" : "none";
    });
}

// 페이지 로딩 시 좋아요 유무 체크
function checkLikeStatus(userNo, ykiho) {
    $.ajax({
        method: 'GET',
        url: `/api/like/existence?userNo=${userNo}&ykiho=${ykiho}`
    })
        .done(function(isLiked) {
            updateLikeButtons(isLiked);
        })
        .fail(function(err) {
            console.error("Error while checking like status:", err.responseText);
        });
}



function getTotalInfo() {
    const hospitalName = getHospitalNameFromUrl();
    const sgguCd = getSgguCdFromUrl();

    $.ajax({
        method: 'GET',
        url: '/search/detail',
        data: {
            yadmNm: hospitalName,
            sgguCd: sgguCd
        }
    }).done(res => {
        const BD = res.data.hospBasisData;
        const DD = res.data.hospDetailData;
        const LD = res.data.hospListData;
        const SD = res.data.hospSubData;
        if (res.result == 'success') {
            // 카카오 맵
            xPos = BD.xpos;
            yPos = BD.ypos;
            makeMap();
            makeMarker();

            //
            $('#hospital-name').text(BD.yadmNm);
            $('#hospital-addr').text(BD.addr);
            $('#hospital-tell').text(BD.telno);
            $('#workhour-mon').text((timeFormat(DD.trmtMonStart) + " - " + timeFormat(DD.trmtMonEnd)));
            $('#workhour-tue').text(timeFormat(DD.trmtTueStart) + " - " + timeFormat(DD.trmtTueEnd));
            $('#workhour-wed').text(timeFormat(DD.trmtWedStart) + " - " + timeFormat(DD.trmtWedEnd));
            $('#workhour-thu').text(timeFormat(DD.trmtThuStart) + " - " + timeFormat(DD.trmtThuEnd));
            $('#workhour-fri').text(timeFormat(DD.trmtFriStart) + " - " + timeFormat(DD.trmtFriEnd));
            $('#workhour-sat').text(timeFormat(DD.trmtSatStart) + " - " + timeFormat(DD.trmtSatEnd));
            $('#workhour-sun').text(timeFormat(DD.trmtSunStart) + " - " + timeFormat(DD.trmtSunEnd));
            $('#hospital-park').text("주차정보" + DD.parkEtc);
        } else {
            alert('병원정보를 불러오지 못했습니다. \n잠시 후 다시 시도해주세요.')
        }
    })
}

function timeFormat(time) {
    return time.slice(0, 2) + ":" + time.slice(2, 4);
}
