function selectChildren(childrenId) {
    document.getElementById("selectedChildNo").value = childrenId;
}

function renderChildCard(child) {
    const card = `
            <div class="children-card">
                <h4>${child.name}</h4>
                <p>생년월일: ${child.birth}</p>
                <p>성별: ${child.gender === 1 || child.gender === 3 ? '남자' : '여자'}</p>
                <p>추가 정보: ${child.info}</p>
                <button class="select-children" onclick="selectChildren(${child.no})">선택</button>
            </div>
        `;

    $("#childrenContainer").append(card);
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

    // 자녀 정보
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

        let childrenId = $("#selectedChildNo").val();
        let guardianId = $("#log").val();
        let timeSlotNo = $("#timeSlotNo").val();
        let symptom = $("#symptom").val();
        let note = $("#note").val();
        let appoStatus = 1; // 기본값 설정 (변경 가능)

        $.ajax({
            url: "api/v1/admin/appo",
            type: "POST",
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify({
                patientId: childrenId,
                guardian: guardianId,
                timeSlotNo: timeSlotNo,
                symptom: symptom,
                note: note,
                appoStatus: appoStatus
            }),
            success: function (response) {
                if (response.success) {
                    window.location.href = "/bookingComplete.jsp";
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
