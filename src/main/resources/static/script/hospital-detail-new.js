let ykihoD = null;
let urlSgguCd = getSgguCdFromUrl();
let urlHospitalName = getHospitalNameUrl();

function getSgguCdFromUrl() {
    const currentUrl = new URL(window.location.href);
    const sgguCd = currentUrl.searchParams.get('sgguCd');
    return sgguCd;
}

function getHospitalNameUrl() {
    const currentUrl = new URL(window.location.href);
    const hosp = currentUrl.searchParams.get('hospitalName');
    return hosp;
}

// 예약하기 버튼 액션
document.getElementById("booking-btn").onclick = function () {
    // 로그인 상태 확인
    $.get("/api/checkLogin", function(isLoggedIn) {
        if(!isLoggedIn) {
            alert("병원 예약은 로그인 후 이용 가능합니다.");
            return;
        }
        let hospitalName = encodeURIComponent(getHospitalNameFromUrl().trim());
        location.href = `/appointment/booking?hospitalName=${hospitalName}&treatmentDate=${formattedDate}&treatmentDay=${selectDay}&doctorNo=${selectedSlotInfo.doctorNo}&slotTime=${selectedSlotInfo.time}&slotWeekday=${selectedSlotInfo.weekday}&timeSlotNo=${selectedSlotInfo.timeSlotNo}`;
    });
}

// 정보 뿌리기
function getHospInfoDetail() {
    const hospitalName = getHospitalNameFromUrl();
    // console.log("hospitalName : ", hospitalName);

    $.ajax({
        method: 'GET',
        url: `/api/appointment/hospitalDetail?hospitalName=${hospitalName}`,
        timeout: 10000
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
<!--                    <p id="doctor-offDay">휴진일</p>-->
                    </div>`;

                    doctorContainer.appendChild(doctorCard);
                });
                $('.appo-table').css({display:"flex"});
                $('.appo-table').show();
                $('.info-title').show();
                $('.doctor-info').show();
                $('.info-sub-title').show();
                $('#booking-btn').show();
            } else {
                $('#booking-btn').remove();
                alert('우리동네소아과에 등록되지 않은 병원 입니다.');
            }
            isLiked();
        })
        .fail(err => {
            console.error("Error:", err);
            alert('병원 등록 정보를 불러오는데 실패했습니다.');
            $('#loading').hide();
        });
}

// 찜 (좋아요)
function handleLikeStatus() {
    $.ajax({
        method: 'GET',
        url: `/api/like/existence/${ykihoD}`
    })
        .done(function (isLiked) {
            updateButtonBasedOnLikeStatus(isLiked);
        })
        .fail(function (err) {
            console.error("Error while checking like status:", err.responseText);
        });
}

function updateButtonBasedOnLikeStatus(isLiked) {
    const noLikes = document.querySelectorAll('.noLike');
    const yesLikes = document.querySelectorAll('.yesLike');

    noLikes.forEach(noLike => {
        noLike.style.display = isLiked ? "none" : "block";
    });

    yesLikes.forEach(yesLike => {
        yesLike.style.display = isLiked ? "block" : "none";
    });
}

function isLiked(){
    const thisYkiho = ykihoD;
    console.log("y:",thisYkiho);
    $.ajax({
        method:'GET',
        url:`/api/like`,
        data:{
            hospitalName:urlHospitalName,
            sgguCd:urlSgguCd
        }
    }).done(res => {
        console.log("isLiked:" + res);
        isLikedHosp = res;
        displayLikeSymbol();
    })
}

function displayLikeSymbol(){
    $('.likeHospital-area').empty();
    var img = 'like_empty.png';
    var className = 'no-like';
    if(isLikedHosp){
        img = 'like_full.png';
        className = 'like';
    }
    $('.likeHospital-area').append(`<img className="${className}" src="/image/${img}" onClick="toggleLike(this)">`);
}

function toggleLike(btn) {
    //const yesLikes = document.querySelector('.yesLike');
    const like = $(btn).attr('className') == 'like' ? true : false;
    if (like) {
        unlikeHospital();
    } else {
        likeHospital();
    }
}

function likeHospital() {
    $.ajax({
        method: 'POST',
        url: `/api/like`,
        data:{
            hospitalName:urlHospitalName,
            sgguCd:urlSgguCd
        }
    }).done(function () {
        console.log("Liked the hospital successfully!");
        //handleLikeStatus(ykihoD);
        isLiked();
    })
    .fail(function (err) {
        console.error("Error while liking the hospital:", err.responseText);
    });
}

function unlikeHospital() {
    $.ajax({
        method: 'DELETE',
        url: `/api/like`,
        data:{
            hospitalName:urlHospitalName,
            sgguCd:urlSgguCd
        }
    })
        .done(function () {
            console.log("Unliked the hospital successfully!");
            //handleLikeStatus(ykihoD);
            isLiked();
        })
        .fail(function (err) {
            console.error("Error while unliking the hospital:", err.responseText);
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
        },
        timeout:10000
    }).done(res => {
        console.log(res);
        const BD = res.data.hospBasisData;
        const DD = res.data.hospDetailData;
        //const LD = res.data.hospListData;
        //const SD = res.data.hospSubData;
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
            if(DD != null){
                $('#workhour-mon').text((timeFormat(DD.trmtMonStart) + " - " + timeFormat(DD.trmtMonEnd)));
                $('#workhour-tue').text(timeFormat(DD.trmtTueStart) + " - " + timeFormat(DD.trmtTueEnd));
                $('#workhour-wed').text(timeFormat(DD.trmtWedStart) + " - " + timeFormat(DD.trmtWedEnd));
                $('#workhour-thu').text(timeFormat(DD.trmtThuStart) + " - " + timeFormat(DD.trmtThuEnd));
                $('#workhour-fri').text(timeFormat(DD.trmtFriStart) + " - " + timeFormat(DD.trmtFriEnd));
                $('#workhour-sat').text(timeFormat(DD.trmtSatStart) + " - " + timeFormat(DD.trmtSatEnd));
                $('#workhour-sun').text(timeFormat(DD.trmtSunStart) + " - " + timeFormat(DD.trmtSunEnd));
                $('#hospital-park').text(DD.parkEtc + "주차 가능");
            }
        } else {
            alert('병원정보를 불러오지 못했습니다. \n잠시 후 다시 시도해주세요.');
            $('#loading').hide();
        }
    }).fail(function (){
        alert('병원정보를 불러오지 못했습니다. \n잠시 후 다시 시도해주세요.');
        $('#loading').hide();
    })
}

function timeFormat(time) {
    return time.slice(0, 2) + ":" + time.slice(2, 4);
}
