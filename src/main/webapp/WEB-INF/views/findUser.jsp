<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>아이디/비밀번호 찾기</title>
    <link href="/css/common.css" rel="stylesheet">
    <link rel="stylesheet" href="/css/join.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
    <script src="/script/find-user.js"></script>
</head>
<body>
<div class="container">
    <section id="main-section">
        <a href="/">
            <h2 class="loginTitle">우리동네소아과</h2>
        </a>
        <div class="wrapper" style="margin-top: 50px">
            <div class="find-div">
                <p class="find-div-subTitle">아이디 찾기</p>
                <div id="find-id">
                    <div class="name-a">
                        <label for="find-id-name">이름</label>
                        <input id="find-id-name">
                    </div>
                    <div class="phone-a">
                        <label for="find-id-phone">전화번호</label>
                        <input id="find-id-phone">
                    </div>
                    <button id="find-id-btn" onclick="findId()">아이디 찾기</button>

                    <div id="find-id-result">
                        <p id="response-id"></p>
                    </div>
                </div>

                <p class="find-div-subTitle">비밀번호 찾기</p>
                <div id="find-password">
                    <div class="id-a">
                        <label for="find-password-id">아이디</label>
                        <input id="find-password-id">
                    </div>
                    <div class="name-a">
                        <label for="find-password-name">이름</label>
                        <input id="find-password-name">
                    </div>
                    <div>
                        <div class="phone-a">
                            <label for="find-password-phone">전화번호</label>
                            <input id="find-password-phone">
                        </div>
                        <button id="send" onclick="sendCode()">인증번호 발송</button>
                    </div>

                    <div id="div-verify">
                        <label for="verify-code">인증번호</label>
                        <input id="verify-code">
                        <button id="verify-btn" onclick="verify()">인증하기</button>
                    </div>

                    <div class="new-password-a" style="display: none">
                        <div id="div-password-new">
                            <label for="password-new">새 비밀번호</label>
                            <input type="password" id="password-new" name="password">
                        </div>
                        <div id="div-password-chk">
                            <label for="password-new-chk">비밀번호확인</label>
                            <input type="password" id="password-new-chk" name="password">
                        </div>
                        <button id="find-password-btn" onclick="findPassword()">비밀번호 찾기</button>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>
</body>
</html>
