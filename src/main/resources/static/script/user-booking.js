function selectChildren(childNo) {
    document.getElementById("selectedChildNo").value = childNo;
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

$(document).ready(function() {
    // 약관
    $(".term-content").hide();

    $(".view-btn").click(function(e) {
        e.preventDefault();

        let termContent = $(this).parent().next(".term-content");

        if (termContent.is(":visible")) {
            termContent.slideUp();
        } else {
            $(".term-content").slideUp();
            termContent.slideDown();
        }
    });

    // 진료일
    let urlParams = new URLSearchParams(window.location.search);

    // let ykiho = urlParams.get('ykiho');
    let treatmentDate = urlParams.get('treatmentDate');
    let treatmentDay = urlParams.get('treatmentDay');

    document.getElementById('treatmentDate').value = treatmentDate;
    document.getElementById('treatmentDay').value = treatmentDay;

    // 자녀 정보
    const parentId = $("#parentId").val();
    console.log("parentId : ", parentId);

    $.ajax({
        url: `/appointment/getChildrenByParentId?parentId=${parentId}`,
        type: "GET",
        dataType: 'json',
        success: function(childrenData) {
            $('#childrenList').empty();  // 기존에 추가된 자녀 정보 삭제
            if (childrenData && childrenData.length > 0) {
                childrenData.forEach(child => {
                    renderChildCard(child);
                });
            }
        },
        error: function(err) {
            console.error('Error fetching children data:', err.responseText);
        }
    });
});
