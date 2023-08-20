$(document).ready(function() {
    $.ajax({
        type: "GET",
        url: "/api/v1/doctor/select",
        success: function (response) {
            console.log(response);
            if (response.response === "success") {
                response.data.forEach(item => {
                    let html = `
                        <div class="doctor-area">
                            <img src="https://d338jhig5816rv.cloudfront.net/admin1">
                            <div class="text-area">
                                <span>의사 번호:<span class="doctor-num">${item.no}</span></span>
                                <span>의사이름:<span class="name">${item.doctorName}</span></span>
                                <span>평균 진료 시간:<span class="average-time-of-care">${item.doctorAverageTimeOfCare}</span></span>
                                <span>현재 의사 상태:<span class="doctor-status">${item.doctorStatus}</span></span>
                            </div>
                        </div>`;
                    $('.doctor-container').append(html);
                });
                $('.doctor-link').on('click', function(event) {
                    const link = $(this)[0];
                    openDoctorForm(event, link);
                });
            } else {
                alert("의사 정보를 불러오지 못했습니다.");
            }
        },
        error: function (xhr, status, error) {
            console.log(error);
            alert("서버에 문제가 발생했습니다. 잠시 후 다시 시도해주세요.");
        }
    });

    $(document).on('click', '.doctor-area', function() {
        var no = $(this).find('.doctor-num').text();
        var doctorName = $(this).find('.name').text(); // 수정한 부분
        var doctorTime = $(this).find('.average-time-of-care').text(); // 수정한 부분
        var doctorStatus = $(this).find('.doctor-status').text(); // 수정한 부분

        console.log(doctorName,doctorTime,doctorStatus)

        // .doctor-info-container의 input 요소에 가져온 값을 대입합니다.
        $('.doctor-info-container input[name="no"]').val(no);
        $('.doctor-info-container input[name="doctorName"]').val(doctorName);
        $('.doctor-info-container input[name="doctorAverageTimeOfCare"]').val(doctorTime);
        $('.doctor-info-container input[name="doctorStatus"]').val(doctorStatus);
        $('.doctor-info-container').addClass('show');
    });

    $('.form_close2').click(function() {
        $('.doctor-info-container').removeClass('show');
    });

    const formOpenBtn = document.querySelector(".dotor-add-area");
    const formContainer = document.querySelector(".form_container");
    const formCloseBtn = document.querySelector(".form_close");

    formOpenBtn.addEventListener("click", () => formContainer.classList.add("show"));
    formCloseBtn.addEventListener("click", () => formContainer.classList.remove("show"));


    $(document).on('click', '.button-delete', deleteDoctor);
    $(document).on('click', '.button-update', updateDoctor);

    /* 의사 삭제하기 */
    function deleteDoctor(){
        var data= {
            no:$('#doctor-no').val(),
            doctorName: $('#doctor-name').val(),
            doctorAverageTimeOfCare: $('#doctor-average-time-of-care').val(),
            file:$('#doctor-image-update').val(),
            doctorStatus:$('#doctor-status').val()
        }
        $.ajax({
            type: "DELETE",
            url: "/api/v1/doctor/delete",
            data: data,
            success: function (response) {
                console.log(response);
                if (response.response === "success") {
                    alert("삭제 완료되었습니다.");

                } else if(response.response === "fail cause already in DB") {
                    alert("이미 있는 데이터 입니다.");

                }else{
                    alert("데이터 저장에 실패하였습니다.")
                    location.reload();
                }
            },
            error: function (xhr, status, error) {
                console.log(error);
                alert("서버에 문제가 발생했습니다. 잠시 후 다시 시도해주세요.");
            }
        });
    }

    /* 의사 데이터 수정하기 */
    function updateDoctor(htmlForm){
        var data= {
            no:$('#doctor-no').val(),
            doctorName: $('#doctor-name').val(),
            doctorAverageTimeOfCare: $('#doctor-average-time-of-care').val(),
            file:$('#doctor-image-update').val(),
            doctorStatus:$('#doctor-status').val()
        }
        $.ajax({
            type: "PUT",
            url: "/api/v1/doctor/update",
            data: data,
            success: function (response) {
                if (response.response === "success") {
                    alert("수정 완료되었습니다.");
                    location.href = "/adminIndex";
                } else if(response.response === "fail cause already in DB") {
                    alert("이미 있는 데이터 입니다.");
                }else{
                    alert("데이터 저장에 실패하였습니다.")
                }
            },
            error: function (xhr, status, error) {
                console.log(error);
                alert("서버에 문제가 발생했습니다. 잠시 후 다시 시도해주세요.");
            }
        });
    }
});
