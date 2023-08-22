function getLikedHospitals() {
    $.ajax({
        method: 'GET',
        url: '/api/likedHospitals',
    })
        .done(res => {
            let divFavor = document.getElementById('div-favor');

            // 찜한 병원 리스트가 비어있는지 확인
            if (res.length === 0) {
                let p = document.createElement('p');
                p.textContent = '찜한 병원이 아직 없어요.';
                divFavor.appendChild(p);
            } else {
                let hospitalNames = res.map(hospital => hospital.hospitalName);
                hospitalNames.forEach(name => {
                    let card = document.createElement('div');
                    card.className = 'hospital-card';

                    let p = document.createElement('p');
                    p.textContent = name;

                    // 카드 클릭 이벤트 리스너 추가
                    card.addEventListener('click', function() {
                        window.location.href = `/appointment/hospitalDetail?hospitalName=${name}`; // 페이지 이동
                    });

                    card.appendChild(p);
                    divFavor.appendChild(card);
                });
            }
        })
        .fail(err => {
            console.error("Error:", err);
        });
}


window.onload = getLikedHospitals;