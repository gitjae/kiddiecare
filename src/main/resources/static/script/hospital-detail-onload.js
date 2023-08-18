var isLikedHosp = false;
window.onload = function (){
    getHospInfoDetail();
    buildCalendar();
    getTotalInfo();

    $('#booking-btn').prop("disabled", true);
}

