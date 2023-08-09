window.onload = function() {
    let urlParams = new URLSearchParams(window.location.search);

    // let ykiho = urlParams.get('ykiho');
    let treatmentDate = urlParams.get('treatmentDate');
    let treatmentDay = urlParams.get('treatmentDay');

    document.getElementById('treatmentDate').value = treatmentDate;
    document.getElementById('treatmentDay').value = treatmentDay;

    console.log("userName : ", userName);   // 수정 필요
};
