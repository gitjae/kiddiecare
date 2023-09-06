let finalList = [];
let setWeekdayArr = [];
let doctorAppoDate = [];


function getDoctorAppoDate(){
    let adminYkiho = $('#ykiho').val();
    let no = $('.selected').attr('id');
    $.ajax({
        type: "GET",
        url: "/api/v1/admin/appo/date-list",
        data: {
            "ykiho" : adminYkiho ,
            "no" : no
        },
        contentType: "application/json; charset=utf-8",
        success: function (response) {
            doctorAppoDate = response.data;
        },
        error: function (error) {
            console.log("의사 예약 데이터 불러오기 실패");
        }
    });
}

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


});

// 날짜범위설정 버튼 클릭 시 제외날짜 설정부분 show
$("#set-date").click(function() {
    // 의사 선택 확인
    if (!$('.select-option .option').hasClass('selected')) {
        alert('의사를 선택해주세요.');
        return;
    }

    const startDate = $('#start-date').val();
    const endDate = $('#end-date').val();

    // 날짜 범위 확인
    if (startDate === '' || endDate === '') {
        alert('날짜 범위를 지정해주세요.');
        return;
    }

    // 올바른 날짜 범위 확인
    if (startDate > endDate) {
        alert('잘못된 날짜 범위입니다. 날짜 범위를 다시 지정해주세요.');
        return;
    }

    // 모든 검사를 통과한 경우 except-area 표시해주고 setDate메소드실행
    $('#except-area').show();
    setDate();
});




// <제외날짜> 날짜 클릭 시 클릭한 날짜 삭제 가능
$(document).ready(function() {
    const buttonDaysArea = $('#except-days-area');

    if (buttonDaysArea.length > 0) {
        buttonDaysArea.on('click', function(event) {
            if (event.target.tagName === 'BUTTON') {
                $(event.target).remove();
            }
        });
    } else {
        console.error('except-days-area 요소 못찾음');
    }
});

function exceptAdd() {
    const exceptDaysArea = document.getElementById('except-days-area');
    const day = document.getElementById('except-day').value;

    let content = "";
    const exceptDaysButtons = document.querySelectorAll("#except-days-area .remove");

    if(day === '') {
        alert('날짜를 입력해주세요.');
        return;
    }

    let dupl = false;
    for (const button of exceptDaysButtons) {
        if (button.textContent === day) {
            alert("이미 존재하는 날짜");
            dupl = true;
            break;
        }
    }

    if (!dupl) {
        content += `
                <button id="btn_${day}" class="remove">${day}</button>
        `
        exceptDaysArea.innerHTML += content;
    }

}

// 요일 추출
function dayWeekExtrc(date, setWeekdayArr) {
    const duplWeek = setWeekdayArr.includes(date.getDay());
    if (!duplWeek) {
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
    setWeekdayArr = [];
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
    if (sundayCheck) {
        for (let date = startDate; date <= endDate; date.setDate(date.getDate() + 1)) {
            if (date.getDay() !== 0) {
                setDateArr.push(date.toISOString().split('T')[0]);
                // 일요일 담기
            } else {
                sundayArr.push(date.toISOString().split('T')[0]);
            }
        }
    } else {
        for (let date = startDate; date <= endDate; date.setDate(date.getDate() + 1)) {
            setDateArr.push(date.toISOString().split('T')[0]);
        }
    }

    // 제외할 날짜들
    for (let i = 0; i < buttons.length; i++) {
        let day = buttons[i].id.split('_')[1];
        exceptDaysArr.push(day);
    }

    // 담은날짜->제외날짜 필터링
    finalList = setDateArr.filter(function (item) {
        return !exceptDaysArr.includes(item);
    });


    finalList.forEach(function (day) {
        day = new Date(day);
        dayWeekExtrc(day, setWeekdayArr);
    })

    setWeekdayArr.sort();

    if(setWeekdayArr.length === 0){
        swal("날짜 설정을 다시 해주세요", "날짜 범위 지정이 잘못되어 있습니다","warning");

    }else{
        // time-set-btn 클릭 시 time-set-area 영역 표시
        $('.time-set-area').show();
        $('.meal-time-set-area').show();

        const setWeekday = document.getElementById('set-weekday');
        let weekday = ["일", "월", "화", "수", "목", "금", "토"];
        let output = "";

        for (let i = 0; i < setWeekdayArr.length; i++) {
            output += `
            <div class="week-area">
                <p class="week-title">${weekday[setWeekdayArr[i]]}요일</p>
                <input type="number" id="start-hour-${i}" min="0" max="24" placeholder="0~24"/>
                :
                <input type="number" id="start-minute-${i}" value="00" readonly/>
                부터
                <input type="number" id="end-hour-${i}" min="0" max="24" placeholder="0~24"/>
                :
                <input type="number" id="end-minute-${i}" value="00" readonly/>
                <span>예약 가능 인원수: </span>
                <input type="text" id="max-num-${i}" class="max-num" >
            </div>
        `;
        }
        setWeekday.innerHTML = output;
    }

}

// 예약 생성하기 버튼 클릭 시 입력되지 않은 영역 예외처리
$('#appo-create-btn').click(function() {
    for (let i = 0; i < setWeekdayArr.length; i++) {

        // 내용이 없으면 alert 표시하고 focus
        if ($('#start-hour-' + i).val() === "") {
            alert('시작 시간을 입력해주세요.');
            $('#start-hour-' + i).focus();
            return;
        } else if ($('#end-hour-' + i).val() === "") {
            alert('끝나는 시간을 입력해주세요.');
            $('#end-hour-' + i).focus();
            return;
        }

        let startHour = parseInt($('#start-hour-' + i).val());
        let endHour = parseInt($('#end-hour-' + i).val());
        let maxNum = parseInt($('#max-num-'+ i).val());

        // 시작시간 < 끝시간일 때 예외처리
        if (startHour > endHour) {
            alert('시작 시간은 종료 시간보다 작아야 합니다.');
            $('#start-hour-' + i).focus();
            return;

        }
        if(startHour === endHour) {
            alert('시작시간과 종료시간이 같습니다. 다시 입력해주세요.');
            $('#start-hour-' + i).focus();
            return;

        }

        if(endHour > 25 || endHour < 0) {
            alert('유효한 시간 범위를 입력해주세요.');
            $('#end-hour-' + i).focus();
            return;
        }
        if(startHour > 25 || startHour < 0) {
            alert('유효한 시간 범위를 입력해주세요.');
            $('#start-hour-' + i).focus();
            return;
        }

        // 예약가능한 인원수 예외처리
        if($('#max-num-'+ i).val() === ""){
            alert('예약가능한 인원수를 입력해주세요.');
            $('#max-num-'+ i).focus();
            return;
        }

        if(maxNum < 0) {
            alert('0 미만의 숫자는 입력 불가능합니다.');
            return;
        }
    }

    // 예외 처리를 통과한 경우 saveTimes() 호출
    saveTimes();
});

function saveTimes() {
    const dateList = [];
    let weekdays = ["일", "월", "화", "수", "목", "금", "토"];
    let groupedData = {};
    let hospitalCode = document.getElementById('ykiho').value;
    // 선택한 의사명
    const selectedDoctor = document.getElementById('selectedDoctor').textContent;

    // 제외할 시간(점심시간)
    let lunchCheck = document.getElementById('no-lunch').checked;
    const lStartHour = parseInt(document.getElementById('l-start-hour').value, 10);
    const lEndHour = parseInt(document.getElementById('l-end-hour').value, 10);

    // 제외할 시간(저녁시간)
    let dinnerCheck = document.getElementById('no-dinner').checked;
    const dStartHour = parseInt(document.getElementById('d-start-hour').value, 10);
    const dEndHour = parseInt(document.getElementById('d-end-hour').value, 10);

    if(!lunchCheck && lStartHour < 0 || lStartHour >= 24 || lEndHour <= 0 || lEndHour > 24) {
        alert("유효한 점심식사 시간을 입력해주세요.");
        return;
    }

    if(!dinnerCheck && dStartHour < 0 || dStartHour >= 24 || dEndHour <= 0 || dEndHour > 24) {
        alert("유효한 저녁식사 시간을 입력해주세요.");
        return;
    }

    const dateFormat = date => `${date.getFullYear()}-${("0" + (date.getMonth() + 1)).slice(-2)}-${("0" + date.getDate()).slice(-2)}`; // 날짜 형식 변환 함수
    finalList.forEach(function (day) {
        day = new Date(day);
        dateList.push(day);
    })

    dateList.forEach(day => {
        const weekDay = day.getDay();
        let data = [];

        if (setWeekdayArr.includes(weekDay)) {
            // 현재 요일 인덱스
            const dayIndex = setWeekdayArr.indexOf(weekDay);
            const startHour = parseInt(document.getElementById(`start-hour-${dayIndex}`).value, 10);
            const endHour = parseInt(document.getElementById(`end-hour-${dayIndex}`).value, 10);

            const maxNum = document.getElementById(`max-num-${dayIndex}`).value;

            // 시간 범위 내의 값들을 데이터 배열에 추가
            for (let hour = startHour; hour < endHour; hour++) {
                // 식사시간 제외 조건
                // - 점심시간 없음에 체크 해제돼있으면 조건 실행
                if (!lunchCheck && hour >= lStartHour && hour < lEndHour) {
                    continue;
                }
                // - 저녁시간 없음에 체크 해제돼있으면 조건 실행
                if (!dinnerCheck && hour >= dStartHour && hour < dEndHour) {
                    continue;
                }

                data.push({
                    date: dateFormat(day), // 날짜 문자열
                    ykiho: hospitalCode,
                    doctorNo: selectedDoctor,
                    weekday: weekdays[weekDay], // 요일 문자열
                    time: hour, // 시간 문자열
                    max: maxNum, // 총 인원
                });
            }

            // 전체적으로 크게 날짜별로 그룹 묶어주기
            data.forEach(item => {
                if (!groupedData[item.date]) {
                    groupedData[item.date] = []; // 해당 날짜의 키가 없으면 빈 배열 추가
                }
                groupedData[item.date].push(item); // 날짜별 배열에 데이터 추가
            })
        }
    });

    $("#loading").show();  // 로딩창 시작

    $.ajax({
        type: "POST",
        url: "/timesetCreate",
        data: JSON.stringify(groupedData),
        contentType: "application/json; charset=utf-8",
        success: function (response) {
            $("#loading").hide();
            location.href = "/admin/timeSlotsCreateComp";
        },
        error: function (error) {
            $("#loading").hide();
            alert("스케줄 생성을 실패하였습니다.");
        }
    });
}