// $(function() {
//     $("#time_set").append(
//
//     )
// });

const dateInput = document.getElementById("date");
const timeSetBody = document.getElementById("time_set_body");

const data = [
    // {time: "9:00", max: 0, count: 0, block: 0, enable: 0},
    {time: "9:00"},
    {time: "10:00"},
    {time: "11:00"},
    {time: "12:00"},
    {time: "13:00"},
    {time: "14:00"},
    {time: "15:00"},
    {time: "16:00"},
    {time: "17:00"},
    {time: "18:00"},
    {time: "19:00"},
    {time: "20:00"},

];

function updateTable() {
    timeSetBody.innerHTML = "";
    for (let item of data) {
        const tr = document.createElement("tr");
        let time = item.time.split(":")[0];

        tr.innerHTML = `
                    <td>${item.time}</td>
                    <td><input type="text" id="max_${time}"> </td>
                    <td><input type="text" id="count_${time}"></td>
                    <td><input type="text" id="block_${time}"></td>
                    <td><input type="text" id="enable_${time}" readonly></td>
                `;
        timeSetBody.appendChild(tr);
    }
}

dateInput.addEventListener("change", () => {
    updateTable();
});

// Initialize the table with the default date value
updateTable();

function appo_create(){
    let name = $('#hospital_name').val();
    let memo = $('#hospital_announcement').val();
    let count = $('#count').val();
    let max = $('#max').val();


    let date = $('#')
    appo_data = {
        "hospital_name" : name,
        "hospital_announcement" : memo,
        "count" : count,
        "max" : max,
    };

    // $.ajax({
    //     type: "POST",
    //     url: "/api/v1/admin/appo/appo-add",
    //     data: JSON.stringify(appo_data),
    //     contentType: "application/json",
    //
    // }).done(function (result) {
    //     if(result.add === "success") {
    //         alert("병원 정보 업로드 성공!");
    //         // location.href="/";
    //     } else {
    //         alert("업로드 실패..");
    //     }
    // })

}