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
                            <a href="#" class="doctor-link">
                                <img src="https://d338jhig5816rv.cloudfront.net/admin1">
                                <div class="text-area">
                                    <span class="name">의사이름: ${item.doctorName}</span>
                                    <span class="average-time-of-care">평균 진료 시간: ${item.doctorAverageTimeOfCare}</span>
                                    <span class="doctor-status">현재 의사 상태: ${item.doctorStatus}</span>
                                </div>
                            </a>
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

    function openDoctorForm(event, link) {
        event.preventDefault();
        const doctorForm = document.getElementById("doctor-form"),
            formCloseBtn = document.querySelector("#form_close"),
            formCloseBtn2 = document.querySelector("#form_close2"),
            doctorName = link.querySelector(".name").textContent,
            doctorStatus = link.querySelector(".doctor-status").textContent;

        // 의사 이름과 상태를 폼에 삽입
        doctorForm.querySelector(".doctor-name").textContent = doctorName;
        doctorForm.querySelector("#doctor_status").value = doctorStatus;

        // 모달 폼 열기
        doctorForm.classList.add("open");
        formCloseBtn.addEventListener("click", closeDoctorForm);
        formCloseBtn2.addEventListener("click", closeDoctorForm);
    }

    const formOpenBtn = document.querySelector(".dotor-add-area"),
        formContainer = document.querySelector(".form_container"),
        formCloseBtn = document.querySelector(".form_close");

    formOpenBtn.addEventListener("click", () => formContainer.classList.add("show"));
    formCloseBtn.addEventListener("click", () => formContainer.classList.remove("show"));


    /* 의사 삭제하기 */
    function deleteDoctor(){
        $.ajax({
            type: "POST",
            url: "/api/v1/doctor/delete",
            data: {adminId: id},
            success: function (response) {
                if (response.response === "success") {
                    alert("삭제 완료되었습니다.");
                    location.reload();
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

    /* 의사 데이터 수정하기 */
    function updateDoctor(){
        $.ajax({
            type: "POST",
            url: "/api/v1/doctor/update",
            data: {adminId: id},
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
