window.onload = function (){
    buildCalendar();
    $('.appo-table').css({"display":"flex"});
}

function appoUpdate(){
    const no = $('.appo-no').text();
    const symptom = $('#appo-symptom').val();
    const note = $('#appo-note').val();
    const timeSlotNo = selectedSlotInfo.timeSlotNo == null ? 0 : selectedSlotInfo.timeSlotNo;

    var data = {
        no:no,
        symptom:symptom,
        note:note,
        timeSlotNo:timeSlotNo
    }

    $.ajax({
        method:'PUT',
        url:'/api/v1/appo/update',
        data:JSON.stringify(data),
        contentType:'application/json; charset=utf-8'
    }).done(res => {
        if(res.update == 'success'){
            alert('예약 수정이 완료되었습니다.');
            location.href = '/mypage';
        } else {
            alert('예약 수정에 실패하였습니다.');
        }
    })
}