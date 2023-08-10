window.onload = function() {
    let urlParams = new URLSearchParams(window.location.search);

    // let ykiho = urlParams.get('ykiho');
    let treatmentDate = urlParams.get('treatmentDate');
    let treatmentDay = urlParams.get('treatmentDay');

    document.getElementById('treatmentDate').value = treatmentDate;
    document.getElementById('treatmentDay').value = treatmentDay;

    // 자녀 정보
    let container = document.getElementById('childrenContainer');

    if (childrenData && childrenData.length > 0) {
        childrenData.forEach(child => {
            let childDiv = document.createElement('div');
            childDiv.className = 'children-card';

            let name = document.createElement('h4');
            name.textContent = child.name;
            childDiv.appendChild(name);

            let birth = document.createElement('p');
            birth.textContent = "생년월일: " + child.birth;
            childDiv.appendChild(birth);

            let gender = document.createElement('p');
            gender.textContent = "성별: " + (child.gender == 1 || child.gender == 3 ? '남자' : '여자');
            childDiv.appendChild(gender);

            let info = document.createElement('p');
            info.textContent = "추가 정보: " + child.info;
            childDiv.appendChild(info);

            let button = document.createElement('button');
            button.className = 'select-children';
            button.textContent = '선택';
            button.onclick = function() {
                selectChildren(child.no);
            };
            childDiv.appendChild(button);

            container.appendChild(childDiv);
        });
    }

    // 약관
    $(".term-content").hide();

    $(".view-btn").click(function(e) {
        e.preventDefault();

        let termContent = $(this).parent().next(".term-content");

        if (termContent.is(":visible")) { // 해당 약관 내용이 이미 보이는 상태인지 확인
            termContent.slideUp();
        } else {
            $(".term-content").slideUp();  // 다른 약관 내용들은 숨기기
            termContent.slideDown();      // 클릭한 약관의 내용만 보이게 하기
        }
    });
};
