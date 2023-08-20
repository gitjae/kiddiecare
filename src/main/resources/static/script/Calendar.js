// window.onload = function () {
//     //getHospInfoDetail();
//     buildCalendar();
//     //getTotalInfo();
//     $('#booking-btn').prop("disabled", true);
// }

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
    // console.log("nowMonth : " + nowMonth.getFullYear());

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
    // 의사 카드 초기화
    $('#doctor-cards').empty();
    $('#booking-btn').prop("disabled", true);

    if (document.getElementsByClassName("choiceDay")[0]) {                              // 기존에 선택한 날짜가 있으면
        document.getElementsByClassName("choiceDay")[0].classList.remove("choiceDay");  // 해당 날짜의 "choiceDay" class 제거
    }
    nowColumn.classList.add("choiceDay");
    // 클릭된 날짜 및 요일 저장
    selectDate = new Date(nowMonth.getFullYear(), nowMonth.getMonth(), parseInt(nowColumn.innerText)); // 선택된 날짜 저장
    let dayNames = ["일", "월", "화", "수", "목", "금", "토"];
    selectDay = dayNames[selectDate.getDay()]; // 선택된 요일 저장
    formattedDate = selectDate.getFullYear() + "-" + leftPad(selectDate.getMonth() + 1) + "-" + leftPad(selectDate.getDate());  // --> 선택된 날짜: Thu Aug 17 2023 00:00:00 GMT+0900 (한국 표준시) 값 변경

    //console.log("선택된 날짜:", formattedDate);  // String
    //console.log("선택된 요일:", selectDay);

    document.querySelector('.time-slots-table').style.display = 'block';        // css

    let ykiho = document.getElementById("hospital-name").getAttribute("ykiho");
    console.log("ykiho : ", ykiho);

    document.querySelector('.time-slots-table').innerHTML = '';     // ajax 호출 전에 슬롯 정보 초기화시켜서 데이터가 있는 날 -> 없는 날 클릭하면 다시 호출되도록

    $.ajax({
        url: "/timeSlotsDateGetByYkiho", method: "GET", data: {
            ykiho: ykiho, date: selectDate
        }
    }).done(res => {
        console.log(res);
        console.log(res.slots);
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
    let timeSlotsContent = '<p class="time-slots-info">(진료 가능 인원 / 최대 인원)</p>';

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
                    ${slot.time.slice(0,-3)}<br>(${slot.enable}/${slot.max})
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
                currentSelectedCard.style.color = '';
            }
            $('#booking-btn').prop("disabled", true);
            // 클릭된 카드의 배경색 변경
            this.style.backgroundColor = '#3E85EF';
            this.style.color = '#FFFFFF';

            // 현재 선택된 카드 업데이트
            currentSelectedCard = this;

            selectedSlotInfo.doctorNo = this.getAttribute('data-doctorNo');
            selectedSlotInfo.date = this.getAttribute('data-date');
            selectedSlotInfo.time = this.getAttribute('data-time');
            selectedSlotInfo.weekday = this.getAttribute('data-weekday');
            selectedSlotInfo.ykiho = this.getAttribute('data-ykiho');
            selectedSlotInfo.timeSlotNo = this.getAttribute('data-timeSlotNo');

            console.log(selectedSlotInfo);

            $.ajax({
                method: 'GET',
                url: '/api/v1/doctor/schedule',
                data:{
                    ykiho:selectedSlotInfo.ykiho,
                    date:selectedSlotInfo.date,
                    time:selectedSlotInfo.time
                }
            }).done(res => {
                console.log("<doctors>")
                console.log(res);

                $('#doctor-cards').empty();
                // 닥터 카드 만들기
                for(let i=0; i<res.doctors.length; i++){
                    var card = `<div class="doctor-card" onclick="selectDoctor(this)">
                        <div class="doctorName" no="${res.doctors[i].no}">${res.doctors[i].doctorName}</div>
                        <div class="timeSlot" slot="${res.slots[i].no}">(${res.slots[i].enable}/${res.slots[i].max})</div>
                    </div>`

                    $('#doctor-cards').append(card);
                }
            })
        });
    });

    let currentSelectedDoctorCard = null;
    document.addEventListener('click', function(event) {
        if (event.target.closest('.doctor-card')) {
            // 이전에 선택된 doctor-card의 배경색을 원래대로 돌려놓기
            if (currentSelectedDoctorCard) {
                currentSelectedDoctorCard.style.backgroundColor = '';
                currentSelectedDoctorCard.style.color = '';
            }

            // 클릭된 doctor-card의 배경색 변경
            const doctorCard = event.target.closest('.doctor-card');
            doctorCard.style.backgroundColor = '#3E85EF';
            doctorCard.style.color = '#FFFFFF';

            // 현재 선택된 doctor-card 업데이트
            currentSelectedDoctorCard = doctorCard;
        }
    });

}


function selectDoctor(card){
    const doctorNo = $(card).find('.doctorName').attr('no');
    const slotNo = $(card).find('.timeSlot').attr('slot');

    selectedSlotInfo.doctorNo = doctorNo;
    selectedSlotInfo.timeSlotNo = slotNo;

    $('#booking-btn').prop("disabled", false);
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

function getHospitalNameFromUrl() {
    const currentUrl = new URL(window.location.href);
    const hospitalName = currentUrl.searchParams.get('hospitalName');
    return hospitalName;
}