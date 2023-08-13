// const dayNames = ["일", "월", "화", "수", "목", "금", "토"];

// const year = date.getFullYear();
// const month = date.getMonth() + 1;
// const day = date.getDate();
// const dayOfWeek = dayNames[date.getDay()];

$(function () {
    let today = new Date();
    today.setDate(today.getDate());
    let todayDate = today.toISOString().split('T')[0];
    const sDay = document.getElementById('start-date');
    const eDay = document.getElementById('end-date');

    sDay.min = todayDate;
    sDay.value = todayDate;

    eDay.min = todayDate;
    console.log("today: "+todayDate);

    get_hospital_name();
});

// 병원명 설정
function get_hospital_name() {
    let ykiho = $('#ykiho').val();

    $.ajax({
        url: `/hospitalName/${ykiho}`,
        method: 'GET',
        timeout: 0
    }).done(function (response) {
        console.log(response);
        $('#hospital_name').text(response.hospitalName);
    }).fail(function (error) {
        console.log(error);
    });
}

// <제외날짜> 날짜 클릭 시 클릭한 날짜 삭제 가능
document.addEventListener('DOMContentLoaded', function() {
    const buttonDaysArea = document.getElementById('except-days-area');

    if (buttonDaysArea) {
        buttonDaysArea.addEventListener('click', function(event) {
            if (event.target.tagName === 'BUTTON') {
                event.target.remove();
            }
        });
    } else {
        console.error('except-days-area element not found');
    }
});

function exceptAdd() {
    const exceptDaysArea = document.getElementById('except-days-area');
    const day = document.getElementById('except-day').value;

    let content = "";
    // console.log(day);
    const exceptDaysButtons = document.querySelectorAll("#except-days-area .remove");

    let dupl = false;
    for(const button of exceptDaysButtons) {
        if(button.textContent === day) {
            alert("이미 존재하는 날짜");
            dupl = true;
            break;
        }
    }

    if(!dupl) {
        content += `
                <button id="btn_${day}" class="remove">${day}</button>
        `
        exceptDaysArea.innerHTML += content;
    }

}

// 요일 추출
function dayWeekExtrc(date, setWeekdayArr) {

    // console.log(date);
    // return `${year}년 ${month}월 ${day}일 (${dayOfWeek}요일)`;
    const duplWeek = setWeekdayArr.includes(date.getDay());
    console.log(duplWeek);
    if(!duplWeek) {
        setWeekdayArr.push(date.getDay());
    }
    console.log(setWeekdayArr);
}

function setDate() {
    const startDate = document.querySelector("#start-date").value;
    const endDate = document.querySelector("#end-date").value;

    // 제외할 조건(공휴일, 주말) 체크박스 체크여부
    const sundayCheck = document.getElementById('except-sunday').checked;

    let setWeekdayArr = [];
    let setDateArr = [];
    const start = new Date(startDate);
    const end = new Date(endDate);

    for (let date = start; date <= end; date.setDate(date.getDate() + 1)) {
        // 요일 배열에 추출
        dayWeekExtrc(date, setWeekdayArr);
        if(setWeekdayArr.length === 7) {
            break;
        }
    }
    for (let date = start; date <= end; date.setDate(date.getDate() + 1)) {
        // 날짜정보 다 담기
        setDateArr.push(date);
    }

    if(sundayCheck && setWeekdayArr.indexOf(6) !== -1) {
        setWeekdayArr.splice(setWeekdayArr.indexOf(6), 1);
    }
    setWeekdayArr.sort();
    console.log("끝"+setWeekdayArr);

    // 제외날짜 범위 설정
    const day = document.getElementById('except-day');
    day.min = startDate;
    day.max = endDate;

    timeSetBtn(setWeekdayArr);
}

// 날짜 범위 설정 끝났을 시 시간 범위 설정하기
function timeSetBtn(setWeekdayArr) {
    // 받아온 요일 정보 배열
    console.log(setWeekdayArr);
    const setWeekday = document.getElementById('set-weekday');
    let weekday = ["월","화","수","목","금","토","일"];
    let output = "";

    for(let i=0; i<setWeekdayArr.length; i++) {
        output += `
            <div class="week-area">
                <p class="week-title">${weekday[setWeekdayArr[i]]}</p>
                <button id="s-ampm-${i}">AM</button>
                <input type="number" id="start-hour-${i}" min="1" max="12" value="12"/>
                <input type="number" id="start-minute-${i}" value="00" readonly/>
                부터
                <button id="e-ampm-${i}">PM</button>
                <input type="number" id="end-hour-${i}" value="01"/>
                <input type="number" id="end-minute-${i}" value="00" readonly/>

                <input type="text" id="max-num-${i}" placeholder="예약가능인원수 설정">
            </div>
        `;
    }

    setWeekday.innerHTML = output;
}