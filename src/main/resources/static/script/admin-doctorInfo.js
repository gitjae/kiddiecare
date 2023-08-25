$(document).ready(function() {
    getDoctorInfo();
    getSubject();

    $(document).on('click', '.doctor-area', function() {
        var no = $(this).find('.doctor-num').text();
        var doctorName = $(this).find('.name').text(); // 수정한 부분
        var doctorTime = $(this).find('.average-time-of-care').text(); // 수정한 부분
        var doctorStatus = $(this).find('.doctor-status').text(); // 수정한 부분
        var doctorImg = $(this).find('.doctor-img').attr('src');

        // .doctor-info-container의 input 요소에 가져온 값을 대입합니다.
        $('.doctor-info-container input[name="no"]').val(no);
        $('.doctor-info-container input[name="doctorName"]').val(doctorName);
        $('.doctor-info-container input[name="doctorAverageTimeOfCare"]').val(doctorTime);
        $('.doctor-info-container input[name="doctorStatus"]').val(doctorStatus);
        $(".doctor-info-container img").attr("src", doctorImg);
        $('.doctor-info-container').addClass('show');
    });

    $('.form_close2').click(function() {
        $('.doctor-info-container').removeClass('show');
    });


});

/* Doctor 데이터 가져오기 */
function getDoctorInfo(){
    $.ajax({
        type: "GET",
        url: "/api/v1/doctor/select",
        success: function (response) {
            if (response.response === "success") {
                $("#doctor-container").empty();
                $("#doctor-container").append(`
                    <div class="dotor-add-area">
        <i class="uil uil-plus"></i>
    </div>
                `);

                const formOpenBtn = document.querySelector(".dotor-add-area");
                const formContainer = document.querySelector(".form_container");
                const formCloseBtn = document.querySelector(".form_close");

                formOpenBtn.addEventListener("click", () => formContainer.classList.add("show"));
                formCloseBtn.addEventListener("click", () => formContainer.classList.remove("show"));

                response.data.forEach(item => {
                    let imageUrl = item.doctorImageUrl === undefined ? "/image/doctor_icon.png" : item.doctorImageUrl;
                    let nowStatus = item.doctorStatus === "0" ? "자리 비움" : "진료 가능"
                    let html = `

                        <div class="doctor-area">
                            <img class="doctor-img" src="${imageUrl}">
                            <div class="text-area">
                                <span>의사 번호: <span class="doctor-num">${item.no}</span></span>
                                <span>의사이름: <span class="name">${item.doctorName}</span></span>
                                <span>평균 진료 시간: <span class="average-time-of-care">${item.doctorAverageTimeOfCare}</span><span>분</span></span>
                                <span>현재 의사 상태: <span class="doctor-status">${nowStatus}</span></span>
                            </div>
                        </div>`;
                    $('#doctor-container').append(html);
                });
            } else {
                alert("의사 정보를 불러오지 못했습니다.");
            }
        },
        error: function (xhr, status, error) {
            alert("서버에 문제가 발생했습니다. 잠시 후 다시 시도해주세요.");
        }
    });
}



function getSubject(){
    $.ajax({
        url: '/api/v1/doctor/subject',
        type: 'GET',
        success: function(data) {
            if(data.response === "success"){
                data.data.forEach(item => {
                    let html = `<option value="${item['no']}">${item["subject"]}</option>`;
                    $('#option-area').append(html);
                });
            }else{
                alert("에러가 발생하였습니다.")
            }
        },
        error: function() {
            alert("서버에 문제가 발생했습니다. 잠시 후 다시 시도해주세요.");
        }
    });
}

/* 의사 만들기 */
function addDoctor(){
    $.ajax({
        url: '/api/v1/doctor/create',
        type: 'POST',
        enctype: 'multipart/form-data',
        data: new FormData($('form')[0]),
        processData: false,
        contentType: false,
        success: function(data) {
            if(data.response === "success"){
                getDoctorInfo();
                $('.doctor-info-container').removeClass('show');
            }else{
                console.log(data.response);
            }
        },
        error: function(xhr, error) {
            console.log(xhr);
            console.log(error);
        }
    });
}

function updateDoctor(htmlForm){
    let file = $('#doctor-image-update').val()
    var data= {
        no:$('#doctor-no').val(),
        doctorName: $('#doctor-name').val(),
        doctorAverageTimeOfCare: $('#doctor-average-time-of-care').val(),
        doctorStatus:$('#doctor-status').val()
    }
    $.ajax({
        type: "PUT",
        url: "/api/v1/doctor/update",
        data: data,
        success: function (response) {
            if (response.response === "success") {
                alert("수정 완료되었습니다.");
                getDoctorInfo()
            } else if(response.response === "fail cause already in DB") {
                alert("이미 있는 데이터 입니다.");
            }else{
                alert("데이터 저장에 실패하였습니다.")
            }
        },
        error: function (xhr, status, error) {
            alert("서버에 문제가 발생했습니다. 잠시 후 다시 시도해주세요.");
        }
    });
}

/* 의사 삭제하기 */
function deleteDoctor(){
    var data= {
        no:$('#doctor-no').val()
    }
    $.ajax({
        type: "DELETE",
        url: "/api/v1/doctor/delete",
        data: data,
        success: function (response) {
            if (response.response === "success") {
                getDoctorInfo()
                alert("삭제 완료되었습니다.");
                $('.doctor-info-container').removeClass('show');
            } else if(response.response === "fail cause already in DB") {
                alert("이미 있는 데이터 입니다.");

            }else{
                alert("데이터 저장에 실패하였습니다.")
                location.reload();
            }
        },
        error: function (xhr, status, error) {
            alert("서버에 문제가 발생했습니다. 잠시 후 다시 시도해주세요.");
        }
    });
}