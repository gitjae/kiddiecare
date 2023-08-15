$(function () {
    get_hospital_name();
    get_doctor_list();
})

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

// 의사 선택
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
            // option.id = doctor.no;
            // option.innerText = doctor.doctorName;
            option.innerText = doctor.no;
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
            selectedDoctor.textContent = e.target.textContent;
            console.log(selectedDoctor.textContent);
        }
    });
}