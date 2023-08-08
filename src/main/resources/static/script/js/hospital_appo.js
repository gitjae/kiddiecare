function appo_create(){
    let name = $('#hospital_name').val();
    let memo = $('#hospital_announcement').val();
    let count = $('#count').val();
    let max = $('#max').val();

    data = {
        "hospital_name" : name,
        "hospital_announcement" : memo,
        "count" : count,
        "max" : max,
    };

    $.ajax({
        type: "POST",
        url: "/api/v1/admin/appo/write",
        timeout: 0,
        data: JSON.stringify(data),
        contentType: "application/json",
        dataType: "json",
    }).done(function (result) {
        if(result.write === "success") {
            alert("병원 정보 업로드 성공!");
            location.href="/";
        } else {
            alert("업로드 실패..");
        }
    })

}