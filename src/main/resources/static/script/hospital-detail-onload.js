window.onload = function (){
    getHospInfoDetail();
    buildCalendar();
    getTotalInfo();
    $('#booking-btn').prop("disabled", true);
}