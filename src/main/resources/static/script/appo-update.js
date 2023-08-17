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
            alert('수정 성공');
        } else {
            alert('예약 수정에 실패했습니다.');
        }
    })
}