// window.onload = function () {
//     getHospInfoDetail();
//     getTotalInfo();
// }
let ykihoD = null;
// ykiho = document.getElementById('hospital-name').getAttribute('ykiho');
// console.log("ykiho : ", ykiho);

function getSgguCdFromUrl() {
    const currentUrl = new URL(window.location.href);
    const sgguCd = currentUrl.searchParams.get('sgguCd');
    return sgguCd;
}

// 예약하기 버튼 액션
document.getElementById("booking-btn").onclick = function () {
    if (selectedSlotInfo.timeSlotNo == null) {
        alert("예약 시간을 선택해주세요.");
    } else {
        let hospitalName = encodeURIComponent(getHospitalNameFromUrl().trim());
        location.href = `/appointment/booking?hospitalName=${hospitalName}&treatmentDate=${formattedDate}&treatmentDay=${selectDay}&doctorNo=${selectedSlotInfo.doctorNo}&slotTime=${selectedSlotInfo.time}&slotWeekday=${selectedSlotInfo.weekday}&timeSlotNo=${selectedSlotInfo.timeSlotNo}`;
    }
}

// 정보 뿌리기
function getHospInfoDetail() {
    const hospitalName = getHospitalNameFromUrl();
    // console.log("hospitalName : ", hospitalName);

    $.ajax({
        method: 'GET',
        url: `/api/appointment/hospitalDetail?hospitalName=${hospitalName}`,
    })
        .done(res => {
            console.log(res);
            if (res.dbHospitalData) {
                document.getElementById('hospital-name').textContent = res.dbHospitalData.hospitalName;
                document.getElementById('hospital-name').setAttribute('ykiho', res.dbHospitalData.ykiho);
                ykihoD = res.dbHospitalData.ykiho;
                document.getElementById('hospital-intro').textContent = res.dbHospitalData.hospitalIntro;

                // 의사 정보 처리
                let doctorContainer = document.querySelector(".doctor-info");
                doctorContainer.innerHTML = ''; // Clear existing doctor info

                res.doctors.forEach(doctor => {
                    let doctorCard = document.createElement('div');
                    doctorCard.classList.add('doctor-card');
                    doctorCard.innerHTML = `
                    <img src="/image/doctor_icon.png">
                    <div class="doctor-text">
                    <p id="doctor-no">의사 번호 : ${doctor.no}</p>
                    <p id="doctor-name">${doctor.doctorName}</p>
                    <p id="doctor-offDay">휴진일</p>
                    </div>`;

                    doctorContainer.appendChild(doctorCard);
                });
            }
        })
        .fail(err => {
            console.error("Error:", err);
        });
}

// 찜 (좋아요)
function likeHospital() {
    $.ajax({
        method: 'POST',
        url: `/api/like/${ykihoD}`
    })
        .done(function () {
            console.log("Liked the hospital successfully!");
            updateLikeButtons(true);
        })
        .fail(function (err) {
            console.error("Error while liking the hospital:", err.responseText);
        });
}

function unlikeHospital() {
    $.ajax({
        method: 'DELETE',
        url: `/api/like/userNo/${ykihoD}`
    })
        .done(function () {
            console.log("Unliked the hospital successfully!");
            updateLikeButtons(false);
        })
        .fail(function (err) {
            console.error("Error while unliking the hospital:", err.responseText);
        });
}

function updateLikeButtons(isLiked) {
    const noLikes = document.querySelectorAll('.noLike');
    const yesLikes = document.querySelectorAll('.yesLike');

    noLikes.forEach(noLike => {
        noLike.style.display = isLiked ? "none" : "block";
    });

    yesLikes.forEach(yesLike => {
        yesLike.style.display = isLiked ? "block" : "none";
    });
}

function checkLikeStatus(userNo, ykiho) {
    $.ajax({
        method: 'GET',
        url: `/api/like/existence?userNo=${userNo}&ykiho=${ykiho}`
    })
        .done(function (isLiked) {
            updateLikeButtons(isLiked);
        })
        .fail(function (err) {
            console.error("Error while checking like status:", err.responseText);
        });
}

function getTotalInfo() {
    const hospitalName = getHospitalNameFromUrl();
    const sgguCd = getSgguCdFromUrl();

    $.ajax({
        method: 'GET',
        url: '/search/detail',
        data: {
            yadmNm: hospitalName,
            sgguCd: sgguCd
        }
    }).done(res => {
        console.log(res);
        const BD = res.data.hospBasisData;
        const DD = res.data.hospDetailData;
        const LD = res.data.hospListData;
        const SD = res.data.hospSubData;
        if (res.result == 'success') {
            // 카카오 맵
            xPos = BD.xpos;
            yPos = BD.ypos;
            makeMap();
            makeMarker();

            //
            $('#hospital-name').text(BD.yadmNm);
            $('#hospital-addr').text(BD.addr);
            $('#hospital-tell').text(BD.telno);
            $('#workhour-mon').text((timeFormat(DD.trmtMonStart) + " - " + timeFormat(DD.trmtMonEnd)));
            $('#workhour-tue').text(timeFormat(DD.trmtTueStart) + " - " + timeFormat(DD.trmtTueEnd));
            $('#workhour-wed').text(timeFormat(DD.trmtWedStart) + " - " + timeFormat(DD.trmtWedEnd));
            $('#workhour-thu').text(timeFormat(DD.trmtThuStart) + " - " + timeFormat(DD.trmtThuEnd));
            $('#workhour-fri').text(timeFormat(DD.trmtFriStart) + " - " + timeFormat(DD.trmtFriEnd));
            $('#workhour-sat').text(timeFormat(DD.trmtSatStart) + " - " + timeFormat(DD.trmtSatEnd));
            $('#workhour-sun').text(timeFormat(DD.trmtSunStart) + " - " + timeFormat(DD.trmtSunEnd));
            $('#hospital-park').text("주차정보" + DD.parkEtc);
        } else {
            alert('병원정보를 불러오지 못했습니다. \n잠시 후 다시 시도해주세요.')
        }
    })
}

function timeFormat(time) {
    return time.slice(0, 2) + ":" + time.slice(2, 4);
}
