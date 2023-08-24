var isLikedHosp = false;
window.onload = function (){
    $('#loading').show();
    getHospInfoDetail();
    buildCalendar();
    getTotalInfo();

    $('#booking-btn').prop("disabled", true);
}

