function getLikedHospitals() {
    $.ajax({
        method: 'GET',
        url: '/api/likedHospitals',
    })
        .done(res => {
            let hospitalNames = res.map(hospital => hospital.hospitalName);
            let divFavor = document.getElementById('div-favor');
            hospitalNames.forEach(name => {
                let card = document.createElement('div');
                card.className = 'hospital-card'; // 카드 스타일링을 위한 클래스 추가

                let p = document.createElement('p');
                p.textContent = name;

                // 카드 클릭 이벤트
                card.addEventListener('click', function() {
                    window.location.href = `/appointment/hospitalDetail?hospitalName=${name}`;
                });

                card.appendChild(p);
                divFavor.appendChild(card);
            });
        })
        .fail(err => {
            console.error("Error:", err);
        });
}

window.onload = getLikedHospitals;