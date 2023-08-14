// const dayNames = ["일", "월", "화", "수", "목", "금", "토"];

// const year = date.getFullYear();
// const month = date.getMonth() + 1;
// const day = date.getDate();
// const dayOfWeek = dayNames[date.getDay()];

let finalList = [];
let setWeekdayArr = [];

$(function () {
    let today = new Date();
    today.setDate(today.getDate());
    // 선택가능 제한일
    let lastDay = new Date();
    lastDay.setDate(lastDay.getDate() + 90);

    let todayDate = today.toISOString().split('T')[0];
    let lastDate = lastDay.toISOString().split('T')[0];
    const sDay = document.getElementById('start-date');
    const eDay = document.getElementById('end-date');

    sDay.min = todayDate;
    sDay.max = lastDate;
    sDay.value = todayDate;

    eDay.min = todayDate;
    eDay.max = lastDate;
    console.log("today: "+todayDate);
    console.log("lastday: "+lastDate);

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
}

function setDate() {
    const startDate = document.querySelector("#start-date").value;
    const endDate = document.querySelector("#end-date").value;

    // 제외날짜 범위 설정
    const day = document.getElementById('except-day');
    day.min = startDate;
    day.max = endDate;
}

// 날짜 범위 설정 끝났을 시 시간 범위 설정하기
function timeSetBtn() {
    let setDateArr = [];
    // let setWeekdayArr = [];
    let sundayArr = [];
    let exceptDaysArr = [];

    const day = document.getElementById('except-day');
    // date에 지정된 최대,최소 날짜
    let startDate = new Date(day.min);
    let endDate = new Date(day.max);

    // 제외할 조건(공휴일, 주말) 체크박스 체크여부
    const sundayCheck = document.getElementById('except-sunday').checked;

    // 제외할 날짜들
    const exceptDaysArea = document.getElementById('except-days-area');
    const buttons = exceptDaysArea.getElementsByTagName('button');

    // 날짜정보 다 담기
    // 일요일제외 체크했을 시
    if(sundayCheck) {
        for (let date = startDate; date <= endDate; date.setDate(date.getDate() + 1)) {
            if(date.getDay() !== 0) {
                setDateArr.push(date.toISOString().split('T')[0]);
                // 일요일 담기
            } else {
                sundayArr.push(date.toISOString().split('T')[0]);
            }
        }
    }else {
        for (let date = startDate; date <= endDate; date.setDate(date.getDate() + 1)) {
            setDateArr.push(date.toISOString().split('T')[0]);
        }
    }

    // 제외할 날짜들
    for (let i = 0; i < buttons.length; i++) {
        let day = buttons[i].id.split('_')[1];
        console.log(day);
        exceptDaysArr.push(day);
    }

    // 담은날짜->제외날짜 필터링
    finalList = setDateArr.filter(function(item) {
        return !exceptDaysArr.includes(item);
    });


    finalList.forEach(function (day) {
        day = new Date(day);
        dayWeekExtrc(day, setWeekdayArr);
    })

    setWeekdayArr.sort();
    // console.log("끝"+setWeekdayArr);
    //
    // console.log("담은날짜" + setDateArr);
    // console.log("일요일" + sundayArr);
    // console.log("제외날짜" + exceptDaysArr);
    // console.log("요일" + setWeekdayArr);
    console.log("최종날짜" + finalList);

    // 받아온 요일 정보 배열
    console.log(setWeekdayArr);
    const setWeekday = document.getElementById('set-weekday');
    let weekday = ["일","월","화","수","목","금","토"];
    let output = "";

    for(let i=0; i<setWeekdayArr.length; i++) {
        output += `
            <div class="week-area">
                <p class="week-title">${weekday[setWeekdayArr[i]]}</p>
                <input type="number" id="start-hour-${i}" min="0" max="24" placeholder="0~24"/>
                <input type="number" id="start-minute-${i}" value="00" readonly/>
                부터
                <input type="number" id="end-hour-${i}" min="0" max="24" placeholder="0~24"/>
                <input type="number" id="end-minute-${i}" value="00" readonly/>

                <input type="text" id="max-num-${i}" placeholder="예약가능인원수 설정">
            </div>
        `;
    }
    setWeekday.innerHTML = output;
}

function saveTimes() {
    const dateList = [];
    let weekdays = ["일","월","화","수","목","금","토"];
    let groupedData = {};

    const dateFormat = date => `${date.getFullYear()}-${("0" + (date.getMonth() + 1)).slice(-2)}-${("0" + date.getDate()).slice(-2)}`; // 날짜 형식 변환 함수
    finalList.forEach(function (day) {
        day = new Date(day);
        dateList.push(day);
    })

    dateList.forEach(day => {
        const weekDay = day.getDay();
        let data = [];

        if(setWeekdayArr.includes(weekDay)) {
            // 현재 요일 인덱스
            const dayIndex = setWeekdayArr.indexOf(weekDay);
            const startHour = parseInt(document.getElementById(`start-hour-${dayIndex}`).value, 10);
            const endHour = parseInt(document.getElementById(`end-hour-${dayIndex}`).value, 10);
            const maxNum = document.getElementById(`max-num-${dayIndex}`).value;

            // 시간 범위 내의 값들을 데이터 배열에 추가
            for (let hour = startHour; hour < endHour; hour++) {
                data.push({
                    date: dateFormat(day), // 날짜 문자열
                    weekday: weekdays[weekDay], // 요일 문자열
                    time: `${("0" + hour).slice(-2)}:00`, // 시간 문자열
                    max: maxNum, // 총 인원
                });
            }
            
            // 전체적으로 크게 날짜별로 그룹 묶어주기
            data.forEach(item => {
                if(!groupedData[item.date]) {
                    groupedData[item.date] = []; // 해당 날짜의 키가 없으면 빈 배열 추가
                }
                groupedData[item.date].push(item); // 날짜별 배열에 데이터 추가
            })
        }
    });

    console.log(groupedData);

    $.ajax({
        type: "POST",
        url: "/api/v1/admin/appo/timeset-create",
        data: JSON.stringify(groupedData),
        contentType: "application/json; charset=utf-8",
        success: function (response) {
            console.log("저장 성공!");
        },
        error: function (error) {
            console.log("저장 실패...ㅠ");
        }
    });
}