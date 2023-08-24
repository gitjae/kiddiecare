<%--
  Created by IntelliJ IDEA.
  User: 채희재
  Date: 2023-08-19
  Time: 오후 8:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="/css/admin-doctorInfo.css">
    <link rel="stylesheet" href="https://unicons.iconscout.com/release/v4.0.0/css/line.css" />
    <script src="/script/admin-doctorInfo.js"></script>
    <title>doctorInfo</title>
</head>
<body>
<div class="form_container">
    <i class="uil uil-times form_close"></i>
    <div class="form doctor_form">
        <form method="POST" onsubmit="return false">
            <h2>의사 추가</h2>
            <div class="input_box">
                <input type="text" name="doctorName" placeholder="의사이름을 입력해주세요." required />
                <i class="uil uil-user"></i>
            </div>
            <div class="input_box">
                <input type="text" name="doctorAverageTimeOfCare" placeholder="진료 평균 시간" required />
                <i class="uil uil-clock"></i>
            </div>
            <div class="option-box">
                <label for="option-area"><i class="uil uil-file-medical"></i>진료 과목</label>
                <select name="subject" id="option-area">
                </select>
            </div>
            <div class="input_box">
                <input type="file" id="doctor-image" name="file" accept=".png,.jpg" size="10000000"/>
                <i class="uil uil-user-square"></i>
            </div>
            <button class="button" onclick="addDoctor()">의사 추가</button>
        </form>
    </div>
</div>
<div class="doctor-info-container">
    <i class="uil uil-times form_close2"></i>
    <div class="form doctor_form">
        <h2>의사 정보 수정</h2>
        <img>
        <input type="hidden" id="doctor-no" name="no" />
        <div class="input_box">
            <input type="text" id="doctor-name" name="doctorName"/>
            <i class="uil uil-user"></i>
        </div>
        <div class="input_box">
            <input type="text" id="doctor-average-time-of-care" name="doctorAverageTimeOfCare" />
            <i class="uil uil-clock"></i>
        </div>
        <div class="input_box">
            <input type="file" id="doctor-image-update" name="file" accept=".png,.jpg" size="10000000"/>
            <i class="uil uil-user-square"></i>
        </div>
        <div class="input_box">
            <input type="text" id="doctor-status" name="doctorStatus"/>
            <i class="uil uil-check"></i>
        </div>
        <div class="btn-area">
            <input type="button" class=" button button-delete" value="삭제" onclick="deleteDoctor()">
            <input type="button" class="button button-update" value="수정" onclick="updateDoctor()">
        </div>
    </div>
</div>
<h1 id="admin-doctor-info-h1">의사 선생님 정보</h1>
<div id="doctor-container">

</div>
</body>
</html>
