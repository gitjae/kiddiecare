const ykiho = $('#ykiho').val();



// 날짜 선택 시 로그인한 병원 timeslot 불러오기
$('#lookup-date').on('change', function (){
    // 날짜 선택 시 하단 내용 표시
    $('.detail-change-area').show();

    let selectedDate = new Date($(this).val());
    let formatDate = selectedDate.toISOString().slice(0, 10);
    let tableBody = $('#appo-mo-table');
    console.log(formatDate);

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
                    <!--<td> detail.no </td>-->
                    <!-- <td>${detail.doctorNo}</td> -->
                    <td>${ $('.option.selected').text()}</td>
                    <td>${detail.weekday}</td>
                    <td>${detail.date}</td>
                    <td>${detail.time}</td>
                    <td><input type="number" class="max_in" value="${detail.max}" min="0">명</td>
                    <td><input type="number" class="block_in" value="${detail.block}" min="0">명</td>
                    <td><input type="number" class="count_in" value="${detail.count}" min="0" readonly>명</td>
<!--                    <td><input type="number" value="detail.enable" readonly>명</td> -->
                    <td><button name="${detail.no}" onclick="modify(this)">수정</button></td>
                </tr>
            `);
            console.log(detail);
        })
    }).fail(function (error) {
       console.log(error);
    });

});

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

    if (countInput <= maxInput && blockInput <= maxInput) { // 요청 조건에 맞게 수정
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
                swal("정보 수정 성공!", "" ,"success" );
                alert("수정 성공!");
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

// RESET 버튼 클릭 시
function reset() {}