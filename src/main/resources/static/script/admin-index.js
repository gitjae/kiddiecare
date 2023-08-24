$(function () {
    get_hospital_name();
    get_doctor_list();
})

function get_hospital_name() {
    let ykiho = $('#ykiho').val();
    $.ajax({
        url: `/hospitalName/${ykiho}`,
        method: 'GET',
        timeout: 0
    }).done(function (response) {
        console.log(response);
        $('#hospital_name').text(response.hospitalName);
        $('#hos-name').text(response.hospitalName)
    }).fail(function (error) {
        console.log(error);
    });
}


function get_doctor_list() {
    let ykiho = document.getElementById('ykiho').value;
    const element = document.getElementsByClassName('select-option')[0];

    $.ajax({
        url: `/api/v1/admin/appo/${ykiho}`,
        method: 'GET',
        timeout: 0
    }).done(function (doctorList) {
        doctorList.forEach(doctor => {
            const option = document.createElement("div");
            option.className = "option";
            option.id = doctor.no;
            // option.innerText = doctor.no;
            option.innerText = doctor.doctorName + " 의사";
            element.appendChild(option);
        });
    }).fail(function (error) {
        console.log(error);
    });

    element.addEventListener('click', function (e) {
        if (e.target.classList.contains('option')) {
            const prevSelectedItem = document.querySelector('.selected');
            if (prevSelectedItem) {
                prevSelectedItem.classList.remove('selected');
            }
            e.target.classList.add('selected');

            // 선택된 의사 이름 표시
            const selectedDoctor = document.getElementById('selectedDoctor');

            // selectedDoctor에 선택한 id 명 표시 (display:none처리되어 있음)
            selectedDoctor.textContent = e.target.id.split(" ")[0];
        }
    });
}

function contentChange(nav){
    const name = $(nav).attr("name");
    $('.main-div').css("display", "none");
    $(`#${name}`).css("display", "block");

    // 의사 영역 표시
    if ($(`#${name}`).is('div#div-schedule.main-div') || $(`#${name}`).is('div#div-appo.main-div') || $(`#${name}`).is('div#div-appo-modify.main-div')) {
        $('#docChoose').css("display", "block");
    } else {
        $('#docChoose').css("display", "none");
    }
}

