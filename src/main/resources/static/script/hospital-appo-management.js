$(function () {

})

// 날짜 선택 시 로그인한 병원의 timeslot불러옴
document.getElementById('confirm-date').addEventListener('change', function (event){
    const selectedDate = new Date(event.target.value);
    const formattedDate = selectedDate.toISOString().slice(0, 10);
    const ykiho = document.getElementById('ykiho').value;
    const selectedDoctor = document.getElementById('selectedDoctor').textContent;

    $.ajax({
        url: '/getTimeSlots',
        method: 'GET',
        data: {
            ykiho: ykiho,
            date: formattedDate,
            doctorNo: selectedDoctor
        },
    }).done(function(list) {
        list.forEach(detail => {
            // time-list 안에 li로 value=시간, text=표시될시간 (ex)9:00~10:00 시간 담기
        })
    }).fail(function (error) {
        console.log(error);
    })

})



