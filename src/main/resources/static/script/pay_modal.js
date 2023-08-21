$(document).ready(function () {
    $("#iamportPayment").click(function () {
        payment();
    });
});

function payment(data) {
    IMP.init('imp40242012'); // 아임포트 관리자의 가맹점 식별코드

    // param
    IMP.request_pay({
        pg : 'kakaopay.TC0ONETIME',
        pay_method : 'card',
        merchant_uid: "order_no_0002", // 상점에서 관리하는 주문 번호로 자동 증가 필요함
        name : '우리동네소아과 예약금 결제',
        amount : 1000,
        buyer_email : 'juntu09@gmail.com',  // 유저 정보로 변경 필요
        buyer_name : '한희수',                // 유저 정보로 변경 필요
        buyer_tel : '01072208935',          // 유저 정보로 변경 필요
    }, function(rsp) { // callback 로직
        if (rsp.success) {
            alert("결제 성공 -> imp_uid : "+rsp.imp_uid+" / merchant_uid(orderKey) : " + rsp.merchant_uid);
        } else {
            alert("결제 실패 : 코드("+rsp.error_code+") / 메시지(" + rsp.error_msg +")");
        }
    });
}