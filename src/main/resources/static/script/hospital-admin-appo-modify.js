const ykiho = $('#ykiho').val();


// 날짜 선택 시 로그인한 병원 timeslot 불러오기
$('#confirm-date').on('change', function (){
    let selectedDate = new Date($(this).val());
    let formatDate = selectedDate.toISOString().slice(0, 10);
    let tableBody = $('#table-body');

    tableBody.html('');

    const doctorNo = $('#selectedDoctor').text();

    console.log(selectedDate);

    $.ajax({
        url: '/getTimeSlots',
        method: 'GET',
        data: {
            ykiho: ykiho,
            date: formatDate,
            doctorNo: doctorNo
        },
    }).done(function (list) {
        tableBody.html('');
        list.forEach((detail) => {
            tableBody.append(`
                <tr>
                    <td>${detail.no}</td>
                    <td>${detail.doctorNo}</td>
                    <td>${detail.weekday}</td>
                    <td>${detail.date}</td>
                    <td>${detail.time}</td>
                    <td><input type="number" value="${detail.max}" min="0">명</td>
                    <td><input type="number" value="${detail.count}" min="0">명</td>
                    <td><input type="number" value="${detail.block}" min="0">명</td>
                    <td><input type="number" value="${detail.enable}" readonly>명</td>
                </tr>
            `);
            console.log(detail);
        })
    }).fail(function (error) {
       console.log(error);
    });

});