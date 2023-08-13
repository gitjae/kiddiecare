let selectedChildId = null;

function selectChildren(childrenId) {
    // 이전에 선택된 카드의 selected-card 클래스 제거
    if (selectedChildId) {
        document.querySelector(`.children-card[data-id='${selectedChildId}']`).classList.remove('selected-card');
    }

    // 현재 선택된 카드에 selected-card 클래스 추가
    document.querySelector(`.children-card[data-id='${childrenId}']`).classList.add('selected-card');

    // 선택된 childrenId를 변수에 저장 및 hidden input에 설정
    selectedChildId = childrenId;
    document.getElementById("selectedChildNo").value = childrenId;
}

function renderChildCard(child) {
    const card = `
            <div class="children-card" data-id="${child.id}">
                <h4>${child.name}</h4>
                <p>생년월일: ${child.birth}</p>
                <p>성별: ${child.gender === 1 || child.gender === 3 ? '남자' : '여자'}</p>
                <p>추가 정보: ${child.info}</p>
                <button class="select-children" onclick="selectChildren(${child.id})">선택</button>
            </div>
        `;

    $("#childrenContainer").append(card);
}

// 예약번호용 난수 생성
function generateEightDigitRandom() {
    return Math.floor(10000000 + Math.random() * 90000000);
}

$(document).ready(function () {
    // 약관
    $(".term-content").hide();

    $(".view-btn").click(function (e) {
        e.preventDefault();

        let termContent = $(this).parent().next(".term-content");

        if (termContent.is(":visible")) {
            termContent.slideUp();
        } else {
            $(".term-content").slideUp();
            termContent.slideDown();
        }
    });

    // 부모 정보
    const parentId = $("#parentId").val();
    console.log("parentId : ", parentId);
    // parentId(no)
    let logValue = $("#log").val();   // log 값 가져오기

    if (logValue) {
        $.ajax({
            url: `/api/v1/users/getNoById?id=${logValue}`,
            type: "GET",
            dataType: 'json',
            success: function (response) {
                const actualParentId = response;
                $.ajax({
                    url: `/api/v1/children/getChildrenByParentId?parentId=${actualParentId}`,
                    type: "GET",
                    dataType: 'json',
                    success: function (childrenData) {
                        if (childrenData && childrenData.length > 0) {
                            childrenData.forEach(child => {
                                renderChildCard(child);
                            });
                        } else {
                            $("#childrenContainer").append("<p>정보가 없습니다.</p>");
                        }
                    },
                });
            },
            error: function (err) {
                console.error('Error fetching parentId:', err.responseText);
            }
        });
    } else {
        console.error('log value is missing or invalid.');
    }

    // "결제하기 패스하고 예약꽂기" 버튼 클릭 이벤트
    $("#payBtn").on('click', function (e) {
        e.preventDefault();

        let patientId = $("#selectedChildNo").val();
        let parentId = $("#parentId").val();
        let timeSlotNo = $("#timeSlotNo").val();
        let symptom = $("#symptom").val();
        let note = $("#note").val();
        let appoStatus = 1; // 기본값 설정 (변경 가능)
        let appoNo = generateEightDigitRandom();    // 난수 생성

        console.log("appoNo :", appoNo);
        console.log("patientId : ", patientId);
        console.log("parentId : ", parentId);
        console.log("timeSlotNo : ", timeSlotNo);
        console.log("symptom : ", symptom);
        console.log("note : ", note);

        $.ajax({
            url: "/api/v1/admin/appo",
            type: "POST",
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify({
                no: appoNo,
                patientId: patientId,
                guardian: parentId,
                timeSlotNo: timeSlotNo,
                symptom: symptom,
                note: note,
                appoStatus: appoStatus
            }),
            success: function (response) {
                if (response.success) {
                    window.location.href = `/bookingComplete`;
                } else {
                    alert("예약 실패");
                }
            },
            error: function (err) {
                console.error('Error during appointment booking:', err.responseText);
                alert("예약 중 오류 발생");
            }
        });
    });
});
