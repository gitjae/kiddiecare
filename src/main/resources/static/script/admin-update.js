$(function() {
    const adminInfoBtn = $('.admin-info-btn');
    const adminInfoForm = $('.admin-info-form');
    const adminInfoUpdateBtn = $('.admin-info-update-btn');
    const adminName = $('.admin-name');
    const hospitalName = $('.hospital-name');
    const adminNameInput = $('input[name="adminName"]');
    const hospitalNameInput = $('input[name="hospitalName"]');

    adminInfoBtn.on('click', () => {

        // form 영역 보이기
        adminInfoForm.show();

        // h2, p 태그 값을 input 태그에 넣기
        adminNameInput.val(adminName.text());
        hospitalNameInput.val(hospitalName.text());
    });

    adminInfoUpdateBtn.on('click', (event) => {
        event.preventDefault();

        // input 태그에서 값을 가져오기
        const updatedAdminName = adminNameInput.val();
        const updatedHospitalName = hospitalNameInput.val();

        // h2, p 태그에 업데이트된 값을 넣기
        adminName.text(updatedAdminName);
        hospitalName.text(updatedHospitalName);

        // form 영역 감추기
        adminInfoForm.hide();
    });
});
