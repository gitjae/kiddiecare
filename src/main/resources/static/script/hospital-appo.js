// 페이지 시작 시 바로 실행
$(function() {
    updateTable();
    apply_event();
    set_date();
    get_hospital_name();
    get_doctor();
});

const dateInput = document.getElementById("date");
const timeSetBody = document.getElementById("time_set_body");
const doctorList = document.getElementById('doctor_list');

function get_hospital_name() {
    let ykiho = $('#ykiho').val();

    $.ajax({
        url: `/hospitalName/${ykiho}`,
        method: 'GET',
        timeout: 0
    }).done(function (response) {
        console.log(response);
        $('#hospital_name').val(response.hospitalName);
    }).fail(function (error) {
        console.log(error);
    });
}
function get_doctor() {
    let ykiho = $('#ykiho').val();


    $.ajax({
        url:`/api/v1/admin/appo/${ykiho}`,
        method: "GET",
        timeout: 0
    }).done(function (list) {
        list.forEach(doctor => {
            console.log(doctor.doctorName);
            const option = document.createElement('option');
            option.value = doctor.doctorName;
            option.innerText = doctor.doctorName;

            doctorList.appendChild(option);
        })

    })
}

function set_date(){
    let sDate = new Date();
    let eDate = new Date();

    // 시작날짜 = 날짜
    sDate.setDate(sDate.getDate());
    // 종료날짜 = 날짜 + 30
    eDate.setDate(eDate.getDate() + 30);

    let minStr = sDate.toISOString().split('T')[0];
    let maxStr = eDate.toISOString().split('T')[0];

    $("#date").prop("min", minStr);
    $("#date").prop("max", maxStr);

    console.log('시작날짜 : ',minStr);
    console.log('종료날짜 : ',maxStr);

    document.getElementById('date').value = new Date().toISOString().substring(0, 10);

}

const data = [
    // {time: "9:00", max: 0, count: 0, block: 0, enable: 0},
    {time: "9:00"},
    {time: "10:00"},
    {time: "11:00"},
    {time: "12:00"},
    {time: "13:00"},
    {time: "14:00"},
    {time: "15:00"},
    {time: "16:00"},
    {time: "17:00"},
    {time: "18:00"},
];

function updateTable() {
    timeSetBody.innerHTML = "";
    for (let item of data) {
        const tr = document.createElement("tr");
        let time = item.time.split(":")[0];

        tr.innerHTML = `
                    <td>${item.time}</td>
                    <td><input type="text" id="max_${time}" value=""> </td>
                    <td><input type="text" id="count_${time}"></td>
                    <td><input type="text" id="block_${time}"></td>
                    <td><input type="text" id="enable_${time}" readonly></td>
                `;
        timeSetBody.appendChild(tr);
    }
}

function calculateEnable(e) {

    let parentNode = e.target.parentNode;

    // 현재 입력된 값을 가져와서 숫자로 변환
    let max = parseInt(parentNode.parentNode.children[1].firstElementChild.value);
    let count = parseInt(parentNode.parentNode.children[2].firstElementChild.value);
    let block = parseInt(parentNode.parentNode.children[3].firstElementChild.value);

    // enable 계산
    let enable = max - (count + block);

    // 결과값을 enable 입력 상자에 입력
    parentNode.parentNode.children[4].firstElementChild.value = enable;
}
dateInput.addEventListener("change", () => {
    updateTable();
    apply_event();

});

function apply_event(){
    // 각 입력 상자들의 DOM 요소 가져오기
    let inputMax = document.querySelectorAll("input[id^='max_']");
    let inputCount = document.querySelectorAll("input[id^='count_']");
    let inputBlock = document.querySelectorAll("input[id^='block_']");
    // let inputEnable = document.querySelectorAll("input[id^='enable_']");

    // 각 입력 상자에 이벤트 리스너 등록
    for(let i = 0; i < inputMax.length; i++){
        inputMax[i].addEventListener("input", calculateEnable, false);
        inputCount[i].addEventListener("input", calculateEnable, false);
        inputBlock[i].addEventListener("input", calculateEnable, false);
    }
}

// updateTable();

function appo_create(){
    convertInputsToJson();
}



function getInputValue(id) {
    return document.getElementById(id).value;
}
function convertInputsToJson() {
    const hours = [9, 10, 11, 12, 13, 14, 15, 16, 17, 18];
    const result = [];
    let date = $('#date').val();
    let docNum = $('#doctor_no').val();
    let ykiho = $('#ykiho').val();

    let date_for_weekday = new Date(date);
    let weekday = ["일","월","화","수","목","금","토"];
    weekday = weekday[date_for_weekday.getDay()];
    console.log(weekday);

    for (const hour of hours) {
        const max = getInputValue(`max_${hour}`);
        const count = getInputValue(`count_${hour}`);
        const block = getInputValue(`block_${hour}`);
        const enable = getInputValue(`enable_${hour}`);

        const data = {
            time: `${hour}`,
            doctorNo: docNum,
            max: max,
            count: count,
            block: block,
            enable: enable,
            date: date,
            weekday: weekday,
            ykiho: ykiho
        };
        result.push(data);
    }
    console.log(result);
    $.ajax({
        type: "POST",
        url: "/api/v1/admin/appo/timeset-add",
        data: JSON.stringify(result),
        contentType: "application/json",

    }).done(function (result) {
        // alert(result.result);
        if(result.result === "success") {
            alert("병원 정보 업로드 성공!");
            // location.href="/";
        } else {
            alert("업로드 실패..");
        }
    })
    console.log(result);
    return result;
}

