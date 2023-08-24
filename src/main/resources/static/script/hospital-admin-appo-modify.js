const ykiho = $('#ykiho').val();

// 날짜 선택 시 로그인한 병원 timeslot 불러오기
$('#lookup-date').on('change', function (){
    modifyList();

});

// RESET 버튼 클릭 시 리스트를 다시 불러옴.
function reset() {
    modifyList();
}

// 리스트 불러오기
function modifyList() {
    // 의사 선택 확인
    if (!$('.select-option .option').hasClass('selected')) {
        alert('의사를 선택해주세요.');
        $('#lookup-date').val('');
        return;
    }

    let selectedDate = new Date($('#lookup-date').val());
    let formatDate = selectedDate.toISOString().slice(0, 10);
    let tableBody = $('#appo-mo-table');
    let detail = $('.detail-change-area');

    tableBody.html('');

    const doctorNo = $('#selectedDoctor').text();

    $.ajax({
        url: '/getTimeSlots',
        method: 'GET',
        data: {
            ykiho: ykiho,
            date: formatDate,
            doctorNo: doctorNo
        },
    }).done(function (list) {

        if (Array.isArray(list) && list.length > 0) {
            // 날짜 선택 시 하단 내용 표시
            $('.detail-change-area').show();
            $('#no-detail').hide();

            tableBody.html('');
            list.forEach((detail) => {
                tableBody.append(`
                    <tr>
                        <!-- 이름만 가져오기 -->
                        <td>${$('.option.selected').text().split(" ")[0]}</td>
                        <td>${detail.weekday}</td>
                        <td>${detail.date}</td>
                        <td>${detail.time}</td>
                        <td><input type="number" class="max_in" value="${detail.max}" min="0">명</td>
                        <td><input type="number" class="block_in" value="${detail.block}" min="0">명</td>
                        <td><input type="number" class="count_in" value="${detail.count}" readonly>명</td>
                        <td><input type="number" class="enable_in" value="${detail.enable}" readonly>명</td> 
                        <td><button name="${detail.no}" onclick="modify(this)">수정</button></td>
                    </tr>
                `);
            })
        }else{
            // $('#appo-mo-table').html('');
            $('.detail-change-area').hide();
            $('#no-detail').show();
        }
    }).fail(function (error) {
        console.log(error);
    });
}


function modify(btn) {

    // timeslot no 가져오기
    const timeSlotNo = parseInt(btn.name);

    // tr 안의 td 요소 중 max_in, block_in인 value 가져오기
    const row = $(btn).closest("tr");
    const maxInput = parseInt(row.find("input.max_in").val());
    const blockInput = parseInt(row.find("input.block_in").val());
    const countInput = parseInt(row.find("input.count_in").val());

    console.log("Max:", maxInput);
    console.log("Block:", blockInput);

    if (countInput <= maxInput && blockInput <= maxInput) {
        // 조건에 맞다면 ajax 전송
        $.ajax({
            url: "/modifyTimeSlots",
            method: "PUT",
            data: {
                timeSlotNo: timeSlotNo,
                max: maxInput,
                block: blockInput
            }
        }).done(function (result){
            if(result.modify === "success") {
                // 제목, 내용, 아이콘
                swal({
                    title: "정보 수정 성공!",
                    text: "수정이 완료되었습니다.",
                    icon: "success",
                    button: "확인",
                }).then((value) => {
                    window.location.reload();
                });

            }else {
                alert("수정 실패");
            }
        }).fail(function (error) {
            alert(error);
        });
    } else {
        alert("숫자를 다시 입력해주세요.");
    }

}


