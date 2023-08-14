<%--
    Created by IntelliJ IDEA.
    User: 집
    Date: 2023-08-13
    Time: 오후 12:43
    To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>병원 등록</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="/css/common.css" rel="stylesheet">
    <link rel="stylesheet" href="/css/admin-join.css">
</head>
<body>
<div class="container">
    <section>
        <form method="POST" action="">
            <h1>병원 관계자 회원가입</h1>
            <div class="input-box">
                <input type="text" id="hosp-name" name="hosp-name" placeholder="병원명" autofocus />
                <input type="button" id="hosp-name-btn" name="hosp-name-btn" value="병원 확인" onclick="chkHospName()" />
                <div class="err-box">
                    <span class="err" id="chk-hosp-name" size="1"></span>
                </div>
            </div>
            <div class="input-box">
                <input type="text" id="hosp-address" name="hosp-address" placeholder="병원 주소" readonly />
            </div>
            <div class="input-box">
                <input type="text" id="admin-id" name="admin-id" placeholder="아이디" />
                <div class="err-box">
                    <span class="err" id="chk-admin-id" size="1"></span>
                </div>
            </div>
            <div class="input-box">
                <input type="password" id="admin-pw" name="admin-pw" placeholder="비밀번호" />
                <div class="pwd-err">
                    <p class="err">*특수문자는 '! @ # $ % ^ & +='만 사용 가능합니다.</p>
                    <span class="err" id="chk-admin-password" size="1"></span>
                </div>
            </div>
            <div class="input-box">
                <input type="email" id="admin-email" name="admin-email" placeholder="이메일" />
                <div class="err-box">
                    <ul>
                        <li class="error" id="error-email">이메일을 입력해주세요.</li>
                    </ul>
                    <span class="err" id="chk-email" size="1"></span>
                    <span class="err" id="chkMsgEmail" size="1"></span>
                </div>
            </div>
            <div class="input-box">
                <input type="text" id="admin-name" name="admin-name" placeholder="이름" />
            </div>
            <div class="input-box">
                <label for="admin-attach">의료기관 개설 허가증/의사 면허/사업자 등록증 첨부</label>
                <input type="file" id="admin-attach" name="admin-attach" />
            </div>
            <div class="remember-forget">
                <label><input type="checkbox"> 로그인 정보 기억하기</label>
                <a href="#">비밀번호를 잃어버리셨나요?</a>
            </div>
            <button type="submit">회원 가입</button>
        </form>
    </section>
</div>
</body>
</html>
