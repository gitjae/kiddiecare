var childrenPage = 1;

function sectionChange(nav){
    const name = $(nav).attr("name");
    $('.main-div').css("display", "none");
    $(`#${name}`).css("display", "block");
}



function prevChildren(){
    let page = childrenPage - 1;
    if(page < 1) {page = 1}
    getChildren(page);
}

function nextChildren(){
    getChildren(childrenPage + 1);
}




function getChildren(page){
    $.ajax({
        method: 'GET',
        url:`/api/v1/children/list/${page}`
    }).done(res => {
        if(res.children.length == 0){
            return;
        }
        childrenPage = page;
        $('#children').empty();
        res.children.forEach(child => {
            $('#children').append(`
                    <div class="div-child">
                        <div class="info-item">
                            <span class="info-title">자녀이름</span>
                            <span class="child-name" child-no="${child.id}">${child.name}</span>
                        </div>
                        <div class="info-item">
                            <span class="info-title">성별</span>
                            <span class="child-gender">${child.gender % 2 == 0 ? "여아" : "남아"}</span>
                        </div>
                        <div class="info-item">
                            <span class="info-title">생년월일</span>
                            <span class="child-birth">${child.birth}</span>
                        </div>
                        <div class="info-item">
                            <span class="info-title">참고사항</span>
                            <span class="child-info">${child.info}</span>
                        </div>
                        <div class="child-btn">
                            <button onclick="updateChildForm(this)">정보 수정</button>
                            <button onclick="delChildInfo(this)">자녀 정보 지우기</button>
                        </div>
                    </div>
            `);
        })
    })
}




function delChildInfo(btn){
    const no = $(btn).closest('.div-child').find('.child-name').attr('child-no');
    if(confirm('정말로 이 자녀정보를 지우시겠습니까?')){
        $.ajax({
            method:'DELETE',
            url:`api/v1/children/child/${no}`
        }).done(res => {
            if(res.delete === 'success'){
                getChildren(1);
            } else {
                alert('자녀 정보를 지우지 못했습니다.')
            }
        })
    }
}

function updateChildForm(btn){
    const div = $(btn).closest('.div-child');
    const no = $(div).find('.child-name').attr('child-no');
    const name = $(div).find('.child-name').text();
    const gender = $(div).find('.child-gender').text() == '남아' ? true : false;
    const birth = $(div).find('.child-birth').text();
    const info = $(div).find('.child-info').text();

    $(div).empty();
    $(div).append(`
        <div id="form" class="form">
            <div id="div-name">
                <label for="name">이름</label>
                <input type="text" id="name" class="name" name="name" child-no="${no}" value="${name}">
            </div>
            <div id="div-birth">
                <label for="birth">생년월일</label>
                <input type="text" id="birth" class="birth" name="birth" value="${birth}">
            </div>
            <div id="div-gender">
                <label id="label-gender">성별</label>
                <label for="gender-m">남</label>
                <input type="radio" id="gender-m" class="gender" name="gender-${no}" value="m" ${gender ? 'checked' : ''}>
                <label for="gender-f">여</label>
                <input type="radio" id="gender-f" class="gender" name="gender-${no}" value="f" ${!gender ? 'checked' : ''}>
            </div>
            <div id="div-info">
                <label for="info">참고사항</label>
                <input type="text" id="info" class="info" name="info" value="${info}">
            </div>
            <button id="submit" onclick="updateChild(this)">수정하기</button>
            <button onclick="updateChildCancel(this)">취소하기</button>
        </div>
    `)
}

function updateChildCancel(btn){
    cancelChildAjax(btn);
}

function updateChild(btn){
    const div = $(btn).closest('.div-child');
    const no = $(div).find('.name').attr('child-no');
    const name = $(div).find('.name').val();
    const birth = $(div).find('.birth').val();
    const info = $(div).find('.info').val();
    const gender = $(div).find('.gender:checked').val() == 'm' ? 1 : 0
    const data = {
        name:name,
        birth:birth,
        info:info,
        gender:gender
    }

    $.ajax({
        method:'PUT',
        url:`api/v1/children/child/${no}`,
        data:JSON.stringify(data),
        contentType:'application/json; charset=utf-8'
    }).done(res => {
        if(res.update === 'success'){
            cancelChildAjax(btn);
        } else {
            alert("자녀정보 수정에 실패했습니다.");
        }
    })
}

function cancelChildAjax(btn){
    const div = $(btn).closest('.div-child');
    const no = $(div).find('.name').attr('child-no');
    $.ajax({
        method:'GET',
        url:`api/v1/children/child/${no}`
    }).done(res => {
        const child = res.child;
        $(div).empty();
        $(div).append(`
            <p class="child-name" child-no="${child.id}">${child.name}</p>
            <p class="child-gender">${child.gender % 2 == 0 ? "여아" : "남아"}</p>
            <p class="child-birth">${child.birth}</p>
            <p class="child-info">${child.info}</p>
            <div class="child-btn">
                <button onclick="updateChildForm(this)">정보 수정</button>
                <button onclick="delChildInfo(this)">자녀 정보 지우기</button>
            </div>
        `)
    })
}