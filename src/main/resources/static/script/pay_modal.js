$(document).ready(function () {
    $("#payBtn").click(function () {
        const userId = document.getElementById('loggedInUser').value;
        // AJAX로 사용자의 전화번호 정보 가져오기
        $.ajax({
            url: "/api/v1/users/findPhone",
            method: "GET",
            data: { id: userId },
            success: function(phone) {
                payment(phone);
            },
            error: function(err) {
                alert("전화번호를 가져오는데 실패했습니다.");
            }
        });
    });
});

// 주문번호 1씩 증가
function generateMerchantUid() {
    const currentDate = new Date();
    const timestamp = currentDate.getTime();
    return "order_no_" + timestamp;
}

function payment(phone) {
    IMP.init('imp40242012'); // 아임포트 관리자의 가맹점 식별코드

    const merchantUid = generateMerchantUid(); // 동적으로 merchant_uid 생성
    const userName = document.getElementById('loggedInUser').value;

    // param
    IMP.request_pay({
        pg : 'kakaopay.TC0ONETIME',
        pay_method : 'card',
        merchant_uid: merchantUid,
        name : '우리동네소아과 예약금 결제',
        amount : 2000,
        buyer_email : 'juntu09@gmail.com',  // 유저 정보로 변경 필요
        buyer_name : userName,
        buyer_tel : phone,
    }, function(rsp) { // callback 로직
        if (rsp.success) {
            // alert("결제 성공 -> imp_uid : "+rsp.imp_uid+" / merchant_uid(orderKey) : " + rsp.merchant_uid);
            location.href = "/bookingComplete"
        } else {
            alert("결제 실패 : 코드("+rsp.error_code+") / 메시지(" + rsp.error_msg +")");
        }
    });
}